package com.youran.generate.pojo.mapper;

import com.youran.generate.util.LabelsUtil;
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


    public static String asString(List<String> labels) {
        return StringUtils.join(labels, LabelsUtil.LABEL_SPLIT);
    }

    public static List<String> asList(String labels) {
        String[] split = StringUtils.split(labels, LabelsUtil.LABEL_SPLIT);
        if (null == split) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(split);
    }

}
