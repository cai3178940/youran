package com.youran.generate.help.chart;

import com.youran.common.util.SafeUtil;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceAddDTO;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceUpdateDTO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.service.chart.source.MetaChartSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.youran.generate.pojo.example.chart.MetaChartSourceExample.*;

@Component
public class MetaChartSourceHelper {

    @Autowired
    private MetaChartSourceService metaChartSourceService;

    /**
     * 生成add测试数据
     * @return
     */
    public MetaChartSourceAddDTO getMetaChartSourceAddDTO(Integer projectId,
                                                          Integer entityId){
        MetaChartSourceAddDTO dto = new MetaChartSourceAddDTO();
        dto.setProjectId(projectId);
        dto.setAggregation(SafeUtil.getBoolean(E_AGGREGATION));
        dto.setEntityId(entityId);
        dto.setLimit(1000);
        return dto;
    }


    /**
     * 生成update测试数据
     * @return
     */
    public MetaChartSourceUpdateDTO getMetaChartSourceUpdateDTO(MetaChartSourcePO metaChartSource){
        MetaChartSourceUpdateDTO dto = new MetaChartSourceUpdateDTO();
        dto.setSourceId(metaChartSource.getSourceId());
        dto.setProjectId(metaChartSource.getProjectId());
        dto.setAggregation(metaChartSource.getAggregation());
        dto.setEntityId(metaChartSource.getEntityId());
        return dto;
    }

    /**
     * 保存示例
     * @return
     */
    public MetaChartSourcePO saveMetaChartSourceExample(Integer projectId,
                                                        Integer entityId){
        MetaChartSourceAddDTO addDTO = this.getMetaChartSourceAddDTO(projectId,entityId);
        return metaChartSourceService.save(addDTO);
    }



}

