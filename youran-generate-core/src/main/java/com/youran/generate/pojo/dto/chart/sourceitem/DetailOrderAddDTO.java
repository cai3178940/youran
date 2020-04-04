package com.youran.generate.pojo.dto.chart.sourceitem;

import com.youran.common.validator.Const;
import com.youran.generate.constant.SortType;
import com.youran.generate.constant.SourceItemSubType;
import com.youran.generate.constant.SourceItemType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 新增【明细排序】入参
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "新增【明细排序】入参")
public class DetailOrderAddDTO extends AbstractSourceItemDTO {

    @ApiModelProperty(notes = "父明细列id", example = "1", required = true)
    @NotNull
    private Integer parentId;

    @ApiModelProperty(notes = "排序方式", example = "1", required = true, allowableValues = SortType.VALUES_STR)
    @NotNull
    @Const(constClass = SortType.class)
    private Integer sortType;

    @Override
    public Integer getType() {
        return SourceItemType.DETAIL_ORDER.getValue();
    }

    @Override
    public Integer getSubType() {
        return SourceItemSubType.NONE.getValue();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
