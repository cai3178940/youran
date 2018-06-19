<#include "/common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "org.slf4j.Logger"/>
<@import "org.slf4j.LoggerFactory"/>
<@import "org.springframework.context.MessageSource"/>
<@import "org.springframework.context.i18n.LocaleContextHolder"/>
<@import "java.util.Locale"/>
<@classCom "静态国际化MessageSource工具类"/>
public class MessageSourceUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageSourceUtil.class);

    private static MessageSource messageSource;

    public static MessageSource getMessageSource(){
        if(messageSource!=null){
            return messageSource;
        }
        MessageSource ms = SpringUtil.getBean(MessageSource.class);
        if(ms != null){
            messageSource = ms;
            return ms;
        }
        return null;
    }

    /**
     * @param code 对应messages配置的key.
     * @return
     */
    public static String getMessage(String code){
        return getMessage(code,null,null);
    }

    /**
     *
     * @param code 对应messages配置的key.
     * @param args 数组参数.
     * @return
     */
    public static String getMessage(String code,Object[] args){
        return getMessage(code, args,"");
    }

    /**
     *
     * @param code 对应messages配置的key.
     * @param defaultMessage 没有设置key的时候的默认值.
     * @return
     */
    public static String getMessage(String code,String defaultMessage){
        return getMessage(code, null,defaultMessage);
    }


    /**
     *
     * @param code 对应messages配置的key.
     * @param args 数组参数.
     * @param defaultMessage 没有设置key的时候的默认值.
     * @return
     */
    public static String getMessage(String code,Object[] args,String defaultMessage){
        Locale locale = LocaleContextHolder.getLocale();
        MessageSource messageSource = getMessageSource();
        if(messageSource!=null){
            return messageSource.getMessage(code, args, defaultMessage, locale);
        }
        if(defaultMessage!=null) {
            return defaultMessage;
        }
        LOGGER.warn("未找到{}对应的消息体",code);
        return code;
    }

}
</#assign>
<#--开始渲染代码-->
package ${commonPackage}.util;

<@printImport/>

${code}
