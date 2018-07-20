<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.constant.ErrorCode"/>
<@import "${commonPackage}.util.MessageSourceUtil"/>
<@classCom "自定义异常"/>
public class ${ProjectName}Exception extends RuntimeException{

    private ErrorCode code;

    public ${ProjectName}Exception() {
        super(MessageSourceUtil.getMessage(ErrorCode.INTERNAL_SERVER_ERROR.getDesc()));
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
        super(MessageSourceUtil.getMessage(code.getDesc()));
        this.code = code;
    }

    public ErrorCode getErrorCode() {
        return code;
    }

    public void setErrorCode(ErrorCode code) {
        this.code = code;
    }
}
</#assign>
<#--开始渲染代码-->
package ${packageName}.exception;

<@printImport/>

${code}
