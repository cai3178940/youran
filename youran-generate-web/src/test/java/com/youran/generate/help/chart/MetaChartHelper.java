package com.youran.generate.help.chart;

import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import org.springframework.stereotype.Component;

@Component
public class MetaChartHelper {

    public ChartItemDTO getChartItemDTOExample(Integer sourceItemId){
        ChartItemDTO dto = new ChartItemDTO();
        dto.setSourceItemId(sourceItemId);
        dto.setTitleAlias("alias");
        dto.setShowFkTitle(false);
        dto.setValuePrefix(null);
        dto.setValueSuffix(null);
        return dto;
    }
}

