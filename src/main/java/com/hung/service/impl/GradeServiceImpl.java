package com.hung.service.impl;

import com.hung.dao.GradeDao;
import com.hung.service.GradeService;
import com.hung.util.spring.annotation.Autowired;
import com.hung.util.spring.annotation.Service;

import java.util.List;

/**
 * @author Hung
 */
@Service("gradeService")
public class GradeServiceImpl implements GradeService {
    @Autowired
    GradeDao gradeDao;

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
}
