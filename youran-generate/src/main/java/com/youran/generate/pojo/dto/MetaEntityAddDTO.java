package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.common.validator.Const;
import com.youran.common.constant.BoolConst;
import com.youran.generate.constant.PatternConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.youran.generate.pojo.example.MetaEntityExample.*;


/**
 * Title:新增实体DTO
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:05
 */
@ApiModel(description = "新增实体参数")
public class MetaEntityAddDTO extends AbstractDTO {


    @ApiModelProperty(notes = N_PROJECTID, example = E_PROJECTID)
    @NotNull
    private Integer projectId;

    @ApiModelProperty(notes = N_SCHEMANAME, example = E_SCHEMANAME)
    @Length(max = 20, message = "schemaName最大长度不能超过20")
    private String schemaName;

    @ApiModelProperty(notes = N_CLASSNAME, example = E_CLASSNAME)
    @NotNull
    @Length(max = 50, message = "className最大长度不能超过50")
    @Pattern(regexp = PatternConst.CLASS_NAME, message = PatternConst.CLASS_NAME_MSG)
    private String className;

    @ApiModelProperty(notes = N_TABLENAME, example = E_TABLENAME)
    @NotNull
    @Length(max = 50, message = "tableName最大长度不能超过50")
    private String tableName;

    @ApiModelProperty(notes = N_TITLE, example = E_TITLE)
    @NotNull
    @Length(max = 25, message = "title最大长度不能超过25")
    private String title;

    @ApiModelProperty(notes = N_DESC, example = E_DESC)
    @Length(max = 250, message = "desc最大长度不能超过250")
    private String desc;

    @ApiModelProperty(notes = N_COMMONCALL, example = E_COMMONCALL)
    @Const(constClass = BoolConst.class)
    private Integer commonCall;

    @ApiModelProperty(notes = N_PAGESIGN, example = E_PAGESIGN)
    @Const(constClass = BoolConst.class)
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
