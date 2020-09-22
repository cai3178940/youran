package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import com.youran.generate.pojo.dto.LabelDTO;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

import static com.youran.generate.pojo.example.MetaEntityExample.*;

/**
 * 实体列表展示对象
 *
 * @author: cbb
 * @date: 2017/5/12
 */
public class MetaEntityListVO extends AbstractVO {

    @ApiModelProperty(notes = N_ENTITYID, example = E_ENTITYID)
    private Integer entityId;

    @ApiModelProperty(notes = N_PROJECTID, example = E_PROJECTID)
    private Integer projectId;

    @ApiModelProperty(notes = N_SCHEMANAME, example = E_SCHEMANAME)
    private String schemaName;

    @ApiModelProperty(notes = N_CLASSNAME, example = E_CLASSNAME)
    private String className;

    @ApiModelProperty(notes = N_TABLENAME, example = E_TABLENAME)
    private String tableName;

    @ApiModelProperty(notes = N_TITLE, example = E_TITLE)
    private String title;

    @ApiModelProperty(notes = N_MODULE, example = E_MODULE)
    private String module;

    @ApiModelProperty(notes = "标签")
    private List<LabelDTO> labels;

    @ApiModelProperty(notes = N_DESC, example = E_DESC)
    private String desc;

    @ApiModelProperty(notes = N_PAGESIGN, example = E_PAGESIGN)
    private Boolean pageSign;

    public Boolean getPageSign() {
        return pageSign;
    }

    public void setPageSign(Boolean pageSign) {
        this.pageSign = pageSign;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<LabelDTO> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelDTO> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("entityId", entityId)
            .append("projectId", projectId)
            .append("schemaName", schemaName)
            .append("className", className)
            .append("tableName", tableName)
            .append("title", title)
            .append("labels", labels)
            .append("desc", desc)
            .append("pageSign", pageSign)
            .toString();
    }
}
