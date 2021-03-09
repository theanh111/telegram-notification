package com.printway.client.amazongateway.reponse.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersList {
    private OrderList orders;
    private String nextToken;
    private String lastUpdatedBefore;
    private String createdBefore;
}
