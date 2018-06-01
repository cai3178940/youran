package com.youran.generate.data;


import com.youran.generate.pojo.dto.MetaCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaCascadeExtUpdateDTO;
import com.youran.generate.pojo.po.MetaCascadeExtPO;

/**
 * Title:测试数据
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 15:21
 */
public class MetaCascadeExtData {

    /**
     * 生成add测试数据
     * @return
     */
    public static MetaCascadeExtAddDTO getAddDTO(Integer fieldId, Integer entityId,Integer cascadeFieldId, Integer cascadeEntityId){
        MetaCascadeExtAddDTO dto = new MetaCascadeExtAddDTO();
        dto.setFieldId(fieldId);
        dto.setEntityId(entityId);
        dto.setAlias("alias"+fieldId);
        dto.setList(1);
        dto.setShow(1);
        dto.setQuery(1);
        dto.setCascadeEntityId(cascadeEntityId);
        dto.setCascadeFieldId(cascadeFieldId);
        return dto;
    }


    /**
     * 生成update测试数据
     * @return
     */
    public static MetaCascadeExtUpdateDTO getUpdateDTO(MetaCascadeExtPO metaCascadeExt){
        MetaCascadeExtUpdateDTO dto= new MetaCascadeExtUpdateDTO();
        dto.setCascadeExtId(metaCascadeExt.getCascadeExtId());
        dto.setFieldId(metaCascadeExt.getFieldId());
        dto.setEntityId(metaCascadeExt.getEntityId());
        dto.setAlias(metaCascadeExt.getAlias()+"s");
        dto.setList(metaCascadeExt.getList());
        dto.setShow(metaCascadeExt.getShow());
        dto.setQuery(metaCascadeExt.getQuery());
        dto.setCascadeEntityId(metaCascadeExt.getCascadeEntityId());
        dto.setCascadeFieldId(metaCascadeExt.getCascadeFieldId());
        return dto;

    }

}
