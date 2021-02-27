package com.printway.service;

import com.printway.client.amazongateway.AmazonGateway;
import com.printway.client.amazongateway.reponse.GetFeedResponse;
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

    public List<TblProductPostStore> submitProducts(String userId) {
        List<ProductAmazon> productAmazonList = productAmazonService.getProductsAmazon(userId);
        if (productAmazonList.isEmpty()){
            throw new BaseException(HttpStatus.NO_CONTENT, "No product available");
        }
        List<GetFeedResponse> responseList = amazonGateway.submitProduct(productAmazonList);
        List<TblProductPostStore> productPostStoreList = new ArrayList<>();
        for (ProductAmazon productAmazon : productAmazonList) {
            TblProductPostStore productPostStore = postStoreService.findById(productAmazon.getId());
            productPostStore.setProductStatus("Processing");
            productPostStoreList.add(productPostStore);
        }

        return postStoreService.updateList(productPostStoreList);
    }

    public TblUserEntity addCookie(String userId, String cookie){
        TblUserEntity userEntity = userService.findById(Integer.valueOf(userId));
        userEntity.setCookie(cookie);
        return userService.update(userEntity);
    }

    public List<TblProductPostStore> checkCustomize(List<String> skuList, String cookie) throws IOException {
        OkHttpClient client = new OkHttpClient();
        for (String sku: skuList){
            Request request = new Request.Builder()
                    .url("https://sellercentral.amazon.com/gestalt/ajax/onebyone/init?sku="+sku)
                    .method("GET", null)
                    .header("Cookie", cookie)
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
            response.body().string();
        }

        return null;
    }

    public List<TblProductPostStore> customize(List<String> skuList, String cookie) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        for (String sku: skuList){
            ProductCustomize productCustomize = productAmazonService.getCustomTemplate(sku);
            RequestBody requestBody = RequestBody.create(mediaType, productCustomize.getCustom());
            Request request = new Request.Builder()
                    .url("https://sellercentral.amazon.com/gestalt/ajax/onebyone/setSellerSpec?sku="+sku+"&useCase=GT_AmazonCustom_1&clientConfigVersion=80c63968-a5c1-4940-9cde-b83d0e9da98d")
                    .method("POST",requestBody)
                    .addHeader("accept-language", "en-US,en;q=0.9")
                    .addHeader("Cookie", cookie)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        }

        return null;
    }
}
