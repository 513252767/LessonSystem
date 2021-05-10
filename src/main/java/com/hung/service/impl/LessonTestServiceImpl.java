package com.hung.service.impl;

import com.hung.dao.GradeDao;
import com.hung.dao.LessonTestDao;
import com.hung.pojo.LessonTest;
import com.hung.service.LessonTestService;
import com.hung.util.spring.annotation.Autowired;
import com.hung.util.spring.annotation.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hung
 */
@Service("lessonTestService")
public class LessonTestServiceImpl implements LessonTestService {
    @Autowired
    LessonTestDao lessonTestDao;
    @Autowired
    GradeDao gradeDao;

    /**
     * 设置考试事件
     *
     * @param lessonTest
     * @return
     */
    @Override
    public Boolean addLessonTest(LessonTest lessonTest) {
        return lessonTestDao.addLessonTest(lessonTest) > 0;
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

    /**
     * 根据课程id查询考试
     *
     * @param
     * @return
     */
    @Override
    public List<LessonTest> queryAllTest(Integer userId) {
        List<String> lessonIds = gradeDao.queryAllTestByUserId(userId);
        List<LessonTest> tests = new ArrayList<>();
        if (lessonIds.size() != 0) {
            for (String lessonId : lessonIds) {
                LessonTest lessonTest = lessonTestDao.queryTestByLessonId(Integer.valueOf(lessonId));
                if (lessonTest!=null){
                    tests.add(lessonTest);
                }
            }
            return tests;
        }
        return null;
    }
}
