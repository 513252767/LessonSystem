package com.hung.service.impl;

import com.hung.dao.GradeDao;
import com.hung.dao.LessonDao;
import com.hung.dao.UserDao;
import com.hung.entity.LeavingMessage;
import com.hung.entity.StudentGrade;
import com.hung.entity.TeacherGrade;
import com.hung.pojo.Grade;
import com.hung.pojo.Lesson;
import com.hung.service.GradeService;
import com.hung.util.spring.annotation.Autowired;
import com.hung.util.spring.annotation.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hung
 */
@Service("gradeService")
public class GradeServiceImpl implements GradeService {
    @Autowired
    GradeDao gradeDao;
    @Autowired
    UserDao userDao;
    @Autowired
    LessonDao lessonDao;

    /**
     * 根据课程id查询所有学生的信息
     *
     * @param lessonId
     * @return
     */
    @Override
    public List<Integer> queryAllByLessonId(Integer lessonId) {
        return gradeDao.queryAllByLessonId(lessonId);
    }

    /**
     * 添加成绩
     *
     * @param lessonId
     * @param userId
     * @param grade
     * @return
     */
    @Override
    public Boolean addGrade(Integer lessonId, Integer userId, String grade) {
        return gradeDao.addGrade(grade, userId, lessonId) > 0;
    }

    /**
     * 根据学生id查询已有课程
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> queryLessonByUserId(Integer userId) {
        return gradeDao.queryAllTestByUserId(userId);
    }

    /**
     * 根据课程id查询查询已选人数
     *
     * @param lessonId
     * @return
     */
    @Override
    public Integer queryNumsByLessonId(Integer lessonId) {
        return gradeDao.queryNumsByLessonId(lessonId);
    }

    /**
     * 选课
     *
     * @param lessonId
     * @param userId
     * @return
     */
    @Override
    public Boolean chooseLesson(Integer lessonId, Integer userId) {
        return gradeDao.chooseLesson(lessonId,userId)>0;
    }

    /**
     * 根据课程Id查询老师评分
     *
     * @param lessonId
     * @return
     */
    @Override
    public List<TeacherGrade> queryTeacherGradeByLessonId(Integer lessonId) {
        List<TeacherGrade> teacherGrades = new ArrayList<>();
        List<Grade> grades = gradeDao.queryTeacherGradeByLessonId(lessonId);
        grades.forEach(grade -> {
            String name = userDao.queryNickNameById(grade.getUserId());
            TeacherGrade teacherGrade = new TeacherGrade(grade.getUserId(),name,grade.getTeacherGrade());
            teacherGrades.add(teacherGrade);
        });
        return teacherGrades;
    }

    /**
     * 根据学生id查询其已有成绩
     *
     * @param userId
     * @return
     */
    @Override
    public List<StudentGrade> queryStudentGradeByUserId(Integer userId) {
        List<Grade> grades = gradeDao.queryStudentGradeByUserId(userId);
        List<StudentGrade> studentGrades = new ArrayList<>();
        grades.forEach(grade -> {
            Lesson lesson = lessonDao.queryLessonById(grade.getLessonId());
            StudentGrade studentGrade = new StudentGrade(lesson.getId(),lesson.getName(),grade.getGrade());
            studentGrades.add(studentGrade);
        });
        return studentGrades;
    }

    /**
     * 留言
     *
     * @param comment
     * @param lessonId
     * @param userId
     * @return
     */
    @Override
    public boolean leaveMessage(String comment, Integer lessonId, Integer userId) {
        return gradeDao.leaveMessage(comment,lessonId,userId)>0;
    }

    /**
     * 根据课程id获取留言
     *
     * @param lessonId
     * @return
     */
    @Override
    public List<LeavingMessage> queryLeavingMessage(Integer lessonId) {
        List<LeavingMessage> leavingMessages = new ArrayList<>();
        List<Grade> grades = gradeDao.queryLeavingMessage(lessonId);
        grades.forEach(grade -> {
            String name = userDao.queryNickNameById(grade.getUserId());
            LeavingMessage leavingMessage = new LeavingMessage(name, grade.getComment());
            leavingMessages.add(leavingMessage);
        });
        return leavingMessages;
    }
}
