package com.printway.client.amazongateway.reponse.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputGetOrders {
    private List<String> marketplaceIds;
    private String createdAfter;
    private String createdBefore;
    private String lastUpdatedAfter;
    private String lastUpdatedBefore;
    private List<String> orderStatuses;
    private List<String> fulfillmentChannels;
    private List<String> paymentMethods;
    private String buyerEmail;
    private String sellerOrderId;
    private Integer maxResultsPerPage;
    private List<String> easyShipShipmentStatuses;
    private String nextToken;
    private List<String> amazonOrderIds;
}
