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
     * 标签名称
     */
    private String name;
    /**
     * 标签描述
     */
    private String desc;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getCandidate() {
        return candidate;
    }

    public void setCandidate(List<String> candidate) {
        this.candidate = candidate;
    }
}
