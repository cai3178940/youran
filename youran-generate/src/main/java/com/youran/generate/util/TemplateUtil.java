package com.youran.generate.util;

import com.youran.generate.pojo.po.MetaFieldPO;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Title: 模板打印辅助类</p>
 * <p>Description: </p>
 *
 * @author cbb
 * @date 2018/8/3
 */
public class TemplateUtil {


    /**
     * 类名截取函数
     * @param classPath
     * @return
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
     * @param dicType
     * @return
     */
    public static boolean isCommonPackage(String dicType){
        return "BoolConst".equals(dicType);
    }

    /**
     * 打印gettersetter方法
     * @param metaFieldPO
     * @return
     */
    public static String printGetterSetter(MetaFieldPO metaFieldPO){
        return printGetterSetter2(metaFieldPO.getJfieldName(),metaFieldPO.getJfieldType());
    }

    /**
     * 打印gettersetter方法
     * @param jfieldName
     * @param jfieldType
     * @return
     */
    public static String printGetterSetter2(String jfieldName,String jfieldType){
        String cap = StringUtils.capitalize(jfieldName);
        String uncap = StringUtils.uncapitalize(jfieldName);
        StringBuilder sb = new StringBuilder();
        sb.append("    public ").append(jfieldType).append(" get").append(cap).append("() {\n")
            .append("        return this.").append(uncap).append(";\n")
            .append("    }\n")
            .append("\n")
            .append("    public void set").append(cap).append("(").append(jfieldType).append(" ").append(uncap).append(") {\n")
            .append("        this.").append(uncap).append(" = ").append(uncap).append(";\n")
            .append("    }\n\n");
        return sb.toString();
    }
    /**
     * 打印gettersetter list方法
     * @param jfieldName
     * @param jfieldType
     * @return
     */
    public static String printGetterSetterList(String jfieldName,String jfieldType){
        String cap = StringUtils.capitalize(jfieldName);
        String uncap = StringUtils.uncapitalize(jfieldName);
        StringBuilder sb = new StringBuilder();
        sb.append("    public List<").append(jfieldType).append("> get").append(cap).append("List() {\n")
            .append("        return this.").append(uncap).append("List;\n")
            .append("    }\n")
            .append("\n")
            .append("    public void set").append(cap).append("List(List<").append(jfieldType).append("> ").append(uncap).append("List) {\n")
            .append("        this.").append(uncap).append("List = ").append(uncap).append("List;\n")
            .append("    }\n\n");
        return sb.toString();
    }



}
