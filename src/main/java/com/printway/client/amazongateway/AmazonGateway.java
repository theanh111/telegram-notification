package com.printway.client.amazongateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.printway.client.BaseRestTemplate;
import com.printway.client.amazongateway.reponse.GetFeedResponse;
import com.printway.client.amazongateway.reponse.document.DocumentReportFeedResponse;
import com.printway.entity.amz.ProductAmazon;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmazonGateway extends BaseRestTemplate {
    private String baseUrl = "http://localhost:8088/amazon/v1/";
    private final Logger logger = Logger.getLogger(this.getClass());

    public List<GetFeedResponse> submitProduct(List<ProductAmazon> productRequestList) {
        List<GetFeedResponse> responses = null;
        HttpEntity<List<ProductAmazon>> request = new HttpEntity<>(productRequestList);
        try {
//            String[] data = getRestTemplate().postForObject(baseUrl + "submit-product" , request, String[].class);
            ResponseEntity<List<GetFeedResponse>> claimResponse = getRestTemplate().exchange(
                    baseUrl + "submit-product", HttpMethod.POST, request, new ParameterizedTypeReference<List<GetFeedResponse>>(){});
            if(claimResponse != null && claimResponse.hasBody()) {
                responses = claimResponse.getBody();
            }
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        return responses;
    }

    public List<GetFeedResponse> getFeedResponses(List<String> feedIdList) throws JsonProcessingException {
        List<GetFeedResponse> responses = null;
        HttpEntity<List<String>> request = new HttpEntity<>(feedIdList);
        try {
//            String[] data = getRestTemplate().postForObject(baseUrl + "submit-product" , request, String[].class);
            ResponseEntity<List<GetFeedResponse>> claimResponse = getRestTemplate().exchange(
                    baseUrl + "get-feed-response", HttpMethod.POST, request, new ParameterizedTypeReference<List<GetFeedResponse>>(){});
            if(claimResponse != null && claimResponse.hasBody()) {
                responses = claimResponse.getBody();
            }
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        return responses;
    }

    public List<DocumentReportFeedResponse> getFeedReportList(List<String> feedIdList) throws JsonProcessingException {
        List<DocumentReportFeedResponse> responses = null;
        HttpEntity<List<String>> request = new HttpEntity<>(feedIdList);
        try {
//            String[] data = getRestTemplate().postForObject(baseUrl + "submit-product" , request, String[].class);
            ResponseEntity<List<DocumentReportFeedResponse>> claimResponse = getRestTemplate().exchange(
                    baseUrl + "get-feed-report", HttpMethod.POST, request, new ParameterizedTypeReference<List<DocumentReportFeedResponse>>(){});
            if(claimResponse != null && claimResponse.hasBody()) {
                responses = claimResponse.getBody();
            }
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        return responses;
    }




}
