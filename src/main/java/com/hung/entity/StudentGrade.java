package com.hung.entity;

/**
 * @author Hung
 */
public class StudentGrade {
    Integer lessonId;
    String lessonName;
    String studentGrade;

    public StudentGrade() {
    }

    public StudentGrade(Integer lessonId, String lessonName, String studentGrade) {
        this.lessonId = lessonId;
        this.lessonName = lessonName;
        this.studentGrade = studentGrade;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade;
    }

    @Override
    public String toString() {
        return "StudentGrade{" +
                "lessonId=" + lessonId +
                ", lessonName='" + lessonName + '\'' +
                ", studentGrade='" + studentGrade + '\'' +
                '}';
    }
}
