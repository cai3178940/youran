package com.youran.common.pojo.vo;

import com.youran.common.constant.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: 通用响应对象
 * Description:
 * Author: cbb
 * Create Time:2017/8/24 15:51
 */
@ApiModel
public class ReplyVO<T> extends AbstractVO {


    public static final String SUCCESS_CODE="0";
    public static final String SUCCESS_MSG="执行成功！";
    public static final String ERROR_CODE="-1";


    @ApiModelProperty(notes = "响应代码【0正确,非0错误】",example = SUCCESS_CODE,required = true)
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
        return new ReplyVO(ERROR_CODE, message);
    }

    public static ReplyVO fail(ErrorCode errorCode) {
        return new ReplyVO(errorCode.getValue(), errorCode.getDesc());
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
     * 添加返回数据
     * @param key
     * @param value
     * @return
     */
    public ReplyVO add(String key, Object value) {
        Map<String, Object> map;
        if (this.data == null) {
            map = new HashMap<>();
        } else if (this.data instanceof Map) {
            map = (Map<String, Object>) this.data;
        } else {
            throw new RuntimeException("not support");
        }
        map.put(key, value);
        setData((T) map);
        return this;
    }


    /**
     * 删除数据
     * @param keys
     * @return
     */
    public ReplyVO remove(String... keys) {
        if (this.data == null || !(this.data instanceof Map)) {
            return this;
        }
        Map<String, Object> map = (Map<String, Object>) this.data;
        for (String key : keys) {
            map.remove(key);
        }
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

    /**
     * 获取dataMap 的值
     * @param key key
     * @return
     */
    public Object get(String key) {
        if (this.data == null || StringUtils.isBlank(key) || !(this.data instanceof Map)) {
            return null;
        }
        Map<String, Object> map = (Map<String, Object>) this.data;
        return map.get(key);
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
}
