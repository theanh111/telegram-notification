package com.printway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.printway.client.amazongateway.AmazonGateway;
import com.printway.client.amazongateway.reponse.orders.*;
import com.printway.entity.db.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class AmazonOrderService {
    @Autowired
    private AmazonGateway amazonGateway;

    @Autowired
    private OrderService orderService;

    public void getOrders(InputGetOrders inputGetOrders) throws JsonProcessingException, Exception {
        GetOrdersResponse ordersResponse = amazonGateway.getOrders(inputGetOrders);
        for (int i = 0; i < ordersResponse.getPayload().getOrders().size(); i++) {
            String amazonOrderId = ordersResponse.getPayload().getOrders().get(i).getAmazonOrderId();
            GetOrderItemsResponse getOrderItemsResponse = amazonGateway.getOrderItems(amazonOrderId, null);
            GetOrderAddressResponse getOrderAddressResponse = amazonGateway.getOrderAddress(amazonOrderId);
            GetOrderResponse getOrderResponse = amazonGateway.getSingleOrder(amazonOrderId);
            GetOrderBuyerInfoResponse getOrderBuyerInfoResponse = amazonGateway.getOrderBuyerOrderInfo(amazonOrderId);
            Date localDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int j = 0; j < getOrderItemsResponse.getPayload().getOrderItems().size(); j++) {
                System.out.println("Amazon Order ID: " + ordersResponse.getPayload().getOrders().get(i).getAmazonOrderId());
                Order order = new Order();
                order.setOrderId(ordersResponse.getPayload().getOrders().get(i).getAmazonOrderId());
                order.setDateOrder(ordersResponse.getPayload().getOrders().get(i).getPurchaseDate());
                OrderItem orderItem = getOrderItemsResponse.getPayload().getOrderItems().get(j);
                Address orderItemAddress = getOrderAddressResponse.getPayload().getShippingAddress();
                com.printway.client.amazongateway.reponse.orders.Order orderInfo = getOrderResponse.getPayload();
                OrderBuyerInfo orderBuyerInfo = getOrderBuyerInfoResponse.getPayload();
//                String[] titleList = getOrderItemsResponse.getPayload().getOrderItems().get(j).getTitle().split(",");
                if (!orderInfo.getOrderStatus().equals("CANCELED")) {
                    String currentDate = formatter.format(localDate);
                    order.setDateTimeEntered(formatter.parse(currentDate));
                    order.setShippingEmail(orderBuyerInfo.getBuyerEmail());
                    order.setProductName(orderItem.getTitle());
                    order.setSku(orderItem.getASIN() + "/" + orderItem.getSellerSKU());
                    order.setUnfulfillQuantity(orderItem.getQuantityOrdered());
                    order.setShippingCity(orderItemAddress.getCity());
                    order.setShippingCountryCode(orderItemAddress.getCountryCode());
                    order.setShippingZip(orderItemAddress.getPostalCode());
                    order.setShippingProvince(orderItemAddress.getStateOrRegion());
                    if (orderInfo.getOrderStatus().equals("SHIPPED")) {
                        order.setOrderStatus(0);
                    }
                    order.setOrderStoreId(orderInfo.getSellerOrderId());
                    order.setOrdStoreInfomation("{\"store_id\":7,\"store_name\":\"Amazon\",\"platform\":\"Amazon\",\"supplier\":\"scalable\"}");
                    orderService.create(order);
                    System.out.println("Done order: " + amazonOrderId);
                } else {
                    System.out.println(" == This order is Canceled from Amazon! ==");
                }
            }
        }
    }
}
