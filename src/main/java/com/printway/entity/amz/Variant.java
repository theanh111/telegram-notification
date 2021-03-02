package com.printway.entity.amz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Variant {
    private String sku;
    private String size;
    private String color;
    private String urlImage;
    private String price;
}
