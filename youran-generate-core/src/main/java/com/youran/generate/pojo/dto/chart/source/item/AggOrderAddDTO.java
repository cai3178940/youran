package com.youran.generate.pojo.dto.chart.source.item;

import com.youran.common.validator.Const;
import com.youran.generate.constant.SortType;
import com.youran.generate.constant.SourceItemType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 * 新增【聚合排序】入参
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "新增【聚合排序】入参")
public class AggOrderAddDTO extends AbstractSourceItemDTO {

    @ApiModelProperty(notes = "排序方式", example = "1", required = true, allowableValues = SortType.VALUES_STR)
    @NotNull
    @Const(constClass = SortType.class)
    private Integer sortType;

    @AssertTrue(message = "parentId不能为空")
    public boolean validateParentId() {
        return this.getParentId() != null;
    }

    @Override
    public Integer getType() {
        return SourceItemType.AGG_ORDER.getValue();
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
