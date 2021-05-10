package com.hung.util.spring.ioc;

/**
 * @author Hung
 */
public interface InitializingBean {
    /**
     *初始化方法
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;
}
