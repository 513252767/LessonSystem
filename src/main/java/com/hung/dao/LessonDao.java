package com.hung.dao;

import com.hung.pojo.Lesson;
import com.hung.util.orm.annotations.Select;
import com.hung.util.orm.annotations.Update;

import java.util.List;

/**
 * @author Hung
 */
public interface LessonDao {
    /**
     * 按照accountId和第几节课查询课程
     *
     * @param accountId
     * @return
     */
    @Select("SELECT week,turn,name,teacher,number,classroom,category FROM lesson WHERE  id=(SELECT lessonid FROM grade WHERE userid = ?)  ORDER BY turn,week;")
    List<Lesson> queryAllLesson(Integer accountId);

    /**
     * 根据教师id查询其课程
     *
     * @param teacherId
     * @return
     */
    @Select("SELECT id,name,classroom,number,category FROM lesson WHERE teacher = ?;")
    List<Lesson> queryAllLessonByTid(Integer teacherId);

    /**
     * 根据lessonId查询课程
     *
     * @param lessonId
     * @return
     */
    @Select("select * from lesson where id = ?;")
    Lesson queryLessonById(Integer lessonId);

    /**
     * 更新课程信息
     *
     * @param lesson
     * @return
     */
    @Update("UPDATE lesson SET (week,turn,name,teacher,number,classroom,category) value(#{week},#{turn},#{name},#{teacher}," +
            "#{number},#{classroom},#{category}) where id=#{id};")
    Integer updateLesson(Lesson lesson);

    /**
     * 条件查询
     * @param className
     * @param teacherId
     * @param category
     * @return
     */
    @Select("select week,turn,name,teacher,number,classroom,category from lesson where name like ? or teacher like ? or category like ?;")
    List<Lesson> queryAllLessonByCondition(String className,String teacherId,String category);
}

