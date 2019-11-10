package com.youran.generate.help;

import com.google.common.base.Joiner;
import com.youran.common.util.SafeUtil;
import com.youran.generate.pojo.dto.MetaIndexAddDTO;
import com.youran.generate.pojo.dto.MetaIndexUpdateDTO;
import com.youran.generate.pojo.po.MetaIndexPO;

import static com.youran.generate.pojo.example.MetaIndexExample.*;

/**
 * 测试数据
 *
 * @author: cbb
 * @date: 2017/5/12
 */
public class MetaIndexHelper {

    /**
     * 生成add测试数据
     *
     * @return
     */
    public static MetaIndexAddDTO getAddDTO(Integer... fieldIds) {
        MetaIndexAddDTO dto = new MetaIndexAddDTO();
        dto.setIndexName(E_INDEXNAME);
        dto.setUnique(SafeUtil.getBoolean(E_UNIQUE));
        dto.setUniqueCheck(SafeUtil.getBoolean(E_UNIQUECHECK));
        dto.setFieldIds(Joiner.on(",").join(fieldIds));
        dto.setEntityId(SafeUtil.getInteger(E_ENTITYID));
        return dto;
    }


    /**
     * 生成update测试数据
     *
     * @return
     */
    public static MetaIndexUpdateDTO getUpdateDTO(MetaIndexPO metaIndex, Integer... fieldIds) {
        MetaIndexUpdateDTO metaIndexUpdateDTO = new MetaIndexUpdateDTO();
        metaIndexUpdateDTO.setIndexId(metaIndex.getIndexId());
        metaIndexUpdateDTO.setIndexName(metaIndex.getIndexName());
        metaIndexUpdateDTO.setEntityId(metaIndex.getEntityId());
        metaIndexUpdateDTO.setUnique(metaIndex.getUnique());
        metaIndexUpdateDTO.setUniqueCheck(metaIndex.getUniqueCheck());
        metaIndexUpdateDTO.setFieldIds(Joiner.on(",").join(fieldIds));
        return metaIndexUpdateDTO;
    }

}
