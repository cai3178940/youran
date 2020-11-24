package com.youran.common.pojo.qo;

import io.swagger.annotations.ApiParam;

import javax.validation.constraints.Max;

/**
 * 查询选项入参
 *
 * @author cbb
 * @date 2020/11/23
 */
public class OptionQO<K, V> extends AbstractQO {

    public static final int DEFAULT_LIMIT = 10000;
    /**
     * 一次请求加载条数
     */
    @ApiParam(value = "一次请求加载条数", example = "20")
    @Max(value = 1000, message = "limit不能大于1000")
    protected Integer limit;

    /**
     * 上次查询最后一条记录的key
     */
    @ApiParam(value = "上次查询最后一条记录的key", example = "1")
    protected K lastKey;

    /**
     * 匹配值
     */
    @ApiParam(value = "匹配值")
    protected V matchValue;

    public OptionQO() {
        // 默认查询1万条
        this.limit = DEFAULT_LIMIT;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public K getLastKey() {
        return lastKey;
    }

    public void setLastKey(K lastKey) {
        this.lastKey = lastKey;
    }

    public V getMatchValue() {
        return matchValue;
    }

    public void setMatchValue(V matchValue) {
        this.matchValue = matchValue;
    }

}

