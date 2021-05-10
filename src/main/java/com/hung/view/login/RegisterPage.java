package com.hung.view.login;

import com.hung.pojo.Account;
import com.hung.service.AccountService;
import com.hung.service.impl.AccountServiceImpl;
import com.hung.util.SqlFilter;
import com.hung.util.aop.ServiceFactory;
import com.hung.util.spring.annotation.Controller;
import com.hung.util.validateCode.ValidateCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * @author Hung
 */
@Controller
public class RegisterPage {

    JFrame jf = new JFrame("注册界面");
    JTextField nameField = new JTextField(15);
    JTextField passwordField = new JTextField(15);
    JTextField passwordConfirmField = new JTextField(15);
    JTextField validateCodeLogin = new JTextField(5);

    /**
     * 创建验证码图片
     */
    ValidateCode vCode = new ValidateCode(160, 40, 5, 150);
    BufferedImage buffImg = vCode.getBuffImg();
    String code = vCode.getCode();
    ImageIcon validateCode = new ImageIcon(buffImg);

    public void init() {
        jf.setSize(1000, 500);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);

        Box box = Box.createVerticalBox();
        box.add(new JLabel("注册界面"));

        //创建用户名框
        Box nameBox = Box.createHorizontalBox();
        JLabel nameLabel = new JLabel("你的用户名:");
        nameBox.add(nameLabel);
        nameBox.add(nameField);
        box.add(nameBox);
        box.add(Box.createVerticalStrut(20));

        //创建密码框
        Box pBox = Box.createHorizontalBox();
        JLabel pLabel = new JLabel("设置密码:");
        pBox.add(pLabel);
        pBox.add(passwordField);
        box.add(pBox);
        box.add(Box.createVerticalStrut(20));

        //创建确认密码框
        Box pfBox = Box.createHorizontalBox();
        JLabel pfLabel = new JLabel("重复密码:");
        pfBox.add(pfLabel);
        pfBox.add(passwordConfirmField);
        box.add(pfBox);
        box.add(Box.createVerticalStrut(20));

        //创建验证码框
        Box vBox = Box.createHorizontalBox();
        JLabel vLabel = new JLabel("验证码:");
        vBox.add(vLabel);
        vBox.add(validateCodeLogin);
        box.add(vBox);
        box.add(Box.createVerticalStrut(20));

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

        JPanel buttonPanel = new JPanel();
        JButton registerButton = new JButton("注册");
        JButton loginButton = new JButton("返回登录页面");
        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);

        //注册按钮功能
        registerButton.addActionListener(actionEvent -> {

            if (!validateCodeLogin.getText().trim().equalsIgnoreCase(code)) {
                //验证码错误
                JOptionPane.showMessageDialog(jf, "验证码错误");
                return;
            }

            if (!passwordField.getText().equals(passwordConfirmField.getText())) {
                //密码不一致
                JOptionPane.showMessageDialog(jf, "密码不一致");
                return;
            }

            String name = nameField.getText().trim();
            String password = passwordField.getText().trim();

            //判断账号密码是否为空
            if ("".equals(name) || "".equals(password)) {
                JOptionPane.showMessageDialog(jf, "账号或密码框为空");
                return;
            }

            //判断输入是否违规
            if (SqlFilter.sqlCheck(name)) {
                JOptionPane.showMessageDialog(jf, "姓名含有违规字符，请重新输入");
            } else if (SqlFilter.sqlCheck(password)) {
                JOptionPane.showMessageDialog(jf, "密码含有违规字符，请重新输入");
            } else {
                Account registerAccount = new Account(name, password);
                AccountService accountService = new ServiceFactory<>(new AccountServiceImpl()).getService();
                if (accountService.registerAccount(registerAccount)) {
                    //注册成功
                    JOptionPane.showMessageDialog(jf, "注册成功,返回登录页面");
                    new LoginPage().init();
                    jf.dispose();
                } else {
                    //注册失败
                    JOptionPane.showMessageDialog(jf, "注册失败");
                }
            }
        });

        //返回登录页面按钮
        loginButton.addActionListener(actionEvent -> {
            new LoginPage().init();
            jf.dispose();
        });

        jf.add(box);
        jf.add(buttonPanel, BorderLayout.SOUTH);
        jf.pack();
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterPage().init();
    }
}
