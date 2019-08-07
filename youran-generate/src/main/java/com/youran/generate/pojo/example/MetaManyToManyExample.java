package com.youran.generate.pojo.example;

import com.youran.common.pojo.example.AbstractExample;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/7/4
 */
public class MetaManyToManyExample extends AbstractExample {

    public static final String N_MTMID = "主键id";
    public static final String E_MTMID = "1";
    public static final String N_PROJECTID = "所属项目id";
    public static final String E_PROJECTID = "1";
    public static final String N_TABLENAME = "关联表名";
    public static final String E_TABLENAME = "r_a_b";
    public static final String N_SCHEMANAME = "模式名";
    public static final String E_SCHEMANAME = "test";
    public static final String N_DESC = "关联描述";
    public static final String E_DESC = "a和b关联表";
    public static final String N_ENTITYID1 = "实体A的id";
    public static final String E_ENTITYID1 = "1";
    public static final String N_ENTITYID2 = "实体B的id";
    public static final String E_ENTITYID2 = "2";
    public static final String N_HOLDREFER1 = "实体A是否持有B的引用";
    public static final String E_HOLDREFER1 = "1";
    public static final String N_HOLDREFER2 = "实体B是否持有A的引用";
    public static final String E_HOLDREFER2 = "1";
    public static final String N_ENTITYIDFIELD1 = "实体A对应多对多关联表的id字段名";
    public static final String E_ENTITYIDFIELD1 = "id_1";
    public static final String N_ENTITYIDFIELD2 = "实体B对应多对多关联表的id字段名";
    public static final String E_ENTITYIDFIELD2 = "id_2";
    public static final String N_NEEDID = "是否需要自增id字段";
    public static final String E_NEEDID = "true";
    public static final String N_BIGID = "id字段是否bigint";
    public static final String E_BIGID = "true";
    public static final String N_SORTED = "是否需要排序";
    public static final String E_SORTED = "true";
    public static final String N_SORTFIELD = "排序字段名";
    public static final String E_SORTFIELD = "order_no";

}
