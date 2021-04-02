package com.printway.service;

import com.printway.repository.NewOrderRepository;

public class NewOrderService {
    private NewOrderRepository newOrderRepository = new NewOrderRepository();

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