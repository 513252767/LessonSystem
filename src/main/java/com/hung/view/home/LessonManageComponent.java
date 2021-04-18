package com.hung.view.home;

import com.hung.pojo.Account;
import com.hung.pojo.Lesson;
import com.hung.service.LessonService;
import com.hung.service.impl.LessonServiceImpl;
import com.hung.util.ListToVector;
import com.hung.util.orm.sqlsession.defaults.ServiceFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

/**
 * 课程管理部分
 * @author Hung
 */
public class LessonManageComponent extends Box {

    private JTable table;
    private Vector<String> title = new Vector<>();
    private Vector<Vector> data = new Vector<>();
    private DefaultTableModel tableModel;

    LessonService lessonService = new ServiceFactory<>(new LessonServiceImpl()).getService();

    public LessonManageComponent(Account account) {
        super(BoxLayout.Y_AXIS);
        Box box = Box.createVerticalBox();

        //组装评论区表格
        String[] titles = {" Id "," 课程 ", " 教室 ", " 最大人数 ", " 类型 "," "};
        for (String t : titles) {
            title.add(t);
        }

        List<Lesson> lessons = lessonService.queryAllLessonByTid(account.getId());
        Vector<Vector> vectors = ListToVector.lmListToVector(lessons);
        for(Vector vector:vectors){
            data.add(vector);
        }

        //初始化页面
        tableModel = new DefaultTableModel(data, title);
        //设置table
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //安排考试或记录成绩按钮
        table.getColumnModel().getColumn(5).setCellEditor(new MyRender());
        table.getColumnModel().getColumn(5).setCellRenderer(new MyRender());

        //组装视图
        box.add(new JScrollPane(table));
        this.add(box);
    }

    /**
     * 渲染器 编辑器
     */
    class MyRender extends AbstractCellEditor implements TableCellRenderer, ActionListener, TableCellEditor {

        private static final long serialVersionUID = 1L;
        private JButton button = null;

        public MyRender() {
            button = new JButton("安排考试、记录成绩、修改课程信息");
            button.addActionListener(this);
        }

        @Override
        public Object getCellEditorValue() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            // TODO Auto-generated method stub
            return button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            // TODO Auto-generated method stub
            return button;
        }
    }

}
