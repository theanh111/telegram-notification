package com.printway.client.paypalgateway.auth;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credential {
    private String clientId;
    private String clientSecret;

    public PayPalHttpClient getClient(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        PayPalEnvironment payPalEnvironment = new PayPalEnvironment.Sandbox(clientId, clientSecret);
        PayPalHttpClient client = new PayPalHttpClient(payPalEnvironment);
        return client;
    }
}
