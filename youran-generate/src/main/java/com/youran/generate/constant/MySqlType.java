package com.youran.generate.constant;

import com.youran.common.validator.Check;

/**
 * <p>Title: Mysql字段类型常量</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2017/4/11 13:20
 */
public class MySqlType {

    public static final  String INT = "int";

    public static final  String VARCHAR = "varchar";

    public static final  String TEXT = "text";

    public static final  String DATETIME = "datetime";

    public static final  String FLOAT = "float";

    public static final  String DOUBLE = "double";

    public static final  String DECIMAL = "decimal";

    public static final  String BIGINT = "bigint";

    public static final  String SMALLINT = "smallint";

    public static final  String MEDIUMINT = "mediumint";

    public static final  String TINYINT = "tinyint";

    public static final  String TIMESTAMP = "timestamp";

    public static final  String CHAR = "char";


    /**
     * 校验有效性
     */
    @Check
    public static final boolean validate(String mysqlType){
        return INT.equals(mysqlType)||
            VARCHAR.equals(mysqlType)||
            TEXT.equals(mysqlType)||
            DATETIME.equals(mysqlType)||
            FLOAT.equals(mysqlType)||
            DOUBLE.equals(mysqlType)||
            DECIMAL.equals(mysqlType)||
            BIGINT.equals(mysqlType)||
            SMALLINT.equals(mysqlType)||
            MEDIUMINT.equals(mysqlType)||
            TINYINT.equals(mysqlType)||
            TIMESTAMP.equals(mysqlType)||
            CHAR.equals(mysqlType);
    }

    public static final JFieldType mapperJFieldType(String mySqlType, int fieldLength){
        if(INT.equals(mySqlType)){
            return JFieldType.INTEGER;
        }
        if(VARCHAR.equals(mySqlType)){
            return JFieldType.STRING;
        }
        if(TEXT.equals(mySqlType)){
            return JFieldType.STRING;
        }
        if(DATETIME.equals(mySqlType)){
            return JFieldType.DATE;
        }
        if(FLOAT.equals(mySqlType)){
            return JFieldType.FLOAT;
        }
        if(DOUBLE.equals(mySqlType)){
            return JFieldType.DOUBLE;
        }
        if(DECIMAL.equals(mySqlType)){
            return JFieldType.BIGDECIMAL;
        }
        if(BIGINT.equals(mySqlType)){
            return JFieldType.LONG;
        }
        if(SMALLINT.equals(mySqlType)){
            return JFieldType.INTEGER;
        }
        if(MEDIUMINT.equals(mySqlType)){
            return JFieldType.INTEGER;
        }
        if(TINYINT.equals(mySqlType)){
            if(fieldLength==1){
                return JFieldType.BOOLEAN;
            }else {
                return JFieldType.INTEGER;
            }
        }
        if(TIMESTAMP.equals(mySqlType)){
            return JFieldType.DATE;
        }
        if(CHAR.equals(mySqlType)){
            return JFieldType.STRING;
        }
        return JFieldType.STRING;
    }


    /**
     * 判断字段类型是否字符串格式
     * @param mySqlType
     * @return
     */
    public static boolean isStringType(String mySqlType){
        return TEXT.equals(mySqlType)
            ||VARCHAR.equals(mySqlType)
            ||CHAR.equals(mySqlType);
    }

    /**
     * 判断字段类型是否数字格式
     * @param mySqlType
     * @return
     */
    public static boolean isNumberType(String mySqlType){
        return INT.equals(mySqlType)
            ||BIGINT.equals(mySqlType)
            ||SMALLINT.equals(mySqlType)
            ||MEDIUMINT.equals(mySqlType)
            ||TINYINT.equals(mySqlType)
            ||FLOAT.equals(mySqlType)
            ||DOUBLE.equals(mySqlType)
            ||DECIMAL.equals(mySqlType);
    }
    /**
     * 判断字段类型是否时间戳格式
     * @param mySqlType
     * @return
     */
    public static boolean isTimestampType(String mySqlType){
        return TIMESTAMP.equals(mySqlType);
    }

    /**
     * 判断字段类型是否日期格式
     * @param mySqlType
     * @return
     */
    public static boolean isDateType(String mySqlType){
        return DATETIME.equals(mySqlType);
    }



}
