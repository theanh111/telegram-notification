package com.printway.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.printway.client.amazongateway.AmazonGateway;
import com.printway.entity.amz.ProductAmazon;
import com.printway.entity.amz.ProductCustomize;
import com.printway.entity.db.TblProductPostStore;
import com.printway.exception.BaseException;
import com.printway.repository.PaymentConfigRepository;
import com.printway.repository.ProductAmazonRepository;
import com.printway.service.AmazonService;
import com.printway.service.ProductAmazonService;
import com.printway.service.TblProductPostStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/amazon/v1")
public class AmazonController extends BaseController {

    @Autowired
    private ProductAmazonService productAmazonService;
    @Autowired
    private AmazonService amazonService;
    @Autowired
    private TblProductPostStoreService productPostStoreService;
    @Autowired
    private PaymentConfigRepository paymentConfigRepository;
    @Autowired
    private ProductAmazonRepository productAmazonRepository;
    @Autowired
    private AmazonGateway amazonGateway;

    @RequestMapping(value = "/{userId}/get", method = RequestMethod.GET)
    public ResponseEntity<?> getProducts(@PathVariable String userId) {
//        List<TblProductAmazon> productAmazonList = amazonService.getProductsAmazon(userId);
        List<ProductAmazon> productAmazonList = productAmazonRepository.getProductsAmazon(userId);
        return new ResponseEntity<List<ProductAmazon>>(productAmazonList, HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseEntity<?> getProducts() throws JsonProcessingException {
        ProductCustomize productCustomize = productAmazonRepository.getCustomTemplate("PW_TEST_X05");
        return new ResponseEntity<>(productCustomize, HttpStatus.OK);
    }
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ResponseEntity<?> checkProduct(@RequestBody String cookie) throws IOException {
        List<String> skuList = new ArrayList<>();
        skuList.add("akisote_shirt_zYO0LsLUCMQ");
        List<TblProductPostStore> productCustomize = amazonService.checkCustomize(skuList, cookie);
        return new ResponseEntity<>(productCustomize, HttpStatus.OK);
    }
    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public ResponseEntity<?> custom(@RequestBody String cookie) throws IOException {
        List<String> skuList = new ArrayList<>();
        skuList.add("akisote_shirt_zj31K4artpM");
        success(amazonService.customize(skuList, cookie));
        return ResponseEntity.status(status).body(body);
    }

    @RequestMapping(value = "/{userId}/submit-product", method = RequestMethod.POST)
    public ResponseEntity<?> submitProducts(@PathVariable String userId) {
        try {
            success(amazonService.submitProducts(userId));
        } catch (BaseException e) {
            unSuccess(e);
        }
        return ResponseEntity.status(status).body(body);
    }
    @RequestMapping(value = "/{userId}/add-cookie", method = RequestMethod.POST)
    public ResponseEntity<?> addCookie(@PathVariable String userId, @RequestBody String cookie) {
        try {
            success(amazonService.addCookie(userId, cookie));
        } catch (BaseException e) {
            unSuccess(e);
        }
        return ResponseEntity.status(status).body(body);
    }

}
