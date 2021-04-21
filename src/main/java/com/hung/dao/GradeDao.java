package com.hung.dao;

import com.hung.util.orm.annotations.Select;
import com.hung.util.orm.annotations.Update;

import java.util.List;

/**
 * @author Hung
 */
public interface GradeDao {
    /**
     * 根据课程id查询所有学生的信息
     *
     * @param lessonId
     * @return
     */
    @Select("select userId from grade where lessonId=? and grade=0")
    List<Integer> queryAllByLessonId(Integer lessonId);


    /**
     * 添加成绩
     *
     * @param lessonId
     * @param userId
     * @param grade
     * @return
     */
    @Update("update grade set grade=?,condition=2 where lessonId=? and userId=?")
    Integer addGrade(String grade, Integer lessonId, Integer userId);

    /**
     * 根据学生id查询它的考试
     *
     * @param userId
     * @return
     */
    @Select("select lessonId from grade where userId=?")
    List<String> queryAllTestByUserId(Integer userId);
}
