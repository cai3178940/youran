package com.youran.common.pojo.qo;

import com.youran.common.util.JsonUtil;

import java.io.Serializable;

/**
 * Title: 抽象数据查询参数
 * Description:
 * Author: cbb
 * Create Time: 2018/1/24 16:21
 */
public abstract class AbstractQO  implements Serializable {

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
