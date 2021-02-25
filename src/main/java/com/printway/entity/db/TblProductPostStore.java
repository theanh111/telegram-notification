package com.printway.entity.db;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tblproduct_post_store")
public class TblProductPostStore {
    @Id
    private int id;
    @Column(name = "sku")
    private String sku;
    @Column(name = "product_name")
    private String prName;
    @Column(name = "url_file_design")
    private String urlFileDesign;
    @Column(name = "product_type_id")
    private int prTypeId;
    @Column(name = "user_id_post")
    private int userIdPost;
    @Column(name = "user_id_email")
    private String userEmail;
    @Column(name = "store_sale_price")
    private double salePrice;
    @Column(name = "store_regular_price")
    private double regularPrice;
    @Column(name = "store_media")
    private String storeMedia;
    @Column(name = "store_variant")
    private String storeVariant;
    @Column(name = "store_option")
    private String storeOption;
    @Column(name = "datetime_create")
    private Timestamp dateTime;
    @Column(name = "datetime_online")
    private String dateTimeOnline;
    @Column(name = "online_store_status")
    private String storeStatus;
    @Column(name = "store_id")
    private String storeId;
    @Column(name = "product_status")
    private String prStatus;
    @Column(name = "store_product_id")
    private String storePrId;
    @Column(name = "store_name")
    private String storeName;


}
