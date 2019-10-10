package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;

import java.util.List;

/**
 * <p>Title:实体内部校验结果展示对象</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2019/10/10
 */
public class MetaEntityInnerValidateVO extends AbstractVO {

    /**
     * 字段校验信息
     */
    private List<MetaFieldValidateVO> fields;

    public List<MetaFieldValidateVO> getFields() {
        return fields;
    }

    public void setFields(List<MetaFieldValidateVO> fields) {
        this.fields = fields;
    }
}
