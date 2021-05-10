package com.hung.util.orm.sqlsession;

import com.hung.util.orm.sqlsession.defaults.DefaultSqlSession;

/**
 * @author Hung
 */
public class SqlSessionFactory {
    static SqlSession<?> sqlSession = new DefaultSqlSession();

    public static SqlSession<?> getSqlSession(){
        return sqlSession;
    }
}
