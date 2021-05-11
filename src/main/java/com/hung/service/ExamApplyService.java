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
}
