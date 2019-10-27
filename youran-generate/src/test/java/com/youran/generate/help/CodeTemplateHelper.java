package com.youran.generate.help;

import com.youran.common.util.SafeUtil;
import com.youran.generate.pojo.dto.CodeTemplateAddDTO;
import com.youran.generate.pojo.dto.CodeTemplateUpdateDTO;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.service.CodeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.youran.generate.pojo.example.CodeTemplateExample.*;

@Component
public class CodeTemplateHelper {

    @Autowired
    private CodeTemplateService codeTemplateService;

    /**
     * 生成add测试数据
     * @return
     */
    public CodeTemplateAddDTO getCodeTemplateAddDTO(){
        CodeTemplateAddDTO dto = new CodeTemplateAddDTO();
        dto.setName(E_NAME);
        dto.setTemplateType(SafeUtil.getInteger(E_TEMPLATE_TYPE));
        dto.setTemplateVersion(E_TEMPLATE_VERSION);
        dto.setSysLowVersion(E_SYS_LOW_VERSION);
        dto.setSysDefault(SafeUtil.getBoolean(E_SYS_DEFAULT));
        dto.setRemark(E_REMARK);
        dto.setFromTemplateId(SafeUtil.getInteger(E_FROM_TEMPLATE_ID));
        return dto;
    }


    /**
     * 生成update测试数据
     * @return
     */
    public CodeTemplateUpdateDTO getCodeTemplateUpdateDTO(CodeTemplatePO codeTemplate){
        CodeTemplateUpdateDTO dto = new CodeTemplateUpdateDTO();
        dto.setTemplateId(codeTemplate.getTemplateId());
        dto.setName(codeTemplate.getName());
        dto.setTemplateType(codeTemplate.getTemplateType());
        dto.setTemplateVersion(codeTemplate.getTemplateVersion());
        dto.setSysLowVersion(codeTemplate.getSysLowVersion());
        dto.setSysDefault(codeTemplate.getSysDefault());
        dto.setRemark(codeTemplate.getRemark());
        dto.setFromTemplateId(codeTemplate.getFromTemplateId());
        return dto;
    }

    /**
     * 保存示例
     * @return
     */
    public CodeTemplatePO saveCodeTemplateExample(){
        CodeTemplateAddDTO addDTO = this.getCodeTemplateAddDTO();
        return codeTemplateService.save(addDTO);
    }



}

