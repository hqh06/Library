package com.qihang.librarymanage.jframe.admin;

import com.qihang.librarymanage.dao.BookTypeDao;
import com.qihang.librarymanage.pojo.Book;
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
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BookInfo {
    private final Container CONTENTPANE;
    private DefaultTableModel defaultTableModel;

    public BookInfo(Container getContentPane) {
        this.CONTENTPANE = getContentPane;
    }

    /**
     * 初始化查询图书页面
     *
     * @return 表对象
     */
    public JTable query() {
        // 创建一个面板
        JPanel bookInfoJPanel = new JPanel();
        bookInfoJPanel.setBounds(40, 100, 760, 500);
        // 设置面板的边框
        bookInfoJPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f));
        bookInfoJPanel.setLayout(null);
        CONTENTPANE.add(bookInfoJPanel);

        // 图书名
        JLabel bookName = new JLabel("书名:");
        bookName.setBounds(150, 50, 100, 40);
        bookName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookInfoJPanel.add(bookName);

        // 创建一个搜索框
        JTextField searchJTextField = new JTextField();
        searchJTextField.setBounds(230, 50, 200, 40);
        searchJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookInfoJPanel.add(searchJTextField);

        // 创建一个搜索按钮
        JButton searchJButton = new JButton("搜索");
        searchJButton.setBounds(450, 50, 80, 40);
        searchJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookInfoJPanel.add(searchJButton);
        searchJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                queryBookType(searchJTextField);
            }
        });

        // 创建一个滚动面板
        JScrollPane bookInfoJScrollPane = new JScrollPane();
        bookInfoJScrollPane.setBounds(25, 120, 710, 330);
        bookInfoJPanel.add(bookInfoJScrollPane); // 将滚动面板添加到JPanel面板中


        // 创建一个表
        JTable bookTypeInfoTable = new JTable() {
            // 重写isCellEditable方法让表格不能修改只能选中
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // 做一个表头栏数据
        String[] tableTitle = {"编号", "书名", "作者", "出版", "类型", "库存", "描述"};
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
//        queryBookType(null); // 初始化表

        return bookTypeInfoTable;
    }

    /**
     * 初始化添加图书页面
     */
    public void add() {
        JLabel title = new JLabel("图书添加");
        title.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        title.setBounds(600, 30, 300, 32);
        CONTENTPANE.add(title);
        query(); // 加载查询到的图书

        // 创建一个面板
        JPanel bookInfoJPanel = new JPanel();
        bookInfoJPanel.setBounds(820, 100, 430, 500);
        // 设置面板的边框
        bookInfoJPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f));
        bookInfoJPanel.setLayout(null);
        CONTENTPANE.add(bookInfoJPanel);

        // 书名
        JLabel bookName = new JLabel("书名:");
        bookName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookName.setBounds(30, 30, 50, 40);
        bookInfoJPanel.add(bookName);
        // 书名单行文本框
        JTextField bookNameJTextField = new JTextField();
        bookNameJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookNameJTextField.setBounds(100, 30, 300, 40);
        bookInfoJPanel.add(bookNameJTextField);

        // 作者
        JLabel author = new JLabel("作者:");
        author.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        author.setBounds(30, 90, 50, 40);
        bookInfoJPanel.add(author);
        // 作者单行文本框
        JTextField authorJTextField = new JTextField();
        authorJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        authorJTextField.setBounds(100, 90, 300, 40);
        bookInfoJPanel.add(authorJTextField);

        // 出版社
        JLabel publish = new JLabel("出版:");
        publish.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        publish.setBounds(30, 150, 50, 40);
        bookInfoJPanel.add(publish);
        // 出版社单行文本框
        JTextField publishJTextField = new JTextField();
        publishJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        publishJTextField.setBounds(100, 150, 300, 40);
        bookInfoJPanel.add(publishJTextField);

        // 图书类型
        JLabel bookType = new JLabel("类型:");
        bookType.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookType.setBounds(30, 210, 50, 40);
        bookInfoJPanel.add(bookType);
        // 图书类型下拉框
        JComboBox<BookType> bookTypeComboBox = new JComboBox<>();
        bookTypeComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookTypeComboBox.setBounds(100, 210, 150, 40);
        showBookType(bookTypeComboBox);
        bookInfoJPanel.add(bookTypeComboBox);

        // 库存
        JLabel bookNumber = new JLabel("库存:");
        bookNumber.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookNumber.setBounds(260, 210, 60, 40);
        bookInfoJPanel.add(bookNumber);
        // 库存单行文本框
        JTextField bookNumberJTextField = new JTextField();
        bookNumberJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookNumberJTextField.setBounds(330, 210, 70, 40);
        bookInfoJPanel.add(bookNumberJTextField);

        // 描述
        JLabel bookRemark = new JLabel("描述:");
        bookRemark.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookRemark.setBounds(30, 270, 50, 40);
        bookInfoJPanel.add(bookRemark);

        // 描述多行文本框
        JTextArea bookRemarkJTextArea = new JTextArea();
        bookRemarkJTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookRemarkJTextArea.setLineWrap(true); // 在达到JTextArea的宽度时自动换行
        bookInfoJPanel.add(bookRemarkJTextArea);

        // 滚动面板
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(100, 270, 300, 120);
        // 禁用水平滚动条
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(bookRemarkJTextArea); // 将JTextArea添加到滚动面板中
        bookInfoJPanel.add(scrollPane);


        // 添加按钮
        JButton addJButton = new JButton("添加");
        addJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        addJButton.setBounds(100, 420, 140, 40);
        bookInfoJPanel.add(addJButton);
        // 添加按钮监听
        addJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bookNameJTextField.getText().trim().isEmpty() ||
                        authorJTextField.getText().trim().isEmpty() ||
                        publishJTextField.getText().trim().isEmpty() ||
                        bookNumberJTextField.getText().trim().isEmpty() ||
                        bookRemarkJTextArea.getText().trim().isEmpty()
                ) {
                    JOptionPane.showMessageDialog(null, "请输入相关信息");
                    return;
                }
                // 实例化图书对象并添加值
                Book book = new Book();
                // 图书名
                book.setBookName(bookNameJTextField.getText().trim());
                // 作者
                book.setAuthor(authorJTextField.getText().trim());
                // 出版社
                book.setPublish(publishJTextField.getText().trim());
                // 将下拉框中选中的项转换为BookType类型，并赋值给selectedItem变量
                BookType selectedItem = (BookType) bookTypeComboBox.getSelectedItem();
                if (selectedItem != null) {
                    // 图书类型
                    book.setTypeId(selectedItem.getId());
                }
                // 其中的正则表达式^[1-9][0-9]*$表示一个或多个数字，但是不能以零开头
                if (!Pattern.matches("^[1-9][0-9]*$", bookNumberJTextField.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "请输入正确的库存");
                    return;
                }
                // 库存
                book.setNumber(Integer.valueOf(bookNumberJTextField.getText().trim()));
                // 描述
                book.setBookRemark(bookRemarkJTextArea.getText().trim());


