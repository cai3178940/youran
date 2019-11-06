package com.youran.generate.help;


import com.youran.common.util.SafeUtil;
import com.youran.generate.pojo.dto.MetaConstAddDTO;
import com.youran.generate.pojo.dto.MetaConstUpdateDTO;
import com.youran.generate.pojo.po.MetaConstPO;

import static com.youran.generate.pojo.example.MetaConstExample.*;

/**
 * 测试数据
 *
 * @author: cbb
 * @date: 2017/5/12
 */
public class MetaConstHelper {

    /**
     * 生成add测试数据
     *
     * @return
     */
    public static MetaConstAddDTO getAddDTO(Integer projectId) {
        MetaConstAddDTO dto = new MetaConstAddDTO();
        dto.setProjectId(projectId);
        dto.setConstName(E_CONSTNAME);
        dto.setConstRemark(E_CONSTREMARK);
        dto.setConstType(SafeUtil.getInteger(E_CONSTTYPE));
        return dto;
    }


    /**
     * 生成update测试数据
     *
     * @return
     */
    public static MetaConstUpdateDTO getUpdateDTO(MetaConstPO metaConst) {
        MetaConstUpdateDTO dto = new MetaConstUpdateDTO();
        dto.setProjectId(metaConst.getProjectId());
        dto.setConstId(metaConst.getConstId());
        dto.setConstName(metaConst.getConstName() + "1");
        dto.setConstRemark(metaConst.getConstRemark() + "1");
        dto.setConstType(metaConst.getConstType());
        return dto;
    }

}
