package com.hung.view.home;

import com.hung.pojo.Account;
import com.hung.pojo.Lesson;
import com.hung.service.LessonService;
import com.hung.service.UserService;
import com.hung.service.impl.LessonServiceImpl;
import com.hung.service.impl.UserServiceImpl;
import com.hung.util.orm.sqlsession.defaults.ServiceFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 安排考试和成绩添加类
 *
 * @author Hung
 */
public class LessonArrangeDialog extends JDialog {

    JTextField idField = new JTextField(15);
    JTextField nameField = new JTextField(30);
    JTextField numField = new JTextField(15);
    JTextField classroomField = new JTextField(15);
    JTextField teacherField = new JTextField(15);
    JTextField categoryField = new JTextField(15);
    JTextField turnField = new JTextField(15);
    JTextField weekField = new JTextField(15);

    LessonService lessonService = new ServiceFactory<>(new LessonServiceImpl()).getService();
    UserService userService = new ServiceFactory<>(new UserServiceImpl()).getService();

    public LessonArrangeDialog(JFrame jf, String title, boolean isModel, Account account, Integer lessonId) {
        super(jf, title, isModel);
        this.setBounds(760, 390, WIDTH, HEIGHT);

        //组装视图
        Box box = Box.createVerticalBox();

        //创建临时变量
        Lesson lesson = lessonService.queryLessonById(lessonId);
        Integer id = lesson.getId();
        String name = lesson.getName();
        String number = lesson.getNumber();
        String classroom = lesson.getClassroom();
        String t = lesson.getTeacher();
        String teacher = userService.queryNickNameById(Integer.parseInt(t));
        String category = lesson.getCategory();
        String turn = lesson.getTurn();
        String week = lesson.getWeek();

        //创建课程Id框
        Box vBox = Box.createHorizontalBox();
        JLabel vLabel = new JLabel("课程id:");
        vBox.add(vLabel);
        vBox.add(idField);
        idField.setText(id.toString());
        idField.setEditable(false);
        box.add(vBox);
        box.add(Box.createVerticalStrut(30));

        //创建课程名框
        Box nBox = Box.createHorizontalBox();
        JLabel nLabel = new JLabel("课程名：");
        nBox.add(nLabel);
        nBox.add(nameField);
        nameField.setText(name);
        box.add(nBox);
        box.add(Box.createVerticalStrut(30));

        //创建课程人数框
        Box numBox = Box.createHorizontalBox();
        JLabel numLabel = new JLabel("课程人数:");
        numBox.add(numLabel);
        numBox.add(numField);
        numField.setText(number);
        box.add(vBox);
        box.add(Box.createVerticalStrut(30));

        //创建课程教室框
        Box cBox = Box.createHorizontalBox();
        JLabel cLabel = new JLabel("教室:");
        cBox.add(cLabel);
        cBox.add(classroomField);
        classroomField.setText(classroom);
        box.add(cBox);
        box.add(Box.createVerticalStrut(30));

        //创建老师名字框
        Box tBox = Box.createHorizontalBox();
        JLabel tLabel = new JLabel("教师名:");
        tBox.add(tLabel);
        tBox.add(teacherField);
        teacherField.setText(teacher);
        teacherField.setEditable(false);
        box.add(tBox);
        box.add(Box.createVerticalStrut(30));

        //创建课程种类框
        Box cateBox = Box.createHorizontalBox();
        JLabel cateLabel = new JLabel("种类: 1为必修，2为选修");
        cateBox.add(cateLabel);
        cateBox.add(categoryField);
        categoryField.setText(category);
        box.add(cateBox);
        box.add(Box.createVerticalStrut(30));

        //创建节数框
        Box turnBox = Box.createHorizontalBox();
        JLabel turnLabel = new JLabel("第几节:");
        turnBox.add(turnLabel);
        turnBox.add(turnField);
        turnField.setText(turn);
        box.add(turnBox);
        box.add(Box.createVerticalStrut(30));

        //创建星期框
        Box wBox = Box.createHorizontalBox();
        JLabel wLabel = new JLabel("星期:");
        wBox.add(wLabel);
        wBox.add(weekField);
        weekField.setText(week);
        box.add(wBox);
        box.add(Box.createVerticalStrut(30));

        //设置底部按键框
        Box btnBox = Box.createHorizontalBox();
        JButton reviseBtn = new JButton("修改");
        reviseBtn.addActionListener(actionEvent -> {
            String nameNow = nameField.getText().trim();
            String numNow = numField.getText().trim();
            String classroomNow = classroomField.getText().trim();
            String categoryNow = categoryField.getText().trim();
            String turnNow = turnField.getText().trim();
            String weekNow = weekField.getText().trim();

            //执行必要判断
            try {
                if (Integer.parseInt(categoryNow) != 1 || (Integer.parseInt(categoryNow) != 2)) {
                    JOptionPane.showMessageDialog(jf, "课程种类设置有误，请重新设置");
                }
                if (Integer.parseInt(turnNow) < 1 || Integer.parseInt(turnNow) > 5) {
                    JOptionPane.showMessageDialog(jf, "节数设置有误，请重新设置");
                }
                if (Integer.parseInt(weekNow) < 1 || Integer.parseInt(weekNow) > 5) {
                    JOptionPane.showMessageDialog(jf, "星期设置有误，请重新设置");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(jf, "课程修改有误，请重新设置");
                return;
            }

            //无误，对课程执行更新
            Lesson lessonRevise = new Lesson(id, weekNow, turnNow, nameNow, teacher, numNow, classroomNow, categoryNow);
            if (lessonService.updateLesson(lessonRevise)) {
                JOptionPane.showMessageDialog(jf, "修改成功!");
            } else {
                JOptionPane.showMessageDialog(jf, "修改失败，请稍后再试");
            }

        });
        JButton cancelReviseBtn = new JButton("取消修改");
        cancelReviseBtn.addActionListener(actionEvent -> {
            int i = JOptionPane.showConfirmDialog(jf, "你确定要取消修改吗？");
            if (i == JOptionPane.YES_OPTION) {
                nameField.setText(name);
                numField.setText(number);
                classroomField.setText(classroom);
                teacherField.setText(teacher);
                categoryField.setText(category);
                turnField.setText(turn);
                weekField.setText(week);
                JOptionPane.showMessageDialog(jf, "撤销修改成功!");
            }
        });
        JButton arrangeExamBtn = new JButton("安排考试");
        arrangeExamBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new ExamArrangeDialog(lessonId,jf).show();
            }
        });
        btnBox.add(reviseBtn);
        btnBox.add(cancelReviseBtn);
        btnBox.add(arrangeExamBtn);
        box.add(btnBox);


    }
}
