package com.youran.generate.pojo.vo.chart.source.item;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.chart.MetaChartSourceItemExample.*;

/**
 * 【图表数据源项】展示对象
 *
 * @author cbb
 * @date 2020/04/04
 */
@ApiModel(description = "【图表数据源项】展示对象")
public class MetaChartSourceItemVO extends AbstractVO {

    @ApiModelProperty(notes = N_SOURCE_ITEM_ID, example = E_SOURCE_ITEM_ID)
    private Integer sourceItemId;

    @ApiModelProperty(notes = N_KEY, example = E_KEY)
    private String key;

    @ApiModelProperty(notes = N_PROJECT_ID, example = E_PROJECT_ID)
    private Integer projectId;

    @ApiModelProperty(notes = N_JOIN_INDEX, example = E_JOIN_INDEX)
    private Integer joinIndex;

    @ApiModelProperty(notes = N_PARENT_ID, example = E_PARENT_ID)
    private Integer parentId;

    @ApiModelProperty(notes = N_PARENT_KEY, example = E_PARENT_KEY)
    private String parentKey;

    public Integer getSourceItemId() {
        return this.sourceItemId;
    }

    public void setSourceItemId(Integer sourceItemId) {
        this.sourceItemId = sourceItemId;
    }

    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getJoinIndex() {
        return this.joinIndex;
    }

    public void setJoinIndex(Integer joinIndex) {
        this.joinIndex = joinIndex;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }
}

