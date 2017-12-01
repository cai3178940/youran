package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaProjectExample.*;

/**
 * Title:新增元数据项目DTO
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:05
 */
@ApiModel(description = "新增元数据项目参数")
public class MetaProjectAddDTO extends AbstractDTO {


    @ApiModelProperty(notes = N_PACKAGENAME, example = E_PACKAGENAME)
    @NotNull
    @Length(min=1, max = 100, message = "packageName最大长度不能超过100")
    private String packageName;

    @ApiModelProperty(notes = N_PROJECTNAME, example = E_PROJECTNAME)
    @NotNull
    @Length(min=1, max = 50, message = "projectName最大长度不能超过50")
    private String projectName;

    @ApiModelProperty(notes = N_AUTHOR, example = E_AUTHOR)
    @Length(min=1, max = 25, message = "author最大长度不能超过25")
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
