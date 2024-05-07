package com.qihang.librarymanage.jframe.admin;

import com.qihang.librarymanage.dao.BookTypeDao;
import com.qihang.librarymanage.pojo.BookType;
import com.qihang.librarymanage.utils.DatabaseUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class Category {
    private final Container CONTENTPANE;
    private DefaultTableModel defaultTableModel;

    public Category(Container getContentPane) {
        this.CONTENTPANE = getContentPane;
    }

    /**
     * 初始化查询图书页面
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
        JLabel bookTypeName = new JLabel("类别名:");
        bookTypeName.setBounds(150, 50, 100, 40);
        bookTypeName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookTypeInfoJPanel.add(bookTypeName);

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
                // 实例化BookType对象并添加值
                BookType bookType = new BookType();
                bookType.setTypeName(searchJTextField.getText().trim());
                queryBookType(bookType);
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
        queryBookType(new BookType()); // 初始化表

        return bookTypeInfoTable;
    }

    /**
     * 初始化添加图书类型页面
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
        JLabel bookTypeName = new JLabel("类别名称:");
        bookTypeName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookTypeName.setBounds(30, 50, 100, 40);
        bookTypeInfoJPanel.add(bookTypeName);
        // 类别名称单行文本框
        JTextField bookTypeNameJTextField = new JTextField();
        bookTypeNameJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookTypeNameJTextField.setBounds(150, 50, 300, 40);
        bookTypeInfoJPanel.add(bookTypeNameJTextField);
        // 类别描述
        JLabel bookTypeDescribe = new JLabel("类别描述:");
        bookTypeDescribe.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookTypeDescribe.setBounds(30, 130, 100, 40);
        bookTypeInfoJPanel.add(bookTypeDescribe);
        // 类别描述多行文本框
        JTextArea bookTypeDescribeJTextArea = new JTextArea();
        bookTypeDescribeJTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookTypeDescribeJTextArea.setLineWrap(true); // 在达到JTextArea的宽度时自动换行
        bookTypeInfoJPanel.add(bookTypeDescribeJTextArea);

        // 滚动面板
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(150, 130, 300, 120);
        // 禁用水平滚动条
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(bookTypeDescribeJTextArea); // 将JTextArea添加到滚动面板中
        bookTypeInfoJPanel.add(scrollPane);

        // 添加按钮
        JButton addJButton = new JButton("添加");
        addJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        addJButton.setBounds(150, 270, 140, 40);
        bookTypeInfoJPanel.add(addJButton);
        // 添加按钮监听
        addJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 实例化图书类型对象并设置对象属性
                BookType bookType = new BookType();
                bookType.setTypeName(bookTypeNameJTextField.getText().trim());
                bookType.setTypeRemark(bookTypeDescribeJTextArea.getText().trim());
                addBookType(bookType);
            }
        });

        // 重置按钮
        JButton resetJButton = new JButton("重置");
        resetJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        resetJButton.setBounds(310, 270, 140, 40);
        bookTypeInfoJPanel.add(resetJButton);
        // 重置按钮监听
        resetJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 清空类别名称和类别描述
                bookTypeNameJTextField.setText("");
                bookTypeDescribeJTextArea.setText("");
            }
        });

    }


    /**
     * 初始化修改图书类型页面
     */
    public void modify() {
        JLabel title = new JLabel("类别更新");
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
                Object bookTypeID = bookTypeInfoTable.getValueAt(selectedRow, 0);
                Object booKTypeName = bookTypeInfoTable.getValueAt(selectedRow, 1);
                Object bookTypeRemark = bookTypeInfoTable.getValueAt(selectedRow, 2);
                // 将获取的值加入文本框
                bookTypeIDJTextField.setText(bookTypeID.toString());
                booKTypeNameJTextField.setText(booKTypeName.toString());
                bookTypeRemarkJTextArea.setText(bookTypeRemark.toString());
            }
        });

        JButton modifyJButton = new JButton("修改");
        modifyJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        modifyJButton.setBounds(110, 330, 300, 40);
        bookTypeInfoJPanel.add(modifyJButton);
        modifyJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 实例化图书类型对象并设置对象属性
                BookType bookType = new BookType();
                bookType.setId(Integer.valueOf(bookTypeIDJTextField.getText().trim()));
                bookType.setTypeName(booKTypeNameJTextField.getText().trim());
                bookType.setTypeRemark(bookTypeRemarkJTextArea.getText().trim());
                modifyBookType(bookType);
            }
        });


    }

    /**
     * 将数据库中查询的数据添加到查询图书页面
     *
     * @param bookType 图书类型对象
     */
    public void queryBookType(BookType bookType) {

        // 设置行数为0每次查询前清空表上一次的数据
        defaultTableModel.setRowCount(0);

        // 先查询所有的类别信息添加到表格中
        DatabaseUtils databaseUtils = new DatabaseUtils();
        Connection connection = null;
        try {
            connection = databaseUtils.getConnection();
            BookTypeDao bookTypeDao = new BookTypeDao();
            ArrayList<BookType> bookTypes = bookTypeDao.bookTypeQuery(connection, bookType);
            for (BookType type : bookTypes) {
                Vector<String> rowData = new Vector<>();
                rowData.add(String.valueOf(type.getId()));
                rowData.add(type.getTypeName());
                rowData.add(type.getTypeRemark());
                defaultTableModel.addRow(rowData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接
                databaseUtils.closeConnection(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将新的图书类别添加到数据库
     *
     * @param bookType 图书类型对象
     */
    public void addBookType(BookType bookType) {

        // 去除前后空格为空
        if (bookType.getTypeName().isEmpty() || bookType.getTypeRemark().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请输入相关信息");
            return;
        }
        DatabaseUtils databaseUtils = new DatabaseUtils();
        Connection connection = null;
        try {
            // 获取数据库连接
            connection = databaseUtils.getConnection();

            // 实例化BookTypeDao对象并调用添加方法
            BookTypeDao bookTypeDao = new BookTypeDao();
            int result = bookTypeDao.bookTypeAdd(connection, bookType);
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "添加成功");
                // 重新刷新表格
                queryBookType(new BookType());

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
                databaseUtils.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将文本框中的内容根据图书类型ID在数据库进行修改对应的值
     *
     * @param bookType 图书类型对象
     */
    public void modifyBookType(BookType bookType) {
        // 检查 booKTypeName, 和 bookTypeRemark 文本框是否为空
        if (bookType.getTypeName().isEmpty() || bookType.getTypeRemark().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请输入相关信息");
            return;
        }

        // 创建一个DatabaseConnect 对象
        DatabaseUtils databaseUtils = new DatabaseUtils();

        // 初始化一个 Connection 对象，用于存储数据库连接
        Connection connection = null;

        try {
            // 获取数据库连接
            connection = databaseUtils.getConnection();
            // 实例化BookTypeDao对象并调用修改方法
            BookTypeDao bookTypeDao = new BookTypeDao();
            int result = bookTypeDao.bookTypeModify(connection, bookType);
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "修改成功");
                // 修改成功后重新加载表
                queryBookType(new BookType());
            } else {
                JOptionPane.showMessageDialog(null, "修改失败");
            }

        } catch (Exception e) {
            // 如果获取数据库连接时出现异常，打印异常堆栈信息
            e.printStackTrace();
        } finally {
            try {
                // 在 finally 块中，无论是否出现异常，都尝试关闭数据库连接
                databaseUtils.closeConnection(connection);
            } catch (SQLException e) {
                // 如果关闭数据库连接时出现 SQLException，打印异常堆栈信息
                e.printStackTrace();
            }
        }

    }
}
