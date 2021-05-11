package com.hung.entity;

/**
 * @author Hung
 */
public class TeacherGrade {
    Integer studentId;
    String studentName;
    String teacherGrade;

    public TeacherGrade() {
    }

    public TeacherGrade(Integer studentId, String studentName, String teacherGrade) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.teacherGrade = teacherGrade;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTeacherGrade() {
        return teacherGrade;
    }

    public void setTeacherGrade(String teacherGrade) {
        this.teacherGrade = teacherGrade;
    }

    @Override
    public String toString() {
        return "TeacherGrade{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", teacherGrade='" + teacherGrade + '\'' +
                '}';
    }
}
