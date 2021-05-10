package com.hung.util;

import com.hung.pojo.Lesson;
import com.hung.pojo.LessonTest;
import com.hung.pojo.User;
import com.hung.service.LessonService;
import com.hung.service.UserService;
import com.hung.service.impl.LessonServiceImpl;
import com.hung.service.impl.UserServiceImpl;
import com.hung.util.aop.ServiceFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author Hung
 */
public class ListToVector {

    /**
     * matter转换
     *
     * @param list
     * @return
     */
    public static Vector<Vector> lListToVector(List<Lesson> list) {
        final int NUM = 6;
        UserService userService = new ServiceFactory<>(new UserServiceImpl()).getService();
        Vector<Vector> objects = new Vector<>();

            for (int j = 1; j < NUM; j++) {
                Map<String, String> map = new LinkedHashMap();
                map.put("第一节课", "第" + j + "节课");
                for (int k = 1; k < NUM; k++) {
                    for (int i = 0; i < list.size(); i++) {
                        Lesson lesson = list.get(i);
                        if (Integer.parseInt(lesson.getTurn()) == j && Integer.parseInt(lesson.getWeek()) == k) {
                            String teacher = userService.queryNickNameById(Integer.valueOf(lesson.getTeacher()));
                            map.put(j + k + "", lesson.getName() + "@" + lesson.getClassroom() + teacher + "");
                            break;
                        } else {
                            map.put(j + k + "", "");
                        }
                    }
                }
                Vector vector = new Vector();
                vector.addAll(map.values());
                objects.add(vector);
            }

        return objects;
    }

    public static Vector<Vector> lmListToVector(List<Lesson> list) {
        Vector<Vector> objects = new Vector<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = new LinkedHashMap<>();
            Lesson lesson = list.get(i);
            map.put("id", lesson.getId().toString());
            map.put("课程", lesson.getName());
            map.put("教室", lesson.getClassroom());
            map.put("最大人数", lesson.getNumber());
            if (Integer.parseInt(lesson.getCategory()) == 1) {
                map.put("类型", "必修");
            } else {
                map.put("类型", "选修");
            }
            Vector vector = new Vector();
            vector.addAll(map.values());
            objects.add(vector);
        }
        return objects;
    }

    public static Vector<Vector> lmListToVector2(List<Lesson> list) {
        Vector<Vector> objects = new Vector<>();
        UserService userService = new ServiceFactory<>(new UserServiceImpl()).getService();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = new LinkedHashMap<>();
            Lesson lesson = list.get(i);
            map.put("星期",lesson.getWeek());
            map.put("节数",lesson.getTurn());
            map.put("课程", lesson.getName());
            String teacher = userService.queryNickNameById(Integer.valueOf(lesson.getTeacher()));
            map.put("教师",teacher);
            map.put("教室", lesson.getClassroom());
            if (Integer.parseInt(lesson.getCategory()) == 1) {
                map.put("类型", "必修");
            } else {
                map.put("类型", "选修");
            }
            Vector vector = new Vector();
            vector.addAll(map.values());
            objects.add(vector);
        }
        return objects;
    }

    public static Vector<Vector> lmListToVector3(List<Lesson> list) {
        Vector<Vector> objects = new Vector<>();
        UserService userService = new ServiceFactory<>(new UserServiceImpl()).getService();
        if (list!=null){
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = new LinkedHashMap<>();
                Lesson lesson = list.get(i);
                map.put("id",lesson.getId().toString());
                map.put("星期",lesson.getWeek());
                map.put("节数",lesson.getTurn());
                map.put("课程", lesson.getName());
                String teacher = userService.queryNickNameById(Integer.valueOf(lesson.getTeacher()));
                map.put("教师",teacher);
                map.put("教室", lesson.getClassroom());
                Vector vector = new Vector();
                vector.addAll(map.values());
                objects.add(vector);
            }
        }
        return objects;
    }

    public static Vector<Vector> uListToVector(List<User> list) {
        Vector<Vector> objects = new Vector<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = new LinkedHashMap<>();
            User user = list.get(i);
            map.put("id", user.getId().toString());
            map.put("name", user.getName());
            Vector vector = new Vector();
            vector.addAll(map.values());
            objects.add(vector);
        }
        return objects;
    }

    public static Vector<Vector> tListToVector(List<LessonTest> list) {
        Vector<Vector> objects = new Vector<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = new LinkedHashMap<>();
            LessonTest lessonTest = list.get(i);
            map.put("id", lessonTest.getLessonId().toString());
            LessonService lessonService = new ServiceFactory<>(new LessonServiceImpl()).getService();
            Lesson lesson = lessonService.queryLessonById(lessonTest.getLessonId());
            map.put("name", lesson.getName());
            map.put("time", lessonTest.getTestTime());
            Vector vector = new Vector();
            vector.addAll(map.values());
            objects.add(vector);
        }
        return objects;
    }
}
