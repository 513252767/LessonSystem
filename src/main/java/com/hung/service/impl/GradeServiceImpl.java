package com.hung.service.impl;

import com.hung.dao.GradeDao;
import com.hung.service.GradeService;
import com.hung.util.orm.sqlsession.SqlSession;
import com.hung.util.orm.sqlsession.defaults.DefaultSqlSession;

import java.util.List;

/**
 * @author Hung
 */
public class GradeServiceImpl implements GradeService {
    /**
     * 使用工厂创建sqlSession对象
     */
    SqlSession sqlSession = new DefaultSqlSession();
    /**
     * 使用SqlSession创建Dao接口的代理对象
     */
    GradeDao gradeDao = (GradeDao) sqlSession.getMapper(GradeDao.class);
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
        return gradeDao.addGrade(grade,userId,lessonId)>0;
    }
}
