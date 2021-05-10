package com.hung.dao;

import com.hung.pojo.LessonTest;
import com.hung.util.orm.annotations.Insert;
import com.hung.util.orm.annotations.Select;
import com.hung.util.spring.annotation.Repository;

import java.util.List;

/**
 * @author Hung
 */
@Repository("lessonTestDao")
public interface LessonTestDao {
    /**
     * 设置考试事件
     *
     * @param lessonTest
     * @return
     */
    @Insert("INSERT into lessonTest (id,lessonId,testTime) values (default,#{lessonId},#{testTime});")
    Integer addLessonTest(LessonTest lessonTest);

    /**
     * 查询所有考试
     *
     * @return
     */
    @Select("select id,lessonId,testTime from lessonTest;")
    List<LessonTest> queryAllTest();

    /**
     * 根据课程id查询所有考试
     *
     * @param lessonId
     * @return
     */
    @Select("select id,lessonId,testTime from lessonTest where lessonId=?;")
    LessonTest queryTestByLessonId(Integer lessonId);
}
