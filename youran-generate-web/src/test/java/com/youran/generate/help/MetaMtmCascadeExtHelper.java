package com.youran.generate.help;

import com.youran.common.util.SafeUtil;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtUpdateDTO;
import com.youran.generate.pojo.po.MetaMtmCascadeExtPO;
import org.springframework.stereotype.Component;

import static com.youran.generate.pojo.example.MetaMtmCascadeExtExample.*;

@Component
public class MetaMtmCascadeExtHelper {

    /**
     * 生成add测试数据
     *
     * @return
     */
    public static MetaMtmCascadeExtAddDTO getAddDTO(Integer mtmId,
                                                    Integer entityId,
                                                    Integer cascadeEntityId,
                                                    Integer cascadeFieldId) {
        MetaMtmCascadeExtAddDTO dto = new MetaMtmCascadeExtAddDTO();
        dto.setMtmId(mtmId);
        dto.setEntityId(entityId);
        dto.setCascadeEntityId(cascadeEntityId);
        dto.setCascadeFieldId(cascadeFieldId);
        dto.setAlias(E_ALIAS);
        dto.setList(SafeUtil.getBoolean(E_LIST));
        dto.setShow(SafeUtil.getBoolean(E_SHOW));
        dto.setQuery(SafeUtil.getBoolean(E_QUERY));
        return dto;
    }


    /**
     * 生成update测试数据
     *
     * @return
     */
    public static MetaMtmCascadeExtUpdateDTO getUpdateDTO(MetaMtmCascadeExtPO metaMtmCascadeExt) {
        MetaMtmCascadeExtUpdateDTO dto = new MetaMtmCascadeExtUpdateDTO();
        dto.setMtmCascadeExtId(metaMtmCascadeExt.getMtmCascadeExtId());
        dto.setMtmId(metaMtmCascadeExt.getMtmId());
        dto.setEntityId(metaMtmCascadeExt.getEntityId());
        dto.setCascadeEntityId(metaMtmCascadeExt.getCascadeEntityId());
        dto.setCascadeFieldId(metaMtmCascadeExt.getCascadeFieldId());
        dto.setAlias(metaMtmCascadeExt.getAlias());
        dto.setList(metaMtmCascadeExt.getList());
        dto.setShow(metaMtmCascadeExt.getShow());
        dto.setQuery(metaMtmCascadeExt.getQuery());
        return dto;
    }


}

