package com.youran.generate.util;

import com.youran.generate.constant.JFieldType;
import com.youran.generate.constant.MetaSpecialField;
import com.youran.generate.constant.MySqlType;
import com.youran.generate.constant.QueryType;

/**
 * <p>Title: 猜测工具类</p>
 * <p>Description: 用来智能猜测元数据字段</p>
 * @author cbb
 * @date 2019/3/15
 */
public class GuessUtil {


    public static final String[] CREATE_PREFIX = {"create","created"};
    public static final String[] OPERATE_PREFIX = {"operate","operated","update","updated","modify","modified"};
    public static final String[] TIME_SUFFIX = {"time","date","at"};
    public static final String[] USER_SUFFIX = {"by","user","er","or"};

    /**
     * 根据字段类型猜测默认值
     * @param fieldType
     * @return
     */
    public static String guessDefaultValueByFieldType(String fieldType){
        if(MySqlType.isStringType(fieldType)){
            return "";
        }
        if(MySqlType.isNumberType(fieldType)){
            return "0";
        }
        if(MySqlType.isTimestampType(fieldType)){
            return "0";
        }
        if(MySqlType.isDateType(fieldType)){
            return "1900-01-01 00:00:00";
        }
        return "";
    }

    /**
     * 根据java字段类型和长度猜测queryType
     * @param jFieldType
     * @param length
     * @return
     */
    public static int guessQueryType(JFieldType jFieldType, int length){
        if(jFieldType==JFieldType.STRING){
            if(length > QueryType.LIKE_LENGTH_THRESHOLD){
                return QueryType.LIKE;
            }else{
                return QueryType.EQ;
            }
        }else if(jFieldType==JFieldType.INTEGER){
            return QueryType.EQ;
        }else if(jFieldType==JFieldType.SHORT){
            return QueryType.EQ;
        }else if(jFieldType==JFieldType.LONG){
            return QueryType.EQ;
        }else if(jFieldType==JFieldType.FLOAT){
            return QueryType.BETWEEN;
        }else if(jFieldType==JFieldType.DOUBLE){
            return QueryType.BETWEEN;
        }else if(jFieldType==JFieldType.BIGDECIMAL){
            return QueryType.BETWEEN;
        }else if(jFieldType==JFieldType.DATE){
            return QueryType.BETWEEN;
        }
        return QueryType.EQ;
    }

    /**
     * 判断字段名是否匹配规则
     * @param fieldNameLowerCase 小写的字段名
     * @param labels 规则数组
     * @param prefix 前缀还是后缀
     * @return
     */
    private static boolean isMatchLabel(String fieldNameLowerCase,String[] labels,boolean prefix){
        for (String label : labels) {
            if(prefix) {
                if (fieldNameLowerCase.startsWith(label)) {
                    return true;
                }
            }else{
                if (fieldNameLowerCase.endsWith(label)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 猜测
     * @param fieldName
     * @param jFieldType
     * @return
     */
    public static String guessSpecialField(String fieldName, JFieldType jFieldType){
        String lowerCase = fieldName.toLowerCase();
        boolean createPre = isMatchLabel(lowerCase,CREATE_PREFIX,true);
        boolean operatePre = isMatchLabel(lowerCase,OPERATE_PREFIX,true);
        if(jFieldType==JFieldType.DATE){
            boolean timeSuff = isMatchLabel(lowerCase,TIME_SUFFIX,false);
            if(createPre && timeSuff){
                return MetaSpecialField.CREATED_TIME;
            }
            if(operatePre && timeSuff){
                return MetaSpecialField.OPERATED_TIME;
            }
        }
        if(jFieldType==JFieldType.STRING) {
            boolean userSuff = isMatchLabel(lowerCase,USER_SUFFIX,false);
            if(createPre && userSuff){
                return MetaSpecialField.CREATED_BY;
            }
            if(operatePre && userSuff){
                return MetaSpecialField.OPERATED_BY;
            }
        }
        return null;
    }



}
