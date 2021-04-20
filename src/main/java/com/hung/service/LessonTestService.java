package com.hung.service;

import com.hung.pojo.LessonTest;

import java.util.List;

/**
 * @author Hung
 */
public interface LessonTestService {
    /**
     * 设置考试事件
     * @param lessonTest
     * @return
     */
    Boolean addLessonTest(LessonTest lessonTest);

    /**
     * 查询所有考试
     * @return
     */
    List<LessonTest> queryAllTest();
}
