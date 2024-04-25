package com.qihang.librarymanage.jframe.admin;

import com.qihang.librarymanage.dao.BookTypeDao;
import com.qihang.librarymanage.utlis.DatabaseConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

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
        JTextField categoryJTextField = new JTextField();
        categoryJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        categoryJTextField.setBounds(300, 50, 300, 40);
        bookInfoJPanel.add(categoryJTextField);
        // 类别描述
        JLabel categoryDescribe = new JLabel("类别描述:");
        categoryDescribe.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        categoryDescribe.setBounds(200, 130, 300, 40);
        bookInfoJPanel.add(categoryDescribe);
        // 类别描述多行文本框
        JTextArea categoryDescribeJTextArea = new JTextArea();
        categoryDescribeJTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        categoryDescribeJTextArea.setLineWrap(true); // 在达到JTextArea的宽度时自动换行
        bookInfoJPanel.add(categoryDescribeJTextArea);

        // 滚动面板
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(300, 130, 300, 120);
        // 禁用水平滚动条
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(categoryDescribeJTextArea); // 将JTextArea添加到滚动面板中
        bookInfoJPanel.add(scrollPane);

        // 添加按钮
        JButton addJButton = new JButton("添加");
        addJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        addJButton.setBounds(330, 270, 80, 40);
        bookInfoJPanel.add(addJButton);
        addJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBookType(categoryJTextField, categoryDescribeJTextArea);
            }
        });

        // 重置按钮
        JButton resetJButton = new JButton("重置");
        resetJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        resetJButton.setBounds(490, 270, 80, 40);
        bookInfoJPanel.add(resetJButton);
        resetJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 清空类别名称和类别描述
                categoryJTextField.setText("");
                categoryDescribeJTextArea.setText("");
            }
        });

    }

    public void modify() {

    }

    /**
     * 添加图书类型
     *
     * @param categoryJTextField        类别名称文本框的对象
     * @param categoryDescribeJTextArea 类别描述文本框的对象
     */
    public void addBookType(JTextField categoryJTextField, JTextArea categoryDescribeJTextArea) {

        String category = categoryJTextField.getText();
        String categoryDescribe = categoryDescribeJTextArea.getText();
        // 去除前后空格为空
        if (category.trim().isEmpty() || categoryDescribe.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请输入相关信息");
            return;
        }
        DatabaseConnect databaseConnect = null;
        Connection connection = null;
        try {
            // 获取数据库连接
            databaseConnect = new DatabaseConnect();
            connection = databaseConnect.getConnection();
            // 调用dao包 添加图书类型
            BookTypeDao bookTypeDao = new BookTypeDao();
            int result = bookTypeDao.categoryAdd(connection, category, categoryDescribe);
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "添加成功");
                // 添加成功清空类别名称和类别描述
                categoryJTextField.setText("");
                categoryDescribeJTextArea.setText("");
            } else if (result == 2) {
                JOptionPane.showMessageDialog(null, "添加失败，类别重复");
            } else {
                JOptionPane.showMessageDialog(null, "添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "添加失败");
        } finally {
            try {
                databaseConnect.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
