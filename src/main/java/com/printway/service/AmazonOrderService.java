package com.printway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.printway.client.amazongateway.AmazonGateway;
import com.printway.client.amazongateway.reponse.orders.*;
import com.printway.entity.db.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmazonOrderService {
    @Autowired
    private AmazonGateway amazonGateway;

    @Autowired
    private OrderService orderService;

    public GetOrdersResponse getOrders(InputGetOrders inputGetOrders) throws JsonProcessingException {
        GetOrdersResponse ordersResponse = amazonGateway.getOrders(inputGetOrders);
        for (int i = 0; i < ordersResponse.getPayload().getOrders().size(); i++) {
            Order order = new Order();
            order.setOrderId(ordersResponse.getPayload().getOrders().get(i).getAmazonOrderId());
            order.setDateOrder(ordersResponse.getPayload().getOrders().get(i).getPurchaseDate());
            GetOrderItemsResponse getOrderItemsResponse = amazonGateway.getOrderItems(ordersResponse.getPayload().getOrders().get(i).getAmazonOrderId(), null);
            for (int j = 0; j < getOrderItemsResponse.getPayload().getOrderItems().size(); j++) {
                order.setProductName(getOrderItemsResponse.getPayload().getOrderItems().get(j).getTitle());
                System.out.println(System.currentTimeMillis());
            }
            orderService.create(order);
        }
        return ordersResponse;
    }
}
