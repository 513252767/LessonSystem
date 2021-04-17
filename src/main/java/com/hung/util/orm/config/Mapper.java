package com.hung.util.orm.config;

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
    private String resultType;

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
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
        return Objects.equals(queryString, mapper.queryString) && Objects.equals(resultType, mapper.resultType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryString, resultType);
    }
}
