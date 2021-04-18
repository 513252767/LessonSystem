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
                        map.put(j + k + "", lesson.getName() + lesson.getClassroom() + lesson.getTeacher() + s);
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

}
