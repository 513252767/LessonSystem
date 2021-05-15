package com.hung.entity;

/**
 * @author Hung
 */
public class Exam {
    Integer lessonId;
    String lessonName;
    String testTime;

    public Exam(Integer lessonId, String lessonName, String testTime) {
        this.lessonId = lessonId;
        this.lessonName = lessonName;
        this.testTime = testTime;
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

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    @Override
    public String toString() {
        return "{\"" +
                "lessonId\":\"" + lessonId +
                "\", \"lessonName\":\"" + lessonName + '\"' +
                ", \"testTime\":\"" + testTime + '\"' +
                '}';
    }
}
