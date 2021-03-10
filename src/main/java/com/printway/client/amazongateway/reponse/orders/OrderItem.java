package com.printway.client.amazongateway.reponse.orders;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private String ASIN;
    private String sellerSKU;
    private String orderItemId;
    private String title;
    private Integer quantityOrdered;
    private Integer quantityShipped;
    private ProductInfoDetail productInfo;
    private PointsGrantedDetail pointsGranted;
    private Money itemPrice;
    private Money shippingPrice;
    private Money itemTax;
    private Money shippingTax;
    private Money shippingDiscount;
    private Money shippingDiscountTax;
    private Money promotionDiscount;
    private Money promotionDiscountTax;
    private PromotionIdList promotionIds;
    private Money coDFee;
    private Money coDFeeDiscount;
    private Boolean isGift;
    private String conditionNote;
    private String conditionId;
    private String conditionSubtypeId;
    private String scheduledDeliveryStartDate;
    private String scheduledDeliveryEndDate;
    private String priceDesignation;
    private TaxCollection taxCollection;
    private Boolean serialNumberRequired;
    private Boolean isTransparency;
    private String iossNumber;
    private String deemedResellerCategory;

}
