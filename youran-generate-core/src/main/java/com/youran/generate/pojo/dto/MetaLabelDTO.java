package com.youran.generate.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

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
     * 候选项
     */
    private List<String> candidate;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getCandidate() {
        return candidate;
    }

    public void setCandidate(List<String> candidate) {
        this.candidate = candidate;
    }
}
