package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;

import static com.youran.generate.pojo.example.TemplateFileExample.*;

/**
 * 查询【模板文件】的参数
 *
 * @author cbb
 * @date 2019/10/24
 */
public class TemplateFileQO extends AbstractQO {

    @ApiParam(value = N_FILE_NAME, example = E_FILE_NAME)
    @Length(max = 100, message = "fileName最大长度不能超过{max}")
    private String fileName;

    @ApiParam(value = N_TEMPLATE_ID, example = E_TEMPLATE_ID)
    private Integer templateId;

    @ApiParam(value = N_CONTEXT_TYPE, example = E_CONTEXT_TYPE)
    private Integer contextType;

    @ApiParam(value = N_ABSTRACTED, example = E_ABSTRACTED)
    private Boolean abstracted;

    @ApiParam(value = "文件名排序标识【1升序,-1降序,0不排序】", example = "1")
    private Integer fileNameSortSign;

    @ApiParam(value = "文件目录排序标识【1升序,-1降序,0不排序】", example = "1")
    private Integer fileDirSortSign;

    @ApiParam(value = "创建时间排序标识【1升序,-1降序,0不排序】", example = "1")
    private Integer createdTimeSortSign;

    @ApiParam(value = "修改时间排序标识【1升序,-1降序,0不排序】", example = "1")
    private Integer operatedTimeSortSign;


    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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


    public Integer getFileNameSortSign() {
        return this.fileNameSortSign;
    }

    public void setFileNameSortSign(Integer fileNameSortSign) {
        this.fileNameSortSign = fileNameSortSign;
    }

    public Integer getFileDirSortSign() {
        return this.fileDirSortSign;
    }

    public void setFileDirSortSign(Integer fileDirSortSign) {
        this.fileDirSortSign = fileDirSortSign;
    }

    public Integer getCreatedTimeSortSign() {
        return this.createdTimeSortSign;
    }

    public void setCreatedTimeSortSign(Integer createdTimeSortSign) {
        this.createdTimeSortSign = createdTimeSortSign;
    }

    public Integer getOperatedTimeSortSign() {
        return this.operatedTimeSortSign;
    }

    public void setOperatedTimeSortSign(Integer operatedTimeSortSign) {
        this.operatedTimeSortSign = operatedTimeSortSign;
    }

}

