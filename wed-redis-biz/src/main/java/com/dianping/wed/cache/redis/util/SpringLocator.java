package com.dianping.wed.cache.redis.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringLocator implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringLocator.applicationContext = applicationContext;
    }

    public static <T> T getBean(String name) {
        if (null == applicationContext) {
            throw new IllegalArgumentException("can not get spring applicationContext!");
        }
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {

        if (null == applicationContext) {
            throw new IllegalArgumentException("can not get spring applicationContext!");
        }

        return (T) BeanFactoryUtils.beanOfType(applicationContext, clazz);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
