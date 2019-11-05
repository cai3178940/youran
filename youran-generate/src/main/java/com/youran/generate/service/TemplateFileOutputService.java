package com.youran.generate.service;

import com.youran.common.exception.BusinessException;
import com.youran.generate.pojo.po.TemplateFilePO;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 【模板文件】输出服务
 *
 * @author: cbb
 * @date: 11/3/2019 15:35
 */
@Service
public class TemplateFileOutputService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateFileOutputService.class);

    /**
     * 把文件输出到目录
     *
     * @param templateFiles 模板文件列表
     * @param outputDir     输出目录
     */
    public void outputTemplateFiles(List<TemplateFilePO> templateFiles, String outputDir) {
        File outputDirFile = new File(outputDir);
        if (!outputDirFile.exists()) {
            outputDirFile.mkdirs();
        }
        try {
            for (TemplateFilePO templateFile : templateFiles) {
                String contentFilePath = outputDir + templateFile.getFileDir() + File.separator + templateFile.getFileName();
                File contentFile = new File(contentFilePath);
                FileUtils.writeStringToFile(contentFile, templateFile.getContent(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            LOGGER.error("模板文件导出异常", e);
            throw new BusinessException("模板文件导出异常");
        }
    }

}
