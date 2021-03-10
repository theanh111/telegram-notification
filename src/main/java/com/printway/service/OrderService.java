package com.printway.service;

import com.printway.entity.db.Order;
import com.printway.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements BasicService<Order>{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order findById(Integer id) {
        return orderRepository.findOne(Long.valueOf(id));
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    public Order create(Order order){
        return  orderRepository.insert(order);
    }
    public List<Order> findByTranId(String tranId){
        return orderRepository.findByTranId(tranId);
    }
    public List<Order> updateListOrder(List<Order> orderList){
        return orderRepository.update(orderList);
    }
    public Order updateOrder(Order order){
        return orderRepository.update(order);
    }
    public List<Order> getListOrderByStatus(String status){
        return orderRepository.findByStatus(status);
    }
}
