package com.hung.view.home;

import com.hung.pojo.LessonTest;
import com.hung.service.LessonTestService;
import com.hung.service.impl.LessonTestServiceImpl;
import com.hung.util.aop.ServiceFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 考试安排对话框
 * @author Hung
 */
public class ExamArrangeDialog extends JDialog {

    LessonTestService lessonTestService = new ServiceFactory<>(new LessonTestServiceImpl()).getService();

    public ExamArrangeDialog(Integer lessonId,JFrame jf) {
        //建立必要部件
        Box box = Box.createHorizontalBox();
        JLabel label = new JLabel("安排你的考试时间");
        JTextArea textArea = new JTextArea(15, 10);
        box.add(label);
        box.add(textArea);

        JButton submit = new JButton("提交");

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String time = textArea.getText().trim();
                if (!"".equals(time)){
                    LessonTest lessonTest = new LessonTest(lessonId, time);
                    if(lessonTestService.addLessonTest(lessonTest)){
                        JOptionPane.showMessageDialog(jf,"提交成功");
                    }else {
                        JOptionPane.showMessageDialog(jf,"提交失败");
                    }
                }else {
                    JOptionPane.showMessageDialog(jf,"请输入考试时间");
                }
            }
        });
        box.add(submit);
        this.add(box);
    }
}
