<#include "/common.ftl">

<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.constant.ErrorCode")/>
<@call this.addImport("${this.commonPackage}.util.MessageSourceUtil")/>
<@call this.addImport("io.swagger.annotations.ApiModel")/>
<@call this.addImport("io.swagger.annotations.ApiModelProperty")/>
<@call this.addImport("org.apache.commons.lang3.StringUtils")/>
<@call this.addImport("java.util.HashMap")/>
<@call this.addImport("java.util.Map")/>
<@call this.printClassCom("通用响应对象")/>
@ApiModel
public class ReplyVO<T> extends AbstractVO {

    public static final String SUCCESS_CODE = "0";
    public static final String DEFAULT_SUCCESS_MSG_KEY = "success";
    public static final String DEFAULT_ERROR_CODE = "-1";

    @ApiModelProperty(notes = "响应代码【0正确,非0错误】", example = SUCCESS_CODE, required = true)
    private String code;

    @ApiModelProperty(notes = "结果描述", example = "success", required = true)
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
        return new ReplyVO(errorCode.getValue(), MessageSourceUtil.getMessage(errorCode.getDesc()));
    }

    public static ReplyVO success() {
        return new ReplyVO(SUCCESS_CODE, MessageSourceUtil.getMessage("reply.success", DEFAULT_SUCCESS_MSG_KEY));
    }


    /**
     * 设置数据
     *
     * @param data
     * @return
     */
    public ReplyVO data(T data) {
        setData(data);
        return this;
    }

    /**
     * 添加返回数据
     *
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
     *
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
     *
     * @return
     */
    public ReplyVO clear() {
        this.data = null;
        return this;
    }

    /**
     * 获取dataMap 的值
     *
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
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.pojo.vo;

<@call this.printImport()/>

${code}
