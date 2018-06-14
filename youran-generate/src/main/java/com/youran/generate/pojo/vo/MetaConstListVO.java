package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.MetaConstExample.*;

/**
 * Title:元数据常量列表展示对象
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 17:54
 */
public class MetaConstListVO extends AbstractVO {

    @ApiModelProperty(notes = N_CONSTID, example = E_CONSTID)
    private Integer constId;

    @ApiModelProperty(notes = N_PROJECTID, example = E_PROJECTID)
    private Integer projectId;

    @ApiModelProperty(notes = N_CONSTNAME, example = E_CONSTNAME)
    private String constName;

    @ApiModelProperty(notes = N_CONSTREMARK, example = E_CONSTREMARK)
    private String constRemark;

    @ApiModelProperty(notes = N_CONSTTYPE, example = E_CONSTTYPE)
    private Integer constType;

    public Integer getConstId() {
        return constId;
    }

    public void setConstId(Integer constId) {
        this.constId = constId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName = constName;
    }

    public String getConstRemark() {
        return constRemark;
    }

    public void setConstRemark(String constRemark) {
        this.constRemark = constRemark;
    }

    public Integer getConstType() {
        return constType;
    }

    public void setConstType(Integer constType) {
        this.constType = constType;
    }
}
