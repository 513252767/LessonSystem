package com.hung.service;

import com.hung.pojo.ExamApply;

import java.util.List;

/**
 * @author Hung
 */
public interface ExamApplyService {
    /**
     * 查询所有考试申请
     * @return
     */
    List<ExamApply> queryExamApplies();

    /**
     * 添加考试申请
     * @param lessonId
     * @param number
     * @param examTime
     * @return
     */
    boolean addExamApply(Integer lessonId,String number,String examTime);
}
