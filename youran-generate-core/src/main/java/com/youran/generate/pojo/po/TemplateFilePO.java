package com.youran.generate.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.io.FilenameUtils;

/**
 * 模板文件
 *
 * @author cbb
 * @date 2019/10/24
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemplateFilePO extends BasePO {

    public static final long TEMPLATE_FILE_LENGTH_LIMIT = 10 * 1024 * 1024L;

    /**
     * 主键ID
     */
    private Integer fileId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件目录
     */
    private String fileDir;

    /**
     * 模板id
     */
    private Integer templateId;

    /**
     * 上下文类型【1全局、2实体、3常量】
     *
     * @see com.youran.generate.constant.ContextType
     */
    private Integer contextType;

    /**
     * 是否抽象文件
     */
    private Boolean abstracted;

    /**
     * 是否二进制文件
     */
    private Boolean binary;

    /**
     * 内容
     */
    @JsonIgnore
    private String content;

    /**
     * 获取模板文件路径
     *
     * @return
     */
    public String fetchFilePath() {
        String filename = this.fileDir + "/" + fileName;
        return FilenameUtils.normalize(filename.replaceAll("\\/+", "/"), true);
    }

    public Boolean getBinary() {
        return binary;
    }

    public void setBinary(Boolean binary) {
        this.binary = binary;
    }

    public Integer getFileId() {
        return this.fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}

