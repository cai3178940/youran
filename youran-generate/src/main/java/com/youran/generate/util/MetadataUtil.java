package com.youran.generate.util;

import com.google.common.base.Joiner;
import com.youran.generate.constant.JFieldType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
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
     * 映射swagger字段类型常量
     * @param jfieldType
     * @return
     */
    public static String getSwaggerType(String jfieldType){
        JFieldType jFieldType = JFieldType.find(jfieldType);
        if(jFieldType==JFieldType.INTEGER){
            return "int";
        }
        return jfieldType;
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
