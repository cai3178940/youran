package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author cbb
 * @date 2018/7/17
 */
public class FieldDiagramVO extends AbstractVO {

    public static final String PRIMARY_KEY = "pk";
    public static final String FOREIGN_KEY = "fk";
    public static final String DELETE = "delete";
    public static final String VERSION = "version";

    private String name;
    private String type;
    private String desc;


    public FieldDiagramVO() {
    }

    public FieldDiagramVO(String name, String type, String desc) {
        this.name = name;
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("name", name)
            .append("type", type)
            .append("desc", desc)
            .toString();
    }
}
