package com.printway.service;

import com.printway.entity.amz.TblProductAmazon;
import com.printway.repository.TblProductAmazonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAmazonService {
    @Autowired
    private TblProductAmazonRepository tblProductAmazonRepository;

    public List<TblProductAmazon> getProductsAmazon(String userId){
        return tblProductAmazonRepository.getProductsAmazon(userId);
    }
}
