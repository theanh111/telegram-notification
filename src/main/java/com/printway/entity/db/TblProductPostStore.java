package com.printway.entity.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblproduct_post_store")
public class TblProductPostStore {
    @Id
    private Integer id;
    @Column(name = "sku")
    private String sku;
    @Column(name = "product_name")
    private String prName;
    @Column(name = "url_file_design")
    private String urlFileDesign;
    @Column(name = "product_type_id")
    private Integer prTypeId;
    @Column(name = "user_id_post")
    private Integer userIdPost;
    @Column(name = "user_id_email")
    private String userIdEmail;
    @Column(name = "store_sale_price")
    private double storeSalePrice;
    @Column(name = "store_regular_price")
    private double storeRegularPrice;
    @Column(name = "store_media")
    private String storeMedia;
    @Column(name = "store_variant")
    private String storeVariant;
    @Column(name = "store_option")
    private String storeOption;
    @Column(name = "datetime_create")
    private Timestamp dateTimeCreate;
    @Column(name = "datetime_online")
    private String dateTimeOnline;
    @Column(name = "online_store_status")
    private String onlineStoreStatus;
    @Column(name = "store_id")
    private String storeId;
    @Column(name = "product_status")
    private String productStatus;
    @Column(name = "store_product_id")
    private String storeProductId;
    @Column(name = "store_name")
    private String storeName;
    @Column(name = "amazon_auto_custom")
    private Integer custom;
}
