package com.printway.client.amazongateway.reponse.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderBuyerInfo {
    private String amazonOrderId;
    private String buyerEmail;
    private String buyerName;
    private String buyerCounty;
    private BuyerTaxInfo buyerTaxInfo;
    private String purchaseOrderNumber;
}
