package com.hung.util.orm.config;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Hung
 * 用于封装执行的SQL语句和结果类型的全限定类名
 */
public class Mapper {
    /**
     * SQL和全限定类名
     */
    private String queryString;
    private Type resultType;
    private Type[] genericParameterTypes;

    public Mapper() {
    }

    public Type[] getGenericParameterTypes() {
        return genericParameterTypes;
    }

    public void setGenericParameterTypes(Type[] genericParameterTypes) {
        this.genericParameterTypes = genericParameterTypes;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Type getResultType() {
        return resultType;
    }

    public void setResultType(Type resultType) {
        this.resultType = resultType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mapper mapper = (Mapper) o;
        return Objects.equals(queryString, mapper.queryString) && Objects.equals(resultType, mapper.resultType) && Arrays.equals(genericParameterTypes, mapper.genericParameterTypes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(queryString, resultType);
        result = 31 * result + Arrays.hashCode(genericParameterTypes);
        return result;
    }

    @Override
    public String toString() {
        return "Mapper{" +
                "queryString='" + queryString + '\'' +
                ", resultType=" + resultType +
                ", genericParameterTypes=" + Arrays.toString(genericParameterTypes) +
                '}';
    }
}
