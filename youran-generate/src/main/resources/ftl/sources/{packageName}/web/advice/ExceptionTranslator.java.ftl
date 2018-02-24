<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "com.alibaba.fastjson.JSONException"/>
<@import "${commonPackage}.constant.ErrorCode"/>
<@import "${commonPackage}.pojo.vo.FieldErrorVO"/>
<@import "${commonPackage}.pojo.vo.ReplyVO"/>
<@import "${commonPackage}.util.JsonUtil"/>
<@import "${packageName}.exception.${ProjectName}Exception"/>
<@import "org.slf4j.Logger"/>
<@import "org.slf4j.LoggerFactory"/>
<@import "org.springframework.core.annotation.AnnotationUtils"/>
<@import "org.springframework.dao.ConcurrencyFailureException"/>
<@import "org.springframework.dao.DuplicateKeyException"/>
<@import "org.springframework.http.HttpStatus"/>
<@import "org.springframework.http.ResponseEntity"/>
<@import "org.springframework.http.converter.HttpMessageNotReadableException"/>
<@import "org.springframework.validation.BindingResult"/>
<@import "org.springframework.validation.FieldError"/>
<@import "org.springframework.web.HttpRequestMethodNotSupportedException"/>
<@import "org.springframework.web.bind.MethodArgumentNotValidException"/>
<@import "org.springframework.web.bind.annotation.ControllerAdvice"/>
<@import "org.springframework.web.bind.annotation.ExceptionHandler"/>
<@import "org.springframework.web.bind.annotation.ResponseBody"/>
<@import "org.springframework.web.bind.annotation.ResponseStatus"/>
<@import "java.util.ArrayList"/>
<@import "java.util.List"/>

<@classCom "异常信息展示"/>
@ControllerAdvice
public class ExceptionTranslator {


    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionTranslator.class);

    /**
     * 参数校验失败
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReplyVO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        LOGGER.warn(JsonUtil.toJSONString(fieldErrors));
        return processFieldErrors(fieldErrors);
    }

    private ReplyVO processFieldErrors(List<FieldError> fieldErrors) {
        ReplyVO dto = new ReplyVO();
        List<FieldErrorVO> errorVOList = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errorVOList.add(new FieldErrorVO(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage()));
        }
        if(fieldErrors.size()>0){
            dto.setCode(ErrorCode.ERR_VALIDATION.getValue());
            dto.setMessage(fieldErrors.get(0).getDefaultMessage());
            dto.setData(errorVOList);
        }

        return dto;
    }


    /**
     * http method有误
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ReplyVO processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return new ReplyVO(ErrorCode.METHOD_NOT_SUPPORTED.getValue(), ErrorCode.METHOD_NOT_SUPPORTED.getDesc());
    }

    /**
     * 未传requestbody
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ReplyVO processHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return new ReplyVO("-1", "HttpMessageNotReadableException");
    }

    /**
     * 线程异常
     * @param ex
     * @return
     */
    @ExceptionHandler(ConcurrencyFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ReplyVO processConcurencyError(ConcurrencyFailureException ex) {
        return new ReplyVO(ErrorCode.CONCURRENCY_FAILURE.getValue(), ErrorCode.CONCURRENCY_FAILURE.getDesc());
    }


    /**
     * 唯一键重复
     * @param ex
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReplyVO processDuplicateKeyException(DuplicateKeyException ex) {
        return new ReplyVO(ErrorCode.INTERNAL_SERVER_ERROR.getValue(), "重复操作");
    }

    /**
     * json解析异常
     * @param ex
     * @return
     */
    @ExceptionHandler(JSONException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReplyVO processJSONException(JSONException ex) {
        return new ReplyVO(ErrorCode.INTERNAL_SERVER_ERROR.getValue(), "参数格式有误："+ex.getMessage());
    }

    /**
     * 自定义异常捕获
     * @param ex
     * @return
     */
    @ExceptionHandler(${ProjectName}Exception.class)
    @ResponseBody
    public ResponseEntity<ReplyVO> process${ProjectName}Exception(${ProjectName}Exception ex) {
        ex.printStackTrace();
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.OK);
        ErrorCode code = ex.getErrorCode();
        ReplyVO replyVO = new ReplyVO(code.getValue(), ex.getMessage());
        return builder.body(replyVO);
    }



    /**
     * 普通异常捕获
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ReplyVO> processRuntimeException(Exception ex) {
        ex.printStackTrace();
        ResponseEntity.BodyBuilder builder;
        ReplyVO replyVO;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            replyVO = new ReplyVO(responseStatus.value().value() + "", responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            replyVO = new ReplyVO(ErrorCode.INTERNAL_SERVER_ERROR.getValue(), ErrorCode.INTERNAL_SERVER_ERROR.getDesc());
        }
        return builder.body(replyVO);
    }

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.web.advice;

<@printImport/>

${code}
