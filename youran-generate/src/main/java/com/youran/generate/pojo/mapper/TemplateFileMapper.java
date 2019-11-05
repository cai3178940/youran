package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.TemplateFileAddDTO;
import com.youran.generate.pojo.dto.TemplateFileUpdateDTO;
import com.youran.generate.pojo.po.TemplateFilePO;
import com.youran.generate.pojo.vo.TemplateFileShowVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 【模板文件】映射
 *
 * @author cbb
 * @date 2019/10/24
 */
@Mapper
public interface TemplateFileMapper {

    TemplateFileMapper INSTANCE = Mappers.getMapper(TemplateFileMapper.class);

    /**
     * addDTO映射po
     *
     * @param templateFileAddDTO
     * @return
     */
    TemplateFilePO fromAddDTO(TemplateFileAddDTO templateFileAddDTO);

    /**
     * 将updateDTO中的值设置到po
     *
     * @param templateFilePO
     * @param templateFileUpdateDTO
     */
    void setUpdateDTO(@MappingTarget TemplateFilePO templateFilePO, TemplateFileUpdateDTO templateFileUpdateDTO);

    /**
     * po映射showVO
     *
     * @param templateFilePO
     * @return
     */
    TemplateFileShowVO toShowVO(TemplateFilePO templateFilePO);

    @BeanMapping(ignoreByDefault = true)
    @Mappings({
        @Mapping(target = "fileName"),
        @Mapping(target = "fileDir"),
        @Mapping(target = "contextType"),
        @Mapping(target = "abstracted"),
    })
    TemplateFilePO copy(TemplateFilePO po);

}

