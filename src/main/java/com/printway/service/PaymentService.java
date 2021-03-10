package com.printway.service;

import com.paypal.http.HttpResponse;
import com.paypal.payouts.*;
import com.paypal.payouts.Currency;
import com.printway.client.paypalgateway.PaypalGateway;
import com.printway.client.paypalgateway.auth.Credential;
import com.printway.client.paypalgateway.input.AuthSeller;
import com.printway.client.paypalgateway.response.CaptureOrderResponse;
import com.printway.client.paypalgateway.response.GetCredentialResponse;
import com.printway.client.paypalgateway.response.LinksResponse;
import com.printway.client.paypalgateway.response.OrderCreateResponse;
import com.printway.client.stripegateway.StripeGateway;
import com.printway.client.stripegateway.response.PaymentResponse;
import com.printway.entity.db.Order;
import com.printway.entity.db.PaymentConfig;
import com.printway.entity.db.TblUserEntity;
import com.printway.entity.db.Transaction;
import com.printway.utils.StringUtils;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.SetupIntent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class PaymentService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private PaypalGateway paypalGateway;
    @Autowired
    private PaymentConfigService paymentConfigService;
    @Autowired
    private StripeGateway stripeGateway;

    private final Logger logger = Logger.getLogger(this.getClass());

    @Transactional
    public OrderCreateResponse createOrderPaypal(List<Order> orderList, String userId) {
        Transaction transaction = new Transaction();
        Transaction updateTransaction = transactionService.create(transaction);
        double totalAmount = 0;
        for (Order o : orderList) {
            totalAmount += o.getOrdPrice();
            o.setUserId(Integer.parseInt(userId));
//            o.setTransactionId(String.valueOf(updateTransaction.getId()));
            orderService.create(o);
        }
        transaction.setAmount(totalAmount);
        transaction.setStatus("Unpaid");
        OrderCreateResponse createResponse = paypalGateway.createOrder(orderList, updateTransaction.getId());
        transaction.setOrderReturnedId(createResponse.getId());
        transactionService.updateTransaction(updateTransaction);
        return createResponse;
    }

    @Transactional
    public Transaction captureOrderPaypal(String transactionId) {
        Transaction transaction = transactionService.findById(Integer.valueOf(transactionId));
        String orderId = transaction.getOrderReturnedId();
        CaptureOrderResponse response = paypalGateway.captureOrder(orderId);
        if (response.getStatus().equals("COMPLETED")) {
            transaction.setStatus("Completed");
            List<Order> orderList = orderService.findByTranId(transactionId);
            for (Order o : orderList) {
                o.setOrderStatus(0);
            }
            orderService.updateListOrder(orderList);
            transactionService.updateTransaction(transaction);
            return transaction;
        }
        return transaction;
    }

    @Transactional
    public String getLinkOnboardPaypal(String userId) {
        String result = null;
        PaymentConfig paymentConfig = new PaymentConfig();
        paymentConfig.setUserId(Integer.parseInt(userId));
        String sellerNonce = StringUtils.randomString();
        paymentConfig.setSellerNonce(sellerNonce);
        paymentConfigService.create(paymentConfig);
        try {
            LinksResponse linksResponse = paypalGateway.getLinkOnboard(sellerNonce);
            result = linksResponse.getLinks().get(1).getHref();

        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        return result;
    }

    @Transactional
    public GetCredentialResponse getCredentialPaypal(AuthSeller authSeller, String userId) {
        PaymentConfig paymentConfig = paymentConfigService.getByUserId(Integer.parseInt(userId));
        GetCredentialResponse response = paypalGateway.getAuth(paymentConfig.getSellerNonce(), authSeller);
        paymentConfig.setClientId(response.getClient_id());
        paymentConfig.setClientSecret(response.getClient_secret());
        paymentConfig.setPaymentType("PAYPAL");
        paymentConfigService.updatePaymentConfig(paymentConfig);
        return response;
    }

    public HttpResponse<CreatePayoutResponse> createPayoutPaypal(List<Order> orderList, PaymentConfig paymentConfig) throws IOException {
        PayoutsPostRequest request = buildBodyPaypal(orderList);
        Credential credential = new Credential();
        HttpResponse<CreatePayoutResponse> response = credential.getClient(paymentConfig.getClientId(), paymentConfig.getClientSecret()).execute(request);
        return response;
    }

    private PayoutsPostRequest buildBodyPaypal(List<Order> orderList) {
        List<PayoutItem> items = new ArrayList<>();
        double amount = 0;
        for (Order o : orderList) {
            amount += o.getOrdPrice();
        }
        items.add(new PayoutItem().senderItemId(StringUtils.randomString()).note("Pay for new orders")
                .receiver("tuan2-facilitator@mbox.re").amount(new Currency().currency("USD").value(String.valueOf(amount))));
        CreatePayoutRequest payoutRequest = new CreatePayoutRequest().senderBatchHeader(new SenderBatchHeader().senderBatchId("Test" + new Random().nextInt(5000))
                .emailMessage("Pay for new orders").emailMessage("Payment for Order").note("Enjoy your sale").recipientType("EMAIL"))
                .items(items);
        return new PayoutsPostRequest().requestBody(payoutRequest);
    }

    public HttpResponse<PayoutBatch> getPayoutBatchPaypal(String batchId, PaymentConfig paymentConfig) {
        HttpResponse<PayoutBatch> response = null;
        PayoutsGetRequest request = new PayoutsGetRequest(batchId).page(1).pageSize(10).totalRequired(true);
        Credential credential = new Credential();
        try {
            response = credential.getClient(paymentConfig.getClientId(), paymentConfig.getClientSecret()).execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public PaymentResponse createPaymentIntentStripe(String userId, List<Order> orderList) {
        Transaction transaction = new Transaction();
        Transaction updateTransaction = transactionService.create(transaction);
        Long totalAmount = 0L;
        for (Order o : orderList) {
            totalAmount += Double.valueOf(o.getOrdPrice()).longValue();
            o.setUserId(Integer.parseInt(userId));
//            o.setTransactionId(String.valueOf(updateTransaction.getId()));
            orderService.create(o);
        }
        transaction.setAmount(totalAmount);
        transaction.setStatus("Unpaid");
        transactionService.updateTransaction(transaction);
        PaymentIntent paymentIntent = stripeGateway.createPaymentIntent(orderList);
        PaymentResponse paymentResponse = new PaymentResponse(paymentIntent.getClientSecret(), paymentIntent.getId(), String.valueOf(transaction.getId()));
        return paymentResponse;
    }

    public PaymentIntent completedPaymentStripe(String paymentIntentId, String transactionId) {
        PaymentIntent paymentIntent = stripeGateway.retrievePaymentIntent(paymentIntentId);
        if (paymentIntent.getStatus().equals("succeeded")) {
            Transaction transaction = transactionService.findById(Integer.valueOf(transactionId));
            transaction.setStatus("Completed");
            List<Order> orderList = orderService.findByTranId(transactionId);
            for (Order o : orderList) {
                o.setOrderStatus(0);
            }
            orderService.updateListOrder(orderList);
            transactionService.updateTransaction(transaction);
        }
        return paymentIntent;
    }

    public PaymentResponse createSetupIntentStripe(String userId) {
        PaymentConfig paymentConfig = new PaymentConfig();
        paymentConfig.setUserId(Integer.parseInt(userId));
        Customer customer = stripeGateway.createCustomer();
//        paymentConfig.setCusStripeId(customer.getId());
        SetupIntent setupIntent = stripeGateway.createSetupIntent(customer.getId());
//        paymentConfig.setSetUpIntentId(setupIntent.getId());
        PaymentConfig updatePmcf = paymentConfigService.create(paymentConfig);
        PaymentResponse paymentResponse = new PaymentResponse();
//        paymentConfig.setCusStripeId(customer.getId());
//        paymentConfig.setSetUpIntentId(setupIntent.getId());
        Map<String, Object> data = new HashMap<>();
        data.put("payment_config_id", updatePmcf.getId());
        paymentResponse.setClientSecret(setupIntent.getClientSecret());
        paymentResponse.setData(data);
        return paymentResponse;
    }
}
