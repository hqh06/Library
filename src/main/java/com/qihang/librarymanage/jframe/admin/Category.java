package com.qihang.librarymanage.jframe.admin;

import com.qihang.librarymanage.dao.BookTypeDao;
import com.qihang.librarymanage.pojo.BookType;
import com.qihang.librarymanage.utlis.DatabaseConnect;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Category {
    private final Container CONTENTPANE;
    private DefaultTableModel defaultTableModel;

    public Category(Container getContentPane) {
        this.CONTENTPANE = getContentPane;
    }

    /**
     * 将图书类型添加到表格
     *
     * @return 表对象
     */
    public JTable query() {
        // 创建一个面板
        JPanel bookTypeInfoJPanel = new JPanel();
        bookTypeInfoJPanel.setBounds(40, 100, 700, 500);
        // 设置面板的边框
        bookTypeInfoJPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f));
        bookTypeInfoJPanel.setLayout(null);
        CONTENTPANE.add(bookTypeInfoJPanel);

        // 类别名
        JLabel categoryName = new JLabel("类别名:");
        categoryName.setBounds(150, 50, 100, 40);
        categoryName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookTypeInfoJPanel.add(categoryName);

        // 创建一个搜索框
        JTextField searchJTextField = new JTextField();
        searchJTextField.setBounds(230, 50, 200, 40);
        searchJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookTypeInfoJPanel.add(searchJTextField);

        // 创建一个搜索按钮
        JButton searchJButton = new JButton("搜索");
        searchJButton.setBounds(450, 50, 80, 40);
        searchJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookTypeInfoJPanel.add(searchJButton);
        searchJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryBookType(searchJTextField);
            }
        });

        // 创建一个滚动面板
        JScrollPane bookInfoJScrollPane = new JScrollPane();
        bookInfoJScrollPane.setBounds(25, 120, 650, 330);
        bookTypeInfoJPanel.add(bookInfoJScrollPane); // 将滚动面板添加到JPanel面板中


        // 创建一个表
        JTable bookTypeInfoTable = new JTable() {
            // 重写isCellEditable方法让表格不能修改只能选中
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // 做一个表头栏数据
        String[] tableTitle = {"编号", "类别名称", "类别描述"};
        String[][] date = {}; // 具体的各栏行记录 先用空的二位数组占位
        defaultTableModel = new DefaultTableModel(date, tableTitle);
        bookTypeInfoTable.setModel(defaultTableModel);

        bookTypeInfoTable.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 20)); // 设置表头字体
        bookTypeInfoTable.getTableHeader().setReorderingAllowed(false); // 禁止拖动表的列
        bookTypeInfoTable.setFont(new Font("微软雅黑", Font.PLAIN, 17));
        bookTypeInfoTable.setRowHeight(40); // 设置行高
        bookTypeInfoTable.getColumnModel().getColumn(0).setMaxWidth(100);

        bookTypeInfoTable.setShowHorizontalLines(true); // 设置水平网格线 第三方主题默认没有
        bookTypeInfoTable.setIntercellSpacing(new Dimension(0, 0)); // 移除单元格间隙

        // 设置表中文字居中
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        bookTypeInfoTable.setDefaultRenderer(Object.class, renderer);
        // 将表添加到滚动面板中
        bookInfoJScrollPane.setViewportView(bookTypeInfoTable);
        queryBookType(null); // 初始化表

        return bookTypeInfoTable;
    }

    /**
     * 添加图书类型
     */

    public void add() {
        JLabel title = new JLabel("类别添加");
        title.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        title.setBounds(600, 30, 300, 32);
        CONTENTPANE.add(title);
        query(); // 加载查询到的图书

        // 创建一个面板
        JPanel bookTypeInfoJPanel = new JPanel();
        bookTypeInfoJPanel.setBounds(760, 100, 490, 500);
        // 设置面板的边框
        bookTypeInfoJPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f));
        bookTypeInfoJPanel.setLayout(null);
        CONTENTPANE.add(bookTypeInfoJPanel);

        // 类别名称
        JLabel categoryName = new JLabel("类别名称:");
        categoryName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        categoryName.setBounds(30, 50, 100, 40);
        bookTypeInfoJPanel.add(categoryName);
        // 类别名称单行文本框
        JTextField categoryJTextField = new JTextField();
        categoryJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        categoryJTextField.setBounds(150, 50, 300, 40);
        bookTypeInfoJPanel.add(categoryJTextField);
        // 类别描述
        JLabel categoryDescribe = new JLabel("类别描述:");
        categoryDescribe.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        categoryDescribe.setBounds(30, 130, 100, 40);
        bookTypeInfoJPanel.add(categoryDescribe);
        // 类别描述多行文本框
        JTextArea categoryDescribeJTextArea = new JTextArea();
        categoryDescribeJTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        categoryDescribeJTextArea.setLineWrap(true); // 在达到JTextArea的宽度时自动换行
        bookTypeInfoJPanel.add(categoryDescribeJTextArea);

        // 滚动面板
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(150, 130, 300, 120);
        // 禁用水平滚动条
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(categoryDescribeJTextArea); // 将JTextArea添加到滚动面板中
        bookTypeInfoJPanel.add(scrollPane);

        // 添加按钮
        JButton addJButton = new JButton("添加");
        addJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        addJButton.setBounds(150, 270, 300, 40);
        bookTypeInfoJPanel.add(addJButton);
        // 添加按钮监听
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
        bookTypeInfoJPanel.add(resetJButton);
        // 重置按钮监听
        resetJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 清空类别名称和类别描述
                categoryJTextField.setText("");
                categoryDescribeJTextArea.setText("");
            }
        });

    }


    /**
     * 修改图书类型
     */
    public void modify() {
        JLabel title = new JLabel("类别修改");
        title.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        title.setBounds(600, 30, 300, 32);
        CONTENTPANE.add(title);

        JTable bookTypeInfoTable = query(); // 加载查询到的图书

        // 创建一个面板
        JPanel bookTypeInfoJPanel = new JPanel();
        bookTypeInfoJPanel.setBounds(760, 100, 490, 500);
        // 设置面板的边框
        bookTypeInfoJPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f));
        bookTypeInfoJPanel.setLayout(null);
        CONTENTPANE.add(bookTypeInfoJPanel);


        // 编号
        JLabel bookTypeID = new JLabel("编号:");
        bookTypeID.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        bookTypeID.setBounds(30, 30, 80, 40);
        bookTypeInfoJPanel.add(bookTypeID);
        // 编号文本框
        JTextField bookTypeIDJTextField = new JTextField();
        bookTypeIDJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        bookTypeIDJTextField.setBounds(110, 30, 300, 40);
        bookTypeIDJTextField.setEditable(false); // 禁止输入
        bookTypeInfoJPanel.add(bookTypeIDJTextField);

        // 类型名
        JLabel bookTypeName = new JLabel("类别名:");
        bookTypeName.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        bookTypeName.setBounds(30, 110, 80, 40);
        bookTypeInfoJPanel.add(bookTypeName);
        // 类别名文本框
        JTextField booKTypeNameJTextField = new JTextField();
        booKTypeNameJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        booKTypeNameJTextField.setBounds(110, 110, 300, 40);
        bookTypeInfoJPanel.add(booKTypeNameJTextField);

        // 类别描述
        JLabel bookTypeRemark = new JLabel("状态:");
        bookTypeRemark.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        bookTypeRemark.setBounds(30, 190, 80, 40);
        bookTypeInfoJPanel.add(bookTypeRemark);

        // 类别描述多行文本框
        JTextArea bookTypeRemarkJTextArea = new JTextArea();
        bookTypeRemarkJTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookTypeRemarkJTextArea.setLineWrap(true); // 在达到JTextArea的宽度时自动换行
        bookTypeInfoJPanel.add(bookTypeRemarkJTextArea);

        // 滚动面板
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(110, 190, 300, 120);
        // 禁用水平滚动条
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(bookTypeRemarkJTextArea); // 将JTextArea添加到滚动面板中
        bookTypeInfoJPanel.add(scrollPane);


        // 给表格添加一个鼠标监听器
        bookTypeInfoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // 获取鼠标点击的行
                int selectedRow = bookTypeInfoTable.getSelectedRow();
                // 根据行和列获取表格中的值
                Object bookId = bookTypeInfoTable.getValueAt(selectedRow, 0);
                Object bookName = bookTypeInfoTable.getValueAt(selectedRow, 1);
                Object bookStatus = bookTypeInfoTable.getValueAt(selectedRow, 2);
                // 将获取的值加入文本框
                bookTypeIDJTextField.setText(bookId.toString());
                booKTypeNameJTextField.setText(bookName.toString());
                bookTypeRemarkJTextArea.setText(bookStatus.toString());
            }
        });

        JButton modifyJButton = new JButton("修改");
        modifyJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        modifyJButton.setBounds(110, 330, 300, 40);
        bookTypeInfoJPanel.add(modifyJButton);
        modifyJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyBookType(bookTypeIDJTextField, booKTypeNameJTextField, bookTypeRemarkJTextArea);
            }
        });


    }

    /**
     * 在数据库中查询图书类型
     *
     * @param searchJTextField 搜索框对象
     */
    public void queryBookType(JTextField searchJTextField) {
        BookType bookType = new BookType();
        // 不为空时设置
        if (searchJTextField != null) {
            bookType.setTypeName(searchJTextField.getText().trim());
        }

        // 设置行数为0每次查询前清空表上一次的数据
        defaultTableModel.setRowCount(0);

        // 先查询所有的类别信息添加到表格中
        DatabaseConnect databaseConnect = new DatabaseConnect();
        Connection connection = null;
        try {
            connection = databaseConnect.getConnection();
            BookTypeDao bookTypeDao = new BookTypeDao();
            // 调用dao包 查询图书类型
            ResultSet resultSet = bookTypeDao.categoryQuery(connection, bookType);
            while (resultSet.next()) {
                Vector<String> rowData = new Vector<>();
                rowData.add(String.valueOf(resultSet.getInt("id")));
                rowData.add(resultSet.getString("type_name"));
                rowData.add(resultSet.getString("type_remark"));
                defaultTableModel.addRow(rowData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接
                databaseConnect.closeConnection(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    /**
     * 将文本框中的内容添加到数据库
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
        DatabaseConnect databaseConnect = new DatabaseConnect();
        Connection connection = null;
        try {
            // 获取数据库连接
            connection = databaseConnect.getConnection();
            // 调用dao包 添加图书类型
            BookTypeDao bookTypeDao = new BookTypeDao();
            int result = bookTypeDao.categoryAdd(connection, category, categoryDescribe);
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "添加成功");
                // 添加成功清空类别名称和类别描述
                categoryJTextField.setText("");
                categoryDescribeJTextArea.setText("");
                // 重新刷新表格
                queryBookType(null);

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

    public void modifyBookType(JTextField bookTypeID, JTextField booKTypeName, JTextArea bookTypeRemarkJTextArea) {
        // 检查 bookTypeID, booKTypeName, 和 bookTypeRemark 文本框是否为空
        if (bookTypeID.getText().isEmpty() || booKTypeName.getText().isEmpty() || bookTypeRemarkJTextArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请输入相关信息");
            return;
        }
        BookType bookType = new BookType();
        bookType.setId(Integer.valueOf(bookTypeID.getText().trim()));
        bookType.setTypeName(booKTypeName.getText().trim());
        bookType.setTypeRemark(bookTypeRemarkJTextArea.getText().trim());

        // 创建一个DatabaseConnect 对象
        DatabaseConnect databaseConnect = new DatabaseConnect();

        // 初始化一个 Connection 对象，用于存储数据库连接
        Connection connection = null;

        try {
            // 获取数据库连接
            connection = databaseConnect.getConnection();

            BookTypeDao bookTypeDao = new BookTypeDao();
            int result = bookTypeDao.categoryModify(connection, bookType);
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "修改成功");
                // 修改成功后重新加载表
                queryBookType(null);
            } else {
                JOptionPane.showMessageDialog(null, "修改失败");
            }

        } catch (Exception e) {
            // 如果获取数据库连接时出现异常，打印异常堆栈信息
            e.printStackTrace();
        } finally {
            try {
                // 在 finally 块中，无论是否出现异常，都尝试关闭数据库连接
                databaseConnect.closeConnection(connection);
            } catch (SQLException e) {
                // 如果关闭数据库连接时出现 SQLException，打印异常堆栈信息
                e.printStackTrace();
            }
        }

    }
}
