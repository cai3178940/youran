package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;

/**
 * 代码文件内容查询参数
 *
 * @author cbb
 * @date 2019/11/5
 */
public class CodeContentQO extends AbstractQO {

    @ApiParam(value = "项目id", example = "1")
    @NotNull
    private Integer projectId;

    @ApiParam(value = "项目版本号", example = "1")
    @NotNull
    private Integer projectVersion;

    @ApiParam(value = "模板id", example = "1")
    @NotNull
    private Integer templateId;

    @ApiParam(value = "模板内部版本号", example = "1")
    @NotNull
    private Integer templateInnerVersion;

    @ApiParam(value = "文件路径", example = "1")
    @NotNull
    private String filePath;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(Integer projectVersion) {
        this.projectVersion = projectVersion;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getTemplateInnerVersion() {
        return templateInnerVersion;
    }

    public void setTemplateInnerVersion(Integer templateInnerVersion) {
        this.templateInnerVersion = templateInnerVersion;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
