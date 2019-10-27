package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.CodeTemplateAddDTO;
import com.youran.generate.pojo.dto.CodeTemplateUpdateDTO;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.pojo.vo.CodeTemplateShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * <p>Title: 【代码模板】映射</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/10/24
 */
@Mapper
public interface CodeTemplateMapper {

    CodeTemplateMapper INSTANCE = Mappers.getMapper( CodeTemplateMapper.class );

    /**
    * addDTO映射po
    * @param codeTemplateAddDTO
    * @return
    */
    CodeTemplatePO fromAddDTO(CodeTemplateAddDTO codeTemplateAddDTO);

    /**
    * 将updateDTO中的值设置到po
    * @param codeTemplatePO
    * @param codeTemplateUpdateDTO
    */
    void setUpdateDTO(@MappingTarget CodeTemplatePO codeTemplatePO, CodeTemplateUpdateDTO codeTemplateUpdateDTO);

    /**
    * po映射showVO
    * @param codeTemplatePO
    * @return
    */
    CodeTemplateShowVO toShowVO(CodeTemplatePO codeTemplatePO);

}

