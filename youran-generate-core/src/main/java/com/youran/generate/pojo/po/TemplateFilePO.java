package com.youran.generate.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.TemplateFileType;
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
     * 文件类型【1普通模板文件、2抽象模板文件、3二进制模板文件、4父路径渲染文件、5文件名渲染文件】
     *
     * @see com.youran.generate.constant.TemplateFileType
     */
    private Integer fileType;

    /**
     * 内容
     */
    @JsonIgnore
    private String content;

    /**
     * 父路径渲染文件
     */
    @JsonIgnore
    private transient TemplateFilePO parentPathTemplateFile;
    /**
     * 文件名渲染文件
     */
    @JsonIgnore
    private transient TemplateFilePO filenameTemplateFile;

    /**
     * 判断是否为内容模板文件
     * @return
     */
    @JsonIgnore
    public boolean isContentFile(){
        return this.isBinaryFile() || this.isGeneralFile();
    }

    /**
     * 判断是否为普通模板文件
     * @return
     */
    @JsonIgnore
    public boolean isGeneralFile(){
        return TemplateFileType.GENERAL.getValue().equals(this.fileType);
    }

    /**
     * 判断是否为二进制内容文件
     * @return
     */
    @JsonIgnore
    public boolean isBinaryFile(){
        return TemplateFileType.BINARY.getValue().equals(this.fileType);
    }

    /**
     * 获取模板文件路径
     *
     * @return
     */
    public String fetchFilePath() {
        String filename = this.fileDir + "/" + fileName;
        return FilenameUtils.normalize(filename.replaceAll("\\/+", "/"), true);
    }

    /**
     * 针对文件名渲染文件，获取对应的文件内容路径
     *
     * @return
     */
    public String fetchContentPathForFilenameFile() {
        if (TemplateFileType.FILENAME.getValue().equals(this.fileType)) {
            if (!this.fileName.startsWith("__")) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                    "文件名渲染文件必须是双下划线开头：" + this.fetchFilePath());
            }
            String filename = this.fileDir + "/" + this.fileName.substring(2);
            return FilenameUtils.normalize(filename.replaceAll("\\/+", "/"), true);
        }
        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR,
            "该模板文件不是文件名渲染文件：" + this.fetchFilePath());
    }


    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TemplateFilePO getParentPathTemplateFile() {
        return parentPathTemplateFile;
    }

    public void setParentPathTemplateFile(TemplateFilePO parentPathTemplateFile) {
        this.parentPathTemplateFile = parentPathTemplateFile;
    }

    public TemplateFilePO getFilenameTemplateFile() {
        return filenameTemplateFile;
    }

    public void setFilenameTemplateFile(TemplateFilePO filenameTemplateFile) {
        this.filenameTemplateFile = filenameTemplateFile;
    }


}

