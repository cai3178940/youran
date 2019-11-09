package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.pojo.mapper.CodeTemplateMapper;
import com.youran.generate.pojo.mapper.TemplateFileMapper;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.pojo.po.TemplateFilePO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * @param templateId 模板id
     * @return
     */
    public CodeTemplatePO getAssembledCodeTemplate(Integer templateId) {
        CodeTemplatePO templatePO = codeTemplateService.getCodeTemplate(templateId, true);
        List<TemplateFilePO> templateFiles = templateFileService.getAllTemplateFiles(templateId);
        if (CollectionUtils.isEmpty(templateFiles)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "模板中不存在有效的模板文件");
        }
        boolean existEffectiveFile = templateFiles.stream()
            .anyMatch(templateFilePO -> !templateFilePO.getAbstracted());
        if (!existEffectiveFile) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "模板中不存在有效的模板文件");
        }
        templatePO.setTemplateFiles(templateFiles);
        return templatePO;
    }

    /**
     * 模板复制
     *
     * @param templateId 待复制的模板id
     * @return 复制后生成的新模板
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public CodeTemplatePO copyCodeTemplate(Integer templateId) {
        CodeTemplatePO old = this.getAssembledCodeTemplate(templateId);
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
