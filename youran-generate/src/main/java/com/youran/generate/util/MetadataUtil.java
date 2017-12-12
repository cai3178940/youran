package com.youran.generate.util;

import com.google.common.base.Joiner;
import com.youran.generate.constant.JFieldType;
import com.youran.generate.constant.MySqlType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/5/21 22:51
 */
public class MetadataUtil {

    public static List<String> MYSQL_KEYWORD;

    static{
        try {
            InputStream stream = MetadataUtil.class.getClassLoader().getResourceAsStream("mysql_keyword.txt");
            MYSQL_KEYWORD = IOUtils.readLines(stream, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将mysql中的关键字加``包裹
     * @return
     */
    public static String wrapMysqlKeyword(String fieldName){
        if(MYSQL_KEYWORD.contains(fieldName.toUpperCase())){
            return "`"+fieldName+"`";
        }
        return fieldName;
    }

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


    /**
     * 获取主键别名
     * @param className
     * @param forSql
     * @return
     */
    public static String getPkAlias(String className,boolean forSql){
        String alias = StringUtils.uncapitalize(className)+"Id";
        if(forSql){
            String[] split = StringUtils.splitByCharacterTypeCamelCase(alias);
            String join = Joiner.on("_").join(split);
            alias = join.toLowerCase();
        }
        return alias;
    }

}
