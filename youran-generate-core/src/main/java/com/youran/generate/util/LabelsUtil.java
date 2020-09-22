package com.youran.generate.util;

import com.youran.generate.pojo.dto.LabelDTO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

public class LabelsUtil {

    public static LabelDTO findLabel(List<LabelDTO> labels, String key) {
        if (CollectionUtils.isEmpty(labels)) {
            return null;
        }
        return labels.stream()
            .filter(labelDTO -> Objects.equals(key, labelDTO.getKey()))
            .findFirst()
            .orElse(null);
    }
}
