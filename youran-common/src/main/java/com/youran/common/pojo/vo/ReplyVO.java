package com.youran.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youran.common.constant.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * <p>Title:通用响应对象</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/8/24
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyVO<T> extends AbstractVO {

    public static final String SUCCESS_CODE="0";
    public static final String SUCCESS_MSG="执行成功！";
    public static final String DEFAULT_ERROR_CODE="-1";

    @ApiModelProperty(notes = "响应代码【0成功，非0失败】",example = SUCCESS_CODE,required = true)
    private String code;

    @ApiModelProperty(notes = "结果描述",example = SUCCESS_MSG,required = true)
    private String message;

    @ApiModelProperty(notes = "返回数据")
    private T data;

    public ReplyVO() {
    }

    public ReplyVO(T data) {
        this.data = data;
    }

    public ReplyVO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ReplyVO fail(String message) {
        return new ReplyVO(DEFAULT_ERROR_CODE, message);
    }

    public static ReplyVO fail(ErrorCode errorCode) {
        return new ReplyVO(errorCode.getValue().toString(), errorCode.getDesc());
    }

    public static ReplyVO success() {
        return new ReplyVO(SUCCESS_CODE, SUCCESS_MSG);
    }


    /**
     * 设置数据
     * @param data
     * @return
     */
    public ReplyVO data(T data){
        setData(data);
        return this;
    }

    /**
     * 设置消息
     * @param message
     * @return
     */
    public ReplyVO message(String message){
        this.setMessage(message);
        return this;
    }

    /**
     * 清空返回数据
     * @return
     */
    public ReplyVO clear() {
        this.data = null;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("code", code)
            .append("message", message)
            .append("data", data)
            .toString();
    }
}
