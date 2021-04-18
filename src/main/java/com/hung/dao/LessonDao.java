package com.hung.dao;

import com.hung.pojo.Lesson;
import com.hung.util.orm.annotations.Select;

import java.util.List;

/**
 * @author Hung
 */
public interface LessonDao {
    /**
     * 按照accountId和第几节课查询课程
     * @param accountId
     * @return
     */
    @Select("SELECT name,teacher,classroom FROM lesson WHERE  id=(SELECT lessonid FROM grade WHERE userid = ?)  ORDER BY turn,week;")
    List<Lesson> queryAllLesson(Integer accountId);
}
