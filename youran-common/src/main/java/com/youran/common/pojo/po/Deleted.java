package com.youran.common.pojo.po;

/**
 * <p>Title:是否逻辑删除接口</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/25
 */
public interface Deleted {

    /**
     * 获取逻辑删除标识
     * @return
     */
    Boolean getDeleted();

    /**
     * 设置逻辑删除标识
     * @param deleted
     */
    void setDeleted(Boolean deleted);

}
