package com.youran.generate.util;

import com.youran.generate.constant.MetaSpecialField;
import com.youran.generate.pojo.po.MetaFieldPO;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Title: 模板打印辅助类</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2018/8/3
 */
public class TemplateUtil {

    /**
     * 类名截取函数
     * @param classPath 类全路径
     * @return 类名
     */
    public static String fetchClassName(String classPath){
        int i = classPath.lastIndexOf(".");
        if(i >0){
            return classPath.substring(i+1);
        }
        return classPath;
    }


    /**
     * 判断是否通用的常量
     * @param constName 常量名称
     * @return
     */
    public static boolean isCommonConst(String constName){
        return "BoolConst".equals(constName);
    }

    /**
     * 打印gettersetter方法
     * @param metaFieldPO 字段元数据对象
     * @return
     */
    public static String printGetterSetterForPO(MetaFieldPO metaFieldPO){
        boolean override = false;
        // 特殊字段标记+字段名都符合，才加override注解
        if(MetaSpecialField.check(metaFieldPO.getSpecialField())
            && MetaSpecialField.check(metaFieldPO.getJfieldName())){
            override = true;
        }
        return printGetterSetter(metaFieldPO.getJfieldName(),metaFieldPO.getJfieldType(),override,1);
    }

    /**
     * 打印gettersetter方法
     * @param metaFieldPO 字段元数据对象
     * @return
     */
    public static String printGetterSetter(MetaFieldPO metaFieldPO){
        return printGetterSetter(metaFieldPO.getJfieldName(),metaFieldPO.getJfieldType());
    }

    /**
     * 打印gettersetter方法
     * @param metaFieldPO 字段元数据对象
     * @param indent 缩进层次
     * @return
     */
    public static String printGetterSetter(MetaFieldPO metaFieldPO,int indent){
        return printGetterSetter(metaFieldPO.getJfieldName(),metaFieldPO.getJfieldType(),false, indent);
    }

    /**
     * 打印gettersetter方法
     * @param jfieldName java字段名
     * @param jfieldType java字段类型
     * @return
     */
    public static String printGetterSetter(String jfieldName,String jfieldType){
        return printGetterSetter(jfieldName,jfieldType,false,1);
    }

    /**
     * 打印gettersetter方法
     * @param jfieldName java字段名
     * @param jfieldType java字段类型
     * @param override 是否加上@Override注解
     * @param indent 缩进层次
     * @return
     */
    public static String printGetterSetter(String jfieldName,String jfieldType, boolean override,int indent){
        StringBuilder indentPrefix = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            indentPrefix.append("    ");
        }
        String cap = StringUtils.capitalize(jfieldName);
        String uncap = StringUtils.uncapitalize(jfieldName);
        StringBuilder sb = new StringBuilder();
        if(override){
            sb.append(indentPrefix).append("@Override\n");
        }
        sb.append(indentPrefix).append("public ").append(jfieldType).append(" get").append(cap).append("() {\n")
            .append(indentPrefix).append("    return this.").append(uncap).append(";\n")
            .append(indentPrefix).append("}\n")
            .append("\n");
        if(override){
            sb.append(indentPrefix).append("@Override\n");
        }
        sb.append(indentPrefix).append("public void set").append(cap).append("(").append(jfieldType).append(" ").append(uncap).append(") {\n")
            .append(indentPrefix).append("    this.").append(uncap).append(" = ").append(uncap).append(";\n")
            .append(indentPrefix).append("}\n\n");
        return sb.toString();
    }

    /**
     * 打印gettersetter list方法
     * @param jfieldName java字段名
     * @param jfieldType java字段类型
     * @return
     */
    public static String printGetterSetterList(String jfieldName,String jfieldType){
        return printGetterSetterList(jfieldName, jfieldType, true);
    }

    /**
     * 打印gettersetter list方法
     * @param jfieldName java字段名
     * @param jfieldType java字段类型
     * @param listSuffix 字段名加List后置
     * @return
     */
    public static String printGetterSetterList(String jfieldName,String jfieldType, boolean listSuffix){
        String cap = StringUtils.capitalize(jfieldName);
        String uncap = StringUtils.uncapitalize(jfieldName);
        StringBuilder sb = new StringBuilder();
        String suffix = listSuffix?"List":"";
        sb.append("    public List<").append(jfieldType).append("> get").append(cap).append(suffix).append("() {\n")
            .append("        return this.").append(uncap).append(suffix).append(";\n")
            .append("    }\n")
            .append("\n")
            .append("    public void set").append(cap).append(suffix).append("(List<").append(jfieldType).append("> ").append(uncap).append(suffix).append(") {\n")
            .append("        this.").append(uncap).append(suffix).append(" = ").append(uncap).append(suffix).append(";\n")
            .append("    }\n\n");
        return sb.toString();
    }



}
