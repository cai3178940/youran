package com.youran.common.util;

import com.youran.common.exception.BusinessException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * spring bean 工具类
 *
 * @author: cbb
 * @date: 2018/6/15
 */
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 注入context
     *
     * @param context
     */
    @Override
    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    /**
     * 得到上下文
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据名称获取
     *
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(beanName);
    }

    /**
     * 根据名称和类型
     *
     * @param beanName
     * @param clz
     * @return
     */
    public static <T> T getBean(String beanName, Class<T> clz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(beanName, clz);
    }

    /**
     * 根据类型获取bean
     *
     * @param clz
     * @return
     */
    public static <T> T getBean(Class<T> clz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clz);
    }


    /**
     * 根据类型获取beanName
     *
     * @param clz
     * @return
     */
    public static String[] getBeanNamesForType(Class<?> clz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBeanNamesForType(clz);
    }

    /**
     * 根据类型获取beanMap
     *
     * @param clz
     * @return
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBeansOfType(clz);
    }

    /**
     * 根据bean上的注解获取bean
     *
     * @param clz
     * @return
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBeansWithAnnotation(clz);
    }

    /**
     * 获取beanFactory
     *
     * @return
     */
    public static DefaultListableBeanFactory getBeanFactory() {
        if (applicationContext == null) {
            return null;
        }
        return (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
    }

    /**
     * 销毁bean
     *
     * @param beanName
     * @return
     */
    public static boolean destroy(String beanName) {
        DefaultListableBeanFactory beanFactory = getBeanFactory();
        if (beanFactory == null) {
            throw new BusinessException("spring容器未启动");
        }
        if (!beanFactory.containsBean(beanName)) {
            return false;
        }
        beanFactory.destroySingleton(beanName);
        beanFactory.destroyBean(beanName);
        beanFactory.removeBeanDefinition(beanName);
        return true;
    }

    /**
     * 动态注册bean
     *
     * @param beanName
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T regist(String beanName, Class<T> clz) {
        DefaultListableBeanFactory beanFactory = getBeanFactory();
        if (beanFactory == null) {
            throw new BusinessException("spring容器未启动");
        }
        if (!beanFactory.containsBean(beanName)) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(clz);
            beanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        }
        return getBean(beanName, clz);
    }

    /**
     * 动态注册bean
     *
     * @param beanName
     * @param beanDefinitionBuilder
     * @return
     */
    public static Object regist(String beanName, BeanDefinitionBuilder beanDefinitionBuilder) {
        DefaultListableBeanFactory beanFactory = getBeanFactory();
        if (beanFactory == null) {
            throw new BusinessException("spring容器未启动");
        }
        if (!beanFactory.containsBean(beanName)) {
            beanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        }
        return getBean(beanName);
    }

}
