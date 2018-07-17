package com.youran.generate.pojo.vo;

import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author cbb
 * @date 2018/7/17
 */
public class EntityDiagramVO {

    /**
     * 实体名称
     */
    private String key;
    /**
     * 字段列表
     */
    private List<FieldDiagramVO> items;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<FieldDiagramVO> getItems() {
        return items;
    }

    public void setItems(List<FieldDiagramVO> items) {
        this.items = items;
    }
}
