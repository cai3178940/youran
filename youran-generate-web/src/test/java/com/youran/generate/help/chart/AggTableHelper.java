package com.youran.generate.help.chart;

import com.youran.generate.pojo.dto.chart.AggTableAddDTO;
import com.youran.generate.pojo.dto.chart.AggTableUpdateDTO;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.po.chart.AggTablePO;
import com.youran.generate.service.chart.AggTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.youran.generate.pojo.example.chart.MetaChartExample.*;

@Component
public class AggTableHelper {

    @Autowired
    private AggTableService aggTableService;

    /**
     * 生成add测试数据
     */
    public AggTableAddDTO getAddDTO(Integer projectId,
                                    Integer sourceId,
                                    List<ChartItemDTO> dimensionList,
                                    List<ChartItemDTO> metricsList){
        AggTableAddDTO dto = new AggTableAddDTO();
        dto.setProjectId(projectId);
        dto.setSourceId(sourceId);
        dto.setChartName(E_CHART_NAME);
        dto.setModule(E_MODULE);
        dto.setTitle(E_TITLE);
        dto.setDimensionList(dimensionList);
        dto.setMetricsList(metricsList);
        return dto;
    }


    /**
     * 生成update测试数据
     */
    public AggTableUpdateDTO getUpdateDTO(AggTablePO po){
        AggTableUpdateDTO dto = new AggTableUpdateDTO();
        dto.setChartId(po.getChartId());
        dto.setProjectId(po.getProjectId());
        dto.setSourceId(po.getSourceId());
        dto.setChartName(po.getChartName());
        dto.setModule(po.getModule());
        dto.setTitle(po.getTitle());
        dto.setDimensionList(po.getDimensionList()
            .stream()
            .collect(Collectors.toList()));
        dto.setMetricsList(po.getMetricsList()
            .stream()
            .collect(Collectors.toList()));
        return dto;
    }

    /**
     * 保存示例
     * @return
     */
    public AggTablePO saveExample(Integer projectId,
                                  Integer sourceId,
                                  List<ChartItemDTO> dimensionList,
                                  List<ChartItemDTO> metricsList){
        AggTableAddDTO addDTO = this.getAddDTO(projectId, sourceId,
            dimensionList, metricsList);
        return aggTableService.save(addDTO);
    }

}

