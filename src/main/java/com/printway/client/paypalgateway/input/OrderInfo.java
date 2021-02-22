package com.printway.client.paypalgateway.input;

import lombok.Data;

@Data
public class OrderInfo {
    private String orderId;
    private String currencyCode;
    private String amount;

//    public static void main(String[] args) throws SerializeException, JsonProcessingException {
//        OrderInfo orderInfo = new OrderInfo();
//        orderInfo.setOrderId("Orderid1");
//        Product product = new Product();
//        product.setSku("SKU1");
//        product.setQuantity(2);
//        product.setBaseCost(10);
//        product.setCurrencyCode("USD");
//        orderInfo.setProduct(product);
//        ObjectMapper mapper = new ObjectMapper();
//        List<OrderInfo> orderInfos = new ArrayList<>();
//        orderInfos.add(orderInfo);
//        orderInfos.add(orderInfo);
//        orderInfos.add(orderInfo);
//        System.out.println(mapper.writeValueAsString(orderInfos));
//
//    }
}
