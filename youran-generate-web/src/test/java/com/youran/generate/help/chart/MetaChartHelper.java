package com.youran.generate.help.chart;

import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MetaChartHelper {

    private static AtomicInteger SEED = new AtomicInteger(0);

    public ChartItemDTO getChartItemDTOExample(Integer sourceItemId) {
        ChartItemDTO dto = new ChartItemDTO();
        dto.setSourceItemId(sourceItemId);
        dto.setAlias("a" + SEED.incrementAndGet());
        dto.setTitleAlias("alias");
        dto.setValuePrefix(null);
        dto.setValueSuffix(null);
        return dto;
    }
}

