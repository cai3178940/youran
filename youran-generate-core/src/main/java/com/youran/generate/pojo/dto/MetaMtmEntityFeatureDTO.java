package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModelProperty;

/**
 * 多对多实体特性
 *
 * @author cbb
 * @date 2019/9/30
 */
public class MetaMtmEntityFeatureDTO extends AbstractDTO {

    /**
     * 是否生成实体单独添加和移除对方关联关系的方法
     */
    @ApiModelProperty(notes = "是否生成实体单独添加和移除对方关联关系的方法", example = "false")
    private Boolean addRemove = false;
    /**
     * 是否生成实体设置对方关联关系的方法
     */
    @ApiModelProperty(notes = "是否生成实体设置对方关联关系的方法", example = "true")
    private Boolean set = true;
    /**
     * 实体是否在添加修改操作时一同维护对方关联
     */
    @ApiModelProperty(notes = "实体是否在添加修改操作时一同维护对方关联", example = "true")
    private Boolean withinEntity = true;

    public Boolean getAddRemove() {
        return addRemove;
    }

    public void setAddRemove(Boolean addRemove) {
        this.addRemove = addRemove;
    }

    public Boolean getSet() {
        return set;
    }

    public void setSet(Boolean set) {
        this.set = set;
    }

    public Boolean getWithinEntity() {
        return withinEntity;
    }

    public void setWithinEntity(Boolean withinEntity) {
        this.withinEntity = withinEntity;
    }
}
