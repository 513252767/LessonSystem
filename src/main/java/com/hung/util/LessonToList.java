package com.hung.util;

import com.hung.pojo.Lesson;
import com.hung.service.UserService;
import com.hung.util.spring.annotation.Autowired;
import com.hung.util.spring.annotation.Component;

import java.util.*;

/**
 * @author Hung
 */
@Component("lessonToList")
public class LessonToList {

    @Autowired
    static UserService userService;

    public static List<List<String>> toList(List<Lesson> list) {
        final int NUM = 6;
        List<List<String>> objects = new ArrayList<>();
        for (int j = 1; j < NUM; j++) {
            List<String> lessonList = new ArrayList<>();
            for (int k = 1; k < NUM; k++) {
                boolean flag=false;
                for (Lesson lesson : list) {
                    if (Integer.parseInt(lesson.getTurn()) == j && Integer.parseInt(lesson.getWeek()) == k) {
                        String teacher = userService.queryNickNameById(Integer.valueOf(lesson.getTeacher()));
                        lessonList.add(lesson.getName() + "@" + lesson.getClassroom() + teacher + "");
                        flag=true;
                        break;
                    }
                }
                if (!flag){
                    lessonList.add("");
                }
            }
            objects.add(lessonList);
        }
        return objects;
    }
}
