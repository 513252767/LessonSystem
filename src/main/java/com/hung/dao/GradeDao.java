package com.hung.dao;

import com.hung.pojo.Grade;
import com.hung.util.orm.annotations.Insert;
import com.hung.util.orm.annotations.Select;
import com.hung.util.orm.annotations.Update;
import com.hung.util.spring.annotation.Repository;

import java.util.List;

/**
 * @author Hung
 */
@Repository("gradeDao")
public interface GradeDao {
    /**
     * 根据课程id查询所有学生的信息
     *
     * @param lessonId
     * @return
     */
    @Select("select userId from grade where lessonId=? and grade=0")
    List<Integer> queryAllByLessonId(Integer lessonId);


    /**
     * 添加成绩
     *
     * @param lessonId
     * @param userId
     * @param grade
     * @return
     */
    @Update("update grade set grade=?,condition='2' where lessonId=? and userId=?")
    Integer addGrade(String grade, Integer lessonId, Integer userId);

    /**
     * 根据学生id查询它的考试
     *
     * @param userId
     * @return
     */
    @Select("select lessonId from grade where userId=?")
    List<String> queryAllTestByUserId(Integer userId);

    /**
     * 根据课程id查询已有人数
     * @param lessonId
     * @return
     */
    @Select("select count(*) from grade where lessonId=?")
    Integer queryNumsByLessonId(Integer lessonId);

    /**
     * 选课
     * @param lessonId
     * @param userId
     * @return
     */
    @Insert("insert into grade (id,lessonId,userId,grade,`condition`,teacherGrade) values (default,?,?,\'0\',\'1\',0);")
    Integer chooseLesson(Integer lessonId,Integer userId);

    /**
     * 根据课程id查找所有对老师的评分
     * @param lessonId
     * @return
     */
    @Select("select userId,teacherGrade from grade where lessonId=?;")
    List<Grade> queryTeacherGradeByLessonId(Integer lessonId);

    /**
     * 根据学生id查询该学生所有已有成绩
     * @param userid
     * @return
     */
    @Select("select lessonId,grade from grade where grade!='0';")
    List<Grade> queryStudentGradeByUserId(Integer userid);

    /**
     * 留言
     * @param comment
     * @param lessonId
     * @param userId
     * @return
     */
    @Update("update grade set comment=? where lessonId=? and userId=?;")
    Integer leaveMessage(String comment,Integer lessonId,Integer userId);

    /**
     * 根据课程id获取留言
     * @param lessonId
     * @return
     */
    @Select("select userId,comment where lessonId=? and condition='2';")
    List<Grade> queryLeavingMessage(Integer lessonId);
}
