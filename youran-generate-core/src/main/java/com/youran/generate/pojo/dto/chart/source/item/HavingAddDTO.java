package com.youran.generate.pojo.dto.chart.source.item;

import com.youran.common.validator.Const;
import com.youran.generate.constant.FilterOperator;
import com.youran.generate.constant.SourceItemType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 新增【having条件】入参
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "新增【having条件】入参")
public class HavingAddDTO extends AbstractSourceItemDTO {

    @ApiModelProperty(notes = "父指标id", example = "1", required = true)
    @NotNull
    private Integer parentId;

    @ApiModelProperty(notes = "过滤运算符", example = "1", required = true, allowableValues = FilterOperator.VALUES_STR)
    @NotNull
    @Const(constClass = FilterOperator.class)
    private Integer filterOperator;

    @ApiModelProperty(notes = "过滤值")
    private String[] filterValue;


    @Override
    public Integer getType() {
        return SourceItemType.HAVING.getValue();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getFilterOperator() {
        return filterOperator;
    }

    public void setFilterOperator(Integer filterOperator) {
        this.filterOperator = filterOperator;
    }

    public String[] getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String[] filterValue) {
        this.filterValue = filterValue;
    }


}
