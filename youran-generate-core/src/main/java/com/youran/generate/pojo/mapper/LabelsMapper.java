package com.youran.generate.pojo.mapper;

import com.youran.common.util.JsonUtil;
import com.youran.generate.pojo.dto.LabelDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 特性DTO转json
 *
 * @author mrj
 * @date 2020/09/12
 */
public class LabelsMapper {


    public static String asString(List<LabelDTO> labels) {
        return JsonUtil.toJSONString(labels);
    }

    public static List<LabelDTO> asList(String labels) {
        if (StringUtils.isBlank(labels)) {
            return new ArrayList<>();
        }
        return JsonUtil.parseArray(labels, LabelDTO.class);
    }

}
