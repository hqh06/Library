package com.qihang.librarymanage.jframe.admin;

import com.qihang.librarymanage.utlis.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdminPage extends JFrame {
    Container container;
    public AdminPage(){
        new Theme(1);
        this.container=getContentPane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2; // 计算水平位置
        int y = (screenSize.height - getHeight()) / 2; // 计算垂直位置

        setTitle("管理员界面");
        setBounds(x - 500, y - 350, 1000, 700); // 让窗口在屏幕居中
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 设置点击X结束程序
        getContentPane().setLayout(null);

        // 创建下拉式菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu categoryMenu = new JMenu("类别管理");
        categoryMenu.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        menuBar.add(categoryMenu);

        JMenuItem categoryAdd = new JMenuItem("类别添加");
        categoryAdd.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        categoryMenu.add(categoryAdd);
        categoryAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Category(getContentPane()).add();
                repaint();// 刷新页面
            }
        });

        JMenuItem categoryModify = new JMenuItem("类别修改");
        categoryModify.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        categoryMenu.add(categoryModify);


        JMenu bookMenu = new JMenu("书籍管理");
        bookMenu.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        menuBar.add(bookMenu);

        JMenuItem bookAdd = new JMenuItem("书籍添加");
        bookAdd.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookMenu.add(bookAdd);

        JMenuItem bookModify = new JMenuItem("书籍修改");
        bookModify.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookMenu.add(bookModify);


        JMenu userMenu = new JMenu("用户管理");
        userMenu.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        menuBar.add(userMenu);

        JMenuItem userInfo = new JMenuItem("用户信息");
        userInfo.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userMenu.add(userInfo);

        JMenuItem userBorrowInfo = new JMenuItem("借阅信息");
        userBorrowInfo.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userMenu.add(userBorrowInfo);

        new Category(getContentPane()).add();

        setVisible(true);
    }

    public static void main(String[] args) {
        new AdminPage();
    }
}
