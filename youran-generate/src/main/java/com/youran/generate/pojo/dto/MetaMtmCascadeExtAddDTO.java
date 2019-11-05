package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import com.youran.generate.constant.PatternConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.youran.generate.pojo.example.MetaMtmCascadeExtExample.*;

/**
 * 新增【多对多级联扩展】的参数
 *
 * @author cbb
 * @date 2019/09/21
 */
@ApiModel(description = "新增【多对多级联扩展】的参数")
public class MetaMtmCascadeExtAddDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_MTM_ID, example = E_MTM_ID, required = true)
    @NotNull
    private Integer mtmId;

    @ApiModelProperty(notes = N_ENTITY_ID, example = E_ENTITY_ID, required = true)
    @NotNull
    private Integer entityId;

    @ApiModelProperty(notes = N_CASCADE_ENTITY_ID, example = E_CASCADE_ENTITY_ID, required = true)
    @NotNull
    private Integer cascadeEntityId;

    @ApiModelProperty(notes = N_CASCADE_FIELD_ID, example = E_CASCADE_FIELD_ID, required = true)
    @NotNull
    private Integer cascadeFieldId;

    @ApiModelProperty(notes = N_ALIAS, example = E_ALIAS, required = true)
    @NotNull
    @Length(max = 255)
    @Pattern(regexp = PatternConst.J_FIELD_NAME, message = PatternConst.J_FIELD_NAME_MSG)
    private String alias;

    @ApiModelProperty(notes = N_LIST, example = E_LIST, required = true)
    @NotNull
    private Integer list;

    @ApiModelProperty(notes = N_SHOW, example = E_SHOW, required = true)
    @NotNull
    private Integer show;

    @ApiModelProperty(notes = N_QUERY, example = E_QUERY, required = true)
    @NotNull
    private Integer query;


    public Integer getMtmId() {
        return this.mtmId;
    }

    public void setMtmId(Integer mtmId) {
        this.mtmId = mtmId;
    }

    public Integer getEntityId() {
        return this.entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getCascadeEntityId() {
        return this.cascadeEntityId;
    }

    public void setCascadeEntityId(Integer cascadeEntityId) {
        this.cascadeEntityId = cascadeEntityId;
    }

    public Integer getCascadeFieldId() {
        return this.cascadeFieldId;
    }

    public void setCascadeFieldId(Integer cascadeFieldId) {
        this.cascadeFieldId = cascadeFieldId;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getList() {
        return this.list;
    }

    public void setList(Integer list) {
        this.list = list;
    }

    public Integer getShow() {
        return this.show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public Integer getQuery() {
        return this.query;
    }

    public void setQuery(Integer query) {
        this.query = query;
    }

}


