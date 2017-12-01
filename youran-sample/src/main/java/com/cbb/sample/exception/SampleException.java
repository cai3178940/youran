package com.cbb.sample.exception;

import org.springframework.http.HttpStatus;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/20 10:28
 */
public class SampleException extends RuntimeException{

    private HttpStatus httpStatus;

    public SampleException() {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public SampleException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public SampleException(String message,HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
