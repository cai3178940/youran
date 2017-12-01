<#include "/common.ftl">
package ${commonPackage}.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

<@classCom "通用自动配置"></@classCom>
public class CommonAutoConfiguration {

    @Bean
    public StartLogCommandLineRunner startLogCommandLineRunner(Environment env){
        return new StartLogCommandLineRunner(env);
    }

    /**
     * 解决直接返回字符串带引号的bug
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(FastJsonHttpMessageConverter4.class)
    public FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter4 fastConverter = new FastJsonHttpMessageConverter4(){
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
        };
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
            SerializerFeature.PrettyFormat,
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.DisableCircularReferenceDetect);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }

}
