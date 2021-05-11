package com.hung.service.impl;

import com.hung.dao.ExamApplyDao;
import com.hung.pojo.ExamApply;
import com.hung.service.ExamApplyService;
import com.hung.util.spring.annotation.Autowired;
import com.hung.util.spring.annotation.Service;

import java.util.List;

/**
 * @author Hung
 */
@Service("examApplyService")
public class ExamApplyServiceImpl implements ExamApplyService {
    @Autowired
    ExamApplyDao examApplyDao;

    /**
     * 查询所有考试申请
     *
     * @return
     */
    @Override
    public List<ExamApply> queryExamApplies() {
        return examApplyDao.queryExamApplies();
    }
}
