package com.qihang.librarymanage.jframe.admin;

import javax.swing.*;
import java.awt.*;

public class Category {
    private final Container CONTENTPANE;
    public Category(Container getContentPane) {
        this.CONTENTPANE = getContentPane;
    }

    public void add() {
        JLabel title = new JLabel("类别添加");
        title.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        title.setBounds(400, 30, 300, 32);
        CONTENTPANE.add(title);
        // 创建一个面板
        JPanel bookInfoJPanel = new JPanel();
        bookInfoJPanel.setBounds(40, 100, 900, 450);
        // 设置面板的边框
        bookInfoJPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f));
        bookInfoJPanel.setLayout(null);
        CONTENTPANE.add(bookInfoJPanel);

        // 类别名称
        JLabel categoryName = new JLabel("类别名称:");
        categoryName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        categoryName.setBounds(200, 50, 300, 40);
        bookInfoJPanel.add(categoryName);
        // 类别名称单行文本框
        JTextField categoryTextBox = new JTextField();
        categoryTextBox.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        categoryTextBox.setBounds(300, 50, 300, 40);
        bookInfoJPanel.add(categoryTextBox);
        // 类别描述
        JLabel categoryDescribe = new JLabel("类别描述:");
        categoryDescribe.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        categoryDescribe.setBounds(200, 130, 300, 40);
        bookInfoJPanel.add(categoryDescribe);
        // 类别描述多行文本框
        JTextArea categoryDescribeTextArea = new JTextArea();
        categoryDescribeTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        categoryDescribeTextArea.setLineWrap(true); // 在达到JTextArea的宽度时自动换行
        bookInfoJPanel.add(categoryDescribeTextArea);

        // 滚动面板
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(300, 130, 300, 120);
        // 禁用水平滚动条
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(categoryDescribeTextArea); // 将JTextArea添加到滚动面板中
        bookInfoJPanel.add(scrollPane);

        // 添加按钮
        JButton addJButton = new JButton("添加");
        addJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        addJButton.setBounds(330, 270, 80, 40);
        bookInfoJPanel.add(addJButton);

        // 重置按钮
        JButton resetJButton = new JButton("重置");
        resetJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        resetJButton.setBounds(490, 270, 80, 40);
        bookInfoJPanel.add(resetJButton);

    }

}
