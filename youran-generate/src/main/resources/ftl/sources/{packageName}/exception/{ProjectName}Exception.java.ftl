<#include "/common.ftl">
package ${packageName}.exception;

import ${commonPackage}.constant.ErrorCode;

<@classCom "自定义异常"></@classCom>
public class ${ProjectName}Exception extends RuntimeException{

    private ErrorCode errorCode;

    public ${ProjectName}Exception() {
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ${ProjectName}Exception(String message) {
    super(message);
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ${ProjectName}Exception(Throwable cause) {
        super(cause.getMessage(),cause);
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ${ProjectName}Exception(String message,Throwable cause) {
        super(message,cause);
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ${ProjectName}Exception(ErrorCode errorCode) {
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
