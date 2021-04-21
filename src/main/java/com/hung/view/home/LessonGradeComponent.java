package com.hung.view.home;

import com.hung.pojo.User;
import com.hung.service.GradeService;
import com.hung.service.UserService;
import com.hung.service.impl.GradeServiceImpl;
import com.hung.service.impl.UserServiceImpl;
import com.hung.util.ListToVector;
import com.hung.util.aop.ServiceFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 课程评分
 * @author Hung
 */
public class LessonGradeComponent extends Box {

    private JTable table;
    private Vector<String> title = new Vector<>();
    private Vector<Vector> data = new Vector<>();
    private DefaultTableModel tableModel;

    JFrame jf=null;


    UserService userService = new ServiceFactory<>(new UserServiceImpl()).getService();
    GradeService gradeService = new ServiceFactory<>(new GradeServiceImpl()).getService();
    Integer lessonId;

    public LessonGradeComponent(JFrame jf,Integer lessonId) {

        super(BoxLayout.Y_AXIS);
        Box box = Box.createVerticalBox();
        this.jf=jf;
        this.lessonId=lessonId;

        //组装考试各人表格
        String[] titles = {" 学生id "," 姓名 "," 评分 "};
        for (String t : titles) {
            title.add(t);
        }

        //查询符合条件的学生id
        List<Integer> userId = gradeService.queryAllByLessonId(lessonId);
        //根据id查询相应名字
        List<User> users = new ArrayList<>();
        for (Integer id:userId){
            User user = new User();
            String name = userService.queryNickNameById(id);
            user.setId(id);
            user.setName(name);
            users.add(user);
        }

        Vector<Vector> vectors = ListToVector.uListToVector(users);
        data.clear();
        for (Vector vector : vectors) {
            data.add(vector);
        }

        //初始化页面
        tableModel = new DefaultTableModel(data, title);
        //设置table
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(2).setCellEditor(new MyRender());
        table.getColumnModel().getColumn(2).setCellRenderer(new MyRender());

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
            button = new JButton("确认分数");
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
            //获取学生userId
            Integer userId = Integer.parseInt( table.getValueAt(table.getSelectedRow(), 0).toString());
            String s = JOptionPane.showInputDialog("请输入成绩");
            //对s进行检验
            if (!"".equals(s)){
                try {
                    Integer grade = Integer.parseInt(s);
                    if (gradeService.addGrade(lessonId,userId,grade.toString())){
                        JOptionPane.showMessageDialog(jf,"成绩添加成功!");
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(jf,"分数格式有误,请重新输入");
                }
            }else {
                JOptionPane.showMessageDialog(jf,"输入为空，请重新输入");
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
