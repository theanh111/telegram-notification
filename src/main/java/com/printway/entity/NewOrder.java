package com.printway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblneworders")
public class NewOrder {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "bar_code")
    private String barCode;
    @Column(name = "shipping_name")
    private String shippingName;
    @Column(name = "shipping_address1")
    private String shippingAddress1;
    @Column(name = "shipping_address2")
    private String shippingAddress2;
    @Column(name = "shipping_city")
    private String shippingCity;
    @Column(name = "shipping_zip")
    private String shippingZip;
    @Column(name = "shipping_province")
    private String shippingProvince;
    @Column(name = "shipping_country")
    private String shippingCountry;
    @Column(name = "shipping_phone")
    private String shippingPhone;
    @Column(name = "line_item_quantity")
    private Integer lineItemQuantity;
    @Column(name = "line_item_name")
    private String lineItemName;
    @Column(name = "product_type")
    private String productType;
    @Column(name = "variant")
    private String variant;
    @Column(name = "print_quantity")
    private Integer printQuantity;
    @Column(name = "printed_quantity")
    private Integer printedQuantity;
    @Column(name = "packaged_quantity")
    private Integer packagedQuantity;
    @Column(name = "error_quantity_in")
    private Integer errorQuantityIn;
    @Column(name = "error_quantity_out")
    private Integer errorQuantityOut;
    @Column(name = "sku")
    private String sku;
    @Column(name = "url_mockup")
    private String urlMockup;
    @Column(name = "partner_id")
    private String partnerId;
    @Column(name = "noted")
    private String noted;
    @Column(name = "status_production")
    private Integer statusProduction;
    @Column(name = "date_received")
    private Date dateReceived;
    @Column(name = "dtime_entered")
    private Date dtimeEntered;
    @Column(name = "who_entered")
    private String whoEntered;
    @Column(name = "status_on_stracking")
    private Integer statusOnStracking;
    @Column(name = "totalpackaged")
    private Integer totalpackaged;
    @Column(name = "status_download_file_design")
    private Integer statusDownloadFileDesign;
    @Column(name = "resend_reason")
    private String resendReason;
    @Column(name = "resend_dtime_entered")
    private Date resendDtimeEntered;
    @Column(name = "resend_who_entered")
    private String resendWhoEntered;
    @Column(name = "material")
    private String material;
}
