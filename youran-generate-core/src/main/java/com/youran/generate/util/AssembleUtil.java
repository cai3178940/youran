package com.youran.generate.util;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;

import java.util.Map;

/**
 * 元数据装配工具类
 *
 * @author: cbb
 * @date: 2020-05-17
 */
public class AssembleUtil {

    /**
     * 从map里面强制获取实体，不存在则抛异常
     *
     * @param entityMap
     * @param entityId
     * @return
     */
    public static MetaEntityPO forceGetEntityFromMap(Map<Integer, MetaEntityPO> entityMap, Integer entityId) {
        MetaEntityPO entityPO = entityMap.get(entityId);
        if (entityPO == null) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源异常，实体不存在，entityId=" + entityId);
        }
        return entityPO;
    }

    /**
     * 从map里面强制获取多对多，不存在则抛异常
     *
     * @param mtmMap
     * @param mtmId
     * @return
     */
    public static MetaManyToManyPO forceGetMtmFromMap(Map<Integer, MetaManyToManyPO> mtmMap, Integer mtmId) {
        MetaManyToManyPO mtmPO = mtmMap.get(mtmId);
        if (mtmPO == null) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "图表数据源异常，多对多不存在，mtmId=" + mtmId);
        }
        return mtmPO;
    }
}
