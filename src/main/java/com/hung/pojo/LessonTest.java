package com.hung.pojo;

import java.util.Objects;

/**
 * 考试课程类
 *
 * @author Hung
 */
public class LessonTest {
    Integer id;
    Integer lessonId;
    String number;
    String testTime;

    public LessonTest() {
    }

    public LessonTest(Integer lessonId, String testTime) {
        this.lessonId = lessonId;
        this.testTime = testTime;
    }

    public LessonTest(Integer lessonId, String number, String testTime) {
        this.lessonId = lessonId;
        this.number = number;
        this.testTime = testTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    @Override
    public String toString() {
        return "LessonTest{" +
                "id=" + id +
                ", lessonId=" + lessonId +
                ", testTime='" + testTime + '\'' +
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
        LessonTest that = (LessonTest) o;
        return Objects.equals(id, that.id) && Objects.equals(lessonId, that.lessonId) && Objects.equals(testTime, that.testTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lessonId, testTime);
    }
}
