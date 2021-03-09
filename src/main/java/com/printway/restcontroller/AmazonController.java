package com.printway.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.printway.client.amazongateway.AmazonGateway;
import com.printway.entity.amz.ProductAmazon;
import com.printway.entity.amz.ProductCustomize;
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
    public ResponseEntity<?> getProducts(@PathVariable String userId) throws JsonProcessingException {
//        List<TblProductAmazon> productAmazonList = amazonService.getProductsAmazon(userId);
        List<ProductAmazon> productAmazonList = productAmazonRepository.getProductsAmazon(userId);
        return new ResponseEntity<List<ProductAmazon>>(productAmazonList, HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseEntity<?> getProducts() throws JsonProcessingException {
        ProductCustomize productCustomize = productAmazonRepository.getCustomTemplate("PW_TEST_X05");
        return new ResponseEntity<>(productCustomize, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}/check-customize", method = RequestMethod.POST)
    public ResponseEntity<?> checkProduct(@PathVariable String userId, @RequestBody List<String> skuList) throws IOException {
        try {
            success(amazonService.checkCustomize(skuList, userId));
        } catch (BaseException e) {
            unSuccess(e);
        }
        return ResponseEntity.status(status).body(body);
    }

    @RequestMapping(value = "/{userId}/customize", method = RequestMethod.POST)
    public ResponseEntity<?> custom(@PathVariable String userId, @RequestBody List<String> skuList) throws IOException {
        try {
            success(amazonService.customize(skuList, userId));
        } catch (BaseException e) {
            unSuccess(e);
        }
        return ResponseEntity.status(status).body(body);
    }

    @RequestMapping(value = "/{userId}/submit-product", method = RequestMethod.POST)
    public ResponseEntity<?> submitProducts(@PathVariable String userId) throws JsonProcessingException {
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

    @RequestMapping(value = "/get-feed-response", method = RequestMethod.POST)
    public ResponseEntity<?> getFeedResponse(@RequestBody List<String> feedIdList) throws JsonProcessingException {
            success(amazonService.getFeedResponses(feedIdList));
        return ResponseEntity.status(status).body(body);
    }

    @RequestMapping(value = "/get-feed-report", method = RequestMethod.POST)
    public ResponseEntity<?> getFeedReport(@RequestBody List<String> feedIdList) throws JsonProcessingException {
        success(amazonService.getFeedReportList(feedIdList));
        return ResponseEntity.status(status).body(body);
    }

    @RequestMapping(value = "/get-order", method = RequestMethod.POST)
    public ResponseEntity<?> getSingleOrder(@RequestBody String orderId) throws JsonProcessingException {
        success(amazonService.getSingleOrder(orderId));
        return ResponseEntity.status(status).body(body);
    }


}
