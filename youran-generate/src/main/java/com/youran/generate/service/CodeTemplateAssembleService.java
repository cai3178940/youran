package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.pojo.po.TemplateFilePO;
import com.youran.generate.pojo.vo.ProgressVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
 * <p>Title: 模板组装服务类</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 11/4/2019 22:48
 */
@Service
public class CodeTemplateAssembleService {

    @Autowired
    private TemplateFileService templateFileService;
    @Autowired
    private CodeTemplateService codeTemplateService;

    /**
     * 获取组装后的模板
     * @param templateId 模板id
     * @param progressConsumer 进度消费者
     * @return
     */
    public CodeTemplatePO getAssembledCodeTemplate(Integer templateId,
                                                   Consumer<ProgressVO> progressConsumer){
        CodeTemplatePO templatePO = codeTemplateService.getCodeTemplate(templateId, true);
        List<TemplateFilePO> templateFiles = templateFileService.getAllTemplateFiles(templateId);
        if(CollectionUtils.isEmpty(templateFiles)){
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"模板中不存在有效的模板文件");
        }
        boolean existEffectiveFile = templateFiles.stream()
            .anyMatch(templateFilePO -> !templateFilePO.getAbstracted());
        if(!existEffectiveFile){
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"模板中不存在有效的模板文件");
        }
        templatePO.setTemplateFiles(templateFiles);
        return templatePO;
    }

}
