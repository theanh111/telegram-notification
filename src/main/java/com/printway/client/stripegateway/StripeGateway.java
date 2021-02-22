package com.printway.client.stripegateway;

import com.printway.client.BaseRestTemplate;
import com.printway.entity.db.Order;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.SetupIntent;
import com.stripe.net.ApiResource;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StripeGateway extends BaseRestTemplate {

    private String baseUrl = "http://localhost:8088/stripe/v1/";
    private final Logger logger = Logger.getLogger(this.getClass());


    public PaymentIntent createPaymentIntent(List<Order> orderList) {
        PaymentIntent paymentIntent = null;
        String result = null;
        HttpEntity<List<Order>> request = new HttpEntity<>(orderList);
        try {
            result = getRestTemplate().postForObject(baseUrl + "/create", request, String.class);
            paymentIntent = ApiResource.GSON.fromJson(result, PaymentIntent.class);
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        return paymentIntent;
    }

    public SetupIntent createSetupIntent(String cusId) {
        SetupIntent setupIntent = null;
        String result = null;
        HttpEntity<String> request = new HttpEntity<>(cusId);
        try {
            result = getRestTemplate().postForObject(baseUrl + "/setup-intent", request, String.class);
            setupIntent = ApiResource.GSON.fromJson(result, SetupIntent.class);
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        return setupIntent;
    }

    public SetupIntent cancelSetupIntent(String cusId) {
        String result = null;
        SetupIntent setupIntent = null;
        HttpEntity<String> request = new HttpEntity<>(cusId);
        try {
            result = getRestTemplate().postForObject(baseUrl + "/cancel-setup-intent", request, String.class);
            setupIntent = ApiResource.GSON.fromJson(result, SetupIntent.class);
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        return setupIntent;
    }

    public PaymentIntent retrievePaymentIntent(String paymentIntentId) {
        String result = null;
        PaymentIntent paymentIntent = null;
        HttpEntity<String> request = new HttpEntity<>(paymentIntentId);
        try {
            result = getRestTemplate().postForObject(baseUrl + "/retrieve-payment-intent", request, String.class);
            paymentIntent = ApiResource.GSON.fromJson(result, PaymentIntent.class);
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        return paymentIntent;
    }

    public Customer createCustomer() {
        String result = null;
        Customer customer = null;
        HttpEntity<String> request = new HttpEntity<>("");
        try {
            result = getRestTemplate().postForObject(baseUrl + "/create-customer", request, String.class);
            customer = ApiResource.GSON.fromJson(result, Customer.class);
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        return customer;
    }

}
