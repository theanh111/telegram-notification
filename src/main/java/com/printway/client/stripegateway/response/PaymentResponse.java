package com.printway.client.stripegateway.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String clientSecret;
    private String paymentIntentId;
    private String transactionId;
    private Map<String, Object> data;

    public PaymentResponse(String clientSecret, String paymentIntentId, String transactionId) {
        this.clientSecret = clientSecret;
        this.paymentIntentId = paymentIntentId;
        this.transactionId = transactionId;
    }
}