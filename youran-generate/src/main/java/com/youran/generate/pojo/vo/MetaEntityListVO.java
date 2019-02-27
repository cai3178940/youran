package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.MetaEntityExample.*;

/**
 * <p>Title:实体列表展示对象</p>
 * <p>Description:</p>
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

    @ApiModelProperty(notes = N_DESC, example = E_DESC)
    private String desc;

    @ApiModelProperty(notes = N_COMMONCALL, example = E_COMMONCALL)
    private Integer commonCall;

    @ApiModelProperty(notes = N_PAGESIGN, example = E_PAGESIGN)
    private Integer pageSign;

    public Integer getPageSign() {
        return pageSign;
    }

    public void setPageSign(Integer pageSign) {
        this.pageSign = pageSign;
    }


    public Integer getCommonCall() {
        return commonCall;
    }

    public void setCommonCall(Integer commonCall) {
        this.commonCall = commonCall;
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

}
