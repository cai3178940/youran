package com.youran.generate.constant;

import com.youran.common.validator.Check;

/**
 * Mysql字段类型常量
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

    public static final JFieldType mapperJFieldType(String mySqlType){
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
            return JFieldType.INTEGER;
        }
        if(TIMESTAMP.equals(mySqlType)){
            return JFieldType.DATE;
        }
        if(CHAR.equals(mySqlType)){
            return JFieldType.STRING;
        }
        return JFieldType.STRING;
    }


}
