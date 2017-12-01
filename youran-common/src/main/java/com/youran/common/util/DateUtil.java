package com.youran.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Title:日期工具
 * Description:
 * Author: cbb
 * Create Time:2017/9/19 14:43
 */
public class DateUtil {

    /**
     * 获取日期字符串yyyy-MM-dd
     * @param date
     * @return
     */
    public static String getDateStr(Date date) {
        return getDateStr(date,"yyyy-MM-dd");
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
        return parseDate(datatime,"yyyy-MM-dd");
    }

}
