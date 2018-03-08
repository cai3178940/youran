<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@classCom "空指针安全的类型转换工具"/>
public class SafeUtil {

    /**
     * 对象转Integer
     * @param obj
     * @return
     */
    public static Integer getInteger(Object obj){
        if(obj==null){
            return null;
        }
        try {
            return Integer.valueOf(obj.toString());
        }catch (Exception e){}
        return null;
    }
    /**
     * 对象转Long
     * @param obj
     * @return
     */
    public static Long getLong(Object obj){
        if(obj==null){
            return null;
        }
        try {
            return Long.valueOf(obj.toString());
        }catch (Exception e){}
        return null;
    }
    /**
     * 对象转Short
     * @param obj
     * @return
     */
    public static Short getShort(Object obj){
        if(obj==null){
            return null;
        }
        try {
            return Short.valueOf(obj.toString());
        }catch (Exception e){}
        return null;
    }
    /**
     * 对象转Double
     * @param obj
     * @return
     */
    public static Double getDouble(Object obj){
        if(obj==null){
            return null;
        }
        try {
            return Double.valueOf(obj.toString());
        }catch (Exception e){}
        return null;
    }
    /**
     * 对象转Float
     * @param obj
     * @return
     */
    public static Float getFloat(Object obj){
        if(obj==null){
            return null;
        }
        try {
            return Float.valueOf(obj.toString());
        }catch (Exception e){}
        return null;
    }
    /**
     * 对象转Boolean
     * @param obj
     * @return
     */
    public static Boolean getBoolean(Object obj){
        if(obj==null){
            return null;
        }
        try {
            return Boolean.valueOf(obj.toString());
        }catch (Exception e){}
        return null;
    }
    /**
     * 转字符串
     * @param obj
     * @return
     */
    public static String getString(Object obj){
        return obj==null?null:obj.toString();
    }


}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.util;

<@printImport/>

${code}
