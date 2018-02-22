<#include "/common.ftl">
package ${packageName}.exception;

import ${commonPackage}.constant.ErrorCode;

<@classCom "自定义异常"></@classCom>
public class ${ProjectName}Exception extends RuntimeException{

    private ErrorCode code;

    public ${ProjectName}Exception() {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ${ProjectName}Exception(String message) {
        super(message);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ${ProjectName}Exception(Throwable cause) {
        super(cause.getMessage(),cause);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ${ProjectName}Exception(String message,Throwable cause) {
        super(message,cause);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ${ProjectName}Exception(ErrorCode code) {
        super(code.getDesc());
        this.code = code;
    }

    public ErrorCode getErrorCode() {
        return code;
    }

    public void setErrorCode(ErrorCode code) {
        this.code = code;
    }
}
