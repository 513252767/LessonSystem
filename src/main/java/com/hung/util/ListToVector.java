package com.hung.util;

import com.hung.pojo.Lesson;
import com.hung.service.UserService;
import com.hung.service.impl.UserServiceImpl;
import com.hung.util.orm.sqlsession.defaults.ServiceFactory;

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
        for (int i = 0; i < list.size(); i++) {
            Lesson lesson = list.get(i);

            for (int j = 1; j < NUM; j++) {
                Map<String, String> map = new LinkedHashMap();
                map.put("第一节课", "第"+j+"节课");
                for (int k = 1; k < NUM; k++) {
                    if (Integer.parseInt(lesson.getTurn()) == j && Integer.parseInt(lesson.getWeek()) == k) {
                        String s = userService.queryNickNameById(Integer.valueOf(lesson.getTeacher()));
                        map.put(j + k + "", lesson.getName() + lesson.getClassroom() + lesson.getTeacher() + s+"");
                    } else {
                        map.put(j + k + "", "");
                    }
                }
                Vector vector = new Vector();
                vector.addAll(map.values());
                objects.add(vector);
            }
        }
        return objects;
    }

    public  static  Vector<Vector> lmListToVector(List<Lesson> list){
        Vector<Vector> objects = new Vector<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = new LinkedHashMap<>();
            Lesson lesson = list.get(i);
            map.put("id",lesson.getId().toString());
            map.put("课程",lesson.getName());
            map.put("教师",lesson.getClassroom());
            map.put("最大人数",lesson.getNumber());
            if (lesson.getCategory()=="1"){
                map.put("类型","必修");
            }else {
                map.put("类型","选修");
            }
            Vector vector = new Vector();
            vector.addAll(map.values());
            objects.add(vector);
        }
        return objects;
    }

}
