package com.printway.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException{
    private HttpStatus errorStatus;


    public BaseException(HttpStatus httpStatus, String errorMsg) {
        super(errorMsg);
        this.errorStatus = httpStatus;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }
}
