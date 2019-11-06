<#include "/abstracted/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("org.apache.commons.lang3.StringUtils")/>
<@call this.addImport("org.apache.commons.lang3.time.DateFormatUtils")/>
<@call this.addImport("java.io.File")/>
<@call this.addImport("java.util.Date")/>
<@call this.printClassCom("临时目录工具类")/>
public class TempDirUtil {


    /**
     * 获取临时目录
     * @param appName
     * @param withDate
     * @return
     */
    public static String getTmpDir(String appName,boolean withDate,boolean withUUID){
        String tempFolder = System.getProperty("java.io.tmpdir");
        if(tempFolder.endsWith(File.separator)){
            tempFolder = tempFolder.substring(0,tempFolder.length()-1);
        }
        if(StringUtils.isNotBlank(appName)) {
            tempFolder += File.separator+appName;
        }
        if(withDate) {
            String todayStr = DateFormatUtils.format(new Date(),"yyyyMMdd");
            tempFolder += File.separator + todayStr;
        }
        if(withUUID){
            tempFolder += File.separator + UUIDUtil.getUUID16();
        }
        return tempFolder;
    }

}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.util;

<@call this.printImport()/>

${code}
