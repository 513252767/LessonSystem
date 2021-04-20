package com.hung.service.impl;

import com.hung.dao.LessonDao;
import com.hung.pojo.Lesson;
import com.hung.service.LessonService;
import com.hung.util.orm.sqlsession.SqlSession;
import com.hung.util.orm.sqlsession.defaults.DefaultSqlSession;

import java.util.List;

/**
 * @author Hung
 */
public class LessonServiceImpl implements LessonService {

    /**
     * 使用工厂创建sqlSession对象
     */
    SqlSession sqlSession = new DefaultSqlSession();
    /**
     * 使用SqlSession创建Dao接口的代理对象
     */
    LessonDao lessonDao = (LessonDao) sqlSession.getMapper(LessonDao.class);

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



}
