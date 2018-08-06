<#include "/common.ftl">

<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.constant.ErrorCode")/>
<@call this.addImport("${this.commonPackage}.util.MessageSourceUtil")/>
<@call this.printClassCom("自定义异常")/>
public class ${this.projectNameUpper}Exception extends RuntimeException{

    private ErrorCode code;

    public ${this.projectNameUpper}Exception() {
        super(MessageSourceUtil.getMessage(ErrorCode.INTERNAL_SERVER_ERROR.getDesc()));
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;

    }

    public ${this.projectNameUpper}Exception(String message) {
        super(message);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ${this.projectNameUpper}Exception(Throwable cause) {
        super(cause.getMessage(),cause);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ${this.projectNameUpper}Exception(String message,Throwable cause) {
        super(message,cause);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ${this.projectNameUpper}Exception(ErrorCode code) {
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
package ${this.packageName}.exception;

<@call this.printImport()/>

${code}
