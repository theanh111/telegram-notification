package com.printway.client.amazongateway.reponse.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemBuyerInfo {
    private String orderItemId;
    private BuyerCustomizedInfoDetail buyerCustomizedInfo;
    private Money giftWrapPrice;
    private Money giftWrapTax;
    private String giftMessageText;
    private String giftWrapLevel;
}
