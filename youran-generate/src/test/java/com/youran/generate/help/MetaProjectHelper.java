package com.youran.generate.help;


import com.youran.common.util.SafeUtil;
import com.youran.generate.constant.FeatureConst;
import com.youran.generate.pojo.dto.MetaProjectAddDTO;
import com.youran.generate.pojo.dto.MetaProjectFeatureDTO;
import com.youran.generate.pojo.dto.MetaProjectUpdateDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;
import com.youran.generate.pojo.po.MetaProjectPO;

import static com.youran.generate.pojo.example.MetaProjectExample.*;

/**
 * <p>Title:测试数据</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
public class MetaProjectHelper {

    /**
     * 生成add测试数据
     * @return
     */
    public static MetaProjectAddDTO getAddDTO(){
        MetaProjectAddDTO metaProjectAddDTO= new MetaProjectAddDTO();
        metaProjectAddDTO.setAuthor(E_AUTHOR);
        metaProjectAddDTO.setProjectName(E_PROJECTNAME);
        metaProjectAddDTO.setPackageName(E_PACKAGENAME);
        metaProjectAddDTO.setProjectDesc(E_PROJECTDESC);
        metaProjectAddDTO.setGroupId(E_GROUPID);
        metaProjectAddDTO.setRemote(SafeUtil.getInteger(E_REMOTE));
        metaProjectAddDTO.setFeature(getFeature());
        return metaProjectAddDTO;
    }


    private static MetaProjectFeatureDTO getFeature(){
        MetaProjectFeatureDTO dto = new MetaProjectFeatureDTO();
        dto.setBootVersion(FeatureConst.Boot.BOOT_1);
        return dto;
    }

    /**
     * 生成update测试数据
     * @return
     */
    public static MetaProjectUpdateDTO getUpdateDTO(MetaProjectPO metaProject){
        MetaProjectUpdateDTO metaProjectUpdateDTO= new MetaProjectUpdateDTO();
        metaProjectUpdateDTO.setProjectId(metaProject.getProjectId());
        metaProjectUpdateDTO.setAuthor(metaProject.getAuthor()+1);
        metaProjectUpdateDTO.setProjectName(metaProject.getProjectName()+1);
        metaProjectUpdateDTO.setPackageName(metaProject.getPackageName()+1);
        metaProjectUpdateDTO.setProjectDesc(metaProject.getProjectDesc()+1);
        metaProjectUpdateDTO.setGroupId(metaProject.getGroupId()+1);
        metaProjectUpdateDTO.setRemote(metaProject.getRemote());
        MetaProjectFeatureDTO featureDTO = FeatureMapper.asDTO(metaProject.getFeature());
        metaProjectUpdateDTO.setFeature(featureDTO);
        return metaProjectUpdateDTO;
    }

}
