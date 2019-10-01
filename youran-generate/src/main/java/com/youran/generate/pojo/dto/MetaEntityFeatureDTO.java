package com.youran.generate.pojo.dto;

import com.youran.common.constant.BoolConst;
import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Title: 实体特性</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/9/30
 */
public class MetaEntityFeatureDTO extends AbstractDTO {

    /**
     * 是否生成save方法
     */
    @ApiModelProperty(notes = "是否生成save方法", example = "1")
    private Integer save = BoolConst.TRUE;
    /**
     * 是否生成update方法
     */
    @ApiModelProperty(notes = "是否生成update方法", example = "1")
    private Integer update = BoolConst.TRUE;
    /**
     * 是否生成delete方法
     */
    @ApiModelProperty(notes = "是否生成delete方法", example = "1")
    private Integer delete = BoolConst.TRUE;
    /**
     * 是否生成deleteBatch方法
     */
    @ApiModelProperty(notes = "是否生成deleteBatch方法", example = "1")
    private Integer deleteBatch = BoolConst.TRUE;
    /**
     * 是否生成list方法
     */
    @ApiModelProperty(notes = "是否生成list方法", example = "1")
    private Integer list = BoolConst.TRUE;
    /**
     * 是否生成show方法
     */
    @ApiModelProperty(notes = "是否生成show方法", example = "1")
    private Integer show = BoolConst.TRUE;

    public Integer getSave() {
        return save;
    }

    public void setSave(Integer save) {
        this.save = save;
    }

    public Integer getUpdate() {
        return update;
    }

    public void setUpdate(Integer update) {
        this.update = update;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public Integer getDeleteBatch() {
        return deleteBatch;
    }

    public void setDeleteBatch(Integer deleteBatch) {
        this.deleteBatch = deleteBatch;
    }

    public Integer getList() {
        return list;
    }

    public void setList(Integer list) {
        this.list = list;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }
}
