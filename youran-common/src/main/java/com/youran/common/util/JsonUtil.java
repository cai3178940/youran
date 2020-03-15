package com.youran.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youran.common.constant.JsonFieldConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 封装json操作
 *
 * @author: cbb
 * @date: 2017/5/20
 */
public class JsonUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setDateFormat(new SimpleDateFormat(JsonFieldConst.DEFAULT_DATETIME_FORMAT));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static ObjectMapper mapperExcludeNull = new ObjectMapper();

    static {
        mapperExcludeNull.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapperExcludeNull.setDateFormat(new SimpleDateFormat(JsonFieldConst.DEFAULT_DATETIME_FORMAT));
        mapperExcludeNull.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public static <T> T parseObject(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return doConvertRuntimeException(() -> mapper.readValue(json, clazz));
    }


    public static String toJSONString(Object obj) {
        return doConvertRuntimeException(() -> mapper.writeValueAsString(obj));
    }

    public static String toJSONString(Object object, boolean prettyFormat) {
        if (prettyFormat) {
            return doConvertRuntimeException(() ->
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
        }
        return toJSONString(object);
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        return doConvertRuntimeException(() -> mapper.readValue(json, javaType));
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static void writeJsonToFile(Object object, boolean prettyFormat, File file) {
        doConvertRuntimeException(() -> {
            if (prettyFormat) {
                mapperExcludeNull.writerWithDefaultPrettyPrinter().writeValue(file, object);
            } else {
                mapperExcludeNull.writeValue(file, object);
            }
            return null;
        });
    }

    public static <T> T parseObjectFromFile(File file, Class<T> clazz) {
        if (!file.exists()) {
            LOGGER.warn("json文件不存在：{}", file.getPath());
            return null;
        }
        return doConvertRuntimeException(() -> mapper.readValue(file, clazz));
    }


    public static <T> List<T> parseArrayFromFile(File file, Class<T> clazz) {
        if (!file.exists()) {
            LOGGER.warn("json文件不存在：{}", file.getPath());
            return Collections.emptyList();
        }
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        List<T> ts = doConvertRuntimeException(() -> mapper.readValue(file, javaType));
        if(ts == null){
            return Collections.emptyList();
        }
        return ts;
    }

    /**
     * 将声明式异常包装成运行时异常抛出
     * @param callable
     * @param <T>
     * @return
     */
    private static <T> T doConvertRuntimeException(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            LOGGER.error("json序列化异常", e);
            if(e instanceof RuntimeException){
                throw (RuntimeException)e;
            }else {
                throw new RuntimeException("json序列化异常", e);
            }
        }
    }

}
