package com.hung.util.jdbc;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Hung
 */
public class MyDataSource implements DataSource {

    private static List<Connection> pool = new LinkedList<>();

    static {
        try {
            for (int i = 0; i < 10; i++) {
                Connection connection = JdbcUtil.getConnection();
                pool.add(connection);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static List<Connection> getPool() {
        return pool;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        if (pool.size() == 0) {
            try {
                for (int i = 0; i < 10; i++) {
                    Connection connection1 = JdbcUtil.getConnection();
                    pool.add(connection1);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        connection = pool.remove(0);
        return connection;
    }

    @Override
    public Connection getConnection(String s, String s1) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter printWriter) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int i) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }
}
