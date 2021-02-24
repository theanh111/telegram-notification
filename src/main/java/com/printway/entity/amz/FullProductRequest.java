package com.printway.entity.amz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullProductRequest {
    private String sku;
    private String title;
    private String brand;
    private String description;
    private List<String> bulletPoint;
    private String currency;
    private String price;
    private List<String> imageLocation;

}
