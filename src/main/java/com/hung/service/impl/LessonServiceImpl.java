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

    //使用工厂创建sqlSession对象
    SqlSession sqlSession = new DefaultSqlSession();
    //使用SqlSession创建Dao接口的代理对象
    LessonDao lessonDao = (LessonDao) sqlSession.getMapper(LessonDao.class);

    /**
     * 根据第几节课来查询当前节课星期一至五的List
     *
     * @param accountId
     * @param turn
     * @return
     */
    @Override
    public List<Lesson> queryAllLessonByTurn(Integer accountId) {
        List<Lesson> lessons = lessonDao.queryAllLesson(accountId);
        return lessons;
    }

}
