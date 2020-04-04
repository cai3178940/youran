package com.youran.generate.pojo.dto.chart.source.item;

import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.generate.constant.SourceItemSubType;
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

    /**
     * 获取数据项类型
     *
     * @return
     * @see SourceItemType
     */
    public abstract Integer getType();

    /**
     * 获取数据项子类型
     *
     * @return
     * @see SourceItemSubType
     */
    public abstract Integer getSubType();

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }
}
