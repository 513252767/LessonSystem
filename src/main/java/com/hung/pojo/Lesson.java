package com.hung.pojo;

import java.util.Objects;

/**
 * 课程类
 * @author Hung
 */
public class Lesson {
    Integer id;
    String week;
    String turn;
    String name;
    String teacher;
    String number;
    String classroom;
    String category;

    public Lesson() {
    }

    public Lesson(Integer id, String week, String turn, String name, String teacher, String number, String classroom, String category) {
        this.id = id;
        this.week = week;
        this.turn = turn;
        this.name = name;
        this.teacher = teacher;
        this.number = number;
        this.classroom = classroom;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", week='" + week + '\'' +
                ", turn='" + turn + '\'' +
                ", name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", number='" + number + '\'' +
                ", classroom='" + classroom + '\'' +
                ", category='" + category + '\'' +
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
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id) && Objects.equals(week, lesson.week) && Objects.equals(turn, lesson.turn) && Objects.equals(name, lesson.name) && Objects.equals(teacher, lesson.teacher) && Objects.equals(number, lesson.number) && Objects.equals(classroom, lesson.classroom) && Objects.equals(category, lesson.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, week, turn, name, teacher, number, classroom, category);
    }
}
