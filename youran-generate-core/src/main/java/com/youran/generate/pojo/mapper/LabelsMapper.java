package com.youran.generate.pojo.mapper;

import com.youran.common.util.JsonUtil;
import com.youran.generate.pojo.dto.MetaEntityFeatureDTO;
import com.youran.generate.pojo.dto.MetaMtmFeatureDTO;
import com.youran.generate.pojo.dto.MetaProjectFeatureDTO;
import com.youran.generate.pojo.dto.chart.source.ChartSourceFeatureDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 特性DTO转json
 *
 * @author mrj
 * @date 2020/09/12
 */
public class LabelsMapper {

    public static final String SPLIT = ";";

    public static String asString(List<String> labels) {
        return StringUtils.join(labels, SPLIT);
    }

    public static List<String> asList(String labels) {
        String[] split = StringUtils.split(labels, SPLIT);
        if(null == split) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(split);
    }

}
