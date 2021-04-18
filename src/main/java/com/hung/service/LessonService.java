package com.hung.service;

import com.hung.pojo.Lesson;

import java.util.List;

/**
 * @author Hung
 */
public interface LessonService {
    /**
     * 根据第几节课来查询当前节课星期一至五的List
     * @param accountId
     * @return
     */
    List<Lesson> queryAllLessonByTurn(Integer accountId);
}
