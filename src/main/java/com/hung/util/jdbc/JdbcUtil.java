package com.hung.util.jdbc;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

/**
 * @author Hung
 */
public class JdbcUtil {
    /*
     * 这个类是jdbc工具类用来直接使用简化书写
     */

    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            //创建集合类
            Properties properties = new Properties();

            //获取src下的文件的方式
            ClassLoader classLoader = JdbcUtil.class.getClassLoader();
            URL resource = classLoader.getResource("Jdbc.properties");
            String path = resource.getPath();
            properties.load(new FileReader(path));

            //获取数据赋值
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    /**
     * 关闭资源
     *
     * @param statement
     * @param connection
     */
    public static void close(Statement statement, Connection connection) {
        try {
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
