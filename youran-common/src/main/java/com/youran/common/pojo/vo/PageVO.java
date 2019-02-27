package com.youran.common.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:分页结果对象</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/8/24
 */
@ApiModel
public class PageVO<T> extends AbstractVO {

    @ApiModelProperty(notes = "每页条数",example = "10",required = true)
    private Integer pageSize;
    @ApiModelProperty(notes = "页码",example = "1",required = true)
    private Integer pageNo;
    @ApiModelProperty(notes = "开始序号",example = "0",required = true)
    private Integer firstEntityIndex;
    @ApiModelProperty(notes = "结束序号",example = "10",required = true)
    private Integer lastEntityIndex;

    @ApiModelProperty(notes = "数据列表",required = true)
    private List<T> entities;
    @ApiModelProperty(notes = "总条数",example = "100",required = true)
    private Integer entityCount;
    @ApiModelProperty(notes = "总页数",example = "10",required = true)
    private Integer pageCount;

    public PageVO() {
    }

    public PageVO(List<T> entities, int pageNo, int pageSize, int entityCount) {
        this.entities = entities;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.entityCount = entityCount;
        firstEntityIndex = (pageNo - 1) * pageSize;
        lastEntityIndex = pageNo * pageSize;
        if ( entityCount % pageSize > 0 ) {
            pageCount = entityCount / pageSize + 1;
        } else {
            pageCount = entityCount / pageSize;
        }
    }

    public PageVO(int pageSize, int pageNo, int entityCount) {
        if (pageNo > 1 && pageSize <= 0) {
            throw new IllegalArgumentException(
                    "Illegal paging arguments. [pageSize=" + pageSize
                            + ", pageIndex=" + pageNo + "]");
        }
        if (pageSize < 0) {
            pageSize = 0;
        }
        if (pageNo < 1) {
            pageNo = 1;
        }
        this.entities = new ArrayList<>();
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.entityCount = entityCount;
        firstEntityIndex = (pageNo - 1) * pageSize;
        lastEntityIndex = pageNo * pageSize;
        if ( entityCount % pageSize > 0 ) {
            pageCount = entityCount / pageSize + 1;
        } else {
            pageCount = entityCount / pageSize;
        }
    }

    public PageVO(int pageSize, long firstEntityIndex, int entityCount) {
        if (firstEntityIndex > 1 && pageSize <= 0) {
            throw new IllegalArgumentException(
                    "Illegal paging arguments. [pageSize=" + pageSize
                            + ", firstEntityIndex=" + firstEntityIndex + "]");
        }
        if (pageSize < 0) {
            pageSize = 0;
        }
        this.firstEntityIndex = (int)firstEntityIndex;
        if (firstEntityIndex < 0) {
            this.firstEntityIndex = 0;
        }
        this.pageSize = pageSize;
        this.entityCount = entityCount;
        lastEntityIndex = pageSize + (int)firstEntityIndex;
        if ( entityCount % pageSize > 0 ) {
            pageCount = entityCount / pageSize + 1;
        } else {
            pageCount = entityCount / pageSize;
        }
    }

    /**
     * @param pageSize
     *            每页记录数
     * @param pageNo
     *            页号
     */
    public PageVO(int pageSize, int pageNo) {
        if (pageNo > 1 && pageSize <= 0) {
            throw new IllegalArgumentException(
                    "Illegal paging arguments. [pageSize=" + pageSize
                            + ", pageIndex=" + pageNo + "]");
        }
        if (pageSize < 0) {
            pageSize = 0;
        }
        if (pageNo < 1) {
            pageNo = 1;
        }
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        firstEntityIndex = (pageNo - 1) * pageSize;
        lastEntityIndex = pageNo * pageSize;
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public Integer getEntityCount() {
        return entityCount;
    }

    public void setEntityCount(Integer entityCount) {
        this.entityCount = entityCount;
    }

    public Integer getFirstEntityIndex() {
        return firstEntityIndex;
    }

    public void setFirstEntityIndex(Integer firstEntityIndex) {
        this.firstEntityIndex = firstEntityIndex;
    }

    public Integer getLastEntityIndex() {
        return lastEntityIndex;
    }

    public void setLastEntityIndex(Integer lastEntityIndex) {
        this.lastEntityIndex = lastEntityIndex;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("pageSize", pageSize)
            .append("pageNo", pageNo)
            .append("firstEntityIndex", firstEntityIndex)
            .append("lastEntityIndex", lastEntityIndex)
            .append("entities", entities)
            .append("entityCount", entityCount)
            .append("pageCount", pageCount)
            .toString();
    }
}
