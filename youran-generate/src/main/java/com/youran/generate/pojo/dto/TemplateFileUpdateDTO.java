package com.youran.generate.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.TemplateFileExample.E_FILE_ID;
import static com.youran.generate.pojo.example.TemplateFileExample.N_FILE_ID;

/**
 * 修改【模板文件】的参数
 *
 * @author cbb
 * @date 2019/10/24
 */
@ApiModel(description = "修改【模板文件】的参数")
public class TemplateFileUpdateDTO extends TemplateFileAddDTO {

    @ApiModelProperty(notes = N_FILE_ID, example = E_FILE_ID, required = true)
    @NotNull
    private Integer fileId;


    public Integer getFileId() {
        return this.fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }


}

