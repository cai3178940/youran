package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 标签对象
 *
 * @author: cbb
 * @date: 2020-09-22
 */
@ApiModel(description = "标签对象")
public class LabelDTO extends AbstractDTO {

    /**
     * 标签key
     */
    @ApiModelProperty(notes = "标签key", example = "abc")
    @NotNull
    private String key;
    /**
     * 标签value
     */
    @ApiModelProperty(notes = "标签value", example = "123")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
