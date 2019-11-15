package com.youran.common.pojo.dto;


import com.youran.common.util.JsonUtil;

import java.io.Serializable;

/**
 * 数据传输对象超类
 *
 * @author: cbb
 * @date: 2017/5/12
 */
public abstract class AbstractDTO implements Serializable {

    private static final long serialVersionUID = 1915714417292764241L;

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
