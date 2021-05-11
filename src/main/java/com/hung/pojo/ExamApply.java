package com.hung.pojo;

import java.util.Objects;

/**
 * @author Hung
 */
public class ExamApply {
    Integer id;
    Integer lessonId;
    String number;
    String examTime;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    @Override
    public String toString() {
        return "ExamApply{" +
                "id=" + id +
                ", lessonId=" + lessonId +
                ", number='" + number + '\'' +
                ", examTime='" + examTime + '\'' +
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
        ExamApply examApply = (ExamApply) o;
        return Objects.equals(id, examApply.id) && Objects.equals(lessonId, examApply.lessonId) && Objects.equals(number, examApply.number) && Objects.equals(examTime, examApply.examTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lessonId, number, examTime);
    }
}
