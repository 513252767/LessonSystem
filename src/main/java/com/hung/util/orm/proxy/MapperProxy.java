package com.hung.util.orm.proxy;


import com.hung.util.orm.annotations.Delete;
import com.hung.util.orm.annotations.Insert;
import com.hung.util.orm.annotations.Select;
import com.hung.util.orm.annotations.Update;
import com.hung.util.orm.config.Mapper;
import com.hung.util.orm.utils.Executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;

/**
 * @author Hung
 */
public class MapperProxy implements InvocationHandler {
    private Map<String, Mapper> mappers;
    private Connection connection;

    public MapperProxy(Map<String, Mapper> mappers, Connection connection) {
        this.mappers = mappers;
        this.connection = connection;
    }

    /**
     * 对方法进行增强
     *
     * @param o
     * @param method
     * @param objects
     * @return
     */
    @Override
    public Object invoke(Object o, Method method, Object[] objects) {
        //获取方法名
        String methodName = method.getName();
        //获取方法所在类的名称
        String className = method.getDeclaringClass().getName();
        //组合key
        String key = className + "." + methodName;
        //获取mappers中的Mapper对象
        Mapper mapper = mappers.get(key);
        //判断是否有mapper
        if (mapper == null) {
            throw new IllegalArgumentException("传入的参数有误");
        }

        //根据不同注解实现不同功能
        Executor executor = Executor.getInstance();
        if (method.isAnnotationPresent(Select.class)) {
            return executor.select(mapper, connection, objects);
        }else if (method.isAnnotationPresent(Update.class) || method.isAnnotationPresent(Delete.class)
                || method.isAnnotationPresent(Insert.class)) {
            return executor.update(mapper, connection, objects);
        }
        return null;
    }
}
