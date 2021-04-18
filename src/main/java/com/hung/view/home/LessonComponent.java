package com.hung.view.home;

import com.hung.pojo.Account;
import com.hung.pojo.Lesson;
import com.hung.service.LessonService;
import com.hung.service.impl.LessonServiceImpl;
import com.hung.util.ListToVector;
import com.hung.util.orm.sqlsession.defaults.ServiceFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * 课程表组件类
 * @author Hung
 */
public class LessonComponent extends Box {

    /**
     * title表格提示,data表格内容
     */
    private JTable table;
    private Vector<String> title = new Vector<>();
    private Vector<Vector> data = new Vector<>();
    private DefaultTableModel tableModel;

    LessonService lessonService = new ServiceFactory<>(new LessonServiceImpl()).getService();
    JFrame jf = null;

    public LessonComponent(JFrame jf, Account account,JSplitPane jSplitPane) {
        super(BoxLayout.Y_AXIS);
        this.jf = jf;
        //组装视图
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.white);
        btnPanel.setMaximumSize(new Dimension(850, 80));
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        //课程表上方查询栏
        JLabel searchLabel = new JLabel("搜索:");
        JTextField fuzzyQueryField = new JTextField(15);
        JButton searchBtn = new JButton("查询");
        btnPanel.add(searchLabel);
        btnPanel.add(fuzzyQueryField);
        btnPanel.add(searchBtn);

        //组装表格
        String[] titles = {"   ", " 星期一 ", " 星期二 ", " 星期三 ", " 星期四 ", " 星期五 "};
        for (String t : titles) {
            title.add(t);
        }

        //设置表格内容
        List<Lesson> lessons = lessonService.queryAllLesson(account.getId());
        Vector<Vector> vectors = ListToVector.lListToVector(lessons);
        data.clear();
        for(Vector vector:vectors){
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

        //组装视图
        Box box = Box.createVerticalBox();
        box.add(btnPanel);
        box.add(new JScrollPane(table));
        this.add(box);
    }

}
