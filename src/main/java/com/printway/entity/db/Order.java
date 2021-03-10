package com.printway.entity.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblorders")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "date_order")
    private Date dateOrder;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "variant_title")
    private String variantTitle;
    @Column(name = "sku")
    private String sku;
    @Column(name = "unfulfill_quantity")
    private Integer unfulfillQuantity;
    @Column(name = "shipping_name")
    private String shippingName;
    @Column(name = "shipping_phone")
    private String shippingPhone;
    @Column(name = "shipping_email")
    private String shippingEmail;
    @Column(name = "shipping_address1")
    private String shippingAddress1;
    @Column(name = "shipping_address2")
    private String shippingAddress2;
    @Column(name = "shipping_city")
    private String shippingCity;
    @Column(name = "shipping_province")
    private String shippingProvince;
    @Column(name = "shipping_province_code")
    private String shippingProvinceCode;
    @Column(name = "shipping_zip")
    private String shippingZip;
    @Column(name = "shipping_country")
    private String shippingCountry;
    @Column(name = "shipping_country_code")
    private String shippingCountryCode;
    @Column(name = "order_line_id")
    private String orderLineId;
    @Column(name = "cart_number")
    private String cartNumber;
    @Column(name = "printer_design_url")
    private String printerDesignUrl;
    @Column(name = "has_special_character")
    private String hasSpecialCharacter;
    @Column(name = "dtime_entered")
    private Date dateTimeEntered;
    @Column(name = "status_download")
    private Short statusDownload;
    @Column(name = "status_get_tracking")
    private Short statusGetTracking;
    @Column(name = "user_name")
    private String username;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "order_reason")
    private String orderReason;
    @Column(name = "order_store_id")
    private String orderStoreId;
    @Column(name = "ord_price")
    private Double ordPrice;
    @Column(name = "ord_store_infomation")
    private String ordStoreInfomation;
    @Column(name = "order_status")
    private Integer orderStatus;
    @Column(name = "dtime_request_cancel")
    private Date dateTimeRequestCancel;
    @Column(name = "dtime_request_resend")
    private Date dateTimeRequestResend;
    @Column(name = "dtime_confirm_resend")
    private String dateTimeComfirmResend;
    @Column(name = "dtime_confirm_canceled")
    private Date dateTimeComfirmCanceled;
    @Column(name = "dtime_request_refun")
    private Date dateTimeRequestRefund;
    @Column(name = "who_confirm_resend")
    private String whoConfirmResend;
    @Column(name = "who_confirm_refun")
    private String whoConfirmRefund;
    @Column(name = "who_confirm_cancel")
    private String whoConfirmCancel;
    @Column(name = "payment_status")
    private Integer paymentStatus;

}
