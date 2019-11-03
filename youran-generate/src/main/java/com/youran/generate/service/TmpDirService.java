package com.youran.generate.service;

import com.youran.common.exception.BusinessException;
import com.youran.common.util.TempDirUtil;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.pojo.po.MetaProjectPO;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;

/**
 * <p>Title: 临时目录服务类</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 11/3/2019 13:58
 */
@Service
public class TmpDirService implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(TmpDirService.class);

    /**
     * 本系统名称，这里用于指定导入导出文件目录所在的父文件夹名称
     */
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    private GenerateProperties generateProperties;

    /**
     * 启动以后清空临时文件夹根目录
     * /[tmp目录]/[spring.application.name]/[youran.version]
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 获取临时文件夹根目录
        String tmpRootDir = this.getTmpRootDir();
        File tmpRootDirFile = new File(tmpRootDir);
        FileUtils.deleteDirectory(tmpRootDirFile);
    }

    /**
     * 获取临时文件夹根目录
     * @return /[tmp目录]/[spring.application.name]_[youran.version]
     */
    private String getTmpRootDir(){
        return TempDirUtil.getTmpDir(appName, false, false)
            + "_" +generateProperties.getVersion();
    }


    /**
     * 获取最新代码目录
     * @param project
     * @return /[tmpRootDir]/code/[projectId]_[projectVersion]
     * @see #getTmpRootDir()
     */
    public String getProjectRecentDir(MetaProjectPO project){
        return this.getTmpRootDir()
            + File.separator + "code"
            + File.separator + project.getProjectId()
            + "_" + project.getProjectVersion();
    }

    /**
     * 获取项目元数据导出目录
     * @param project
     * @return /[tmpRootDir]/meta_export/[projectId]_[projectVersion]
     * @see #getTmpRootDir()
     */
    public String getProjectExportDir(MetaProjectPO project) {
        return this.getTmpRootDir()
            + File.separator + "meta_export"
            + File.separator + project.getProjectId()
            + "_" + project.getProjectVersion();
    }

    /**
     * 获取项目元数据导入文件路径
     * @return /[tmpRootDir]/meta_import/[currentTimeMillis].zip
     * @see #getTmpRootDir()
     */
    public String getProjectImportFilePath() {
        return this.getTmpRootDir()
            + File.separator + "meta_import"
            + File.separator + System.currentTimeMillis() + ".zip";
    }


    /**
     * 获取最新模板目录
     * @param templatePO
     * @return /[tmpRootDir]/tpl/[templateId]_[templateInnerVersion]
     * @see #getTmpRootDir()
     */
    public String getTemplateRecentDir(CodeTemplatePO templatePO){
        return this.getTmpRootDir()
            + File.separator + "tpl"
            + File.separator + templatePO.getTemplateId()
            + "_" + templatePO.getInnerVersion();
    }


    /**
     * 获取模板导出目录
     * @param templatePO
     * @return /[tmpRootDir]/template_export/[templateId]_[templateInnerVersion]
     * @see #getTmpRootDir()
     */
    public String getTemplateExportDir(CodeTemplatePO templatePO) {
        return this.getTmpRootDir()
            + File.separator + "template_export"
            + File.separator + templatePO.getTemplateId()
            + "_" + templatePO.getInnerVersion();
    }

    /**
     * 获取模板导入文件路径
     * @return /[tmpRootDir]/template_import/[currentTimeMillis].zip
     * @see #getTmpRootDir()
     */
    public String getTemplateImportFilePath() {
        return this.getTmpRootDir()
            + File.separator + "template_import"
            + File.separator + System.currentTimeMillis() + ".zip";
    }

    /**
     * 根据zip文件去除后缀的路径
     * @return
     */
    public String getPathWithoutZipFileSuffix(File zipFile){
        String path = zipFile.getPath();
        // 去除末尾.zip
        return path.substring(0,path.length()-4);
    }

    /**
     * 获取父目录下第一个子目录
     * @param parentDir
     * @return
     */
    public String getFirstChildDir(String parentDir){
        File parent = new File(parentDir);
        if(!parent.exists()){
            LOGGER.error("父目录不存在：{}",parentDir);
            throw new BusinessException();
        }
        if(!parent.isDirectory()){
            LOGGER.error("该路径不是文件夹：{}",parentDir);
            throw new BusinessException();
        }
        return Arrays.stream(parent.listFiles())
            .filter(File::isDirectory)
            .map(File::getPath)
            .findFirst()
            .orElse(null);
    }



}
