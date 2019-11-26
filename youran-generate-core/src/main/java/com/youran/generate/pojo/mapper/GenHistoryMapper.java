package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.po.GenHistoryPO;
import com.youran.generate.pojo.vo.GenHistoryShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 生成历史映射
 * <p>基于mapstruct来实现，编译器自动生成实现类
 *
 * @author cbb
 * @date 2019/11/26
 */
@Mapper
public interface GenHistoryMapper {

    GenHistoryMapper INSTANCE = Mappers.getMapper(GenHistoryMapper.class);


    /**
     * po映射showVO
     */
    GenHistoryShowVO toShowVO(GenHistoryPO po);

}
