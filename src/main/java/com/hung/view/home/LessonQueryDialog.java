package com.hung.view.home;

import com.hung.pojo.Lesson;
import com.hung.service.LessonService;
import com.hung.service.impl.LessonServiceImpl;
import com.hung.util.ListToVector;
import com.hung.util.aop.ServiceFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

/**
 * @author Hung
 */
public class LessonQueryDialog extends JDialog {

    private JTable table;
    private Vector<String> title = new Vector<>();
    private Vector<Vector> data = new Vector<>();
    private DefaultTableModel tableModel;

    LessonService lessonService  = new ServiceFactory<>(new LessonServiceImpl()).getService();

    public LessonQueryDialog(String condition) {
        //建立必要部件
        Box box = Box.createHorizontalBox();

        //组装考试各人表格
        String[] titles = {"星期", " 节数 ", " 课程名称 "," 教师 "," 教室 "," 种类 "};
        for (String t : titles) {
            title.add(t);
        }

        List<Lesson> lessons = lessonService.queryLessonByCondition(condition);
        Vector<Vector> vectors = ListToVector.lmListToVector2(lessons);
        data.clear();
        for (Vector vector : vectors) {
            data.add(vector);
        }

        tableModel = new DefaultTableModel(data, title);
        //设置table
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        box.add(new JScrollPane(table));
        this.add(box);
    }
}
