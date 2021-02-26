package com.printway.entity.amz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TblProductAmazon {
    @Id
    private Integer id;
    @Column(name = "sku")
    private String sku;
    @Column(name = "product_name")
    private String title;
    @Column(name = "brand_name")
    private String brand;
    @Column(name = "pro_description")
    private String description;
    @Column(name = "amazon_bullet_print")
    private List<String> bulletPoint;
    @Column(name = "currency")
    private String currency;
    @Column(name = "store_sale_price")
    private String price;
    @Column(name = "store_media")
    private List<String> imageLocation;
}
