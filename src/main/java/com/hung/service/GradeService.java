package com.hung.service;

import com.hung.entity.LeavingMessage;
import com.hung.entity.StudentGrade;
import com.hung.entity.TeacherGrade;

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

    /**
     * 根据课程Id查询老师评分
     * @param lessonId
     * @return
     */
    List<TeacherGrade> queryTeacherGradeByLessonId(Integer lessonId);

    /**
     * 根据学生id查询其已有成绩
     * @param userId
     * @return
     */
    List<StudentGrade> queryStudentGradeByUserId(Integer userId);

    /**
     * 留言
     * @param comment
     * @param lessonId
     * @param userId
     * @return
     */
    boolean leaveMessage(String comment,Integer lessonId,Integer userId);

    /**
     * 根据课程id获取留言
     * @param lessonId
     * @return
     */
    List<LeavingMessage> queryLeavingMessage(Integer lessonId);
}
