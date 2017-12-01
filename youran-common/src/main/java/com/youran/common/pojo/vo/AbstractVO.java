package com.youran.common.pojo.vo;


import com.youran.common.util.JsonUtil;

import java.io.Serializable;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/10/17 15:41
 */
public abstract class AbstractVO implements Serializable {

    private static final long serialVersionUID = -1417748095004687576L;

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
