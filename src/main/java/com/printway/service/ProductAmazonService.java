package com.printway.service;

import com.printway.entity.amz.ProductAmazon;
import com.printway.entity.amz.ProductCustomize;
import com.printway.repository.ProductAmazonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAmazonService {
    @Autowired
    private ProductAmazonRepository productAmazonRepository;

    public List<ProductAmazon> getProductsAmazon(String userId){
        return productAmazonRepository.getProductsAmazon(userId);
    }
    public ProductCustomize getCustomTemplate(String sku){
        return productAmazonRepository.getCustomTemplate(sku);
    }

}
