package com.youran.generate.exception;

import com.youran.common.constant.ErrorCode;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/9/20
 */
public class GenerateException extends RuntimeException{

    private ErrorCode errorCode;

    public GenerateException() {
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public GenerateException(String message) {
        super(message);
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public GenerateException(Throwable cause) {
        super(cause.getMessage(),cause);
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public GenerateException(String message,Throwable cause) {
        super(message,cause);
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public GenerateException(ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
