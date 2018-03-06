package com.youran.common.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.youran.common.xss.XSSUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

/**
 * Title:自定义fastjson转换器
 * Description:
 * Author: cbb
 * Create Time: 2018/3/6 12:50
 */
public class MyFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter4 {

    @Override
    protected void writeInternal(Object obj, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        HttpHeaders headers = outputMessage.getHeaders();
        OutputStream out = outputMessage.getBody();
        if(type!=null && type.equals(String.class)){
            out.write(obj.toString().getBytes("UTF-8"));
            return;
        }
        ByteArrayOutputStream outnew = new ByteArrayOutputStream();
        FastJsonConfig fastJsonConfig = this.getFastJsonConfig();
        int len = JSON.writeJSONString(outnew, fastJsonConfig.getCharset(), obj, fastJsonConfig.getSerializeConfig(), fastJsonConfig.getSerializeFilters(), fastJsonConfig.getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, fastJsonConfig.getSerializerFeatures());
        if (fastJsonConfig.isWriteContentLength()) {
            headers.setContentLength((long) len);
        }

        outnew.writeTo(out);
        outnew.close();
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream is = inputMessage.getBody();
        String input = IOUtils.toString(is, "UTF-8");
        if(type.equals(String.class)){
            return XSSUtil.clean(input);
        }
        FastJsonConfig fastJsonConfig = this.getFastJsonConfig();
        Object obj = JSON.parseObject(input, type, fastJsonConfig.getFeatures());
        String value = JSON.toJSONString(obj, fastJsonConfig.getSerializeConfig(), fastJsonConfig.getSerializeFilters(), fastJsonConfig.getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, fastJsonConfig.getSerializerFeatures());
        return JSON.parseObject(value, type, fastJsonConfig.getFeatures());
    }


}
