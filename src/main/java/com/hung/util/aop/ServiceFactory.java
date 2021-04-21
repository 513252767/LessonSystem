package com.hung.util.aop;

import com.hung.util.LogWriter;
import com.hung.util.jdbc.ConnectionUtils;
import com.hung.util.jdbc.TransactionManager;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDate;

/**
 * @author Hung
 */
public class ServiceFactory<T> {
    private T service;
    ConnectionUtils connectionUtils = new ConnectionUtils();
    TransactionManager transactionManager = new TransactionManager(connectionUtils);

    public ServiceFactory(T service) {
        this.service = service;
    }

    /**
     * 获取Service代理对象
     *
     * @return
     */
    public T getService() {
        return (T) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) {
                Object value;
                try {
                    transactionManager.beginTransaction();
                    value = method.invoke(service, objects);
                    transactionManager.commit();
                    return value;
                } catch (Exception e) {
                    transactionManager.rollback();
                    String info= LocalDate.now()+" "+Thread.currentThread().toString()+e.getMessage()+"  "+e.getCause();
                    try {
                        LogWriter.log(info);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    throw new RuntimeException(e);
                } finally {
                    transactionManager.release();
                }
            }
        });
    }

}
