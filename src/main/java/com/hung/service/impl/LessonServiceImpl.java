package com.hung.service.impl;

import com.hung.dao.LessonDao;
import com.hung.dao.PageDao;
import com.hung.entity.PageBean;
import com.hung.pojo.Lesson;
import com.hung.service.LessonService;
import com.hung.util.spring.annotation.Autowired;
import com.hung.util.spring.annotation.Service;

import java.util.List;

/**
 * @author Hung
 */
@Service("lessonService")
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonDao lessonDao;
    PageDao pageDao=new PageDao();

    /**
     * 根据第几节课来查询当前节课星期一至五的List
     *
     * @param accountId
     * @return
     */
    @Override
    public List<Lesson> queryAllLesson(Integer accountId) {
        List<Lesson> lessons = lessonDao.queryAllLesson(accountId);
        return lessons;
    }

    /**
     * 根据教师id查询课程
     *
     * @param teacherId
     * @return
     */
    @Override
    public List<Lesson> queryAllLessonByTid(Integer teacherId) {
        return lessonDao.queryAllLessonByTid(teacherId);
    }

    /**
     * 根据lessonId查询课程
     *
     * @param lessonId
     * @return
     */
    @Override
    public Lesson queryLessonById(Integer lessonId) {
        return lessonDao.queryLessonById(lessonId);
    }

    /**
     * 更新课程信息
     *
     * @param lesson
     * @return
     */
    @Override
    public Boolean updateLesson(Lesson lesson) {
        return lessonDao.updateLesson(lesson)>0;
    }

    /**
     * 模糊查询
     *
     * @param condition
     * @return
     */
    @Override
    public List<Lesson> queryLessonByCondition(String condition) {
        condition="%"+condition+"%";
        return lessonDao.queryAllLessonByCondition(condition, condition, condition);
    }

    /**
     * 查询所有本人未选的选修课
     *
     * @param userId
     * @return
     */
    @Override
    public List<Lesson> queryAllOptionalCourse(Integer userId) {
        //查询所有未选选修课程
        return lessonDao.queryAllOptionalCourse(userId);
    }

    /**
     * 查看已完成的
     *
     * @return
     */
    @Override
    public List<Lesson> queryAllLessons() {
        return lessonDao.queryAllLessons();
    }

    /**
     * 按照页数查找数据
     *
     * @param _currentPage
     * @param _rows
     * @param condition
     * @return
     */
    @Override
    public PageBean<Lesson> findDataByPage(String _currentPage, String _rows,String condition) {
        int currentPage=Integer.parseInt(_currentPage);
        int rows=Integer.parseInt(_rows);

        //防止按上一页按钮会出错
        if (currentPage<=0){
            currentPage=1;
        }

        //创建对象设置参数
        PageBean<Lesson> pb = new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

        //调用dao查询总记录数
        int totalCount = pageDao.queryTotalCount(condition);
        pb.setTotalCount(totalCount);

        //计算开始页码
        int start= ( currentPage - 1 ) * rows;
        List<Lesson> list = pageDao.queryLessons(start,rows,condition);
        pb.setList(list);

        //计算总页码
        int totalPage = (totalCount%rows) == 0? totalCount/rows:totalCount/rows+1;
        pb.setTotalPage(totalPage);

        return pb;
    }
}
