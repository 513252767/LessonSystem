package com.hung.view.home;

import com.hung.pojo.Account;
import com.hung.pojo.Lesson;
import com.hung.service.GradeService;
import com.hung.service.LessonService;
import com.hung.service.impl.GradeServiceImpl;
import com.hung.service.impl.LessonServiceImpl;
import com.hung.util.ListToVector;
import com.hung.util.aop.ServiceFactory;

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
 * @author Hung
 */
public class LessonChooseComponent extends Box {

    private JTable table;
    private Vector<String> title = new Vector<>();
    private Vector<Vector> data = new Vector<>();
    private DefaultTableModel tableModel;

    JFrame jf=null;
    Account account=null;

    LessonService lessonService = new ServiceFactory<>(new LessonServiceImpl()).getService();
    GradeService gradeService = new ServiceFactory<>(new GradeServiceImpl()).getService();

    public LessonChooseComponent(JFrame jf,Account account) {
        super(BoxLayout.Y_AXIS);
        Box box = Box.createVerticalBox();
        this.jf=jf;
        this.account=account;

        //组装选课表格
        String[] titles = {"课程Id"," 星期", " 节数 ", " 课程名称 "," 教师 "," 教室 ","  "};
        for (String t : titles) {
            title.add(t);
        }

        List<Lesson> lessons = lessonService.queryAllOptionalCourse(account.getId());
        Vector<Vector> vectors = ListToVector.lmListToVector3(lessons);
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
        //选择按钮
        table.getColumnModel().getColumn(6).setCellEditor(new MyRender());
        table.getColumnModel().getColumn(6).setCellRenderer(new MyRender());

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
            button = new JButton("安排考试、修改课程信息");
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
            //获取id
            Integer lessonId = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
            //查询相应课程
            Lesson lesson = lessonService.queryLessonById(lessonId);
            //查询学生已有课程
            List<String> lessonIds = gradeService.queryLessonByUserId(account.getId());
            //根据已选课程查看是否科室被占
            for (String id:lessonIds){
                Lesson lessonChoice = lessonService.queryLessonById(Integer.valueOf(id));
                if (!lesson.getWeek().equals(lessonChoice.getWeek()) && !lesson.getTurn().equals(lessonChoice.getTurn())){
                    //判断当前课的人数
                    Integer nums = gradeService.queryNumsByLessonId(lessonId);
                    if (Integer.parseInt(lesson.getNumber())>nums){
                        //可以选择
                        if (gradeService.chooseLesson(lessonId,account.getId())){
                            JOptionPane.showMessageDialog(jf,"选课成功!");
                        }else {
                            JOptionPane.showMessageDialog(jf,"某些特殊原因，你选课失败");
                        }
                    }else {
                        JOptionPane.showMessageDialog(jf,"你选择的课人数已满");
                    }
                }else {
                    JOptionPane.showMessageDialog(jf,"你所选课与已有课时间冲突");
                }
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            // TODO Auto-generated method stub
            return button;
        }
    }
}
