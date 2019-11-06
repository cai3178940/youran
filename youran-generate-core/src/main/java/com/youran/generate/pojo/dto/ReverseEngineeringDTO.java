package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 反向工程参数
 *
 * @author: cbb
 * @date: 2018/5/30
 */
@ApiModel(description = "反向工程参数")
public class ReverseEngineeringDTO extends AbstractDTO {

    @ApiModelProperty(notes = "所属项目id", example = "1")
    @NotNull
    private Integer projectId;

    @ApiModelProperty(notes = "DDL语句", example = "create table ...")
    @NotNull
    @Length(min = 1, max = 10000, message = "ddl最大长度不能超过{max}")
    private String ddl;

    @ApiModelProperty(notes = "数据库类型", example = "mysql")
    @NotNull
    @Length(min = 1, max = 20, message = "dbType最大长度不能超过{max}")
    private String dbType;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getDdl() {
        return ddl;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
}
