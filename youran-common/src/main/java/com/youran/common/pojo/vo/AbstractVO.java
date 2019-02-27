package com.youran.common.pojo.vo;


import com.youran.common.util.JsonUtil;

import java.io.Serializable;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/10/17
 */
public abstract class AbstractVO implements Serializable {

    private static final long serialVersionUID = -1417748095004687576L;

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
