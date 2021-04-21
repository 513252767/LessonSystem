package com.hung.service;

import com.hung.pojo.Lesson;

import java.util.List;

/**
 * @author Hung
 */
public interface LessonService {
    /**
     * 根据第几节课来查询当前节课星期一至五的List
     *
     * @param accountId
     * @return
     */
    List<Lesson> queryAllLesson(Integer accountId);

    /**
     * 根据教师id查询课程
     *
     * @param teacherId
     * @return
     */
    List<Lesson> queryAllLessonByTid(Integer teacherId);

    /**
     * 根据lessonId查询课程
     *
     * @param lessonId
     * @return
     */
    Lesson queryLessonById(Integer lessonId);

    /**
     * 更新课程信息
     *
     * @param lesson
     * @return
     */
    Boolean updateLesson(Lesson lesson);


}
