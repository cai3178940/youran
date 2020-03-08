package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.TemplateFileType;
import com.youran.generate.pojo.mapper.CodeTemplateMapper;
import com.youran.generate.pojo.mapper.TemplateFileMapper;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.pojo.po.TemplateFilePO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 模板组装及复制服务类
 *
 * @author: cbb
 * @date: 11/4/2019 22:48
 */
@Service
public class CodeTemplateAssembleAndCopyService {

    @Autowired
    private TemplateFileService templateFileService;
    @Autowired
    private CodeTemplateService codeTemplateService;

    /**
     * 获取组装后的模板
     *
     * @param templateId           模板id
     * @param checkAndAssembleFile 是否校验并组装每个文件
     * @return
     */
    public CodeTemplatePO getAssembledCodeTemplate(Integer templateId, boolean checkAndAssembleFile) {
        CodeTemplatePO templatePO = codeTemplateService.getCodeTemplate(templateId, true);
        List<TemplateFilePO> templateFiles = templateFileService.getAllTemplateFiles(templateId);
        if (CollectionUtils.isEmpty(templateFiles)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "模板中不存在有效的模板文件");
        }
        // 校验并组装每个模板文件
        if (checkAndAssembleFile) {
            Map<TemplateFileType, List<TemplateFilePO>> map = templateFiles.stream()
                .collect(Collectors.groupingBy(e -> TemplateFileType.find(e.getFileType())));

            if (CollectionUtils.isEmpty(map.get(TemplateFileType.GENERAL))
                && CollectionUtils.isEmpty(map.get(TemplateFileType.BINARY))) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "模板中不存在有效的模板文件");
            }
            List<TemplateFilePO> parentPathFiles = map.get(TemplateFileType.PARENT_PATH);
            Map<String, TemplateFilePO> parentPathFilesMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(parentPathFiles)) {
                parentPathFilesMap = parentPathFiles.stream()
                    .collect(Collectors.toMap(
                        e -> e.getFileDir(),
                        e -> e,
                        (e1, e2) -> {
                            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                                String.format("该目录下存在多个父路径渲染文件：%s", e1.getFileDir()));
                        }));
            }


            List<TemplateFilePO> filenameFiles = map.get(TemplateFileType.FILENAME);

            Map<String, TemplateFilePO> filenameFilesMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(filenameFiles)) {
                filenameFilesMap = filenameFiles.stream()
                    .collect(Collectors.toMap(e -> e.fetchContentPathForFilenameFile(), e -> e));
            }

            // 组装每个模板文件
            for (TemplateFilePO filePO : templateFiles) {
                this.assembleTemplateFilePO(filePO, parentPathFilesMap, filenameFilesMap);
            }
        }
        templatePO.setTemplateFiles(templateFiles);
        return templatePO;
    }

    /**
     * 组装模板文件
     *
     * @param filePO
     * @param parentPathFilesMap 所有父路径渲染文件
     * @param filenameFilesMap   所有文件名渲染文件
     */
    private void assembleTemplateFilePO(TemplateFilePO filePO,
                                        Map<String, TemplateFilePO> parentPathFilesMap,
                                        Map<String, TemplateFilePO> filenameFilesMap) {
        // 非内容文件不需要组装
        if (!filePO.isContentFile()) {
            return;
        }
        TemplateFilePO parentPathFile = parentPathFilesMap.get(filePO.getFileDir());
        filePO.setParentPathTemplateFile(parentPathFile);
        TemplateFilePO filenameFile = filenameFilesMap.get(filePO.fetchFilePath());
        filePO.setFilenameTemplateFile(filenameFile);
    }


    /**
     * 模板复制
     *
     * @param templateId 待复制的模板id
     * @return 复制后生成的新模板
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public CodeTemplatePO copyCodeTemplate(Integer templateId) {
        CodeTemplatePO old = this.getAssembledCodeTemplate(templateId, false);
        CodeTemplatePO template = CodeTemplateMapper.INSTANCE.copy(old);
        template.setSysDefault(false);
        template.setCode("copy_" + System.currentTimeMillis());
        template.setName("复制的模板");
        template.setTemplateVersion("0.0.1");
        codeTemplateService.doSave(template);
        // 开始复制模板文件
        for (TemplateFilePO oldFile : old.getTemplateFiles()) {
            TemplateFilePO filePO = TemplateFileMapper.INSTANCE.copy(oldFile);
            filePO.setTemplateId(template.getTemplateId());
            templateFileService.doSave(filePO);
        }
        return template;
    }

}
