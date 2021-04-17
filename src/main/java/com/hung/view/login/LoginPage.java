package com.hung.view.login;

import com.hung.pojo.Account;
import com.hung.service.AccountService;
import com.hung.service.impl.AccountServiceImpl;
import com.hung.util.SqlFilter;
import com.hung.util.orm.sqlsession.defaults.ServiceFactory;
import com.hung.util.validateCode.ValidateCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * @author Hung
 */
public class LoginPage {
    AccountService accountService = new ServiceFactory<>(new AccountServiceImpl()).getService();
    /**
     * 生成必要组件
     */
    JFrame jf = new JFrame("登录界面");
    JTextField nameField = new JTextField(15);
    JPasswordField passwordField = new JPasswordField(15);
    JTextField validateCodeLogin = new JTextField(15);


    /**
     * 获取验证码
     */
    ValidateCode vCode = new ValidateCode(160, 40, 5, 150);
    BufferedImage buffImg = vCode.getBuffImg();
    String code = vCode.getCode();
    ImageIcon validateCode = new ImageIcon(buffImg);

    final int WIDTH = 600;
    final int HEIGHT = 500;

    /**
     * 组装视图
     */
    public void init() {
        //jf.setBounds(1100,500,WIDTH,HEIGHT);
        jf.setSize(1000, 500);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);

        Box box = Box.createVerticalBox();

        box.add(new JLabel("登录界面"));

        //创建用户名框
        Box nameBox = Box.createHorizontalBox();
        JLabel nameLabel = new JLabel("用户名:");
        nameBox.add(nameLabel);
        nameBox.add(nameField);
        box.add(nameBox);
        box.add(Box.createVerticalStrut(30));

        //创建密码框
        Box pBox = Box.createHorizontalBox();
        JLabel pLabel = new JLabel("密码:");
        pBox.add(pLabel);
        pBox.add(passwordField);
        box.add(pBox);
        box.add(Box.createVerticalStrut(30));

        //创建验证码框
        Box vBox = Box.createHorizontalBox();
        JLabel vLabel = new JLabel("验证码:");
        vBox.add(vLabel);
        vBox.add(validateCodeLogin);
        box.add(vBox);
        box.add(Box.createVerticalStrut(30));

        //验证码图片
        JLabel jLabel = new JLabel();
        jLabel.setIcon(validateCode);
        jLabel.setToolTipText("点击刷新");
        jLabel.addMouseListener(new MouseAdapter() {
            //点击刷新
            @Override
            public void mouseClicked(MouseEvent e) {
                ValidateCode vNewCode = new ValidateCode(160, 40, 5, 150);
                code = vNewCode.getCode();
                jLabel.setIcon(new ImageIcon(vNewCode.getBuffImg()));
                jLabel.updateUI();
            }
        });
        box.add(jLabel);

        jf.add(box);

        //登录注册按钮
        JPanel jPanel = new JPanel();
        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");
        jPanel.add(loginButton);
        jPanel.add(registerButton);
        jf.add(jPanel, BorderLayout.SOUTH);

        //登录按钮功能
        loginButton.addActionListener(actionEvent -> {
            String name = nameField.getText().trim();
            String password = passwordField.getText().trim();

            if (!validateCodeLogin.getText().trim().equalsIgnoreCase(code)) {
                //验证码错误
                JOptionPane.showMessageDialog(jf, "验证码错误");
                return;
            }
            Account account = new Account();
            //判断账号密码是否为空
            if ("".equals(name) || "".equals(password)) {
                JOptionPane.showMessageDialog(jf, "账号或密码框为空");
                return;
            } else {
                //判断输入是否违规
                if (SqlFilter.sqlCheck(name)) {
                    JOptionPane.showMessageDialog(jf, "姓名含有违规字符，请重新输入");
                    return;
                } else if (SqlFilter.sqlCheck(password)) {
                    JOptionPane.showMessageDialog(jf, "密码含有违规字符，请重新输入");
                    return;
                } else {
                    account = accountService.loginAccount(new Account(name, password));
                }
            }

            if (account.getName() != null) {
                //登录成功
                JOptionPane.showMessageDialog(jf, "登录成功");
                new MainPage().init(account);
                jf.dispose();
            } else {
                //登录失败
                JOptionPane.showMessageDialog(jf, "用户名或密码错误");
            }
        });

        //注册按钮功能
        registerButton.addActionListener(actionEvent -> {
            new RegisterPage().init();
            jf.dispose();
        });

        jf.pack();
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginPage().init();
    }
}