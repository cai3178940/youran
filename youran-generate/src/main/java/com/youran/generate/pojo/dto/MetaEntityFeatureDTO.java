package com.youran.generate.pojo.dto;

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
    @ApiModelProperty(notes = "是否生成save方法", example = "true")
    private Boolean genSave = true;
    /**
     * 是否生成update方法
     */
    @ApiModelProperty(notes = "是否生成update方法", example = "true")
    private Boolean genUpdate = true;
    /**
     * 是否生成delete方法
     */
    @ApiModelProperty(notes = "是否生成delete方法", example = "true")
    private Boolean genDelete = true;
    /**
     * 是否生成deleteBatch方法
     */
    @ApiModelProperty(notes = "是否生成deleteBatch方法", example = "true")
    private Boolean genDeleteBatch = true;
    /**
     * 是否生成list方法
     */
    @ApiModelProperty(notes = "是否生成list方法", example = "true")
    private Boolean genList = true;
    /**
     * 是否生成show方法
     */
    @ApiModelProperty(notes = "是否生成show方法", example = "true")
    private Boolean genShow = true;

    public Boolean getGenSave() {
        return genSave;
    }

    public void setGenSave(Boolean genSave) {
        this.genSave = genSave;
    }

    public Boolean getGenUpdate() {
        return genUpdate;
    }

    public void setGenUpdate(Boolean genUpdate) {
        this.genUpdate = genUpdate;
    }

    public Boolean getGenDelete() {
        return genDelete;
    }

    public void setGenDelete(Boolean genDelete) {
        this.genDelete = genDelete;
    }

    public Boolean getGenDeleteBatch() {
        return genDeleteBatch;
    }

    public void setGenDeleteBatch(Boolean genDeleteBatch) {
        this.genDeleteBatch = genDeleteBatch;
    }

    public Boolean getGenList() {
        return genList;
    }

    public void setGenList(Boolean genList) {
        this.genList = genList;
    }

    public Boolean getGenShow() {
        return genShow;
    }

    public void setGenShow(Boolean genShow) {
        this.genShow = genShow;
    }
}
