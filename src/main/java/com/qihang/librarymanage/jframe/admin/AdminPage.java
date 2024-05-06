package com.qihang.librarymanage.jframe.admin;

import com.qihang.librarymanage.utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdminPage extends JFrame {
    Container container;

    public AdminPage() {
        new Theme(1);
        this.container = getContentPane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2; // 计算水平位置
        int y = (screenSize.height - getHeight()) / 2; // 计算垂直位置

        setTitle("管理员界面");
        setBounds(x - 650, y - 375, 1300, 750); // 让窗口在屏幕居中
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
                // 清除当前容器中所有的内容并刷新
                getContentPane().removeAll();
                getContentPane().revalidate();
                getContentPane().repaint();
                new Category(getContentPane()).add();
            }
        });

        JMenuItem categoryModify = new JMenuItem("类别更新");
        categoryModify.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        categoryMenu.add(categoryModify);
        categoryModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 清除当前容器中所有的内容并刷新
                getContentPane().removeAll();
                getContentPane().revalidate();
                getContentPane().repaint();
                new Category(getContentPane()).modify();
            }
        });


        JMenu bookMenu = new JMenu("书籍管理");
        bookMenu.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        menuBar.add(bookMenu);

        JMenuItem bookAdd = new JMenuItem("书籍添加");
        bookAdd.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookMenu.add(bookAdd);
        bookAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 清除当前容器中所有的内容并刷新
                getContentPane().removeAll();
                getContentPane().revalidate();
                getContentPane().repaint();
                new BookInfo(getContentPane()).add();
            }
        });

        JMenuItem bookModify = new JMenuItem("书籍更新");
        bookModify.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookMenu.add(bookModify);
        bookModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 清除当前容器中所有的内容并刷新
                getContentPane().removeAll();
                getContentPane().revalidate();
                getContentPane().repaint();
                new BookInfo(getContentPane()).modify();
            }
        });


        JMenu userMenu = new JMenu("用户管理");
        userMenu.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        menuBar.add(userMenu);

        JMenuItem userInfo = new JMenuItem("用户添加");
        userInfo.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userMenu.add(userInfo);
        userInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 清除当前容器中所有的内容并刷新
                getContentPane().removeAll();
                getContentPane().revalidate();
                getContentPane().repaint();
                new UserInfo(getContentPane()).add();
            }
        });

        JMenuItem userUpdate = new JMenuItem("用户更新");
        userUpdate.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userMenu.add(userUpdate);
        userUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 清除当前容器中所有的内容并刷新
                getContentPane().removeAll();
                getContentPane().revalidate();
                getContentPane().repaint();
                new UserInfo(getContentPane()).modify();
            }
        });

        new UserInfo(getContentPane()).add(); //测试

        setVisible(true);
    }

    public static void main(String[] args) {
        new AdminPage();
    }
}
