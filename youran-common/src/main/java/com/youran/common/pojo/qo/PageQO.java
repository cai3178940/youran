package com.youran.common.pojo.qo;

import io.swagger.annotations.ApiParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 分页查询dto
 *
 * @author: cbb
 * @date: 2017/5/12
 */
public class PageQO extends AbstractQO {

    public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 每页的条数
     */
    @ApiParam(value = "分页参数，每页的条数", example = "20")
    @Max(value = 1000, message = "pageSize不能大于1000")
    protected Integer pageSize;

    /**
     * 当前第几页
     */
    @ApiParam(value = "分页参数，第几页", example = "1")
    @Min(value = 1, message = "currentPage不能小于1")
    protected Integer currentPage;

    public PageQO() {
        //默认第一页，每页20条
        this(1, 20);
    }

    public PageQO(Integer currentPage, Integer pageSize) {
        if (currentPage == null) {
            currentPage = DEFAULT_PAGE_NO;
        }
        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.currentPage = currentPage;
        this.pageSize = pageSize;
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

    public int getStartIndex() {
        return (currentPage - 1) * pageSize;
    }

    public int getEndIndex() {
        return currentPage * pageSize;
    }

}
