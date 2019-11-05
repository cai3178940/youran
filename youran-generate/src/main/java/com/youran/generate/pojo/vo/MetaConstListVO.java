package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.youran.generate.pojo.example.MetaConstExample.*;

/**
 * 常量列表展示对象
 *
 * @author: cbb
 * @date: 2017/5/12
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("constId", constId)
            .append("projectId", projectId)
            .append("constName", constName)
            .append("constRemark", constRemark)
            .append("constType", constType)
            .toString();
    }
}
