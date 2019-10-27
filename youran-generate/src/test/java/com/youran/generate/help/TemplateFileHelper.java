package com.youran.generate.help;

import com.youran.common.util.SafeUtil;
import com.youran.generate.pojo.dto.TemplateFileAddDTO;
import com.youran.generate.pojo.dto.TemplateFileUpdateDTO;
import com.youran.generate.pojo.po.TemplateFilePO;
import com.youran.generate.service.TemplateFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.youran.generate.pojo.example.TemplateFileExample.*;

@Component
public class TemplateFileHelper {

    @Autowired
    private TemplateFileService templateFileService;

    /**
     * 生成add测试数据
     * @return
     */
    public TemplateFileAddDTO getTemplateFileAddDTO(Integer templateId){
        TemplateFileAddDTO dto = new TemplateFileAddDTO();
        dto.setFileName(E_FILE_NAME);
        dto.setFileDir(E_FILE_DIR);
        dto.setTemplateId(templateId);
        dto.setContextType(SafeUtil.getInteger(E_CONTEXT_TYPE));
        dto.setAbstracted(SafeUtil.getBoolean(E_ABSTRACTED));
        dto.setContent(E_CONTENT);
        return dto;
    }


    /**
     * 生成update测试数据
     * @return
     */
    public TemplateFileUpdateDTO getTemplateFileUpdateDTO(TemplateFilePO templateFile){
        TemplateFileUpdateDTO dto = new TemplateFileUpdateDTO();
        dto.setFileId(templateFile.getFileId());
        dto.setFileName(templateFile.getFileName());
        dto.setFileDir(templateFile.getFileDir());
        dto.setTemplateId(templateFile.getTemplateId());
        dto.setContextType(templateFile.getContextType());
        dto.setAbstracted(templateFile.getAbstracted());
        dto.setContent(templateFile.getContent());
        return dto;
    }

    /**
     * 保存示例
     * @return
     */
    public TemplateFilePO saveTemplateFileExample(Integer templateId){
        TemplateFileAddDTO addDTO = this.getTemplateFileAddDTO(templateId);
        return templateFileService.save(addDTO);
    }



}

