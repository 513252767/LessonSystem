package com.hung.util.orm.sqlsession.defaults;


import com.hung.util.jdbc.ConnectionUtils;
import com.hung.util.orm.config.Mapper;
import com.hung.util.orm.proxy.MapperProxy;
import com.hung.util.orm.sqlsession.SqlSession;
import com.hung.util.orm.utils.ConfigBuilder;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * 获取SqlSession类
 *
 * @author Hung
 */
public class DefaultSqlSession<T> implements SqlSession {

    private Map<String, Mapper> mappers;
    private Connection connection;

    public DefaultSqlSession() {
    }

    /**
     * 根据参数创建一个代理对象
     *
     * @param daoInterfaceClass dao的接口字节码
     * @return
     */
    @Override
    public T getMapper(Class daoInterfaceClass) {
        try {
            mappers = ConfigBuilder.loadMapperAnnotation(daoInterfaceClass.getName());
            connection = new ConnectionUtils().getThreadConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //代理谁就用谁的类加载器，实现相同的接口
        return (T) Proxy.newProxyInstance(daoInterfaceClass.getClassLoader(), new Class[]{daoInterfaceClass},
                new MapperProxy(mappers, connection));

    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
