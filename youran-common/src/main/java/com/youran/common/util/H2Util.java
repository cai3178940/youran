package com.youran.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * H2数据库工具类
 * @author cbb
 * @date 2017/3/27 14:37
 */
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
        return script;
    }


}
