package com.youran.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.util.Date;

/**
 * 临时目录工具类
 * @author cbb
 * @date 2017/3/27
 */
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
