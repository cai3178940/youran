package com.youran.common.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 分页结果对象
 *
 * @author: cbb
 * @date: 2017/8/24
 */
@ApiModel
public class PageVO<T> extends AbstractVO {

    @ApiModelProperty(notes = "每页条数", example = "10", required = true)
    private Integer pageSize;
    @ApiModelProperty(notes = "页码", example = "1", required = true)
    private Integer currentPage;
    @ApiModelProperty(notes = "开始序号", example = "0", required = true)
    private Integer firstIndex;
    @ApiModelProperty(notes = "结束序号", example = "10", required = true)
    private Integer lastIndex;

    @ApiModelProperty(notes = "数据列表", required = true)
    private List<T> list;
    @ApiModelProperty(notes = "总条数", example = "100", required = true)
    private Integer total;
    @ApiModelProperty(notes = "总页数", example = "10", required = true)
    private Integer pageCount;

    public PageVO() {
    }

    public PageVO(List<T> list, int currentPage, int pageSize, int total) {
        if (pageSize <= 0) {
            throw new IllegalArgumentException(
                "Illegal paging arguments. [pageSize=" + pageSize + "]");
        }
        this.list = list;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        this.firstIndex = (currentPage - 1) * pageSize;
        this.lastIndex = currentPage * pageSize;
        if (total % pageSize > 0) {
            this.pageCount = total / pageSize + 1;
        } else {
            this.pageCount = total / pageSize;
        }
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("pageSize", pageSize)
            .append("currentPage", currentPage)
            .append("firstIndex", firstIndex)
            .append("lastIndex", lastIndex)
            .append("list", list)
            .append("total", total)
            .append("pageCount", pageCount)
            .toString();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(Integer firstIndex) {
        this.firstIndex = firstIndex;
    }

    public Integer getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(Integer lastIndex) {
        this.lastIndex = lastIndex;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}
