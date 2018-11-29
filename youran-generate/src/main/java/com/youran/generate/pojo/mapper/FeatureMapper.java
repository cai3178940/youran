package com.youran.generate.pojo.mapper;

import com.youran.common.util.JsonUtil;
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


    public static MetaProjectFeatureDTO asDTO(String str){
        return JsonUtil.parseObject(str,MetaProjectFeatureDTO.class);
    }

}
