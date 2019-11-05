package com.youran.generate.pojo.dto;

import com.youran.common.constant.BoolConst;
import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.common.validator.Const;
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
    @ApiModelProperty(notes = "是否生成实体单独添加和移除对方关联关系的方法", example = "0")
    @Const(constClass = BoolConst.class)
    private Integer addRemove = BoolConst.FALSE;
    /**
     * 是否生成实体设置对方关联关系的方法
     */
    @ApiModelProperty(notes = "是否生成实体设置对方关联关系的方法", example = "1")
    @Const(constClass = BoolConst.class)
    private Integer set = BoolConst.TRUE;
    /**
     * 实体是否在添加修改操作时一同维护对方关联
     */
    @ApiModelProperty(notes = "实体是否在添加修改操作时一同维护对方关联", example = "1")
    @Const(constClass = BoolConst.class)
    private Integer withinEntity = BoolConst.TRUE;

    public Integer getAddRemove() {
        return addRemove;
    }

    public void setAddRemove(Integer addRemove) {
        this.addRemove = addRemove;
    }

    public Integer getSet() {
        return set;
    }

    public void setSet(Integer set) {
        this.set = set;
    }

    public Integer getWithinEntity() {
        return withinEntity;
    }

    public void setWithinEntity(Integer withinEntity) {
        this.withinEntity = withinEntity;
    }
}
