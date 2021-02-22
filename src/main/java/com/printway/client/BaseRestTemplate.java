package com.printway.client;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public abstract class BaseRestTemplate {
    private final Logger logger = Logger.getLogger(this.getClass());

    public RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(180000);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        return restTemplate;
    }

    protected <D> D getForObject(Class<D> dataClazz, String url, String param) {
        D result = null;
        try {
            if (param != null) {
                result = getRestTemplate().getForObject(url + param, dataClazz);
            } else {
                result = getRestTemplate().getForObject(url, dataClazz);
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    protected <D> D getForObject(Class<D> dataClazz, String url, Map<String, String> param) {
        D result = null;
        try {
            if (param != null && !param.isEmpty()) {
                result = getRestTemplate().getForObject(url, dataClazz, param);
            } else {
                result = getRestTemplate().getForObject(url, dataClazz);
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }
    public <D> D postForObject(Class<D> dataClazz, String mapUrl, Object data) {
        D apiResponse = null;
        HttpEntity<Object> request = new HttpEntity<>(data);
        try {
            String url = getFullUrl(mapUrl, null);
            apiResponse = getRestTemplate().postForObject(url, request, dataClazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
    }
    private String getFullUrl(String mapUrl, String param) {
        String url;
        if (param != null) {
            url = mapUrl + param;
        } else {
            url = mapUrl;
        }
        return url;
    }
}
