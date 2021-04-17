package com.hung.util.orm.utils;

import com.hung.util.orm.config.Mapper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责执行SQL语句，并且封装结果集
 *
 * @author Hung
 */
public class Executor {

    public Executor() {
    }

    /**
     * 查找全部
     *
     * @param mapper
     * @param conn
     * @param <E>
     * @return
     */
    public <E, T> List<E> selectAll(Mapper mapper, Connection conn, Object[] objects, Type[] genericParameterTypes) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            //1.取出mapper中的数据
            String queryString = mapper.getQueryString();
            String resultType = mapper.getResultType();
            Class domainClass = Class.forName(resultType);

            //判断参数集合是否为空
            if (genericParameterTypes.length != 0) {
                //遍历参数集合
                for (int i = 0; i < genericParameterTypes.length; i++) {
                    Type genericParameterType = genericParameterTypes[i];
                    //非包装集合使用#{}形式在此替换
                    if (genericParameterType != Integer.class && genericParameterType != String.class) {
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
                                } else if (type == int.class || type == Integer.class) {
                                    replaceValue = value.toString();
                                }
                            }
                            //判断类型
                            if (replaceValue != null) {
                                //判断sql中是否有此，有则替换，无则结束
                                if (queryString.contains(condition)) {
                                    queryString = queryString.replace(condition, replaceValue);
                                }
                            }
                        }
                    }
                }
            }
            //2.获取PreparedStatement对象
            pstm = conn.prepareStatement(queryString);
            int j = 1;

            if (genericParameterTypes.length != 0) {
                //封装基本包装类型数据
                for (int i = 0; i < genericParameterTypes.length; i++) {
                    Type genericParameterType = genericParameterTypes[i];
                    //包装类型用占位符？,在此填充
                    T o = (T) objects[i];
                    if (genericParameterType == Integer.class) {
                        pstm.setInt(j, (Integer) o);
                        j++;
                    } else {
                        pstm.setString(j, o.toString());
                        j++;
                    }
                }
            }

            //3.执行SQL语句，获取结果集
            rs = pstm.executeQuery();
            //4.封装结果集
            List<E> list = new ArrayList<E>();
            while (rs.next()) {
                //实例化要封装的实体类对象
                Class<E> pclass = (Class<E>) Class.forName(mapper.getResultType());
                if (pclass == Integer.class) {
                    Integer anInt = rs.getInt(1);
                    list.add((E) anInt);
                } else if (isContain(pclass)) {
                    E obj = (E) rs.getString(1);
                    list.add(obj);
                }else {
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
                        PropertyDescriptor pd = new PropertyDescriptor(columnName, domainClass);
                        //获取它的写入方法
                        Method writeMethod = pd.getWriteMethod();
                        //把获取的列的值，给对象赋值
                        writeMethod.invoke(obj, columnValue);
                    }
                    //把赋好值的对象加入到集合中
                    list.add(obj);
                }
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            release(pstm, rs);
        }
    }

    /**
     * 查找一个方法
     *
     * @param mapper
     * @param conn
     * @param objects
     * @param <E>
     * @return
     */
    public <E, T> E select(Mapper mapper, Connection conn, Object[] objects, Type[] genericParameterTypes) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            //1.取出mapper中的数据
            String queryString = mapper.getQueryString();
            String resultType = mapper.getResultType();
            Class domainClass = Class.forName(resultType);

            //实例化传入参数类型
            if (genericParameterTypes.length != 0) {
                for (int i = 0; i < genericParameterTypes.length; i++) {
                    Type genericParameterType = genericParameterTypes[i];
                    if (genericParameterType != Integer.class && genericParameterType != String.class) {
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
                                } else if (type == int.class || type == Integer.class) {
                                    replaceValue = value.toString();
                                }
                            }
                            //判断类型
                            if (replaceValue != null) {
                                //判断sql中是否有此，有则替换，无则结束
                                if (queryString.contains(condition)) {
                                    queryString = queryString.replace(condition, replaceValue);
                                }
                            }
                        }
                    }
                }
            }

            //2.获取PreparedStatement对象
            pstm = conn.prepareStatement(queryString);
            int j = 1;
            //封装基本包装类型数据
            if (genericParameterTypes.length != 0) {
                for (int i = 0; i < genericParameterTypes.length; i++) {
                    Type genericParameterType = genericParameterTypes[i];
                    T o = (T) objects[i];
                    if (genericParameterType == Integer.class) {
                        pstm.setInt(j, (Integer) o);
                        j++;
                    } else if (genericParameterType == String.class) {
                        pstm.setString(j, o.toString());
                        j++;
                    }
                }
            }
            //3.执行SQL语句，获取结果集
            rs = pstm.executeQuery();
            //实例化要封装的实体类对象
            Class<E> pclass = (Class<E>) Class.forName(mapper.getResultType());
            //4.封装结果集
            while (rs.next()) {
                if (pclass == Integer.class) {
                    Integer integer = rs.getInt(1);
                    return (E) integer;
                } else if (isContain(pclass)) {
                    E obj = (E) rs.getString(1);
                    return obj;
                }
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
                    PropertyDescriptor pd = new PropertyDescriptor(columnName, domainClass);
                    //获取它的写入方法
                    Method writeMethod = pd.getWriteMethod();
                    //把获取的列的值，给对象赋值
                    writeMethod.invoke(obj, columnValue);
                }
                return obj;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            release(pstm, rs);
        }
    }

    /**
     * update方法
     *
     * @param mapper
     * @param conn
     * @param objects
     * @param genericParameterTypes
     * @param <T>
     * @return
     */
    public <T> Integer update(Mapper mapper, Connection conn, Object[] objects, Type[] genericParameterTypes) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            //1.取出mapper中的数据
            String queryString = mapper.getQueryString();
            //实例化传入参数类型
            if (genericParameterTypes.length != 0) {
                for (int i = 0; i < genericParameterTypes.length; i++) {
                    Type genericParameterType = genericParameterTypes[i];
                    if (genericParameterType != Integer.class && genericParameterType != String.class) {
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
                                } else if (type == int.class || type == Integer.class) {
                                    replaceValue = value.toString();
                                }
                            }
                            //判断类型
                            if (replaceValue != null) {
                                //判断sql中是否有此，有则替换，无则结束
                                if (queryString.contains(condition)) {
                                    queryString = queryString.replace(condition, replaceValue);
                                }
                            }
                        }
                    }
                }
            }

            //2.获取PreparedStatement对象
            pstm = conn.prepareStatement(queryString);
            int j = 1;
            //封装基本包装类型数据
            if (genericParameterTypes.length != 0) {
                for (int i = 0; i < genericParameterTypes.length; i++) {
                    Type genericParameterType = genericParameterTypes[i];
                    T o = (T) objects[i];
                    if (genericParameterType == Integer.class) {
                        pstm.setInt(j, (Integer) o);
                        j++;
                    } else {
                        pstm.setString(j, o.toString());
                        j++;
                    }
                }
            }
            //3.执行SQL语句，获取结果集
            int i = pstm.executeUpdate();
            //4.封装结果集
            return i;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            release(pstm, rs);
        }
    }

    public boolean isContain(Class className) {
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
}
