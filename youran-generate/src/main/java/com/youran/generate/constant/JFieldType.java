package com.youran.generate.constant;

import com.youran.common.validator.Check;

import java.util.HashMap;
import java.util.Map;

/**
 * Title:字段类型对应关系
 * Description:
 * Author: cbb
 * Create Time:2017/9/20 15:19
 */
public enum JFieldType {

    INTEGER("Integer",MySqlType.INT,"INTEGER"),
    SHORT("Short",MySqlType.SMALLINT,"SMALLINT"),
    LONG("Long",MySqlType.BIGINT,"BIGINT"),
    STRING("String",MySqlType.VARCHAR,"VARCHAR"),
    DATE("Date",MySqlType.DATETIME,"TIMESTAMP"),
    DOUBLE("Double",MySqlType.DOUBLE,"DOUBLE"),
    FLOAT("Float",MySqlType.FLOAT,"FLOAT"),
    BIGDECIMAL("BigDecimal",MySqlType.DECIMAL,"DECIMAL");

    private final String javaType;
    private final String mySqlType;
    private final String jdbcType;

    JFieldType(String javaType, String mySqlType, String jdbcType) {
        this.javaType = javaType;
        this.mySqlType = mySqlType;
        this.jdbcType = jdbcType;
    }

    private static final Map<String, JFieldType> LOOKUP = new HashMap<>();

    static {
        for (JFieldType e : JFieldType.values()) {
            LOOKUP.put(e.javaType, e);
        }
    }

    public static JFieldType find(String javaType) {
        return LOOKUP.get(javaType);
    }


    /**
     * 校验有效性
     */
    @Check
    public static final boolean validate(String javaType){
        JFieldType theEnum = find(javaType);
        return theEnum!=null;
    }

    public String getJavaType() {
        return javaType;
    }

    public String getMySqlType() {
        return mySqlType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public static String mapperMySqlType(String javaType) {
        JFieldType theEnum = find(javaType);
        if(theEnum!=null){
            return theEnum.mySqlType;
        }
        return null;
    }

    public static String mapperJdbcType(String javaType) {
        JFieldType theEnum = find(javaType);
        if(theEnum!=null){
            return theEnum.jdbcType;
        }
        return null;
    }
}
