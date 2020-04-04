package com.youran.generate.pojo.dto.chart;

import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.generate.constant.ChartType;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 抽象图表DTO
 *
 * @author: cbb
 * @date: 2020-04-04
 */
public abstract class AbstractChartDTO extends AbstractDTO {

    @ApiModelProperty(notes = "数据源id", example = "1", required = true)
    @NotNull
    private Integer sourceId;

    @ApiModelProperty(notes = "图表名称", example = "chart_1", required = true)
    @NotNull
    @Length(max = 64)
    private String name;

    @ApiModelProperty(notes = "模块名", example = "system")
    @Length(max = 50)
    private String module;

    @ApiModelProperty(notes = "图表标题", example = "示例图表", required = true)
    @NotNull
    @Length(max = 64)
    private String title;

    /**
     * 获取图表类型
     *
     * @return
     * @see ChartType
     */
    public abstract Integer getChartType();

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
