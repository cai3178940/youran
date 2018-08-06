<#include "/common.ftl">

<#--定义主体代码-->
<#assign code>
<@call this.addImport("org.apache.commons.lang3.StringUtils")/>
<@call this.addImport("org.apache.commons.lang3.time.DateFormatUtils")/>
<@call this.addImport("org.apache.commons.lang3.time.DateUtils")/>
<@call this.addImport("java.text.ParseException")/>
<@call this.addImport("java.util.Date")/>
<@call this.printClassCom("日期工具")/>
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
            throw new IllegalArgumentException(MessageSourceUtil.getMessage("error.data_format_error")+",datatime="+datatime);
        }
        return parseDate(datatime,dateFormat);
    }

}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.util;

<@call this.printImport()/>

${code}
