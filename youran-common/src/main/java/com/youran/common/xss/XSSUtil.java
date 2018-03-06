package com.youran.common.xss;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time: 2018/3/6 11:28
 */
public class XSSUtil {

    public static String clean(String value){
        //允许base64格式的图片,字符串不进行美化
        return Jsoup.clean(value,"",
            Whitelist.basicWithImages().addProtocols("img","src","data"),
            new Document.OutputSettings().prettyPrint(false));
    }

}
