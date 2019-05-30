package com.youran.generate.web.advice;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>Title:异常信息展示</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/10/13
 */
@ControllerAdvice
public class ExceptionTranslator {


    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionTranslator.class);

    /**
     * body参数校验失败
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<String> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return processBindingResult(result);
    }
    /**
     * param参数校验失败
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<String> processValidationError(BindException ex) {
        BindingResult result = ex.getBindingResult();
        return processBindingResult(result);
    }

    /**
     * 参数校验
     * @param result
     * @return
     */
    private ResponseEntity<String> processBindingResult(BindingResult result) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        LOGGER.warn("参数校验失败：{}", JsonUtil.toJSONString(fieldErrors));
        String errorMsg = null;
        if (CollectionUtils.isNotEmpty(fieldErrors)) {
            errorMsg = fieldErrors.get(0).getDefaultMessage();
        }
        return ResponseEntity.status(ErrorCode.BAD_PARAMETER.getValue())
            .body(errorMsg);
    }


    /**
     * http method有误
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<String> processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return buildErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 未传requestbody
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<String> processHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return buildErrorResponse(ErrorCode.BAD_REQUEST,"HttpMessageNotReadableException");
    }

    /**
     * 线程异常
     * @param ex
     * @return
     */
    @ExceptionHandler(ConcurrencyFailureException.class)
    @ResponseBody
    public ResponseEntity<String> processConcurencyError(ConcurrencyFailureException ex) {
        return buildErrorResponse(ErrorCode.CONFLICT);
    }


    /**
     * 唯一键重复
     * @param ex
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public ResponseEntity<String> processDuplicateKeyException(DuplicateKeyException ex) {
        return buildErrorResponse(ErrorCode.DUPLICATE_KEY);
    }



    /**
     * 业务异常捕获
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<String> processBusinessException(BusinessException ex) {
        ex.printStackTrace();
        return buildErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR,ex.getMessage());
    }



    /**
     * 普通异常捕获
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> processRuntimeException(Exception ex) {
        LOGGER.error("系统内部错误",ex);
        return buildErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
    }




    private ResponseEntity<String> buildErrorResponse(ErrorCode errorCode){
        return buildErrorResponse(errorCode,null);
    }

    private ResponseEntity<String> buildErrorResponse(ErrorCode errorCode,String message){
        if(StringUtils.isBlank(message)){
            message = errorCode.getDesc();
        }
        return ResponseEntity.status(errorCode.getValue())
            .body(message);
    }

}
