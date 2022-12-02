package com.youran.generate.service;

import com.youran.common.exception.BusinessException;
import com.youran.common.util.MD5Util;
import com.youran.common.util.TempDirUtil;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.pojo.po.MetaProjectPO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;

/**
 * 数据目录服务类
 *
 * @author: cbb
 * @date: 11/3/2019 13:58
 */
@Service
public class DataDirService implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataDirService.class);

    /**
     * 本系统名称，这里用于指定导入导出文件目录所在的父文件夹名称
     */
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    private GenerateProperties generateProperties;


    /**
     * 启动以后清空数据目录
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 获取数据目录
        String dataDir = this.getDataDir();
        File dataDirFile = new File(dataDir);
        if (dataDirFile.exists()) {
            FileUtils.deleteDirectory(dataDirFile);
        } else {
            dataDirFile.mkdirs();
        }
        LOGGER.info("初始化数据目录：{}", dataDirFile.getPath());
    }

    /**
     * 获取数据目录
     *
     * @return 数据目录
     */
    private String getDataDir() {
        String dataDir = generateProperties.getDataDir();
        if (StringUtils.isNotBlank(dataDir)) {
            return dataDir;
        }
        // 如果没有配置，则默认使用 /[tmp目录]/[spring.application.name]_[youran.version]
        return TempDirUtil.getTmpDir(appName, false, false)
            + "_" + generateProperties.getVersion();
    }


    /**
     * 获取最新代码目录
     *
     * @param project
     * @param templatePO
     * @return /[dataDir]/code/[projectId]_[projectVersion]_[templateId]_[templateInnerVersion]
     * @see #getDataDir()
     */
    public String getProjectRecentDir(MetaProjectPO project, CodeTemplatePO templatePO) {
        return this.getDataDir()
            + File.separator + "code"
            + File.separator + project.getProjectId()
            + "_" + project.getProjectVersion()
            + "_" + templatePO.getTemplateId()
            + "_" + templatePO.getInnerVersion();
    }

    /**
     * 获取最新仓库目录
     *
     * @param projectId
     * @param templateId
     * @return /[dataDir]/repo/[projectId]_[templateId]_[lastVersion]
     * @see #getDataDir()
     */
    public String getProjectRepoDir(Integer projectId, Integer templateId, Integer lastVersion) {
        return this.getDataDir()
            + File.separator + "repo"
            + File.separator + projectId
            + "_" + templateId
            + "_" + lastVersion;
    }

    /**
     * 获取私钥文件路径
     *
     * @param projectId  项目ID
     * @param privateKey 私钥内容
     * @return
     */
    public String getPrivateKeyFilePath(Integer projectId, String privateKey) {
        return this.getDataDir()
            + File.separator + "privateKey"
            + File.separator + projectId
            + MD5Util.getMD5(privateKey);
    }

    /**
     * 获取项目元数据导出目录
     *
     * @param project
     * @return /[dataDir]/meta_export/[projectId]_[projectVersion]
     * @see #getDataDir()
     */
    public String getProjectExportDir(MetaProjectPO project) {
        return this.getDataDir()
            + File.separator + "meta_export"
            + File.separator + project.getProjectId()
            + "_" + project.getProjectVersion();
    }

    /**
     * 获取项目元数据导入文件路径
     *
     * @return /[dataDir]/meta_import/[currentTimeMillis].zip
     * @see #getDataDir()
     */
    public String getProjectImportFilePath() {
        return this.getDataDir()
            + File.separator + "meta_import"
            + File.separator + System.currentTimeMillis() + ".zip";
    }


    /**
     * 获取最新模板目录
     *
     * @param templatePO
     * @return /[dataDir]/tpl/[templateId]_[templateInnerVersion]
     * @see #getDataDir()
     */
    public String getTemplateRecentDir(CodeTemplatePO templatePO) {
        return this.getDataDir()
            + File.separator + "tpl"
            + File.separator + templatePO.getTemplateId()
            + "_" + templatePO.getInnerVersion();
    }


    /**
     * 获取模板导出目录
     *
     * @param templatePO
     * @return /[dataDir]/template_export/[templateId]_[templateInnerVersion]
     * @see #getDataDir()
     */
    public String getTemplateExportDir(CodeTemplatePO templatePO) {
        return this.getDataDir()
            + File.separator + "template_export"
            + File.separator + templatePO.getTemplateId()
            + "_" + templatePO.getInnerVersion();
    }

    /**
     * 获取模板导入文件路径
     *
     * @return /[dataDir]/template_import/[currentTimeMillis].zip
     * @see #getDataDir()
     */
    public String getTemplateImportFilePath() {
        return this.getDataDir()
            + File.separator + "template_import"
            + File.separator + System.currentTimeMillis() + ".zip";
    }

    /**
     * 根据zip文件去除后缀的路径
     *
     * @return
     */
    public String getPathWithoutZipFileSuffix(File zipFile) {
        String path = zipFile.getPath();
        // 去除末尾.zip
        return path.substring(0, path.length() - 4);
    }

    /**
     * 获取父目录下第一个子目录
     *
     * @param parentDir
     * @return
     */
    public String getFirstChildDir(String parentDir) {
        File parent = new File(parentDir);
        if (!parent.exists()) {
            LOGGER.error("父目录不存在：{}", parentDir);
            throw new BusinessException();
        }
        if (!parent.isDirectory()) {
            LOGGER.error("该路径不是文件夹：{}", parentDir);
            throw new BusinessException();
        }
        return Arrays.stream(parent.listFiles())
            .filter(File::isDirectory)
            .map(File::getPath)
            .findFirst()
            .orElse(null);
    }


}
