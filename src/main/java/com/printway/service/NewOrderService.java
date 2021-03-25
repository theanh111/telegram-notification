package com.printway.service;

import com.printway.repository.NewOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewOrderService {
    @Autowired
    private NewOrderRepository newOrderRepository;
    public Integer findNewestOrder(String oneHourBack, String currentDate) {
        return newOrderRepository.findNewestOrder(oneHourBack, currentDate);
    }
    public Integer countOrder(String currentDate) {
        return newOrderRepository.countOrder(currentDate);
    }
    public Integer countPrintQuantity(String currentDate) {
        return newOrderRepository.countPrintQuantity(currentDate);
    }
    public Integer countPrintedQuantity(String currentDate) {
        return newOrderRepository.countPrintedQuantity(currentDate);
    }
    public Integer countPackagedQuantity(String currentDate) {
        return newOrderRepository.countPackagedQuantity(currentDate);
    }
    public Integer countErrorQuantityIn(String currentDate) {
        return newOrderRepository.countErrorQuantityIn(currentDate);
    }
}
