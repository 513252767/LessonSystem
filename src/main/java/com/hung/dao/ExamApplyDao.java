package com.hung.dao;

import com.hung.pojo.ExamApply;
import com.hung.util.orm.annotations.Select;
import com.hung.util.spring.annotation.Repository;

import java.util.List;

/**
 * @author Hung
 */
@Repository("examApplyDao")
public interface ExamApplyDao {
    /**
     * 查询所有考试申请
     * @return
     */
    @Select("select id,lessonId,number,examTime from exam_apply;")
    List<ExamApply> queryExamApplies();
}
