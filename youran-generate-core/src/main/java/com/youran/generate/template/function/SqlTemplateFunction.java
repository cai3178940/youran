package com.youran.generate.template.function;

import com.youran.generate.constant.DefaultValue;
import com.youran.generate.constant.MySqlType;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.util.GuessUtil;
import com.youran.generate.util.MetadataUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * sql模板函数
 * <p>作为内置函数用来辅助freemarker模板
 *
 * @author cbb
 * @date 2019/11/11
 */
public class SqlTemplateFunction {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlTemplateFunction.class);

    private static final String[] SPECIAL_DEFAULT_VALUE = {"NOW", "CURRENT_TIMESTAMP"};
    private static List<String> MYSQL_KEYWORD;

    static {
        try {
            InputStream stream = MetadataUtil.class.getClassLoader().getResourceAsStream("mysql_keyword.txt");
            MYSQL_KEYWORD = IOUtils.readLines(stream, "utf-8");
        } catch (IOException e) {
            LOGGER.error("MetadataUtil初始化异常", e);
        }
    }

    /**
     * 将mysql中的关键字加``包裹
     *
     * @return
     */
    public static String wrapMysqlKeyword(String fieldName) {
        if (MYSQL_KEYWORD.contains(fieldName.toUpperCase())) {
            return "`" + fieldName + "`";
        }
        return fieldName;
    }


    /**
     * 获取【字段默认值】展示
     *
     * @return
     */
    public static String getDefaultDisplay(MetaFieldPO field) {
        // 主键无默认值
        if (field.getPrimaryKey()) {
            return "";
        }
        if (DefaultValue.NULL.equalsIgnoreCase(field.getDefaultValue())) {
            if (!field.getNotNull()) {
                return " DEFAULT NULL";
            } else {
                return "";
            }
        } else {
            StringBuilder sb = new StringBuilder(" DEFAULT ");
            // 是否需要引号包裹
            boolean needWrap = ifDefaultValueNeedWrap(field);
            if (needWrap) {
                sb.append("'");
            }
            if (StringUtils.isBlank(field.getDefaultValue())) {
                sb.append(GuessUtil.guessDefaultValueByFieldType(field.getFieldType()));
            } else {
                sb.append(field.getDefaultValue());
            }
            if (needWrap) {
                sb.append("'");
            }
            return sb.toString();
        }
    }

    /**
     * 判断默认值是否需要引号包裹
     *
     * @param field
     * @return
     */
    public static boolean ifDefaultValueNeedWrap(MetaFieldPO field) {
        String defaultValue = field.getDefaultValue();
        // 字符型或数字需要引号包裹
        if (MySqlType.isStringType(field.getFieldType())
            || MySqlType.isNumberType(field.getFieldType())) {
            if (defaultValue != null
                && defaultValue.startsWith(DefaultValue.SINGLE_QUOTE)
                && defaultValue.endsWith(DefaultValue.SINGLE_QUOTE)) {
                return false;
            }
            return true;
        }
        // 日期字段特殊默认值不需要引号包裹
        if (MySqlType.isDateType(field.getFieldType()) || MySqlType.isDateTimeType(field.getFieldType())) {
            if (StringUtils.isNotBlank(defaultValue)) {
                for (String specialValue : SPECIAL_DEFAULT_VALUE) {
                    if (defaultValue.toUpperCase().indexOf(specialValue) > -1) {
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
     * 获取【备注】展示
     *
     * @param comment
     * @param forField 字段或表
     * @return
     */
    public static String getCommentDisplay(String comment, boolean forField) {
        if (StringUtils.isBlank(comment)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(" COMMENT");
        if (forField) {
            sb.append(" ");
        } else {
            sb.append("=");
        }
        sb.append("'")
            .append(CommonTemplateFunction.convertCommentDisplay(comment))
            .append("'");
        return sb.toString();
    }




    /**
     * 获取【字段长度】展示
     *
     * @return
     */
    public static String getLengthDisplay(MetaFieldPO field) {
        if (!showFieldLength(field.getFieldType())) {
            return "";
        }
        // 如果字段长度为0,则直接返回空串
        if (field.getFieldLength() <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder("(");
        sb.append(field.getFieldLength());
        if (showFieldScale(field.getFieldType())) {
            sb.append(",").append(field.getFieldScale());
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 是否需要展示字段长度
     *
     * @param fieldType
     * @return
     */
    public static boolean showFieldLength(String fieldType) {
        return !MySqlType.DATE.equals(fieldType)
            && !MySqlType.DATETIME.equals(fieldType)
            && !MySqlType.TEXT.equals(fieldType);
    }

    /**
     * 是否需要展示字段精度
     *
     * @param fieldType
     * @return
     */
    public static boolean showFieldScale(String fieldType) {
        return MySqlType.DECIMAL.equals(fieldType)
            || MySqlType.DOUBLE.equals(fieldType)
            || MySqlType.FLOAT.equals(fieldType);
    }


    /**
     * 获取【字段自增】展示
     *
     * @return
     */
    public static String getAutoIncrementDisplay(MetaFieldPO field) {
        if (field.getPrimaryKey()
            && field.getAutoIncrement()) {
            return " AUTO_INCREMENT";
        }
        return "";
    }

    /**
     * 获取【字段非空】展示
     *
     * @return
     */
    public static String getNotNullDisplay(MetaFieldPO field) {
        if (!field.getPrimaryKey()
            && field.getNotNull()) {
            return " NOT NULL";
        }
        return "";

    }


}
