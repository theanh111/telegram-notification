package com.printway.client.amazongateway.reponse.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String amazonOrderId;
    private String sellerOrderId;
    private Date purchaseDate;
    private String lastUpdateDate;
    private String orderStatus;
    private String fulfillmentChannel;
    private String salesChannel;
    private String orderChannel;
    private String shipServiceLevel;
    private Money orderTotal;
    private Integer numberOfItemsShipped;
    private Integer numberOfItemsUnshipped;
    private String paymentExecutionDetail;
    private String paymentMethod;
    private List<String> paymentMethodDetails;
    private String marketplaceId;
    private String shipmentServiceLevelCategory;
    private String easyShipShipmentStatus;
    private String cbaDisplayableShippingLabel;
    private String orderType;
    private String earliestShipDate;
    private String latestShipDate;
    private String earliestDeliveryDate;
    private String latestDeliveryDate;
    private Boolean isBusinessOrder;
    private Boolean isPrime;
    private Boolean isPremiumOrder;
    private Boolean isGlobalExpressEnabled;
    private String replacedOrderId;
    private Boolean isReplacementOrder;
    private String promiseResponseDueDate;
    private Boolean isEstimatedShipDateSet;
    private Boolean isSoldByAB = null;
    private String assignedShipFromLocationAddress;
    private String fulfillmentInstruction;
}
