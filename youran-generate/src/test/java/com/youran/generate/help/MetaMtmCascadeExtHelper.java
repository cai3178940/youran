package com.youran.generate.help;

import com.youran.common.util.SafeUtil;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtUpdateDTO;
import com.youran.generate.pojo.po.MetaMtmCascadeExtPO;
import com.youran.generate.service.MetaMtmCascadeExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.youran.generate.pojo.example.MetaMtmCascadeExtExample.*;

@Component
public class MetaMtmCascadeExtHelper {

    @Autowired
    private MetaMtmCascadeExtService metaMtmCascadeExtService;

    /**
     * 生成add测试数据
     * @return
     */
    public MetaMtmCascadeExtAddDTO getMetaMtmCascadeExtAddDTO(){
        MetaMtmCascadeExtAddDTO dto = new MetaMtmCascadeExtAddDTO();
        dto.setMtmId(SafeUtil.getInteger(E_MTM_ID));
        dto.setEntityId(SafeUtil.getInteger(E_ENTITY_ID));
        dto.setCascadeEntityId(SafeUtil.getInteger(E_CASCADE_ENTITY_ID));
        dto.setCascadeFieldId(SafeUtil.getInteger(E_CASCADE_FIELD_ID));
        dto.setAlias(E_ALIAS);
        dto.setList(SafeUtil.getInteger(E_LIST));
        dto.setShow(SafeUtil.getInteger(E_SHOW));
        dto.setQuery(SafeUtil.getInteger(E_QUERY));
        return dto;
    }


    /**
     * 生成update测试数据
     * @return
     */
    public MetaMtmCascadeExtUpdateDTO getMetaMtmCascadeExtUpdateDTO(MetaMtmCascadeExtPO metaMtmCascadeExt){
        MetaMtmCascadeExtUpdateDTO dto = new MetaMtmCascadeExtUpdateDTO();
        dto.setCascadeMtmExtId(metaMtmCascadeExt.getCascadeMtmExtId());
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

    /**
     * 保存示例
     * @return
     */
    public MetaMtmCascadeExtPO saveMetaMtmCascadeExtExample(){
        MetaMtmCascadeExtAddDTO addDTO = this.getMetaMtmCascadeExtAddDTO();
        return metaMtmCascadeExtService.save(addDTO);
    }



}

