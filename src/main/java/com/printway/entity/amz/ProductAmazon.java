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
public class ProductAmazon {
    private Integer id;
    private String sku;
    private String category;
    private String title;
    private String brand;
    private String description;
    private List<String> bulletPoint;
    private String currency;
    private String price;
    private List<String> imageLocation;
    private List<Variant> variantList;
    private Integer autoCustom;
}
