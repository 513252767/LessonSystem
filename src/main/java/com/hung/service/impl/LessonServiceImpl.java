package com.hung.service.impl;

import com.hung.dao.LessonDao;
import com.hung.pojo.Lesson;
import com.hung.service.LessonService;
import com.hung.util.spring.annotation.Autowired;
import com.hung.util.spring.annotation.Service;

import java.util.List;

/**
 * @author Hung
 */
@Service("lessonService")
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonDao lessonDao;

    /**
     * 根据第几节课来查询当前节课星期一至五的List
     *
     * @param accountId
     * @return
     */
    @Override
    public List<Lesson> queryAllLesson(Integer accountId) {
        List<Lesson> lessons = lessonDao.queryAllLesson(accountId);
        return lessons;
    }

    /**
     * 根据教师id查询课程
     *
     * @param teacherId
     * @return
     */
    @Override
    public List<Lesson> queryAllLessonByTid(Integer teacherId) {
        return lessonDao.queryAllLessonByTid(teacherId);
    }

    /**
     * 根据lessonId查询课程
     *
     * @param lessonId
     * @return
     */
    @Override
    public Lesson queryLessonById(Integer lessonId) {
        return lessonDao.queryLessonById(lessonId);
    }

    /**
     * 更新课程信息
     *
     * @param lesson
     * @return
     */
    @Override
    public Boolean updateLesson(Lesson lesson) {
        return lessonDao.updateLesson(lesson)>0;
    }

    /**
     * 模糊查询
     *
     * @param condition
     * @return
     */
    @Override
    public List<Lesson> queryLessonByCondition(String condition) {
        condition="%"+condition+"%";
        return lessonDao.queryAllLessonByCondition(condition, condition, condition);
    }

    /**
     * 查询所有本人未选的选修课
     *
     * @param userId
     * @return
     */
    @Override
    public List<Lesson> queryAllOptionalCourse(Integer userId) {
        //查询所有未选选修课程
        return lessonDao.queryAllOptionalCourse(userId);
    }

    /**
     * 查看已完成的
     *
     * @return
     */
    @Override
    public List<Lesson> queryAllLessons() {
        return lessonDao.queryAllLessons();
    }
}
