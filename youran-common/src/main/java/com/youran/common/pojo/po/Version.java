package com.youran.common.pojo.po;

/**
 * 乐观锁接口
 *
 * @author: cbb
 * @date: 2017/5/25
 */
public interface Version {

    /**
     * 获取乐观锁版本号
     *
     * @return
     */
    Integer getVersion();

    /**
     * 设置乐观锁版本号
     *
     * @param version
     */
    void setVersion(Integer version);

}
