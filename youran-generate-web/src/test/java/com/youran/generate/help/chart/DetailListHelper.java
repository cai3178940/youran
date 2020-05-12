package com.youran.generate.help.chart;

import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.dto.chart.DetailListAddDTO;
import com.youran.generate.pojo.dto.chart.DetailListUpdateDTO;
import com.youran.generate.pojo.po.chart.DetailListPO;
import com.youran.generate.service.chart.DetailListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.youran.generate.pojo.example.chart.MetaChartExample.*;

@Component
public class DetailListHelper {

    @Autowired
    private DetailListService aggTableService;

    /**
     * 生成add测试数据
     */
    public DetailListAddDTO getAddDTO(Integer projectId,
                                      Integer sourceId,
                                      List<ChartItemDTO> columnList) {
        DetailListAddDTO dto = new DetailListAddDTO();
        dto.setProjectId(projectId);
        dto.setSourceId(sourceId);
        dto.setChartName(E_CHART_NAME);
        dto.setModule(E_MODULE);
        dto.setTitle(E_TITLE);
        dto.setColumnList(columnList);
        dto.setDefaultPageSize(10);
        return dto;
    }


    /**
     * 生成update测试数据
     */
    public DetailListUpdateDTO getUpdateDTO(DetailListPO po) {
        DetailListUpdateDTO dto = new DetailListUpdateDTO();
        dto.setChartId(po.getChartId());
        dto.setProjectId(po.getProjectId());
        dto.setSourceId(po.getSourceId());
        dto.setChartName(po.getChartName());
        dto.setModule(po.getModule());
        dto.setTitle(po.getTitle());
        dto.setColumnList(po.getColumnList()
            .stream()
            .collect(Collectors.toList()));
        dto.setDefaultPageSize(po.getDefaultPageSize());
        return dto;
    }

    /**
     * 保存示例
     *
     * @return
     */
    public DetailListPO saveExample(Integer projectId,
                                              Integer sourceId,
                                              List<ChartItemDTO> columnList) {
        DetailListAddDTO addDTO = this.getAddDTO(projectId, sourceId,
            columnList);
        return aggTableService.save(addDTO);
    }

}

