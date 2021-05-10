package com.hung.util.spring.ioc;


import com.hung.util.LogWriter;
import com.hung.util.jdbc.ConnectionUtils;
import com.hung.util.jdbc.TransactionManager;
import com.hung.util.orm.sqlsession.SqlSession;
import com.hung.util.orm.sqlsession.SqlSessionFactory;
import com.hung.util.spring.annotation.Component;
import com.hung.util.spring.annotation.Repository;
import com.hung.util.spring.annotation.Service;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;

/**
 * @author Hung
 */
@Component("beanPostProcessor")
public class BeanPostProcessorImpl implements BeanPostProcessor {

    ConnectionUtils connectionUtils = new ConnectionUtils();
    TransactionManager transactionManager = new TransactionManager(connectionUtils);

    /**
     * 初始化前操作
     *
     * @param bean
     * @param beanName
     * @return
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName, Class<?> beanClazz) {
        return bean;
    }

    /**
     * 初始化后操作
     *
     * @param bean
     * @param beanName
     * @param beanClazz
     * @return
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName, Class<?> beanClazz) {
        //根据dao和service实现不同代理方法
        Class<?> beanClass = null;

        if (bean != null) {
            beanClass = bean.getClass();
        } else {
            beanClass = beanClazz;
        }

        if (beanClass.isAnnotationPresent(Service.class) || beanClazz.isAnnotationPresent(Service.class)) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (o, method, objects) -> {
                Object value;
                try {
                    transactionManager.beginTransaction();
                    value = method.invoke(bean, objects);
                    transactionManager.commit();
                    return value;
                } catch (Exception e) {
                    transactionManager.rollback();
                    String info = LocalDateTime.now() + " " + Thread.currentThread().getName() + " " + e.getMessage() + "  " + e.getCause();
                    try {
                        LogWriter.log(info + "\n");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    throw new RuntimeException(e);
                } finally {
                    transactionManager.release();
                }
            });
        } else if (beanClass.isAnnotationPresent(Repository.class) || beanClazz.isAnnotationPresent(Repository.class)) {
            SqlSession<?> sqlSession = SqlSessionFactory.getSqlSession();
            return sqlSession.getMapper(beanClass);
        }
        return bean;
    }
}
