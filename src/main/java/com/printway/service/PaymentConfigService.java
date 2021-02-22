package com.printway.service;

import com.printway.entity.db.PaymentConfig;
import com.printway.repository.PaymentConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentConfigService {
    @Autowired
    private PaymentConfigRepository paymentConfigRepository;

    public PaymentConfig updatePaymentConfig(PaymentConfig paymentConfig) {
        return paymentConfigRepository.update(paymentConfig);
    }

    public PaymentConfig create(PaymentConfig paymentConfig) {
        return paymentConfigRepository.insert(paymentConfig);
    }

    public PaymentConfig getByUserId(Integer userId) {
        return paymentConfigRepository.getByUserId(userId);
    }

    public PaymentConfig getById(Integer userId) {
        return paymentConfigRepository.findOne(userId);
    }
    public PaymentConfig getByUserIdAndPaymentType(Integer userId, String paymentType) {
        return paymentConfigRepository.getByUserIdAndPaymentType(userId, paymentType);
    }
}
