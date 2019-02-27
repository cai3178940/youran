package com.youran.generate.constant;

import com.youran.common.validator.Check;

/**
 * <p>Title: 查询方式</p>
 * <p>Description: </p>
 * @author: cbb
 * @date: 2017/9/20
 */
public class QueryType {

    /**
     * 等于
     */
    public static final int EQ = 1;
    /**
     * like
     */
    public static final int LIKE = 2;
    /**
     * 大于
     */
    public static final int GT = 3;
    /**
     * 大于等于
     */
    public static final int GE = 4;
    /**
     * 小于
     */
    public static final int LT = 5;
    /**
     * 小于等于
     */
    public static final int LE = 6;
    /**
     * between
     */
    public static final int BETWEEN = 7;

    @Check
    public static final boolean check(int value) {
        return EQ == value || LIKE == value
            || GT == value || GE == value
            || LT == value || LE == value
            || BETWEEN == value;
    }

    /**
     * 映射符号
     * @param type
     * @return
     */
    public static String mapperSymbol(int type) {
        switch (type) {
            case EQ:
                return "=";
            case LIKE:
                return "like";
            case GT:
                return ">";
            case GE:
                return ">=";
            case LT:
                return "&lt;";
            case LE:
                return "&lt;=";
            case BETWEEN:
                return "between";
            default:
                return null;
        }
    }


    /**
     * 根据java字段类型和长度猜测queryType
     * @param jFieldType
     * @param length
     * @return
     */
    public static int guessQueryType(JFieldType jFieldType,int length){

        if(jFieldType==JFieldType.STRING){
            if(length>32){
                return LIKE;
            }else{
                return EQ;
            }
        }else if(jFieldType==JFieldType.INTEGER){
            return EQ;
        }else if(jFieldType==JFieldType.SHORT){
            return EQ;
        }else if(jFieldType==JFieldType.LONG){
            return EQ;
        }else if(jFieldType==JFieldType.FLOAT){
            return BETWEEN;
        }else if(jFieldType==JFieldType.DOUBLE){
            return BETWEEN;
        }else if(jFieldType==JFieldType.BIGDECIMAL){
            return BETWEEN;
        }else if(jFieldType==JFieldType.DATE){
            return BETWEEN;
        }

        return EQ;
    }

}
