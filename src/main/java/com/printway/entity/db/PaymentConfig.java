package com.printway.entity.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name = "tblpayment_config")
public class PaymentConfig {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "payment_type")
    private String paymentType;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "client_secret")
    private String clientSecret;
    @Column(name = "seller_nonce")
    private String sellerNonce;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "customer_id_stripe")
    private String cusStripeId;
    @Column(name = "setup_intent_id_stripe")
    private String setUpIntentId;

}
