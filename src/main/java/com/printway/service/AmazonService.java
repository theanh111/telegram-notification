package com.printway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.printway.client.amazongateway.AmazonGateway;
import com.printway.client.amazongateway.reponse.GetFeedResponse;
import com.printway.client.amazongateway.reponse.document.DocumentReportFeedResponse;
import com.printway.entity.amz.ProductAmazon;
import com.printway.entity.amz.ProductCustomize;
import com.printway.entity.db.TblProductPostStore;
import com.printway.entity.db.TblUserEntity;
import com.printway.exception.BaseException;
import com.squareup.okhttp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AmazonService {

    @Autowired
    private ProductAmazonService productAmazonService;
    @Autowired
    private AmazonGateway amazonGateway;
    @Autowired
    private TblProductPostStoreService postStoreService;
    @Autowired
    private TblUserService userService;

    public List<TblProductPostStore> submitProducts(String userId) throws JsonProcessingException {
        List<ProductAmazon> productAmazonList = productAmazonService.getProductsAmazon(userId);
        if (productAmazonList.isEmpty()) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "No product available");
        }
        try {
            List<GetFeedResponse> responseList = amazonGateway.submitProduct(productAmazonList);
            if (responseList.isEmpty()){
                throw new BaseException(HttpStatus.BAD_GATEWAY, "Something wrong! Please try again!");
            }
        }catch (BaseException e){
            throw new BaseException(HttpStatus.BAD_GATEWAY, "Something wrong! Please try again!");
        }
        List<TblProductPostStore> productPostStoreList = new ArrayList<>();
        for (ProductAmazon productAmazon : productAmazonList) {
            TblProductPostStore productPostStore = postStoreService.findById(productAmazon.getId());
            productPostStore.setOnlineStoreStatus("Processing");
            productPostStoreList.add(productPostStore);
        }

        return postStoreService.updateList(productPostStoreList);
    }

    public TblUserEntity addCookie(String userId, String cookie) {
        TblUserEntity userEntity = userService.findById(Integer.valueOf(userId));
        userEntity.setCookie(cookie);
        return userService.update(userEntity);
    }

    public List<TblProductPostStore> checkCustomize(List<String> skuList, String userId) throws IOException {
        String cookie = userService.findById(Integer.valueOf(userId)).getCookie();
        List<TblProductPostStore> tblProductPostStoreList = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        for (String sku : skuList) {
            Request request = new Request.Builder()
                    .url("https://sellercentral.amazon.com/gestalt/ajax/onebyone/init?sku=" + sku)
                    .method("GET", null)
                    .header("Cookie", cookie)
                    .build();
            Response response = client.newCall(request).execute();
            String data = response.body().string();
            TblProductPostStore productPostStore = postStoreService.findBySku(sku);
            if (data.contains("doctype")) {
                throw new BaseException(HttpStatus.FORBIDDEN, "Expired cookie");
            } else if (data.contains("multiple offers on this product")) {
                productPostStore.setOnlineStoreStatus("Done");
                productPostStore.setProductStatus("Being Hijacked");
                tblProductPostStoreList.add(postStoreService.update(productPostStore));
            } else if (data.contains("sku you provided is invalid")) {
                productPostStore.setOnlineStoreStatus("Processing");
                productPostStore.setProductStatus("Draft");
                tblProductPostStoreList.add(postStoreService.update(productPostStore));
            } else {
                JsonObject json = new Gson().fromJson(data, JsonObject.class);
                if (json.get("sellerConfig").isJsonNull()) {
                    productPostStore.setOnlineStoreStatus("Done");
                    productPostStore.setProductStatus("Enable Customize");
                    tblProductPostStoreList.add(postStoreService.update(productPostStore));
                }
            }
        }

        return tblProductPostStoreList;
    }

    public List<TblProductPostStore> customize(List<String> skuList, String userId) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String cookie = userService.findById(Integer.valueOf(userId)).getCookie();
        List<TblProductPostStore> tblProductPostStoreList = new ArrayList<>();
        for (String sku : skuList) {
            ProductCustomize productCustomize = productAmazonService.getCustomTemplate(sku);
            TblProductPostStore productPostStore = postStoreService.findBySku(sku);
            RequestBody requestBody = RequestBody.create(mediaType, productCustomize.getCustom());
            Request request = new Request.Builder()
                    .url("https://sellercentral.amazon.com/gestalt/ajax/onebyone/setSellerSpec?sku=" + sku + "&useCase=GT_AmazonCustom_1&clientConfigVersion=80c63968-a5c1-4940-9cde-b83d0e9da98d")
                    .method("POST", requestBody)
                    .addHeader("accept-language", "en-US,en;q=0.9")
                    .addHeader("Cookie", cookie)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                productPostStore.setProductStatus("Customized");
                tblProductPostStoreList.add(postStoreService.update(productPostStore));
            } else {
                throw new BaseException(HttpStatus.FORBIDDEN, "Expired Cookie");
            }
        }
        return tblProductPostStoreList;
    }

    public List<GetFeedResponse> getFeedResponses(List<String> feedIdList) throws JsonProcessingException {
        List<GetFeedResponse> responses = amazonGateway.getFeedResponses(feedIdList);
        return responses;
    }

    public List<DocumentReportFeedResponse> getFeedReportList(List<String> feedIdList) throws JsonProcessingException {
        List<DocumentReportFeedResponse> responses = amazonGateway.getFeedReportList(feedIdList);
        return responses;
    }
}
