package com.hung.view.home;

import javax.swing.*;

/**
 * 考试安排对话框
 * @author Hung
 */
public class ExamArrangeDialog extends JDialog {


    public ExamArrangeDialog(Integer lessonId) {

        Box box = Box.createHorizontalBox();
        JLabel label = new JLabel("安排你的考试时间");
        JTextArea textArea = new JTextArea(15, 10);
        box.add(label);
        box.add(textArea);


    }
}
