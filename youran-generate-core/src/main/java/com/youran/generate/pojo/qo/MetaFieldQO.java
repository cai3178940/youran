package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.AbstractQO;
import com.youran.common.util.ConvertUtil;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.AssertTrue;
import java.util.List;

import static com.youran.generate.pojo.example.MetaFieldExample.E_ENTITYID;
import static com.youran.generate.pojo.example.MetaFieldExample.N_ENTITYID;

/**
 * 查询参数
 *
 * @author: cbb
 * @date: 2017/5/12
 */
public class MetaFieldQO extends AbstractQO {

    @ApiParam(value = N_ENTITYID, example = E_ENTITYID)
    private Integer entityId;

    @ApiParam(value = "字段id【逗号分隔】", example = "1,2,3")
    private String fieldId;

    @ApiParam(hidden = true)
    private List<Integer> _fieldIds;

    @ApiParam(value = "是否携带级联字段数量", example = "true")
    private Boolean withCascadeFieldNum;

    @AssertTrue(message = "缺少入参")
    public boolean validate() {
        if (entityId == null && StringUtils.isBlank(fieldId)) {
            return false;
        }
        this._fieldIds = ConvertUtil.convertIntegerList(fieldId);
        return true;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public List<Integer> get_fieldIds() {
        return _fieldIds;
    }

    public void set_fieldIds(List<Integer> _fieldIds) {
        this._fieldIds = _fieldIds;
    }

    public Boolean getWithCascadeFieldNum() {
        return withCascadeFieldNum;
    }

    public void setWithCascadeFieldNum(Boolean withCascadeFieldNum) {
        this.withCascadeFieldNum = withCascadeFieldNum;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }
}
