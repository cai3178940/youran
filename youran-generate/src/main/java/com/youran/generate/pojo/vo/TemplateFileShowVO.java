package com.youran.generate.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.TemplateFileExample.E_CONTENT;
import static com.youran.generate.pojo.example.TemplateFileExample.N_CONTENT;

/**
 * 【模板文件】详情展示对象
 *
 * @author cbb
 * @date 2019/10/24
 */
@ApiModel(description = "【模板文件】详情展示对象")
public class TemplateFileShowVO extends TemplateFileListVO {

    @ApiModelProperty(notes = N_CONTENT, example = E_CONTENT)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

