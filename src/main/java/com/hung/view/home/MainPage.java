package com.hung.view.home;

import com.hung.pojo.Account;
import com.hung.view.login.LoginPage;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主页面
 *
 * @author Hung
 */
public class MainPage {
    JFrame jf = new JFrame("课表系统");


    public void init(Account account) {
        jf.setTitle("欢迎来到课表系统,你好" + account.getName() + "!");
        jf.setSize(400, 600);
        jf.setLocationRelativeTo(null);
        //jf.setResizable(false);

        //设置菜单栏
        JMenuBar menuBar = new JMenuBar();
        JMenu setting = new JMenu("设置");
        JMenuItem exchangeAccount = new JMenuItem("切换账号");
        JMenuItem exit = new JMenuItem("退出程序");

        //切换账号添加功能
        exchangeAccount.addActionListener(actionEvent -> {
            new LoginPage().init();
            jf.dispose();
        });

        //退出程序添加功能
        exit.addActionListener(actionEvent -> System.exit(0));

        setting.add(exchangeAccount);
        setting.add(exit);
        menuBar.add(setting);
        jf.setJMenuBar(menuBar);

        //设置分割面板
        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setDividerLocation(150);
        splitPane.setDividerSize(7);

        //设置左侧树
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("课表系统");
        DefaultMutableTreeNode part = new DefaultMutableTreeNode("课程表");
        DefaultMutableTreeNode userManage = new DefaultMutableTreeNode("个人设置");
        DefaultMutableTreeNode lessonManage = new DefaultMutableTreeNode("课程管理");
        DefaultMutableTreeNode examQuery = new DefaultMutableTreeNode("查询考试");
        DefaultMutableTreeNode examManage = new DefaultMutableTreeNode("考试管理");

        //添加结点
        root.add(userManage);

        if (account.getRoleId() == 1) {
            //学生特有
            root.add(part);
            root.add(examQuery);
        }else {
            //教师特有
            root.add(lessonManage);
            root.add(examManage);
        }

        JTree jTree = new JTree(root);
        jTree.setSelectionRow(2);
        splitPane.setRightComponent(new PersonComponent(account, jf));

        jTree.addTreeSelectionListener(selectionEvent -> {
            //得到结点对象
            Object lastPathComponent = selectionEvent.getNewLeadSelectionPath().getLastPathComponent();

            if (lastPathComponent.equals(part)) {
                splitPane.setRightComponent(new PartComponent(option, account, jf, splitPane));
                splitPane.setDividerLocation(150);
            } else if (lastPathComponent.equals(userManage)) {
                splitPane.setRightComponent(new PersonComponent(account, jf));
                splitPane.setDividerLocation(150);
            } else if (lastPathComponent.equals(lessonManage)) {
                splitPane.setRightComponent(new FavoriteComponent(account));
                splitPane.setDividerLocation(150);
            } else if (lastPathComponent.equals(examQuery)) {
                splitPane.setRightComponent(new PraiseComponent(account));
                splitPane.setDividerLocation(150);
            } else if (lastPathComponent.equals(examManage)) {
                splitPane.setRightComponent(new ReportComponent());
            }
        });

        splitPane.setLeftComponent(jTree);

        jf.add(new JScrollPane(splitPane));

        jf.pack();
        jf.setVisible(true);
    }

}
