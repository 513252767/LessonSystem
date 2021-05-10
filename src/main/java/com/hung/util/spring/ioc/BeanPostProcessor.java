package com.hung.util.spring.ioc;

/**
 * @author Hung
 */
public interface BeanPostProcessor {

    /**
     * 初始化前操作
     *
     * @param bean
     * @param beanName
     * @param beanClazz
     * @return
     */
    Object postProcessBeforeInitialization(Object bean, String beanName, Class<?> beanClazz);

    /**
     * 初始化后操作
     *
     * @param bean
     * @param beanName
     * @param beanClazz
     * @return
     */
    Object postProcessAfterInitialization(Object bean, String beanName, Class<?> beanClazz);
}
