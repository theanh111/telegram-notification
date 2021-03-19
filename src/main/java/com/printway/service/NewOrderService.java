package com.printway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.printway.entity.NewOrder;
import com.printway.repository.NewOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NewOrderService {
    @Autowired
    private NewOrderRepository newOrderRepository;

    public List<Integer> findNewestOrder(Date oneHourBack, Date currentDate) throws JsonProcessingException {
        return newOrderRepository.findNewestOrder(oneHourBack, currentDate);
    }

//    public List<NewOrder> findNewestOrder(Date currentDate, Date oneHourBack) throws JsonProcessingException {
//        return newOrderRepository.findNewestOrder(currentDate, oneHourBack);
//    }

    public List<Integer> countOrder(String currentDate) {
        return newOrderRepository.countOrder(currentDate);
    }

    public List<Integer> countPrintQuantity(String currentDate) {
        return newOrderRepository.countPrintQuantity(currentDate);
    }

    public List<Integer> countPrintedQuantity(String currentDate) {
        return newOrderRepository.countPrintedQuantity(currentDate);
    }

    public List<Integer> countPackagedQuantity(String currentDate) {
        return newOrderRepository.countPackagedQuantity(currentDate);
    }

    public List<Integer> countErrorQuantityIn(String currentDate) {
        return newOrderRepository.countErrorQuantityIn(currentDate);
    }
}
