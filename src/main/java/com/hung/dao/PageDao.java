package com.hung.dao;

import com.hung.pojo.Lesson;
import com.hung.util.jdbc.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hung
 */
public class PageDao {
    /**
     * 查询对应瓜圈的所有事件
     *
     * @param start
     * @param rows
     * @param condition
     * @return
     */
    public List<Lesson> queryLessons(int start, int rows, String condition){
        List<Lesson> lessons = new ArrayList<>();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            connection = JdbcUtil.getConnection();
            String sql="select * from lesson where 1=1";

            StringBuilder sb=new StringBuilder(sql);
            if (condition!=null && !"".equals(condition)){
                sb.append(" and name like '%"+condition+"%'");
            }
            sb.append(" limit ?,? ");

            //传入参数,执行sql对象
            preparedStatement = connection.prepareStatement(sb.toString());
            preparedStatement.setInt(1,start);
            preparedStatement.setInt(2,rows);

            //获取resultSet中的数据保存到list中
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Lesson lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setName(resultSet.getString("name"));
                lesson.setCategory(resultSet.getString("category"));
                lesson.setClassroom(resultSet.getString("classroom"));
                lesson.setNumber(resultSet.getString("number"));
                lesson.setTeacher(resultSet.getString("teacher"));
                lesson.setTurn(resultSet.getString("turn"));
                lesson.setWeek(resultSet.getString("week"));
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtil.close(preparedStatement,connection);
        }
        return lessons;
    }

    /**
     * 查询总页数
     * @return
     * @param condition
     */
    public int queryTotalCount(String condition){
        int count=0;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            connection = JdbcUtil.getConnection();
            //遍历条件查询语句
            String sql="select count(*) from lesson where 1 =1";
            StringBuilder sb=new StringBuilder(sql);

            if (condition!=null && !"".equals(condition)){
                sb.append(" and name like '%"+condition+"%'");
            }

            //执行sql对象
            preparedStatement = connection.prepareStatement(sb.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            //获取总记录数
            while (resultSet.next()){
                count= resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtil.close(preparedStatement,connection);
        }
        return count;
    }
}
