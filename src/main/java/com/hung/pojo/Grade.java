package com.hung.pojo;

import java.util.Objects;

/**
 * 分数类
 * @author Hung
 */
public class Grade {
    Integer id;
    Integer lessonId;
    Integer userId;
    String grade;

    public Grade() {
    }

    public Grade(Integer id, Integer lessonId, Integer userId, String grade) {
        this.id = id;
        this.lessonId = lessonId;
        this.userId = userId;
        this.grade = grade;
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

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", lessonId=" + lessonId +
                ", userId=" + userId +
                ", grade='" + grade + '\'' +
                '}';
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
        return Objects.equals(id, grade1.id) && Objects.equals(lessonId, grade1.lessonId) && Objects.equals(userId, grade1.userId) && Objects.equals(grade, grade1.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lessonId, userId, grade);
    }
}
