package com.youran.generate.pojo.mapper;

import com.youran.common.util.JsonUtil;
import com.youran.generate.pojo.dto.MetaEntityFeatureDTO;
import com.youran.generate.pojo.dto.MetaProjectFeatureDTO;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author cbb
 * @date 2018/11/28
 */
public class FeatureMapper {

    public static String asString(MetaProjectFeatureDTO dto) {
        return dto != null ? JsonUtil.toJSONString(dto) : null;
    }

    public static String asString(MetaEntityFeatureDTO dto) {
        return dto != null ? JsonUtil.toJSONString(dto) : null;
    }


    public static MetaProjectFeatureDTO asProjectFeatureDTO(String str){
        return JsonUtil.parseObject(str,MetaProjectFeatureDTO.class);
    }

    public static MetaEntityFeatureDTO asEntityFeatureDTO(String str){
        return JsonUtil.parseObject(str,MetaEntityFeatureDTO.class);
    }



}
