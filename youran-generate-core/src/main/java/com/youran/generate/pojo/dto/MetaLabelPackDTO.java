package com.youran.generate.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.generate.constant.LabelType;

import java.util.List;

/**
 * 标签元数据包
 *
 * @author: cbb
 * @date: 2020-09-13
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaLabelPackDTO extends AbstractDTO {

    /**
     * 项目标签元数据
     */
    private List<MetaLabelDTO> project;
    /**
     * 实体标签元数据
     */
    private List<MetaLabelDTO> entity;
    /**
     * 字段标签元数据
     */
    private List<MetaLabelDTO> field;

    public List<MetaLabelDTO> fetchByType(String labelType) {
        if (LabelType.PROJECT.equals(labelType)) {
            return this.project;
        } else if (LabelType.ENTITY.equals(labelType)) {
            return this.entity;
        } else if (LabelType.FIELD.equals(labelType)) {
            return this.field;
        }
        return null;
    }

    public List<MetaLabelDTO> getProject() {
        return project;
    }

    public void setProject(List<MetaLabelDTO> project) {
        this.project = project;
    }

    public List<MetaLabelDTO> getEntity() {
        return entity;
    }

    public void setEntity(List<MetaLabelDTO> entity) {
        this.entity = entity;
    }

    public List<MetaLabelDTO> getField() {
        return field;
    }

    public void setField(List<MetaLabelDTO> field) {
        this.field = field;
    }
}
