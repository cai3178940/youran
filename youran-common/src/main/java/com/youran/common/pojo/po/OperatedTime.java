package com.youran.common.pojo.po;

import java.util.Date;

/**
 * <p>Title:操作日期接口</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/25
 */
public interface OperatedTime {

    /**
     * 获取操作时间
     * @return
     */
    Date getOperatedTime();

    /**
     * 设置操作时间
     * @param operatedTime
     */
    void setOperatedTime(Date operatedTime);

}
