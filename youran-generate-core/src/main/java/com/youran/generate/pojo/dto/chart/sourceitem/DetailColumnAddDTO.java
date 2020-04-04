package com.youran.generate.pojo.dto.chart.sourceitem;

import com.youran.common.validator.Const;
import com.youran.generate.constant.CustomFieldType;
import com.youran.generate.constant.SourceItemSubType;
import com.youran.generate.constant.SourceItemType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 新增【明细列】入参
 *
 * @author: cbb
 * @date: 2020-04-04
 */
@ApiModel(description = "新增【明细列】入参")
public class DetailColumnAddDTO extends AbstractSourceItemDTO {

    @ApiModelProperty(notes = "字段id", example = "1")
    private Integer fieldId;

    @ApiModelProperty(notes = "别名", example = "alias1")
    private String alias;

    @ApiModelProperty(notes = "是否自定义", example = "true", required = true)
    @NotNull
    private Boolean custom;

    @ApiModelProperty(notes = "自定义内容")
    private String customContent;

    @ApiModelProperty(notes = "自定义字段类型", example = "1", allowableValues = CustomFieldType.VALUES_STR)
    @Const(constClass = CustomFieldType.class)
    private Integer customFieldType;

    @Override
    public Integer getType() {
        return SourceItemType.DETAIL_COLUMN.getValue();
    }

    @Override
    public Integer getSubType() {
        return SourceItemSubType.NONE.getValue();
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public String getCustomContent() {
        return customContent;
    }

    public void setCustomContent(String customContent) {
        this.customContent = customContent;
    }

    public Integer getCustomFieldType() {
        return customFieldType;
    }

    public void setCustomFieldType(Integer customFieldType) {
        this.customFieldType = customFieldType;
    }
}
