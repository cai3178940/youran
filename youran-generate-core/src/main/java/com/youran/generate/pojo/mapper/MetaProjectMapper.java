package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.MetaProjectAddDTO;
import com.youran.generate.pojo.dto.MetaProjectUpdateDTO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.vo.MetaProjectShowVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 项目映射
 * <p>基于mapstruct来实现，编译器自动生成实现类
 *
 * @author: cbb
 * @date 2017/5/24
 */
@Mapper(uses = FeatureMapper.class)
public interface MetaProjectMapper {

    MetaProjectMapper INSTANCE = Mappers.getMapper(MetaProjectMapper.class);

    /**
     * addDTO映射po
     *
     * @param addDTO
     * @return
     */
    MetaProjectPO fromAddDTO(MetaProjectAddDTO addDTO);

    /**
     * 将updateDTO中的值设置到po
     *
     * @param metaProjectPO
     * @param metaProjectUpdateDTO
     */
    @Mapping(target = "password", ignore = true)
    void setPO(@MappingTarget MetaProjectPO metaProjectPO, MetaProjectUpdateDTO metaProjectUpdateDTO);

    /**
     * po映射showVO
     *
     * @param metaProjectPO
     * @return
     */
    MetaProjectShowVO toShowVO(MetaProjectPO metaProjectPO);

    /**
     * 映射项目属性-忽略远程git信息
     *
     * @param project
     * @return
     */
    @BeanMapping(ignoreByDefault = true)
    @Mappings({
        @Mapping(target = "packageName"),
        @Mapping(target = "projectName"),
        @Mapping(target = "projectDesc"),
        @Mapping(target = "groupId"),
        @Mapping(target = "author"),
        @Mapping(target = "remote", constant = "false"),
        @Mapping(target = "feature"),
    })
    MetaProjectPO copyWithoutRemote(MetaProjectPO project);


}
