package com.printway.client.paypalgateway;

import com.printway.client.BaseRestTemplate;
import com.printway.client.paypalgateway.input.AuthSeller;
import com.printway.client.paypalgateway.response.CaptureOrderResponse;
import com.printway.client.paypalgateway.response.GetCredentialResponse;
import com.printway.client.paypalgateway.response.LinksResponse;
import com.printway.client.paypalgateway.response.OrderCreateResponse;
import com.printway.entity.db.Order;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaypalGateway extends BaseRestTemplate {
    private String baseUrl = "http://localhost:8088/paypal/v1/";
    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     * tuanlv
     * Create an order to paypal
     */
    public OrderCreateResponse createOrder(List<Order> orderList, int id) {
        OrderCreateResponse order = null;
        HttpEntity<List<Order>> request = new HttpEntity<>(orderList);
        try {
            order = getRestTemplate().postForObject(baseUrl + "create/" + id, request, OrderCreateResponse.class);
//            order = postForObject( Order.class,baseUrl + "create", request);
            logger.info("Order Id: " + order.getId());
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        return order;
    }

    /**
     * tuanlv
     * Capture order amount
     */

    public CaptureOrderResponse captureOrder(String orderId) {
        CaptureOrderResponse captureOrderResponse = null;
        HttpEntity<String> request = new HttpEntity<>(orderId);
        try {
            captureOrderResponse = getRestTemplate().postForObject(baseUrl + "capture/" + orderId, request, CaptureOrderResponse.class);
            logger.info("Order Id: " + captureOrderResponse.getOrderId());
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        return captureOrderResponse;
    }

    /**
     * tuanlv
     * Get Link onboard
     */

    public LinksResponse getLinkOnboard(String sellerNonce) {
        LinksResponse linksResponse = null;
        HttpEntity<String> request = new HttpEntity<>("");
        try {
            linksResponse = getRestTemplate().postForObject(baseUrl + "get-link-signup/" + sellerNonce, request, LinksResponse.class);
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        return linksResponse;
    }

    public GetCredentialResponse getAuth(String sellerNonce, AuthSeller authSeller) {
        GetCredentialResponse response = null;
        HttpEntity<AuthSeller> request = new HttpEntity<>(authSeller);
        try {
            response = getRestTemplate().postForObject(baseUrl + "get-authen/" + sellerNonce, request, GetCredentialResponse.class);
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        return response;
    }
}
