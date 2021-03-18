package com.printway.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class BaseResponse {
    private Map<String, Object> body;

    public BaseResponse(){
        body = new HashMap<>();
    }
    public Map<String,Object> success(Object data){
        body.clear();
        body.put("data", data);
        return body;
    }
    public Map<String, Object> unSuccess(String msg){
        body.clear();
        body.put("msg", msg);
        return body;
    }
}
