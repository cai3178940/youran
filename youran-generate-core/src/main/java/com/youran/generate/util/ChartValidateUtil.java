package com.youran.generate.util;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 图表校验工具类
 *
 * @author: cbb
 * @date: 2020-07-11
 */
public class ChartValidateUtil {

    /**
     * 校验图表项字段别名冲突
     *
     * @param list
     */
    public static void checkItemAliasConflict(List list) {
        Set<String> set = new HashSet<>();
        for (Object obj : list) {
            ChartItemDTO item = (ChartItemDTO) obj;
            if (set.contains(item.getAlias())) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表项字段别名有重复：" + item.getAlias());
            }
            set.add(item.getAlias());
        }
    }

}
