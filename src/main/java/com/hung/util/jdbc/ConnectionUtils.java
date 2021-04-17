package com.hung.util.jdbc;

import java.sql.Connection;

/**
 * @author Hung
 */
public class ConnectionUtils {

    private ThreadLocal<Connection> t1 = new ThreadLocal<>();
    MyDataSource myDataSource = new MyDataSource();


    public MyDataSource getMyDataSource() {
        return myDataSource;
    }

    public void setMyDataSource(MyDataSource myDataSource) {
        this.myDataSource = myDataSource;
    }

    /**
     * 获取当前线程上的连接
     *
     * @return
     */
    public Connection getThreadConnection() {
        Connection connection = t1.get();
        try {
            if (connection == null) {
                connection = myDataSource.getConnection();
                t1.set(connection);
            }
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeConnection() {
        t1.remove();
    }

}
