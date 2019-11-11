package com.youran.generate.template.function;

import com.youran.generate.constant.JFieldType;
import com.youran.generate.constant.MetaSpecialField;
import com.youran.generate.pojo.po.MetaFieldPO;
import org.apache.commons.lang3.StringUtils;

/**
 * java模板函数
 * <p>作为内置函数用来辅助freemarker模板
 *
 * @author cbb
 * @date 2019/11/11
 */
public class JavaTemplateFunction {


    /**
     * 类名截取函数
     *
     * @param classPath 类全路径
     * @return 类名
     */
    public static String fetchClassName(String classPath) {
        int i = classPath.lastIndexOf(".");
        if (i > 0) {
            return classPath.substring(i + 1);
        }
        return classPath;
    }


    /**
     * 打印gettersetter方法
     *
     * @param metaFieldPO 字段元数据对象
     * @return
     */
    public static String printGetterSetterForPO(MetaFieldPO metaFieldPO) {
        boolean override = false;
        // 特殊字段标记+字段名都符合，才加override注解
        if (MetaSpecialField.check(metaFieldPO.getSpecialField())
            && MetaSpecialField.check(metaFieldPO.getJfieldName())) {
            override = true;
        }
        return printGetterSetter(metaFieldPO.getJfieldName(), metaFieldPO.getJfieldType(), override, 1);
    }



    /**
     * 打印gettersetter方法
     *
     * @param metaFieldPO 字段元数据对象
     * @return
     */
    public static String printGetterSetter(MetaFieldPO metaFieldPO) {
        return printGetterSetter(metaFieldPO.getJfieldName(), metaFieldPO.getJfieldType());
    }

    /**
     * 打印gettersetter方法
     *
     * @param metaFieldPO 字段元数据对象
     * @param indent      缩进层次
     * @return
     */
    public static String printGetterSetter(MetaFieldPO metaFieldPO, int indent) {
        return printGetterSetter(metaFieldPO.getJfieldName(), metaFieldPO.getJfieldType(), false, indent);
    }

    /**
     * 打印gettersetter方法
     *
     * @param jfieldName java字段名
     * @param jfieldType java字段类型
     * @return
     */
    public static String printGetterSetter(String jfieldName, String jfieldType) {
        return printGetterSetter(jfieldName, jfieldType, false, 1);
    }

    /**
     * 打印gettersetter方法
     *
     * @param jfieldName java字段名
     * @param jfieldType java字段类型
     * @param override   是否加上@Override注解
     * @param indent     缩进层次
     * @return
     */
    public static String printGetterSetter(String jfieldName, String jfieldType, boolean override, int indent) {
        StringBuilder indentPrefix = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            indentPrefix.append("    ");
        }
        String cap = StringUtils.capitalize(jfieldName);
        String uncap = StringUtils.uncapitalize(jfieldName);
        StringBuilder sb = new StringBuilder();
        if (override) {
            sb.append(indentPrefix).append("@Override\n");
        }
        sb.append(indentPrefix).append("public ").append(jfieldType).append(" get").append(cap).append("() {\n")
            .append(indentPrefix).append("    return this.").append(uncap).append(";\n")
            .append(indentPrefix).append("}\n")
            .append("\n");
        if (override) {
            sb.append(indentPrefix).append("@Override\n");
        }
        sb.append(indentPrefix).append("public void set").append(cap).append("(").append(jfieldType).append(" ").append(uncap).append(") {\n")
            .append(indentPrefix).append("    this.").append(uncap).append(" = ").append(uncap).append(";\n")
            .append(indentPrefix).append("}\n\n");
        return sb.toString();
    }

    /**
     * 打印gettersetter list方法
     *
     * @param jfieldName java字段名
     * @param jfieldType java字段类型
     * @return
     */
    public static String printGetterSetterList(String jfieldName, String jfieldType) {
        return printGetterSetterList(jfieldName, jfieldType, true);
    }

    /**
     * 打印gettersetter list方法
     *
     * @param jfieldName java字段名
     * @param jfieldType java字段类型
     * @param listSuffix 字段名加List后置
     * @return
     */
    public static String printGetterSetterList(String jfieldName, String jfieldType, boolean listSuffix) {
        String cap = StringUtils.capitalize(jfieldName);
        String uncap = StringUtils.uncapitalize(jfieldName);
        StringBuilder sb = new StringBuilder();
        String suffix = listSuffix ? "List" : "";
        sb.append("    public List<").append(jfieldType).append("> get").append(cap).append(suffix).append("() {\n")
            .append("        return this.").append(uncap).append(suffix).append(";\n")
            .append("    }\n")
            .append("\n")
            .append("    public void set").append(cap).append(suffix).append("(List<").append(jfieldType).append("> ").append(uncap).append(suffix).append(") {\n")
            .append("        this.").append(uncap).append(suffix).append(" = ").append(uncap).append(suffix).append(";\n")
            .append("    }\n\n");
        return sb.toString();
    }



    /**
     * 映射swagger字段类型常量
     *
     * @param jfieldType
     * @return
     */
    public static String getSwaggerType(String jfieldType) {
        JFieldType jFieldType = JFieldType.find(jfieldType);
        if (jFieldType == JFieldType.INTEGER) {
            return "int";
        }
        return jfieldType;
    }


    /**
     * 转换【备注】展示，增加缩进星号
     *
     * @param comment
     * @return
     */
    public static String convertCommentDisplayWithIndentStar(String comment) {
        if (StringUtils.isBlank(comment)) {
            return "     *";
        }
        String[] lines = comment.split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            sb.append("     * ").append(line);
            if (i < lines.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }


}
