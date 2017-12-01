package com.youran.generate.data;


import com.youran.generate.pojo.dto.MetaProjectAddDTO;
import com.youran.generate.pojo.dto.MetaProjectUpdateDTO;
import com.youran.generate.pojo.po.MetaProjectPO;

import static com.youran.generate.pojo.example.MetaProjectExample.*;

/**
 * Title:测试数据
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 15:21
 */
public class MetaProjectData {

    /**
     * 生成add测试数据
     * @return
     */
    public static MetaProjectAddDTO getAddDTO(){
        MetaProjectAddDTO metaProjectAddDTO= new MetaProjectAddDTO();
        metaProjectAddDTO.setAuthor(E_AUTHOR);
        metaProjectAddDTO.setProjectName(E_PROJECTNAME);
        metaProjectAddDTO.setPackageName(E_PACKAGENAME);
        return metaProjectAddDTO;
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
        return metaProjectUpdateDTO;
    }

}
