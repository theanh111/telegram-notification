package com.printway.entity.amz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCustomize {
    private Integer id;
    private String sku;
    private String custom;

}
