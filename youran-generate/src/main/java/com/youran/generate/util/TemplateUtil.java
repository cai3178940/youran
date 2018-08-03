package com.youran.generate.util;

import com.youran.common.util.DateUtil;

import java.util.Date;

/**
 * <p>Title: 模板打印辅助类</p>
 * <p>Description: </p>
 *
 * @author cbb
 * @date 2018/8/3
 */
public class TemplateUtil {

    /**
     * 打印类注释
     * @return
     */
    public String printClassCom(String title, String desc, String author, Date createdTime){
        StringBuilder sb = new StringBuilder();
        sb.append("/**")
            .append(" * <p>Title: ").append(title).append("</p>")
            .append(" * <p>Description: ").append(desc).append("</p>")
            .append(" * @author ").append(author)
            .append(" * @date ").append(DateUtil.getDateStr(createdTime,"yyyy/MM/dd"))
            .append(" */");
        return sb.toString();
    }


    /**
     * 类名截取函数
     * @param classPath
     * @return
     */
    public String fetchClassName(String classPath){
        int i = classPath.lastIndexOf(".");
        if(i >0){
            return classPath.substring(i+1);
        }
        return classPath;
    }


}
