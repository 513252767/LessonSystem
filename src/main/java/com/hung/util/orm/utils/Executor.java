package com.hung.util.orm.utils;

import com.hung.util.orm.config.Mapper;
import org.jetbrains.annotations.Nullable;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

/**
 * 负责执行SQL语句，并且封装结果集
 *
 * @author Hung
 */
public class Executor {

    private static Executor instance = new Executor();

    public <E> E select(Mapper mapper, Connection conn, Object[] objects) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Type type = mapper.getResultType();
            Class<?> domainClass = null;
            //1.取出mapper中的数据
            String queryString = mapper.getQueryString();
            Type[] genericParameterTypes = mapper.getGenericParameterTypes();
            //对#{}进行赋值
            queryString = paramSet(objects, genericParameterTypes, queryString);
            //2.获取PreparedStatement对象
            pstm = conn.prepareStatement(queryString);
            //对占位符数据注入
            preparedSet(objects, genericParameterTypes, pstm);
            //3.执行SQL语句，获取结果集
            rs = pstm.executeQuery();
            //根据返回值类型封装
            if (type instanceof ParameterizedType) {
                //参数化类型强转
                ParameterizedType ptype = (ParameterizedType) type;
                //得到参数化类型中的实际类型参数
                Type[] types = ptype.getActualTypeArguments();
                //取出第一个
                domainClass = (Class<?>) types[0];
                E rs1 = resultSetCollection(rs, domainClass, ptype);
                if (rs1 != null) {
                    return rs1;
                }
            } else {
                //不是参数化类型
                Class<E> pclass = (Class<E>) type;
                //封装结果集
                if (rs.next()) {
                    return resultSetNotCollection(rs, pclass);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            release(pstm, rs);
        }
        return null;
    }

