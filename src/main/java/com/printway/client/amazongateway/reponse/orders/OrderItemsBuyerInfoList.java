package com.printway.client.amazongateway.reponse.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsBuyerInfoList {
    private OrderItemBuyerInfoList orderItems;
    private String nextToken;
    private String amazonOrderId;
}
