package com.printway.repository;

import com.printway.entity.db.PaymentConfig;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PaymentConfigRepository extends BaseRepository<PaymentConfig, Integer> {
    public PaymentConfigRepository() {
        super(PaymentConfig.class);
    }

    public PaymentConfig getByUserId(Integer userId) {
        String query = "select * from tblpayment_config where user_id = :userId";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return queryOne(query, true, params);
    }
    public PaymentConfig getByUserIdAndPaymentType(Integer userId, String paymentType) {
        String query = "select * from tblpayment_config where user_id = :userId and payment_type = :paymentType";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("paymentType", paymentType);
        return queryOne(query, true, params);
    }
}
