package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.common.validator.Const;
import com.youran.generate.constant.ContextType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.TemplateFileExample.*;

/**
 * 新增【模板文件】的参数
 *
 * @author cbb
 * @date 2019/10/24
 */
@ApiModel(description = "新增【模板文件】的参数")
public class TemplateFileAddDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_FILE_NAME, example = E_FILE_NAME, required = true)
    @NotNull
    @Length(max = 100)
    private String fileName;

    @ApiModelProperty(notes = N_FILE_DIR, example = E_FILE_DIR, required = true)
    @NotNull
    @Length(max = 300)
    private String fileDir;

    @ApiModelProperty(notes = N_TEMPLATE_ID, example = E_TEMPLATE_ID, required = true)
    @NotNull
    private Integer templateId;

    @ApiModelProperty(notes = N_CONTEXT_TYPE, example = E_CONTEXT_TYPE, required = true, allowableValues = ContextType.VALUES_STR)
    @NotNull
    @Const(constClass = ContextType.class)
    private Integer contextType;

    @ApiModelProperty(notes = N_ABSTRACTED, example = E_ABSTRACTED, required = true)
    @NotNull
    private Boolean abstracted;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public Integer getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getContextType() {
        return this.contextType;
    }

    public void setContextType(Integer contextType) {
        this.contextType = contextType;
    }

    public Boolean getAbstracted() {
        return this.abstracted;
    }

    public void setAbstracted(Boolean abstracted) {
        this.abstracted = abstracted;
    }

}


