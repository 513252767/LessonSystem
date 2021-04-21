package com.hung.view.home;

import com.hung.pojo.LessonTest;
import com.hung.service.LessonTestService;
import com.hung.service.impl.LessonTestServiceImpl;
import com.hung.util.ListToVector;
import com.hung.util.orm.sqlsession.defaults.ServiceFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

/**
 * 查询考试
 * @author Hung
 */
public class ExamQuery extends Box {

    private JTable table;
    private Vector<String> title = new Vector<>();
    private Vector<Vector> data = new Vector<>();
    private DefaultTableModel tableModel;

    LessonTestService lessonTestService = new ServiceFactory<>(new LessonTestServiceImpl()).getService();

    public ExamQuery(Integer userId) {
        super(BoxLayout.Y_AXIS);
        Box box = Box.createVerticalBox();

        //组装考试表格
        String[] titles = {"课程Id "," 课程名称 "," 考试时间 "};
        for (String t : titles) {
            title.add(t);
        }

        //查询考试信息
        List<LessonTest> tests = lessonTestService.queryAllTest(userId);
        Vector<Vector> vectors = ListToVector.tListToVector(tests);
        data.clear();
        for (Vector vector : vectors) {
            data.add(vector);
        }

        //初始化页面
        tableModel = new DefaultTableModel(data, title);
        //设置table
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        box.add(new JScrollPane(table));
        this.add(box);
    }
}
