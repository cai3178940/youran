package com.youran.common.pojo.dto;


import com.youran.common.util.JsonUtil;

import java.io.Serializable;

/**
 * <p>Title:数据传输对象超类</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
public abstract class AbstractDTO implements Serializable {

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
