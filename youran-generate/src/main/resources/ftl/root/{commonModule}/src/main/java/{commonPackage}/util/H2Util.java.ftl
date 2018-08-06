<#include "/common.ftl">

<#--定义主体代码-->
<#assign code>
<@call this.addImport("org.apache.commons.io.FileUtils")/>
<@call this.addImport("org.apache.commons.io.IOUtils")/>
<@call this.addImport("org.apache.commons.lang3.StringUtils")/>
<@call this.addImport("org.apache.commons.lang3.time.DateFormatUtils")/>
<@call this.addImport("java.io.File")/>
<@call this.addImport("java.io.IOException")/>
<@call this.addImport("java.io.InputStream")/>
<@call this.addImport("java.net.URL")/>
<@call this.addImport("java.util.Date")/>
<@call this.printClassCom("H2数据库工具类")/>
public class H2Util {


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


    /**
     * 修改数据库脚本并保存到临时目录
     * @param pathInclass 文件在classpath下路径
     * @return 临时文件路径
     */
    public static String getH2Script(String pathInclass) {
        String tempFolder = getTmpDir(null,true,false);
        String path = tempFolder+File.separator+pathInclass;
        File file = new File(path);
        File parentFile = file.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        try {
            ClassLoader classLoader = H2Util.class.getClassLoader();
            URL resource = classLoader.getResource(pathInclass);
            InputStream inputStream = resource.openStream();
            String script = IOUtils.toString(inputStream,"UTF-8");
            script = filterScript(script);
            FileUtils.write(file,script,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 过滤建表语句
     * @param script
     * @return
     */
    public static String filterScript(String script) {
        script = script.replaceAll("(CHARSET=[a-zA-Z0-9]+)\\s(ROW_FORMAT=[a-zA-Z]+\\s)?(COMMENT=.*)?;","$1;");
        script = script.replaceAll("(double|DOUBLE)\\([0-9,]+\\)","$1");
        script = script.replaceAll("(float|FLOAT)\\([0-9,]+\\)","$1");
        return script;
    }


}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.util;

<@call this.printImport()/>

${code}
