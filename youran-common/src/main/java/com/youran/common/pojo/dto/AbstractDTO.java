package com.youran.common.pojo.dto;


import com.youran.common.util.JsonUtil;

import java.io.Serializable;

/**
 * Title:数据传输对象超类
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 21:26
 */
public class AbstractDTO implements Serializable {

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
