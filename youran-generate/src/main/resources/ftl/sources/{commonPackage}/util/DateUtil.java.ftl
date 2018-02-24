<#include "/common.ftl">
package ${commonPackage}.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

<@classCom "日期工具"/>
public class DateUtil {

    public static final String DATE_FORMAT_1 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取日期字符串yyyy-MM-dd
     * @param date
     * @return
     */
    public static String getDateStr(Date date) {
        return getDateStr(date,DATE_FORMAT_1);
    }

    /**
     * 获取指定格式的日期字符串
     * @param date
     * @param dateFormat
     * @return
     */
    public static String getDateStr(Date date, String dateFormat) {
        if(date==null || StringUtils.isBlank(dateFormat)){
            return "";
        }
        return DateFormatUtils.format(date,dateFormat);
    }

    /**
     * 解析日期字符串
     * @param datatime
     * @param dateFormat
     * @return
     */
    public static Date parseDate(String datatime,String dateFormat){
        if(StringUtils.isBlank(datatime)){
            return null;
        }
        try {
            return DateUtils.parseDate(datatime,dateFormat);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
    /**
     * 解析日期字符串yyyy-MM-dd
     * @param datatime
     * @return
     */
    public static Date parseDate(String datatime){
        if(StringUtils.isBlank(datatime)){
            return null;
        }
        String dateFormat;
        if(datatime.length()==DATE_FORMAT_1.length()){
            dateFormat = DATE_FORMAT_1;
        }else if(datatime.length()==DATE_FORMAT_2.length()){
            dateFormat = DATE_FORMAT_2;
        }else{
            throw new IllegalArgumentException("日期格式有误，datatime="+datatime);
        }
        return parseDate(datatime,dateFormat);
    }

}
