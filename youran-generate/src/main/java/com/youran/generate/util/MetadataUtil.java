package com.youran.generate.util;

import com.google.common.base.Joiner;
import com.youran.common.constant.BoolConst;
import com.youran.generate.constant.DefaultValue;
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

    private static final String[] SPECIAL_DEFAULT_VALUE = {"NOW","CURRENT_TIMESTAMP"};

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
        return !MySqlType.DATE.equals(fieldType)
            && !MySqlType.DATETIME.equals(fieldType)
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
     * 获取【字段长度】展示
     * @return
     */
    public static String getLengthDisplay(MetaFieldPO field){
        if(!showFieldLength(field.getFieldType())){
            return "";
        }
        // 如果字段长度为0,则直接返回空串
        if(field.getFieldLength()<=0){
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
        if(DefaultValue.NULL.equals(field.getDefaultValue())) {
            if (field.getNotNull() == BoolConst.FALSE){
                return " DEFAULT NULL";
            }else {
                return "";
            }
        }else{
            StringBuilder sb = new StringBuilder(" DEFAULT ");
            // 是否需要引号包裹
            boolean needWrap = ifDefaultValueNeedWrap(field);
            if(needWrap){
                sb.append("'");
            }
            if(StringUtils.isBlank(field.getDefaultValue())){
                sb.append(GuessUtil.guessDefaultValueByFieldType(field.getFieldType()));
            }else {
                sb.append(field.getDefaultValue());
            }
            if(needWrap){
                sb.append("'");
            }
            return sb.toString();
        }
    }

    /**
     * 判断默认值是否需要引号包裹
     * @param field
     * @return
     */
    public static boolean ifDefaultValueNeedWrap(MetaFieldPO field){
        // 字符型需要引号包裹
        if(MySqlType.isStringType(field.getFieldType())){
            return true;
        }
        // 日期字段特殊默认值不需要引号包裹
        if(MySqlType.isDateType(field.getFieldType()) || MySqlType.isDateTimeType(field.getFieldType())){
            String defaultValue = field.getDefaultValue();
            if(StringUtils.isNotBlank(defaultValue)){
                for (String specialValue : SPECIAL_DEFAULT_VALUE) {
                    if(defaultValue.toUpperCase().indexOf(specialValue) > -1){
                        return false;
                    }
                }
            }
            // 非特殊默认值，需要引号包裹
            return true;
        }
        return false;
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
            .replaceAll("\n","");
    }

    /**
     * 转换【备注】展示，增加缩进星号
     * @param comment
     * @return
     */
    public static String convertCommentDisplayWithIndentStar(String comment){
        if(StringUtils.isBlank(comment)){
            return "     *";
        }
        String[] lines = comment.split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            sb.append("     * ").append(line);
            if(i<lines.length-1){
                sb.append("\n");
            }
        }
        return sb.toString();
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
