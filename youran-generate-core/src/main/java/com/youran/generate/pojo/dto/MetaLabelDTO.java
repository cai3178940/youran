package com.youran.generate.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youran.generate.constant.EditType;

import java.util.List;

/**
 * 标签元数据
 *
 * @author: cbb
 * @date: 2020-09-13
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaLabelDTO {

    /**
     * 标签key
     */
    private String key;
    /**
     * 编辑方式
     * @see EditType
     */
    private Integer editType;
    /**
     * 候选项
     */
    private List<String> candidate;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getEditType() {
        return editType;
    }

    public void setEditType(Integer editType) {
        this.editType = editType;
    }

    public List<String> getCandidate() {
        return candidate;
    }

    public void setCandidate(List<String> candidate) {
        this.candidate = candidate;
    }
}
