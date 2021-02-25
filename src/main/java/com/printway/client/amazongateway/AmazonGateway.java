package com.printway.client.amazongateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.printway.client.BaseRestTemplate;
import com.printway.client.amazongateway.reponse.GetFeedResponse;
import com.printway.client.paypalgateway.response.OrderCreateResponse;
import com.printway.entity.amz.FullProductRequest;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmazonGateway extends BaseRestTemplate {
    private String baseUrl = "http://localhost:8088/amazon/v1/";
    private final Logger logger = Logger.getLogger(this.getClass());

    public List<GetFeedResponse> submitProduct(List<FullProductRequest> productRequestList) throws JsonProcessingException {
        String responses = null;
        HttpEntity<List<FullProductRequest>> request = new HttpEntity<>(productRequestList);
        try {
            responses = getRestTemplate().postForObject(baseUrl + "submit-product" , request, String.class);
//            order = postForObject( Order.class,baseUrl + "create", request);
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        ObjectMapper mapper = new ObjectMapper();
        List<GetFeedResponse> getFeedResponseList = mapper.readValue(responses, new TypeReference<List<GetFeedResponse>>() {});
        return getFeedResponseList;
    }
}
