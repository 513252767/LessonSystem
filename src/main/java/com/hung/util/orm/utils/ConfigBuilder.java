package com.hung.util.orm.utils;


import com.hung.util.orm.annotations.Delete;
import com.hung.util.orm.annotations.Insert;
import com.hung.util.orm.annotations.Select;
import com.hung.util.orm.annotations.Update;
import com.hung.util.orm.config.Mapper;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于解析配置文件
 *
 * @author Hung
 */
public class ConfigBuilder {

    /**
     * 根据传入的参数，得到dao中所有被select注解标注的方法。
     * 根据方法名称和类名，以及方法上注解value属性的值，组成Mapper的必要信息
     *
     * @param daoClassPath
     * @return
     */
    public static Map<String, Mapper> loadMapperAnnotation(String daoClassPath) throws Exception {
        //定义返回值对象
        Map<String, Mapper> mappers = new HashMap<String, Mapper>();
        //1.得到dao接口的字节码对象
        Class daoClass = Class.forName(daoClassPath);
        //2.得到dao接口中的方法数组
        Method[] methods = daoClass.getMethods();
        //3.遍历Method数组
        for (Method method : methods) {
            //取出每一个方法，判断是否有select注解
            boolean isAnnotated = method.isAnnotationPresent(Select.class) || method.isAnnotationPresent(Update.class) ||
                    method.isAnnotationPresent(Delete.class) || method.isAnnotationPresent(Insert.class);
            if (isAnnotated) {
                //创建Mapper对象
                Mapper mapper = new Mapper();
                //取出注解的value属性值
                String queryString = "";
                if (method.getAnnotation(Select.class) != null) {
                    Select selectAnno = method.getAnnotation(Select.class);
                    queryString = selectAnno.value();
                } else if (method.getAnnotation(Update.class) != null) {
                    Update updateAnno = method.getAnnotation(Update.class);
                    queryString = updateAnno.value();
                } else if (method.getAnnotation(Delete.class) != null) {
                    Delete deleteAnno = method.getAnnotation(Delete.class);
                    queryString = deleteAnno.value();
                } else if (method.getAnnotation(Insert.class) != null) {
                    Insert insertAnno = method.getAnnotation(Insert.class);
                    queryString = insertAnno.value();
                }
                mapper.setQueryString(queryString);
                //获取当前方法的返回值，还要求必须带有泛型信息
                Type type = method.getGenericReturnType();
                //判断type是不是参数化的类型
                if (type instanceof ParameterizedType) {
                    //强转
                    ParameterizedType ptype = (ParameterizedType) type;
                    //得到参数化类型中的实际类型参数
                    Type[] types = ptype.getActualTypeArguments();
                    //取出第一个
                    Class domainClass = (Class) types[0];
                    //获取domainClass的类名
                    String resultType = domainClass.getName();
                    //给Mapper赋值
                    mapper.setResultType(resultType);
                } else {
                    //不是参数化类型，直接设置
                    mapper.setResultType(type.getTypeName());
                }
                //组装key的信息
                //获取方法的名称
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String key = className + "." + methodName;
                //给map赋值
                mappers.put(key, mapper);
            }
        }
        return mappers;
    }
}
