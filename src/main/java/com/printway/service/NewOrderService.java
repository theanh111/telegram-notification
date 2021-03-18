package com.printway.service;

import com.printway.entity.NewOrder;
import com.printway.repository.NewOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class NewOrderService implements BasicService<NewOrder>{
    @Autowired
    private NewOrderRepository newOrderRepository;


    @Override
    public NewOrder findById(Integer id) {
        return newOrderRepository.findOne(id);
    }

    @Override
    public NewOrder update(NewOrder newOrder) {
        return null;
    }

    @Override
    public NewOrder create(NewOrder newOrder) {
        return null;
    }

    public List<Integer> countOrder(String currentDate) throws ParseException {
        return newOrderRepository.countOrder(currentDate);
    }

    public List<Integer> countPrintQuantity(String currentDate) throws ParseException {
        return newOrderRepository.countPrintQuantity(currentDate);
    }

    public List<Integer> countPrintedQuantity(String currentDate) throws ParseException {
        return newOrderRepository.countPrintedQuantity(currentDate);
    }

    public List<Integer> countPackagedQuantity(String currentDate) throws ParseException {
        return newOrderRepository.countPackagedQuantity(currentDate);
    }

    public List<Integer> countErrorQuantityIn(String currentDate) throws ParseException {
        return newOrderRepository.countErrorQuantityIn(currentDate);
    }
}
