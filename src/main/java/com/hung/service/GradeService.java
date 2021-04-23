package com.hung.service;

import java.util.List;

/**
 * @author Hung
 */
public interface GradeService {
    /**
     * 根据课程id查询所有学生的信息
     *
     * @param lessonId
     * @return
     */
    List<Integer> queryAllByLessonId(Integer lessonId);

    /**
     * 添加成绩
     *
     * @param lessonId
     * @param userId
     * @param grade
     * @return
     */
    Boolean addGrade(Integer lessonId, Integer userId, String grade);

    /**
     * 根据学生id查询已有课程
     * @param userId
     * @return
     */
    List<String> queryLessonByUserId(Integer userId);


    /**
     * 根据课程id查询查询已选人数
     * @param lessonId
     * @return
     */
    Integer queryNumsByLessonId(Integer lessonId);

    /**
     * 选课
     * @param lessonId
     * @param userId
     * @return
     */
    Boolean chooseLesson(Integer lessonId,Integer userId);
}
