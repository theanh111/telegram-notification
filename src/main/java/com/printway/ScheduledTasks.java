package com.printway;


import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.payouts.CreatePayoutResponse;
import com.paypal.payouts.PayoutBatch;
import com.printway.entity.db.Order;
import com.printway.entity.db.PaymentConfig;
import com.printway.entity.db.Transaction;
import com.printway.service.*;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodListParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentConfigService paymentConfigService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Value("${stripe.secret}")
    private String secret;

    private final Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    //    @Scheduled(fixedDelay = 1, initialDelay = 10000)
    public void autoPayPaypal() {
        List<Order> ordersUnpaid = orderService.getListOrderByStatus("Unpaid");
        Map<Integer, List<Order>> sortList = ordersUnpaid.stream().collect(Collectors.groupingBy(Order::getUserId));
        for (Map.Entry entry : sortList.entrySet()) {
            List<Order> orderList = (List<Order>) entry.getValue();
            int userId = orderList.get(0).getUserId();
            Transaction transaction = transactionService.create(new Transaction());
            double totalAmount = 0;
            for (Order order : orderList) {
                order.setTransactionId(String.valueOf(transaction.getId()));
                totalAmount += order.getAmount();
            }
            transaction.setAmount(totalAmount);
            transactionService.updateTransaction(transaction);
            PaymentConfig paymentConfig = paymentConfigService.getByUserId(userId);
            if (paymentConfig.getClientId() != null && paymentConfig.getClientSecret() != null && paymentConfig.getPaymentType().equals("PAYPAL")) {
                try {
                    HttpResponse<CreatePayoutResponse> response = paymentService.createPayoutPaypal(orderList, paymentConfig);
                    Thread.sleep(10000);
                    HttpResponse<PayoutBatch> payoutBatchHttpResponse = paymentService.getPayoutBatchPaypal(response.result().batchHeader().payoutBatchId(), paymentConfig);
                    if (payoutBatchHttpResponse.result().batchHeader().batchStatus().equals("SUCCESS")) {
                        if (payoutBatchHttpResponse.result().items().get(0).transactionStatus().equals("SUCCESS")) {
                            transaction.setStatus("Completed");
                            for (Order o : orderList) {
                                o.setStatus("Paid");
                            }
                            transactionService.updateTransaction(transaction);
                            orderService.updateListOrder(orderList);
                        }
                    }
                } catch (HttpException | InterruptedException e) {
                    logger.info(e);
                } catch (IOException e) {
                    logger.info(e);
                }
            }

        }
    }

    @Scheduled(fixedDelay = 1, initialDelay = 10000)
    public void autoPayStripe() throws StripeException {
        List<Order> ordersUnpaid = orderService.getListOrderByStatus("Unpaid");
        Map<Integer, List<Order>> sortList = ordersUnpaid.stream().collect(Collectors.groupingBy(Order::getUserId));
        for (Map.Entry entry : sortList.entrySet()) {
            List<Order> orderList = (List<Order>) entry.getValue();
            int userId = orderList.get(0).getUserId();
            Transaction transaction = transactionService.create(new Transaction());
            double totalAmount = 0;
            for (Order order : orderList) {
                order.setTransactionId(String.valueOf(transaction.getId()));
                totalAmount += order.getAmount();
            }
            transaction.setAmount(totalAmount);
            transactionService.updateTransaction(transaction);
            PaymentConfig paymentConfig = paymentConfigService.getByUserIdAndPaymentType(userId, "STRIPE");
            Stripe.apiKey = secret;
            PaymentMethodListParams params = PaymentMethodListParams.builder()
                    .setCustomer(paymentConfig.getCusStripeId()).setType(PaymentMethodListParams.Type.CARD).build();
            PaymentMethodCollection collection = PaymentMethod.list(params);

            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                    .setAmount(Double.valueOf(totalAmount).longValue()).setCurrency("usd").setPaymentMethod(collection.getData().get(0).getId())
                    .setCustomer(paymentConfig.getCusStripeId()).setConfirm(true).setOffSession(true).build();
            try {
                PaymentIntent paymentIntent = PaymentIntent.create(createParams);
                if (paymentIntent.getStatus().equals("succeeded")) {
                    transaction.setStatus("Completed");
                    for (Order o : orderList) {
                        o.setStatus("Paid");
                    }
                    transactionService.updateTransaction(transaction);
                    orderService.updateListOrder(orderList);
                }
            } catch (CardException e) {
                if (e.getDeclineCode().equals("insufficient_funds")){
                    emailSenderService.sentInsufficientFundsMail();
                }else if(e.getDeclineCode().equals("authentication_required")){
                    emailSenderService.sentChangePaymentMethod();
                }
                System.out.println("Err code : " + e.getCode());
            }
        }
    }
}
