package com.printway.restcontroller;

import com.printway.client.paypalgateway.response.OrderCreateResponse;
import com.printway.entity.amz.TblProductAmazon;
import com.printway.entity.db.Order;
import com.printway.service.ProductAmazonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/amazon/v1")
public class AmazonController {

    @Autowired
    private ProductAmazonService amazonService;

    @RequestMapping(value = "/{userId}/get", method = RequestMethod.GET)
    public ResponseEntity<?> getProducts(@PathVariable String userId) {
        List<TblProductAmazon> productAmazonList = amazonService.getProductsAmazon(userId);
        return new ResponseEntity<List<TblProductAmazon>>(productAmazonList, HttpStatus.OK);
    }
}
