package com.youran.generate.pojo.dto.chart.source.item;

import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.generate.constant.SourceItemType;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 抽象图表数据项DTO
 *
 * @author: cbb
 * @date: 2020-04-04
 */
public abstract class AbstractSourceItemDTO extends AbstractDTO {

    @ApiModelProperty(notes = "所属数据源id", example = "1", required = true)
    @NotNull
    private Integer sourceId;

    @ApiModelProperty(notes = "数据项唯一键", example = "1", required = true)
    @NotNull
    private String key;

    @ApiModelProperty(notes = "项目id", example = "1", required = true)
    @NotNull
    private Integer projectId;

    @ApiModelProperty(notes = "关联实体序号", example = "1", required = true)
    @NotNull
    private Integer joinIndex;

    @ApiModelProperty(notes = "父id", example = "1")
    private Integer parentId;

    @ApiModelProperty(notes = "父数据项唯一键", example = "1")
    private String parentKey;

    /**
     * 获取数据项类型
     *
     * @return
     * @see SourceItemType
     */
    public abstract Integer getType();

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getJoinIndex() {
        return joinIndex;
    }

    public void setJoinIndex(Integer joinIndex) {
        this.joinIndex = joinIndex;
    }

    public Integer getParentId() {
        return parentId;
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
