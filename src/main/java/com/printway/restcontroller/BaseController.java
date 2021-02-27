package com.printway.restcontroller;

import com.printway.exception.BaseResponse;
import com.printway.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class BaseController {
    HttpStatus status;
    Map<String, Object> body;

    @Autowired
    private BaseResponse response;

    void success(Object data) {
        status = HttpStatus.OK;
        body = response.success(data);
    }

    void unSuccess(BaseException e) {
        status = e.getErrorStatus();
        body = response.unSuccess(e.getMessage());
    }
}