    /**
     * update方法
     *
     * @param mapper  mapper数据
     * @param conn    连接
     * @param objects 参数
     * @return 1为成功
     */
    public Integer update(Mapper mapper, Connection conn, Object[] objects) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            //取出mapper中的数据
            String queryString = mapper.getQueryString();
            Type[] genericParameterTypes = mapper.getGenericParameterTypes();
            queryString = paramSet(objects, genericParameterTypes, queryString);
            //2.获取PreparedStatement对象
            pstm = conn.prepareStatement(queryString);
            preparedSet(objects, genericParameterTypes, pstm);
            //3.执行SQL语句
            return pstm.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            release(pstm, rs);
        }
    }

    private <E> E resultSetNotCollection(ResultSet rs, Class<E> pclass) throws SQLException, InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        if (pclass == Integer.class) {
            Integer integer = rs.getInt(1);
            return (E) integer;
        } else if (isContain(pclass)) {
            E obj = (E) rs.getString(1);
            return obj;
        }
        E obj = getObj(rs, pclass);
        return obj;
    }

    @Nullable
    private <E> E resultSetCollection(ResultSet rs, Class<?> domainClass, ParameterizedType ptype) throws SQLException, InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        if (ptype.getRawType() == List.class) {
            //封装List
            return listPack(rs, domainClass);
        } else if (ptype.getRawType() == Set.class) {
            //封装Set
            return setPack(rs, domainClass);
        }
        return null;
    }

    private <E, T> E setPack(ResultSet rs, Class<?> domainClass) throws SQLException, InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        Set<T> set = new HashSet<>();
        while (rs.next()){
            if (domainClass == Integer.class) {
                Integer anInt = rs.getInt(1);
                set.add((T) anInt);
            } else if (isContain(domainClass)) {
                T obj = (T)rs.getString(1);
                set.add(obj);
            } else {
                T obj = (T) getObj(rs, domainClass);
                //把赋好值的对象加入到集合中
                set.add(obj);
            }
        }
        return (E) set;
    }

    private <E, T> E listPack(ResultSet rs, Class<?> domainClass) throws SQLException, InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        List<T> list = new ArrayList<>();
        while (rs.next()){
            if (domainClass == Integer.class) {
                Integer anInt = rs.getInt(1);
                list.add((T) anInt);
            } else if (isContain(domainClass)) {
                T obj = (T)rs.getString(1);
                list.add(obj);
            } else {
                T obj = (T) getObj(rs, domainClass);
                //把赋好值的对象加入到集合中
                list.add(obj);
            }
        }
        return (E) list;
    }

    /**
     * #{}参数设置
     *
     * @param objects               参数
     * @param genericParameterTypes 参数类型
     * @param queryString           sql语句
     * @param <T>                   参数类型泛型
     * @return sql语句
     */
    private <T> String paramSet(Object[] objects, Type[] genericParameterTypes, String queryString) throws IllegalAccessException {
        //判断是否为空
        if (genericParameterTypes.length != 0) {
            //遍历参数集合
            for (int i = 0; i < genericParameterTypes.length; i++) {
                Type genericParameterType = genericParameterTypes[i];
                //非包装集合使用#{}形式在此替换
                if (genericParameterType !=Integer.class && genericParameterType != String.class) {
                    //反射获取实例
                    T o = (T) objects[i];
                    //反射获取参数
                    Field[] declaredFields = o.getClass().getDeclaredFields();
                    for (Field field : declaredFields) {
                        //获取属性信息
                        field.setAccessible(true);
                        String name = field.getName();
                        String condition = "#{" + name + "}";
                        Object value = field.get(o);
                        Class<?> type = field.getType();
                        String replaceValue = null;
                        //根据不同类型包装
                        if (value != null) {
                            if (type == String.class) {
                                replaceValue = "\"" + value + "\"";
                            } else if (typeJudge(type)) {
                                replaceValue = value.toString();
                            }
                        }
                        //判断类型
                        if (replaceValue != null && queryString.contains(condition)) {
                            //判断sql中是否有此，有则替换，无则结束
                            queryString = queryString.replace(condition, replaceValue);
                        }
                    }
                }
            }
        }
        return queryString;
    }

    boolean typeJudge(Class<?> type) {
        return type == int.class || type == Integer.class || type == Long.class || type == Short.class || type == Float.class
                || type == Double.class || type == Character.class;
    }

    /**
     * 占位符注入参数
     *
     * @param objects               参数
     * @param genericParameterTypes 参数类型
     * @param preparedStatement
     * @param <T>                   参数类型泛型
     */
    private <T> void preparedSet(Object[] objects, Type[] genericParameterTypes, PreparedStatement preparedStatement) throws SQLException {
        if (genericParameterTypes.length != 0) {
            int j = 1;
            //封装基本包装类型数据
            for (int i = 0; i < genericParameterTypes.length; i++) {
                Type genericParameterType = genericParameterTypes[i];
                //包装类型用占位符？,在此填充
                T o = (T) objects[i];
                if (genericParameterType == Integer.class) {
                    preparedStatement.setInt(j, (Integer) o);
                    j++;
                } else if (genericParameterType == String.class) {
                    preparedStatement.setString(j, o.toString());
                    j++;
                }
            }
        }
    }

    /**
     * 结果集封装返回对象
     *
     * @param rs     结果集
     * @param pclass 封装结果集的参数类型
     * @param <E>    返回值泛型
     */
    private <E> E getObj(ResultSet rs, Class<E> pclass) throws InstantiationException, IllegalAccessException, SQLException, IntrospectionException, InvocationTargetException {
        E obj = pclass.newInstance();
        //取出结果集的元信息：ResultSetMetaData
        ResultSetMetaData rsmd = rs.getMetaData();
        //取出总列数
        int columnCount = rsmd.getColumnCount();
        //遍历总列数
        for (int i = 1; i <= columnCount; i++) {
            //获取每列的名称，列名的序号是从1开始的
            String columnName = rsmd.getColumnName(i);
            //根据得到列名，获取每列的值
            Object columnValue = rs.getObject(columnName);
            //给obj赋值：使用Java内省机制（借助PropertyDescriptor实现属性的封装）
            PropertyDescriptor pd = new PropertyDescriptor(columnName, pclass);
            //获取它的写入方法
            Method writeMethod = pd.getWriteMethod();
            //把获取的列的值，给对象赋值
            writeMethod.invoke(obj, columnValue);
        }
        return obj;
    }

    public boolean isContain(Class<?> className) {
        return className == String.class || className == Boolean.class || className == Long.class || className == Short.class;
    }

    private void release(PreparedStatement pstm, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (pstm != null) {
            try {
                pstm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Executor getInstance(){
        return instance;
    }

    private Executor() {
    }
}
