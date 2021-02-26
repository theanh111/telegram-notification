package com.printway.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.printway.client.amazongateway.AmazonGateway;
import com.printway.client.amazongateway.reponse.GetFeedResponse;
import com.printway.client.amazongateway.reponse.document.DocumentReportFeedResponse;
import com.printway.entity.amz.FullProductRequest;
import com.printway.entity.amz.TblProductAmazon;
import com.printway.repository.PRA;
import com.printway.repository.PaymentConfigRepository;
import com.printway.service.ProductAmazonService;
import com.printway.service.TblProductPostStoreService;
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
    @Autowired
    private TblProductPostStoreService productPostStoreService;
    @Autowired
    private PaymentConfigRepository paymentConfigRepository;
    @Autowired
    private PRA pra;
    @Autowired
    private AmazonGateway amazonGateway;

    @RequestMapping(value = "/{userId}/get", method = RequestMethod.GET)
    public ResponseEntity<?> getProducts(@PathVariable String userId) {
//        List<TblProductAmazon> productAmazonList = amazonService.getProductsAmazon(userId);
        List<TblProductAmazon> productAmazonList = pra.getProductAmazon(userId);
        return new ResponseEntity<List<TblProductAmazon>>(productAmazonList, HttpStatus.OK);
    }
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseEntity<?> getProducts(@RequestBody List<FullProductRequest> productRequestList) throws JsonProcessingException {
            List<GetFeedResponse> responses = amazonGateway.submitProduct(productRequestList);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    @RequestMapping(value = "/gett", method = RequestMethod.POST)
    public ResponseEntity<?> getFeedResponse(@RequestBody List<String> feedIdList) throws JsonProcessingException {
        List<GetFeedResponse> responses = amazonGateway.getFeedResponses(feedIdList);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    @RequestMapping(value = "/gettt", method = RequestMethod.POST)
    public ResponseEntity<?> getReport(@RequestBody List<String> feedIdList) throws JsonProcessingException {
        List<DocumentReportFeedResponse> responses = amazonGateway.getFeedReportList(feedIdList);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

}
