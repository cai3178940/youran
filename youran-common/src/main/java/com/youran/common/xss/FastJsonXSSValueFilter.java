package com.youran.common.xss;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * Title:XSS过滤
 * Description:
 * Author: cbb
 * Create Time: 2018/3/2 17:55
 */
public class FastJsonXSSValueFilter implements ValueFilter {


    @Override
    public Object process(Object obj, String name, Object value) {
        if(value instanceof String) {
            return XSSUtil.clean((String)value);
        }
        return value;
    }

}
