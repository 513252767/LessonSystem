package com.hung.service.impl;

import com.hung.dao.LessonTestDao;
import com.hung.pojo.LessonTest;
import com.hung.service.LessonTestService;
import com.hung.util.orm.sqlsession.SqlSession;
import com.hung.util.orm.sqlsession.defaults.DefaultSqlSession;

import java.util.List;

/**
 * @author Hung
 */
public class LessonTestServiceImpl implements LessonTestService {

    /**
     *   使用工厂创建sqlSession对象
     */
    SqlSession sqlSession = new DefaultSqlSession();
    /**
     * 使用SqlSession创建Dao接口的代理对象
     */
    LessonTestDao lessonTestDao=(LessonTestDao) sqlSession.getMapper(LessonTestDao.class);

    /**
     * 设置考试事件
     *
     * @param lessonTest
     * @return
     */
    @Override
    public Boolean addLessonTest(LessonTest lessonTest) {
        return lessonTestDao.addLessonTest(lessonTest)>0;
    }

    /**
     * 查询所有考试
     *
     * @return
     */
    @Override
    public List<LessonTest> queryAllTest() {
        return lessonTestDao.queryAllTest();
    }
}
