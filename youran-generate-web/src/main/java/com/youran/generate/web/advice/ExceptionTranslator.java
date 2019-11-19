package com.youran.generate.web.advice;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.pojo.vo.ReplyVO;
import com.youran.common.util.JsonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 异常信息展示
 *
 * @author: cbb
 * @date: 2017/10/13
 */
@ControllerAdvice
public class ExceptionTranslator {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionTranslator.class);

    /**
     * body参数校验失败
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ReplyVO> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return processBindingResult(result);
    }

    /**
     * param参数校验失败
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<ReplyVO> processValidationError(BindException ex) {
        BindingResult result = ex.getBindingResult();
        return processBindingResult(result);
    }

    /**
     * 参数校验
     *
     * @param result
     * @return
     */
    private ResponseEntity<ReplyVO> processBindingResult(BindingResult result) {
        List<ObjectError> errors = result.getAllErrors();
        String errorMsg = null;
        if (CollectionUtils.isNotEmpty(errors)) {
            List<String> errorMsgs = errors.stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            LOGGER.warn(JsonUtil.toJSONString(errorMsgs));
            errorMsg = errorMsgs.stream()
                .collect(Collectors.joining(";"));
        }
        return buildErrorResponse(ErrorCode.BAD_PARAMETER, errorMsg);
    }


    /**
     * http method有误
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<ReplyVO> processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return buildErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 未传requestbody
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ReplyVO> processHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return buildErrorResponse(ErrorCode.BAD_REQUEST, "HttpMessageNotReadableException");
    }

    /**
     * 线程异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConcurrencyFailureException.class)
    @ResponseBody
    public ResponseEntity<ReplyVO> processConcurencyError(ConcurrencyFailureException ex) {
        return buildErrorResponse(ErrorCode.CONFLICT);
    }


    /**
     * 唯一键重复
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public ResponseEntity<ReplyVO> processDuplicateKeyException(DuplicateKeyException ex) {
        return buildErrorResponse(ErrorCode.DUPLICATE_KEY);
    }


    /**
     * 业务异常捕获
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<ReplyVO> processBusinessException(BusinessException ex) {
        LOGGER.error("业务异常", ex);
        ErrorCode errorCode = ex.getErrorCode();
        if (errorCode == null) {
            errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        }
        return buildErrorResponse(errorCode, ex.getMessage());
    }


    /**
     * 普通异常捕获
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ReplyVO> processRuntimeException(Exception ex) {
        LOGGER.error("系统内部错误", ex);
        return buildErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<ReplyVO> buildErrorResponse(ErrorCode errorCode) {
        return buildErrorResponse(errorCode, null);
    }


    private ResponseEntity<ReplyVO> buildErrorResponse(ErrorCode errorCode, String message) {
        if (StringUtils.isBlank(message)) {
            message = errorCode.getDesc();
        }
        return ResponseEntity.status(errorCode.getValue())
            .body(ReplyVO.fail(message));
    }

}
