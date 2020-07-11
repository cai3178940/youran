package com.youran.generate.help.chart;

import com.youran.common.util.SafeUtil;
import com.youran.generate.pojo.dto.chart.*;
import com.youran.generate.pojo.po.chart.*;
import com.youran.generate.service.chart.MetaDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.youran.generate.pojo.example.chart.MetaDashboardExample.*;

@Component
public class MetaDashboardHelper {

    @Autowired
    private MetaDashboardService metaDashboardService;

    /**
     * 生成add测试数据
     * @return
     */
    public MetaDashboardAddDTO getMetaDashboardAddDTO(){
        MetaDashboardAddDTO dto = new MetaDashboardAddDTO();
        dto.setName(E_NAME);
        dto.setTitle(E_TITLE);
        dto.setModule(E_MODULE);
        dto.setFeature(E_FEATURE);
        dto.setProjectId(SafeUtil.getInteger(E_PROJECT_ID));
        return dto;
    }


    /**
     * 生成update测试数据
     * @return
     */
    public MetaDashboardUpdateDTO getMetaDashboardUpdateDTO(MetaDashboardPO metaDashboard){
        MetaDashboardUpdateDTO dto = new MetaDashboardUpdateDTO();
        dto.setDashboardId(metaDashboard.getDashboardId());
        dto.setName(metaDashboard.getName());
        dto.setTitle(metaDashboard.getTitle());
        dto.setModule(metaDashboard.getModule());
        dto.setFeature(metaDashboard.getFeature());
        dto.setProjectId(metaDashboard.getProjectId());
        return dto;
    }

    /**
     * 保存示例
     * @return
     */
    public MetaDashboardPO saveMetaDashboardExample(){
        MetaDashboardAddDTO addDTO = this.getMetaDashboardAddDTO();
        return metaDashboardService.save(addDTO);
    }



}

