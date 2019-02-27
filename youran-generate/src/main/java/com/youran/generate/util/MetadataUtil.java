package com.youran.generate.util;

import com.google.common.base.Joiner;
import com.youran.common.constant.BoolConst;
import com.youran.generate.constant.JFieldType;
import com.youran.generate.constant.MySqlType;
import com.youran.generate.pojo.po.MetaFieldPO;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/21
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


    /**
     * 下划线转驼峰
     * @param name
     * @param capFirst
     * @return
     */
    public static String underlineToCamelCase(String name,boolean capFirst){
        String[] split = StringUtils.split(name,"_");
        String value = Arrays.stream(split)
            .map(s -> StringUtils.capitalize(s.toLowerCase()))
            .collect(Collectors.joining(""));
        if(!capFirst){
            return StringUtils.uncapitalize(value);
        }
        return value;
    }

    /**
     * 驼峰转下划线
     * @param name
     * @param upCase
     * @return
     */
    public static String camelCaseToUnderline(String name,boolean upCase){
        String[] split = StringUtils.splitByCharacterTypeCamelCase(name);
        Stream<String> stream = Arrays.stream(split);
        if(upCase){
            stream = stream.map(String::toUpperCase);
        }else{
            stream = stream.map(String::toLowerCase);
        }
        return stream.collect(Collectors.joining("_"));
    }



    /**
     * 是否需要展示字段长度
     * @param fieldType
     * @return
     */
    public static boolean showFieldLength(String fieldType){
        return !MySqlType.DATETIME.equals(fieldType)
            && !MySqlType.TEXT.equals(fieldType);
    }
    /**
     * 是否需要展示字段精度
     * @param fieldType
     * @return
     */
    public static boolean showFieldScale(String fieldType){
        return MySqlType.DECIMAL.equals(fieldType)
            ||MySqlType.DOUBLE.equals(fieldType)
            ||MySqlType.FLOAT.equals(fieldType);
    }
    /**
     * 默认值是否需要展示单引号
     * @param fieldType
     * @return
     */
    public static boolean showDefaultValueApostrophe(String fieldType){
        return MySqlType.TEXT.equals(fieldType)
            ||MySqlType.VARCHAR.equals(fieldType)
            ||MySqlType.CHAR.equals(fieldType)
            ||MySqlType.DATETIME.equals(fieldType);
    }

    /**
     * 获取【字段长度】展示
     * @return
     */
    public static String getLengthDisplay(MetaFieldPO field){
        if(!showFieldLength(field.getFieldType())){
            return "";
        }
        StringBuilder sb = new StringBuilder("(");
        sb.append(field.getFieldLength());
        if(showFieldScale(field.getFieldType())){
            sb.append(",").append(field.getFieldScale());
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 获取【字段自增】展示
     * @return
     */
    public static String getAutoIncrementDisplay(MetaFieldPO field){
        if(field.getPrimaryKey() == BoolConst.TRUE
            && field.getAutoIncrement() == BoolConst.TRUE){
            return " AUTO_INCREMENT";
        }
        return "";
    }

    /**
     * 获取【字段非空】展示
     * @return
     */
    public static String getNotNullDisplay(MetaFieldPO field){
        if(field.getPrimaryKey() == BoolConst.FALSE
            && field.getNotNull() == BoolConst.TRUE){
            return " NOT NULL";
        }
        return "";

    }

    /**
     * 获取【字段默认值】展示
     * @return
     */
    public static String getDefaultDisplay(MetaFieldPO field){
        // 主键无默认值
        if(BoolConst.TRUE==field.getPrimaryKey()){
            return "";
        }
        if("NULL".equals(field.getDefaultValue())) {
            if (field.getNotNull() == BoolConst.FALSE){
                return " DEFAULT NULL";
            }else {
                return "";
            }
        }else{
            StringBuilder sb = new StringBuilder(" DEFAULT ");
            boolean showApostrophe = showDefaultValueApostrophe(field.getFieldType());
            if(showApostrophe){
                sb.append("'");
            }
            sb.append(field.getDefaultValue());
            if(showApostrophe){
                sb.append("'");
            }
            return sb.toString();
        }
    }

    /**
     * 转换【备注】展示
     * @param comment
     * @return
     */
    public static String convertCommentDisplay(String comment){
        if(StringUtils.isBlank(comment)){
            return "";
        }
        return comment.replaceAll("\'","\"")
            .replaceAll("\n","\\n");
    }

    /**
     * 获取【备注】展示
     * @param comment
     * @param forField 字段或表
     * @return
     */
    public static String getCommentDisplay(String comment,boolean forField){
        if(StringUtils.isBlank(comment)){
            return "";
        }
        StringBuilder sb = new StringBuilder(" COMMENT");
        if(forField){
            sb.append(" ");
        }else{
            sb.append("=");
        }
        sb.append("'")
            .append(convertCommentDisplay(comment))
            .append("'");
        return sb.toString();
    }



}
