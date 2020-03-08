package com.youran.generate.service;

import com.youran.common.exception.BusinessException;
import com.youran.common.util.Base64Util;
import com.youran.common.util.JsonUtil;
import com.youran.generate.constant.TemplateFileType;
import com.youran.generate.pojo.mapper.CodeTemplateMapper;
import com.youran.generate.pojo.mapper.TemplateFileMapper;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.pojo.po.TemplateFilePO;
import com.youran.generate.util.Zip4jUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 【代码模板】导入导出服务
 *
 * @author: cbb
 * @date: 11/3/2019 15:03
 */
@Service
public class TemplateImportExportService {

    public static final String TEMPLATE_JSON_FILE = "template.json";
    public static final String TEMPLATE_FILE_DIR = "ftl";

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateImportExportService.class);
    @Autowired
    private TemplateFileService templateFileService;
    @Autowired
    private DataDirService dataDirService;
    @Autowired
    private CodeTemplateService codeTemplateService;
    @Autowired
    private TemplateFileOutputService templateFileOutputService;
    @Autowired
    private CodeTemplateAssembleAndCopyService codeTemplateAssembleAndCopyService;

    /**
     * 模板导出
     *
     * @param templateId
     * @return
     */
    public File exportTemplate(Integer templateId) {
        CodeTemplatePO templatePO = codeTemplateAssembleAndCopyService.getAssembledCodeTemplate(templateId, false);
        String exportDir = dataDirService.getTemplateExportDir(templatePO);
        String zipFilePath = exportDir + ".zip";
        File dir = new File(exportDir);
        File outFile = new File(zipFilePath);
        try {
            FileUtils.deleteDirectory(dir);
            FileUtils.deleteQuietly(outFile);
            FileUtils.forceMkdir(dir);
        } catch (IOException e) {
            LOGGER.error("创建导出目录失败", e);
        }
        // 导出模板json文件
        JsonUtil.writeJsonToFile(templatePO, true, new File(dir, TEMPLATE_JSON_FILE));
        // 模板文件输出目录
        String outputDir = exportDir + File.separator + TEMPLATE_FILE_DIR;
        // 把文件输出到目录
        templateFileOutputService.outputTemplateFiles(templatePO.getTemplateFiles(), outputDir);
        // 将文件夹打成压缩包
        Zip4jUtil.compressFolder(dir, outFile);
        return outFile;
    }


    /**
     * 模板导入
     *
     * @param zipFile
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public CodeTemplatePO importTemplate(File zipFile) {
        String importDir = dataDirService.getPathWithoutZipFileSuffix(zipFile);
        // 解压zip包
        Zip4jUtil.extractAll(zipFile, importDir);
        LOGGER.info("将zip包解压到：{}", importDir);
        // json文件所在目录
        String jsonDir = dataDirService.getFirstChildDir(importDir) + File.separator;
        // 模板文件所在目录
        String tplDir = jsonDir + TEMPLATE_FILE_DIR;
        File tplDirFile = new File(tplDir);
        if (!tplDirFile.exists() || !tplDirFile.isDirectory()) {
            throw new BusinessException("导入失败，" + TEMPLATE_FILE_DIR + "目录不存在");
        }
        // 读取模板json文件，并解析成po
        CodeTemplatePO templateFromJson = JsonUtil.parseObjectFromFile(
            new File(jsonDir + TEMPLATE_JSON_FILE), CodeTemplatePO.class);
        if (templateFromJson == null) {
            throw new BusinessException("导入失败");
        }
        CodeTemplatePO templatePO = this.saveTemplate(templateFromJson);
        List<TemplateFilePO> templateFiles = templateFromJson.getTemplateFiles();
        if (CollectionUtils.isNotEmpty(templateFiles)) {
            for (TemplateFilePO templateFile : templateFiles) {
                this.saveTemplateFile(templateFile, templatePO.getTemplateId(), tplDirFile);
            }
        }
        return templatePO;
    }


    /**
     * 把json中解析出来的模板保存到数据库
     *
     * @param templateFromJson
     * @return
     */
    private CodeTemplatePO saveTemplate(CodeTemplatePO templateFromJson) {
        CodeTemplatePO templatePO = CodeTemplateMapper.INSTANCE.copy(templateFromJson);
        codeTemplateService.doSave(templatePO);
        LOGGER.debug("导入模板：{}", JsonUtil.toJSONString(templatePO));
        return templatePO;
    }


    /**
     * 把json中解析出来的模板文件保存到数据库
     *
     * @param templateFileFromJson
     * @param templateId
     * @param tplDir
     * @return
     */
    private TemplateFilePO saveTemplateFile(TemplateFilePO templateFileFromJson,
                                            Integer templateId, File tplDir) {
        TemplateFilePO po = TemplateFileMapper.INSTANCE.copy(templateFileFromJson);
        po.setTemplateId(templateId);
        String filePath = po.getFileDir() + File.separator + po.getFileName();
        File contentFile = new File(tplDir, filePath);
        if (!contentFile.exists() || contentFile.isDirectory()) {
            throw new BusinessException("模板文件缺失：" + po.getFileName());
        }
        if (contentFile.length() > TemplateFilePO.TEMPLATE_FILE_LENGTH_LIMIT) {
            throw new BusinessException("模板文件(" + filePath + ")超过最大长度限制：" +
                FileUtils.byteCountToDisplaySize(TemplateFilePO.TEMPLATE_FILE_LENGTH_LIMIT));
        }
        String content;
        try {
            if (TemplateFileType.BINARY.getValue().equals(po.getFileType())) {
                content = Base64Util.encodeFile(contentFile);
            } else {
                content = FileUtils.readFileToString(contentFile, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            LOGGER.error("读取文件内容异常", e);
            throw new BusinessException("读取模板文件内容异常");
        }
        po.setContent(content);
        templateFileService.doSave(po);
        LOGGER.debug("导入模板文件：{}", JsonUtil.toJSONString(po));
        return po;
    }

}
