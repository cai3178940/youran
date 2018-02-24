<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "io.swagger.annotations.ApiModel"/>
<@import "io.swagger.annotations.ApiModelProperty"/>
<@import "java.util.ArrayList"/>
<@import "java.util.List"/>
<@classCom "分页结果对象"/>
@ApiModel
public class PageVO<T> extends AbstractVO {

    @ApiModelProperty(notes = "每页条数",example = "10",required = true)
    private int pageSize;
    @ApiModelProperty(notes = "页码",example = "1",required = true)
    private int pageNo;
    @ApiModelProperty(notes = "开始序号",example = "0",required = true)
    private int firstEntityIndex;//开始序号
    @ApiModelProperty(notes = "结束序号",example = "10",required = true)
    private int lastEntityIndex;//结束序号

    @ApiModelProperty(notes = "数据列表",required = true)
    private List<T> entities;
    @ApiModelProperty(notes = "总条数",example = "100",required = true)
    private int entityCount;//总数
    @ApiModelProperty(notes = "总页数",example = "10",required = true)
    private int pageCount;//总页数

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

        if (pageSize < 0)
            pageSize = 0;
        if (pageNo < 1)
            pageNo = 1;

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

        if (pageSize < 0)
            pageSize = 0;
        this.firstEntityIndex = (int)firstEntityIndex;
        if (firstEntityIndex < 0)
            this.firstEntityIndex = 0;

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

        if (pageSize < 0)
            pageSize = 0;
        if (pageNo < 1)
            pageNo = 1;

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

    public int getEntityCount() {
        return entityCount;
    }

    public void setEntityCount(int entityCount) {
        this.entityCount = entityCount;
    }

    public int getFirstEntityIndex() {
        return firstEntityIndex;
    }

    public void setFirstEntityIndex(int firstEntityIndex) {
        this.firstEntityIndex = firstEntityIndex;
    }

    public int getLastEntityIndex() {
        return lastEntityIndex;
    }

    public void setLastEntityIndex(int lastEntityIndex) {
        this.lastEntityIndex = lastEntityIndex;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.pojo.vo;

<@printImport/>

${code}
