package com.youran.generate.util;

import com.youran.generate.constant.JFieldType;
import com.youran.generate.constant.MySqlType;

import java.lang.reflect.Field;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/5/21 22:51
 */
public class MetadataUtil {


    /**
     * 验证java字段和数据库字段是否默认匹配
     * @param jfieldType
     * @param fieldType
     * @return
     */
    public static boolean matchMySqlType(String jfieldType,String fieldType){
        String mapperFieldType = JFieldType.mapperMySqlType(jfieldType);
        return fieldType.equals(mapperFieldType);
    }

    /**
     * 验证java字段和jdbc类型是否默认匹配
     * @param jfieldType
     * @param fieldType
     * @return
     */
    public static boolean matchJdbcType(String jfieldType,String fieldType){
        String mapperFieldType = JFieldType.mapperJdbcType(jfieldType);
        return fieldType.equals(mapperFieldType);
    }

    /**
     * 获取数据库字段的java代码字面表观
     * @param fieldType
     * @return
     */
    public static String getFieldTypeApparent(String fieldType){
        Field[] declaredFields = MySqlType.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            try {
                Object value = declaredField.get(MySqlType.class);
                if(value.equals(fieldType)){
                    return "MySqlType."+declaredField.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
