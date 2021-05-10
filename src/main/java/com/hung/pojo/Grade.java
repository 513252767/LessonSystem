package com.hung.pojo;

import java.util.Objects;

/**
 * 分数类
 *
 * @author Hung
 */
public class Grade {
    Integer id;
    Integer lessonId;
    Integer userId;
    String grade;
    String comment;
    String teacherGrade;
    /**
     *
     */
    String condition;

    public Grade() {

    }

    public Grade(Integer id, Integer lessonId, Integer userId, String grade, String condition) {
        this.id = id;
        this.lessonId = lessonId;
        this.userId = userId;
        this.grade = grade;
        this.condition = condition;
    }

    public Grade(Integer id, Integer lessonId, Integer userId, String grade) {
        this.id = id;
        this.lessonId = lessonId;
        this.userId = userId;
        this.grade = grade;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTeacherGrade() {
        return teacherGrade;
    }

    public void setTeacherGrade(String teacherGrade) {
        this.teacherGrade = teacherGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Grade grade1 = (Grade) o;
        return Objects.equals(id, grade1.id) && Objects.equals(lessonId, grade1.lessonId) && Objects.equals(userId, grade1.userId) && Objects.equals(grade, grade1.grade) && Objects.equals(comment, grade1.comment) && Objects.equals(teacherGrade, grade1.teacherGrade) && Objects.equals(condition, grade1.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lessonId, userId, grade, comment, teacherGrade, condition);
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", lessonId=" + lessonId +
                ", userId=" + userId +
                ", grade='" + grade + '\'' +
                ", comment='" + comment + '\'' +
                ", teacherGrade='" + teacherGrade + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
