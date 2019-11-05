package com.youran.generate.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 模板文件
 *
 * @author cbb
 * @date 2019/10/24
 */
public class TemplateFilePO extends BasePO {

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
     * 内容
     */
    @JsonIgnore
    private String content;

    /**
     * 构建模板文件路径
     *
     * @return
     */
    public String buildFilePath() {
        return this.fileDir + fileName;
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

