package com.hung.view.home;

import com.hung.pojo.Account;
import com.hung.pojo.User;
import com.hung.service.AccountService;
import com.hung.service.UserService;
import com.hung.service.impl.AccountServiceImpl;
import com.hung.service.impl.UserServiceImpl;
import com.hung.util.orm.sqlsession.defaults.ServiceFactory;

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
    JTextField majorField=new JTextField(15);
    JTextField teamField = new JTextField(15);
    JTextField genderField = new JTextField(15);
    JTextField introductionField = new JTextField(15);

    AccountService accountService = new ServiceFactory<>(new AccountServiceImpl()).getService();
    UserService userService = new ServiceFactory<>(new UserServiceImpl()).getService();

    public PersonComponent(Account account, JFrame jf) {
        super(BoxLayout.Y_AXIS);
        Box box = Box.createVerticalBox();

        //根据account查询user信息
        User user = userService.queryUserById(account.getId());

        //定义临时变量，让文本回显
        String password = account.getPassword();
        String nickName = user.getName();
        String major = user.getMajor();
        String team = user.getTeam();
        String gender = user.getGender();
        String introduction = user.getIntroduction();

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
        nickNameField.setText(user.getName());
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

        //创建专业框
        Box bBox = Box.createHorizontalBox();
        JLabel bLabel = new JLabel("专业:");
        bBox.add(bLabel);
        majorField.setText(user.getMajor());
        bBox.add(majorField);
        box.add(bBox);
        box.add(Box.createVerticalStrut(30));

        //创建班级框
        Box tBox = Box.createHorizontalBox();
        JLabel tLabel = new JLabel("班级:");
        bBox.add(tLabel);
        teamField.setText(user.getTeam());
        bBox.add(teamField);
        box.add(tBox);
        box.add(Box.createVerticalStrut(30));

        //创建描述框
        Box dBox = Box.createHorizontalBox();
        JLabel dLabel = new JLabel("自我介绍:");
        dBox.add(dLabel);
        introductionField.setText(user.getIntroduction());
        dBox.add(introductionField);
        box.add(dBox);
        box.add(Box.createVerticalStrut(30));

        //创建提交、取消按钮,添加对应功能
        JPanel btnPanel = new JPanel();
        JButton submit = new JButton("提交");
        JButton cancel = new JButton("撤销修改");

        submit.addActionListener(actionEvent -> {
            //判空操作
            String passwordNow = passwordField.getText().trim();
            String nickNameNow = nickNameField.getText().trim();
            String genderNow = genderField.getText().trim();
            String teamNow = teamField.getText().trim();
            String majorNow = majorField.getText().trim();
            String introductionNow = introductionField.getText().trim();
            if (passwordNow.isEmpty() || nickNameNow.isEmpty() || genderNow.isEmpty() || teamNow.isEmpty() ||
                    majorNow.isEmpty() || introductionNow.isEmpty()){
                JOptionPane.showMessageDialog(jf,"请不要出现空行哦");
                return;
            }
            //封装account和user，更新信息
            Account accountRevise = new Account(account.getId(), nameField.getText().trim(),passwordNow);
            User userRevise = new User(nickNameNow,genderNow,teamNow, majorNow,introductionNow,account.getId());
            if (accountService.updateAccount(accountRevise) && userService.updateUser(userRevise)) {
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
                majorField.setText(major);
                teamField.setText(team);
                introductionField.setText(introduction);
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