//                addBookType(categoryJTextField, categoryDescribeJTextArea);
            }
        });

        // 重置按钮
        JButton resetJButton = new JButton("重置");
        resetJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        resetJButton.setBounds(260, 420, 140, 40);
        bookInfoJPanel.add(resetJButton);
        // 重置按钮监听
        resetJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 清空文本框中的内容
                bookNameJTextField.setText("");
                authorJTextField.setText("");
                bookNumberJTextField.setText("");
                publishJTextField.setText("");
                bookRemarkJTextArea.setText("");
            }
        });

    }

    /**
     * 初始化更新图书页面
     */
    public void modify() {
        JLabel title = new JLabel("图书更新");
        title.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        title.setBounds(600, 30, 300, 32);
        CONTENTPANE.add(title);

        JTable bookInfoTable = query(); // 加载查询到的图书

        // 创建一个面板
        JPanel bookInfoJPanel = new JPanel();
        bookInfoJPanel.setBounds(760, 100, 490, 500);
        // 设置面板的边框
        bookInfoJPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f));
        bookInfoJPanel.setLayout(null);
        CONTENTPANE.add(bookInfoJPanel);

        // 书名
        JLabel bookName = new JLabel("书名:");
        bookName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookName.setBounds(30, 50, 100, 40);
        bookInfoJPanel.add(bookName);

        // 书名单行文本框
        JTextField bookNameJTextField = new JTextField();
        bookNameJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookNameJTextField.setBounds(150, 50, 300, 40);
        bookInfoJPanel.add(bookNameJTextField);

        // 作者
        JLabel author = new JLabel("作者:");
        author.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        author.setBounds(30, 100, 100, 40);
        bookInfoJPanel.add(author);
        // 作者单行文本框
        JTextField authorJTextField = new JTextField();
        authorJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        authorJTextField.setBounds(150, 100, 300, 40);
        bookInfoJPanel.add(authorJTextField);

        // 图书类型
        JLabel bookType = new JLabel("类型:");
        bookType.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookType.setBounds(30, 150, 100, 40);
        bookInfoJPanel.add(bookType);
        // 图书类型单行文本框
        JTextField bookTypeJTextField = new JTextField();
        bookTypeJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookTypeJTextField.setBounds(150, 150, 300, 40);
        bookInfoJPanel.add(bookTypeJTextField);

        // 出版社
        JLabel publish = new JLabel("出版:");
        publish.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        publish.setBounds(30, 200, 100, 40);
        bookInfoJPanel.add(publish);
        // 图书类型单行文本框
        JTextField publishJTextField = new JTextField();
        publishJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        publishJTextField.setBounds(150, 200, 300, 40);
        bookInfoJPanel.add(publishJTextField);


        // 给表格添加一个鼠标监听器
        bookInfoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // 获取鼠标点击的行
                int selectedRow = bookInfoTable.getSelectedRow();
                // 根据行和列获取表格中的值
                Object bookId = bookInfoTable.getValueAt(selectedRow, 0);
                Object bookName = bookInfoTable.getValueAt(selectedRow, 1);
                Object bookType = bookInfoTable.getValueAt(selectedRow, 2);
                Object publish = bookInfoTable.getValueAt(selectedRow, 3);
                // 将获取的值加入文本框
                bookNameJTextField.setText(bookId.toString());
                authorJTextField.setText(bookName.toString());
                bookTypeJTextField.setText(bookType.toString());
                publishJTextField.setText(publish.toString());
            }
        });

        JButton modifyJButton = new JButton("修改");
        modifyJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        modifyJButton.setBounds(110, 330, 300, 40);
        bookInfoJPanel.add(modifyJButton);
        modifyJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                modifyBookType(bookTypeIDJTextField, booKTypeNameJTextField, bookTypeRemarkJTextArea);
            }
        });


    }

    /**
     * 在图书添加页面中
     * 通过数据库中获取的数据
     * 构造图书类型下拉框
     */
    public void showBookType(JComboBox<BookType> bookTypeComboBox) {
        // 创建一个DatabaseConnect 对象
        DatabaseConnect databaseConnect = new DatabaseConnect();

        // 初始化一个 Connection 对象，用于存储数据库连接
        Connection connection = null;

        try {
            // 获取数据库连接
            connection = databaseConnect.getConnection();
            BookTypeDao bookTypeDao = new BookTypeDao();
            ResultSet resultSet = bookTypeDao.bookTypeQuery(connection, new BookType()); // 传入一个空BookType获取全部图书类型
            while (resultSet.next()) {
                BookType bookType = new BookType();
                bookType.setId(resultSet.getInt("id"));
                bookType.setTypeName(resultSet.getString("type_name"));
                bookTypeComboBox.addItem(bookType);
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
