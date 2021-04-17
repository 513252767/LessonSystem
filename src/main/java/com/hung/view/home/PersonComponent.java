package com.hung.view.home;

import com.hung.pojo.Account;
import com.hung.pojo.User;
import com.hung.service.impl.UserServiceImpl;
import com.hung.service.serviceImpl.AccountServiceImpl;
import com.hung.service.serviceImpl.UserServiceImpl;

import javax.swing.*;

/**
 * 个人信息组件
 *
 * @author Hung
 */
public class PersonComponent extends Box {

    JTextField nameField = new JTextField(15);
    JTextField passwordField = new JTextField(15);
    JTextField nickNameField = new JTextField(15);
    JTextField birthdayField = new JTextField(15);
    JTextField genderField = new JTextField(15);
    JTextField descField = new JTextField(15);

    public PersonComponent(Account account, JFrame jf) {
        super(BoxLayout.Y_AXIS);
        Box box = Box.createVerticalBox();

        //根据account查询user信息
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.queryUserById(account.getId());

        //定义临时变量，让文本回显
        String password = account.getPassword();
        String nickName = user.getNickName();
        String gender = user.getGender();
        String birthday = user.getBirthday();
        String desc = user.getDesc();

        //用户名框
        Box nameBox = Box.createHorizontalBox();
        JLabel nameLabel = new JLabel("你的用户名:");
        nameBox.add(nameLabel);
        nameField.setText(account.getName());
        nameField.setEditable(false);
        nameBox.add(nameField);
        box.add(nameBox);
        box.add(Box.createVerticalStrut(30));

        //创建密码框
        Box pBox = Box.createHorizontalBox();
        JLabel pLabel = new JLabel("你的密码:");
        pBox.add(pLabel);
        passwordField.setText(account.getPassword());
        pBox.add(passwordField);
        box.add(pBox);
        box.add(Box.createVerticalStrut(30));

        //创建昵称框
        Box nBox = Box.createHorizontalBox();
        JLabel nLabel = new JLabel("昵称:");
        nBox.add(nLabel);
        nickNameField.setText(user.getNickName());
        nickNameField.setEditable(false);
        nBox.add(nickNameField);
        box.add(nBox);
        box.add(Box.createVerticalStrut(30));

        //创建性别框
        Box gBox = Box.createHorizontalBox();
        JLabel gLabel = new JLabel("性别:");
        gBox.add(gLabel);
        genderField.setText(user.getGender());
        gBox.add(genderField);
        box.add(gBox);
        box.add(Box.createVerticalStrut(30));

        //创建生日框
        Box bBox = Box.createHorizontalBox();
        JLabel bLabel = new JLabel("生日:");
        bBox.add(bLabel);
        birthdayField.setText(user.getBirthday());
        bBox.add(birthdayField);
        box.add(bBox);
        box.add(Box.createVerticalStrut(30));

        //创建描述框
        Box dBox = Box.createHorizontalBox();
        JLabel dLabel = new JLabel("自我介绍:");
        dBox.add(dLabel);
        descField.setText(user.getDesc());
        dBox.add(descField);
        box.add(dBox);
        box.add(Box.createVerticalStrut(30));

        //创建提交、取消按钮,添加对应功能
        JPanel btnPanel = new JPanel();
        JButton submit = new JButton("提交");
        JButton cancel = new JButton("撤销修改");

        submit.addActionListener(actionEvent -> {
            AccountServiceImpl accountService = new AccountServiceImpl();
            UserServiceImpl userService1 = new UserServiceImpl();
            //封装account和user，更新信息
            Account accountRevise = new Account(account.getId(), nameField.getText().trim(), passwordField.getText().trim());
            User userRevise = new User(account.getId(), nickNameField.getText().trim(), genderField.getText().trim(), birthdayField.getText().trim(), descField.getText().trim());
            if (accountService.updateAccount(accountRevise) && userService1.updateUser(userRevise)) {
                JOptionPane.showMessageDialog(jf, "成功修改!");
            } else {
                JOptionPane.showMessageDialog(jf, "修改过程可能出了点问题哦。。。请尝试重新提交");
            }
        });

        cancel.addActionListener(actionEvent -> {
            int i = JOptionPane.showConfirmDialog(jf, "你确定要取消修改吗？");
            if (i == JOptionPane.YES_OPTION) {
                passwordField.setText(password);
                nickNameField.setText(nickName);
                genderField.setText(gender);
                birthdayField.setText(birthday);
                descField.setText(desc);
                JOptionPane.showMessageDialog(jf, "撤销修改成功!");
            }
        });

        btnPanel.add(submit);
        btnPanel.add(cancel);

        //组装视图
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(box);
        verticalBox.add(btnPanel);
        this.add(verticalBox);

    }
}
