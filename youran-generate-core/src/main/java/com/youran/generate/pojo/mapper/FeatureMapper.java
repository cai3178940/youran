package com.youran.generate.pojo.mapper;

import com.youran.common.util.JsonUtil;
import com.youran.generate.pojo.dto.MetaEntityFeatureDTO;
import com.youran.generate.pojo.dto.MetaMtmFeatureDTO;
import com.youran.generate.pojo.dto.MetaProjectFeatureDTO;
import com.youran.generate.pojo.dto.chart.ChartFeatureDTO;
import com.youran.generate.pojo.dto.chart.source.ChartSourceFeatureDTO;
import com.youran.generate.pojo.dto.chart.source.item.ChartSourceItemFeatureDTO;

/**
 * 特性DTO转json
 *
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

    public static String asString(MetaMtmFeatureDTO dto) {
        return dto != null ? JsonUtil.toJSONString(dto) : null;
    }

    public static String asString(ChartFeatureDTO dto) {
        return dto != null ? JsonUtil.toJSONString(dto) : null;
    }

    public static String asString(ChartSourceItemFeatureDTO dto) {
        return dto != null ? JsonUtil.toJSONString(dto) : null;
    }

    public static String asString(ChartSourceFeatureDTO dto) {
        return dto != null ? JsonUtil.toJSONString(dto) : null;
    }

    public static MetaProjectFeatureDTO asProjectFeatureDTO(String str) {
        return JsonUtil.parseObject(str, MetaProjectFeatureDTO.class);
    }

    public static MetaEntityFeatureDTO asEntityFeatureDTO(String str) {
        return JsonUtil.parseObject(str, MetaEntityFeatureDTO.class);
    }

    public static MetaMtmFeatureDTO asMtmFeatureDTO(String str) {
        return JsonUtil.parseObject(str, MetaMtmFeatureDTO.class);
    }

    public static ChartFeatureDTO asChartFeatureDTO(String str) {
        return JsonUtil.parseObject(str, ChartFeatureDTO.class);
    }

    public static ChartSourceItemFeatureDTO asChartSourceItemFeatureDTO(String str) {
        return JsonUtil.parseObject(str, ChartSourceItemFeatureDTO.class);
    }

    public static ChartSourceFeatureDTO asChartSourceFeatureDTO(String str) {
        return JsonUtil.parseObject(str, ChartSourceFeatureDTO.class);
    }
}
