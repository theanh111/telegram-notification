package com.printway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.printway.client.amazongateway.AmazonGateway;
import com.printway.client.amazongateway.reponse.orders.*;
import com.printway.entity.db.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AmazonOrderService {
    @Autowired
    private AmazonGateway amazonGateway;

    @Autowired
    private OrderService orderService;

    public void getOrders(InputGetOrders inputGetOrders) throws JsonProcessingException, Exception {
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        GetOrdersResponse ordersResponse = amazonGateway.getOrders(inputGetOrders);
        OrderList orderList = ordersResponse.getPayload().getOrders();
        for (int i = 0; i < orderList.size(); i++) {
            String randomOrderId = "#" + randomStringGenerator.generateRandomString();
            String amazonOrderId = orderList.get(i).getAmazonOrderId();
            GetOrderItemsResponse getOrderItemsResponse = amazonGateway.getOrderItems(amazonOrderId, null);
            OrderItemList orderItemList = getOrderItemsResponse.getPayload().getOrderItems();
            GetOrderAddressResponse getOrderAddressResponse = amazonGateway.getOrderAddress(amazonOrderId);
            OrderAddress orderAddress = getOrderAddressResponse.getPayload();
            Address orderItemAddress = orderAddress.getShippingAddress();
            GetOrderResponse getOrderResponse = amazonGateway.getSingleOrder(amazonOrderId);
            GetOrderBuyerInfoResponse getOrderBuyerInfoResponse = amazonGateway.getOrderBuyerOrderInfo(amazonOrderId);
            Date localDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int j = 0; j < orderItemList.size(); j++) {
                System.out.println(i + ") Amazon Order ID: " + orderList.get(i).getAmazonOrderId());
                Order order = new Order();
                OrderItem orderItem = orderItemList.get(j);
                com.printway.client.amazongateway.reponse.orders.Order orderInfo = getOrderResponse.getPayload();
                OrderBuyerInfo orderBuyerInfo = getOrderBuyerInfoResponse.getPayload();
                String currentDate = formatter.format(localDate);
                if (!orderInfo.getOrderStatus().equals("CANCELED")) {
                    if (orderInfo.getOrderStatus().equals("SHIPPED")) {
                        order.setOrderStatus(1);
                        System.out.println("- Order status: " + orderInfo.getOrderStatus());
                    } else {
                        order.setOrderStatus(0);
                        System.out.println("- Order status: " + orderInfo.getOrderStatus());
                    }
                    order.setOrderId(randomOrderId);
                    order.setDateOrder(orderList.get(i).getPurchaseDate());
                    order.setProductName(orderItem.getTitle());
                    order.setVariantTitle("null");
                    order.setSku(orderItem.getASIN() + "/" + orderItem.getSellerSKU());
                    order.setUnfulfillQuantity(orderItem.getQuantityOrdered());
                    order.setShippingName("null");
                    order.setShippingPhone("null");
                    order.setShippingEmail(orderBuyerInfo.getBuyerEmail());
                    order.setShippingAddress1("null");
                    order.setShippingAddress2("null");
                    order.setShippingCity(orderItemAddress.getCity());
                    order.setShippingProvince(orderItemAddress.getStateOrRegion());
                    order.setShippingProvinceCode(orderItemAddress.getStateOrRegion());
                    order.setShippingZip(orderItemAddress.getPostalCode());
                    order.setShippingCountry(orderItemAddress.getCountryCode());
                    order.setShippingCountryCode(orderItemAddress.getCountryCode());
                    order.setOrderLineId(orderItem.getOrderItemId());
                    order.setCartNumber("null");
                    order.setPrinterDesignUrl("null");
                    order.setHasSpecialCharacter("null");
                    order.setDateTimeEntered(formatter.parse(currentDate));
                    order.setStatusDownload(Short.parseShort("0"));
                    order.setStatusGetTracking(Short.parseShort("0"));
                    order.setUsername("null");
                    order.setOrderStoreId(amazonOrderId);
                    order.setOrdStoreInfomation("{\"store_id\":7,\"store_name\":\"Amazon\",\"platform\":\"Amazon\"}");
                    orderService.create(order);
                    System.out.println("Stored to Database, Amazon Order ID: " + amazonOrderId);
                } else {
                    System.out.println(" == This order is Canceled from Amazon! ==");
                }
            }
        }
    }
}
