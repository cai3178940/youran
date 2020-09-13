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
    private List<MetaLabelDTO> projectMetaLabel;
    /**
     * 实体标签元数据
     */
    private List<MetaLabelDTO> entityMetaLabel;
    /**
     * 字段标签元数据
     */
    private List<MetaLabelDTO> fieldMetaLabel;

    public List<MetaLabelDTO> fetchByType(String labelType) {
        if (LabelType.PROJECT.equals(labelType)) {
            return this.projectMetaLabel;
        } else if (LabelType.ENTITY.equals(labelType)) {
            return this.entityMetaLabel;
        } else if (LabelType.FIELD.equals(labelType)) {
            return this.fieldMetaLabel;
        }
        return null;
    }


    public List<MetaLabelDTO> getProjectMetaLabel() {
        return projectMetaLabel;
    }

    public void setProjectMetaLabel(List<MetaLabelDTO> projectMetaLabel) {
        this.projectMetaLabel = projectMetaLabel;
    }

    public List<MetaLabelDTO> getEntityMetaLabel() {
        return entityMetaLabel;
    }

    public void setEntityMetaLabel(List<MetaLabelDTO> entityMetaLabel) {
        this.entityMetaLabel = entityMetaLabel;
    }

    public List<MetaLabelDTO> getFieldMetaLabel() {
        return fieldMetaLabel;
    }

    public void setFieldMetaLabel(List<MetaLabelDTO> fieldMetaLabel) {
        this.fieldMetaLabel = fieldMetaLabel;
    }
}
