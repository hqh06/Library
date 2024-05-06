package com.qihang.librarymanage.jframe.admin;

import com.qihang.librarymanage.dao.BookDao;
import com.qihang.librarymanage.dao.BookTypeDao;
import com.qihang.librarymanage.pojo.Book;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;
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
                Book book = new Book();
                book.setBookName(searchJTextField.getText().trim());
                queryBook(book);
            }
        });

        // 创建一个滚动面板
        JScrollPane bookInfoJScrollPane = new JScrollPane();
        bookInfoJScrollPane.setBounds(25, 120, 710, 330);
        bookInfoJPanel.add(bookInfoJScrollPane); // 将滚动面板添加到JPanel面板中


        // 创建一个表
        JTable bookInfoTable = new JTable() {
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
        bookInfoTable.setModel(defaultTableModel);

        bookInfoTable.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 20)); // 设置表头字体
        bookInfoTable.getTableHeader().setReorderingAllowed(false); // 禁止拖动表的列
        bookInfoTable.setFont(new Font("微软雅黑", Font.PLAIN, 17));
        bookInfoTable.setRowHeight(40); // 设置行高
        bookInfoTable.getColumnModel().getColumn(0).setMaxWidth(100);

        bookInfoTable.setShowHorizontalLines(true); // 设置水平网格线 第三方主题默认没有
        bookInfoTable.setIntercellSpacing(new Dimension(0, 0)); // 移除单元格间隙

        // 设置表中文字居中
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        bookInfoTable.setDefaultRenderer(Object.class, renderer);
        // 将表添加到滚动面板中
        bookInfoJScrollPane.setViewportView(bookInfoTable);


        queryBook(new Book()); // 初始化表

        return bookInfoTable;
    }

    /**
     * 初始化添加图书页面
     */
    public void add() {
        JLabel title = new JLabel("图书添加");
        title.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        title.setBounds(600, 30, 300, 32);
        CONTENTPANE.add(title);
        query();// 加载查询到的图书

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
        // 获取所有的图书类型
        ArrayList<BookType> bookTypes = showBookType();
        for (BookType type : bookTypes) {
            bookTypeComboBox.addItem(type);
        }
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
        scrollPane.setBounds(100, 270, 300, 90);
        // 禁用水平滚动条
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(bookRemarkJTextArea); // 将JTextArea添加到滚动面板中
        bookInfoJPanel.add(scrollPane);

        // 添加按钮
        JButton addJButton = new JButton("添加");
        addJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        addJButton.setBounds(100, 400, 140, 40);
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

                addBook(book);
            }
        });

        // 重置按钮
        JButton resetJButton = new JButton("重置");
        resetJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        resetJButton.setBounds(260, 400, 140, 40);
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
        bookInfoJPanel.setBounds(820, 100, 430, 500);
        // 设置面板的边框
        bookInfoJPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f));
        bookInfoJPanel.setLayout(null);
        CONTENTPANE.add(bookInfoJPanel);

        // 编号
        JLabel bookId = new JLabel("编号:");
        bookId.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookId.setBounds(30, 30, 50, 40);
        bookInfoJPanel.add(bookId);
        // 编号单行文本框
        JTextField bookIdJTextField = new JTextField();
        bookIdJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookIdJTextField.setBounds(100, 30, 300, 40);
        bookIdJTextField.setEditable(false); // 禁止输入
        bookInfoJPanel.add(bookIdJTextField);

        // 书名
        JLabel bookName = new JLabel("书名:");
        bookName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookName.setBounds(30, 90, 50, 40);
        bookInfoJPanel.add(bookName);
        // 书名单行文本框
        JTextField bookNameJTextField = new JTextField();
        bookNameJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookNameJTextField.setBounds(100, 90, 300, 40);
        bookInfoJPanel.add(bookNameJTextField);

        // 作者
        JLabel author = new JLabel("作者:");
        author.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        author.setBounds(30, 150, 50, 40);
        bookInfoJPanel.add(author);
        // 作者单行文本框
        JTextField authorJTextField = new JTextField();
        authorJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        authorJTextField.setBounds(100, 150, 300, 40);
        bookInfoJPanel.add(authorJTextField);

        // 出版社
        JLabel publish = new JLabel("出版:");
        publish.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        publish.setBounds(30, 210, 50, 40);
        bookInfoJPanel.add(publish);
        // 出版社单行文本框
        JTextField publishJTextField = new JTextField();
        publishJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        publishJTextField.setBounds(100, 210, 300, 40);
        bookInfoJPanel.add(publishJTextField);

        // 图书类型
        JLabel bookType = new JLabel("类型:");
        bookType.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookType.setBounds(30, 270, 50, 40);
        bookInfoJPanel.add(bookType);
        // 图书类型下拉框
        JComboBox<BookType> bookTypeComboBox = new JComboBox<>();
        bookTypeComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookTypeComboBox.setBounds(100, 270, 150, 40);
        // 获取所有的图书类型
        ArrayList<BookType> bookTypes = showBookType();
        for (BookType type : bookTypes) {
            bookTypeComboBox.addItem(type);
        }
        bookInfoJPanel.add(bookTypeComboBox);

        // 库存
        JLabel bookNumber = new JLabel("库存:");
        bookNumber.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookNumber.setBounds(260, 270, 60, 40);
        bookInfoJPanel.add(bookNumber);
        // 库存单行文本框
        JTextField bookNumberJTextField = new JTextField();
        bookNumberJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookNumberJTextField.setBounds(330, 270, 70, 40);
        bookInfoJPanel.add(bookNumberJTextField);

        // 描述
        JLabel bookRemark = new JLabel("描述:");
        bookRemark.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        bookRemark.setBounds(30, 330, 50, 40);
        bookInfoJPanel.add(bookRemark);

        // 描述多行文本框
        JTextArea bookRemarkJTextArea = new JTextArea();
        bookRemarkJTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookRemarkJTextArea.setLineWrap(true); // 在达到JTextArea的宽度时自动换行
        bookInfoJPanel.add(bookRemarkJTextArea);

        // 滚动面板
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(100, 330, 300, 90);
        // 禁用水平滚动条
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(bookRemarkJTextArea); // 将JTextArea添加到滚动面板中
        bookInfoJPanel.add(scrollPane);

        // 修改按钮
        JButton modifyJButton = new JButton("修改");
        modifyJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        modifyJButton.setBounds(100, 430, 140, 40);
        bookInfoJPanel.add(modifyJButton);
        // 添加按钮监听
        modifyJButton.addActionListener(new ActionListener() {
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
                // 图书Id
                book.setId(Integer.valueOf(bookIdJTextField.getText().trim()));
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

                modifyBook(book);
            }
        });

        // 删除按钮
        JButton deleteJButton = new JButton("删除");
        deleteJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        deleteJButton.setBounds(260, 430, 140, 40);
        bookInfoJPanel.add(deleteJButton);
        // 删除按钮监听
        deleteJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book book = new Book();
                book.setId(Integer.valueOf(bookIdJTextField.getText().trim()));
                deleteBook(book);
            }
        });

        // 给表格添加一个鼠标监听器
        bookInfoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // 获取鼠标点击的行
                int selectedRow = bookInfoTable.getSelectedRow();
                // 根据行和列获取表格中的值
                Object bookId = bookInfoTable.getValueAt(selectedRow, 0);
                Object bookName = bookInfoTable.getValueAt(selectedRow, 1);
                Object bookAuthor = bookInfoTable.getValueAt(selectedRow, 2);
                Object bookPublish = bookInfoTable.getValueAt(selectedRow, 3);
                Object Type = bookInfoTable.getValueAt(selectedRow, 4);
                Object number = bookInfoTable.getValueAt(selectedRow, 5);
                Object remark = bookInfoTable.getValueAt(selectedRow, 6);

                // 将获取的值加入文本框
                bookIdJTextField.setText(bookId.toString());
                bookNameJTextField.setText(bookName.toString());
                authorJTextField.setText(bookAuthor.toString());
                publishJTextField.setText(bookPublish.toString());
                ArrayList<BookType> bookTypesTemp = showBookType();
                for (int i = 0; i < bookTypesTemp.toArray().length; i++) {
                    if (bookTypesTemp.get(i).getTypeName().equals(Type)) {
                        bookTypeComboBox.setSelectedIndex(i);
                    }
                }
                bookNumberJTextField.setText(number.toString());
                bookRemarkJTextArea.setText(remark.toString());
            }
        });


    }

    /**
     * 在图书添加页面中
     * 通过数据库中获取的数据
     * 构造图书类型下拉框
     */
    public ArrayList<BookType> showBookType() {
        // 创建一个DatabaseConnect 对象
        DatabaseUtils databaseUtils = new DatabaseUtils();

        // 初始化一个 Connection 对象，用于存储数据库连接
        Connection connection = null;

        ArrayList<BookType> bookTypes = new ArrayList<>();

        try {
            // 获取数据库连接
            connection = databaseUtils.getConnection();
            BookTypeDao bookTypeDao = new BookTypeDao();
            ResultSet resultSet = bookTypeDao.bookTypeQuery(connection, new BookType()); // 传入一个空BookType获取全部图书类型

            while (resultSet.next()) {
                BookType bookType = new BookType();
                bookType.setId(resultSet.getInt("id"));
                bookType.setTypeName(resultSet.getString("type_name"));
                bookTypes.add(bookType);
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
        return bookTypes;
    }

    /**
     * 连接数据库并查询图书
     *
     * @param book 图书对象
     */
    public void queryBook(Book book) {
        // 设置行数为0每次查询前清空表上一次的数据
        defaultTableModel.setRowCount(0);

        // 创建一个DatabaseConnect 对象
        DatabaseUtils databaseUtils = new DatabaseUtils();

        // 初始化一个 Connection 对象，用于存储数据库连接
        Connection connection = null;

        try {
            // 获取数据库连接
            connection = databaseUtils.getConnection();
            // 实例化BookDao对象并调用图书查询方法
            BookDao bookDao = new BookDao();
            ResultSet resultSet = bookDao.queryBook(connection, book);
            ArrayList<Book> books = new ArrayList<>();
            // 获取查询到的所有图书
            while (resultSet.next()) {
                Book bookTemp = new Book();
                bookTemp.setId(resultSet.getInt("id"));
                bookTemp.setBookName(resultSet.getString("book_name"));
                bookTemp.setAuthor(resultSet.getString("author"));
                bookTemp.setPublish(resultSet.getString("publish"));
                bookTemp.setTypeId(resultSet.getInt("type_id"));
                bookTemp.setNumber(resultSet.getInt("number"));
                bookTemp.setBookRemark(resultSet.getString("book_remark"));
                books.add(bookTemp);
            }
            // 查询所有的图书类型
            ArrayList<BookType> bookTypes = showBookType();
            for (Book bookTemp : books) {
                Vector<String> rowData = new Vector<>();
                rowData.add(String.valueOf(bookTemp.getId()));
                rowData.add(bookTemp.getBookName());
                rowData.add(bookTemp.getAuthor());
                rowData.add(bookTemp.getPublish());

                for (BookType bookType : bookTypes) {
                    if (Objects.equals(bookTemp.getTypeId(), bookType.getId())) {
                        rowData.add(bookType.getTypeName());
                    }
                }
                rowData.add(String.valueOf(bookTemp.getNumber()));
                rowData.add(bookTemp.getBookRemark());


                defaultTableModel.addRow(rowData);
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

    /**
     * 连接数据库并添加图书
     *
     * @param book 图书对象
     */
    public void addBook(Book book) {
        // 创建一个DatabaseConnect 对象
        DatabaseUtils databaseUtils = new DatabaseUtils();

        // 初始化一个 Connection 对象，用于存储数据库连接
        Connection connection = null;

        try {
            // 获取数据库连接
            connection = databaseUtils.getConnection();

            BookDao bookDao = new BookDao();
            int result = bookDao.addBook(connection, book);
            if (result == 2) {
                JOptionPane.showMessageDialog(null, "添加重复");
            } else if (result == 1) {
                JOptionPane.showMessageDialog(null, "添加成功");
                // 刷新表格
                queryBook(new Book());
            } else {
                JOptionPane.showMessageDialog(null, "添加失败");
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

    /**
     * 连接数据库并修改图书
     * @param book 图书对象
     */
    public void modifyBook(Book book) {
        // 创建一个DatabaseConnect 对象
        DatabaseUtils databaseUtils = new DatabaseUtils();

        // 初始化一个 Connection 对象，用于存储数据库连接
        Connection connection = null;

        try {
            // 获取数据库连接
            connection = databaseUtils.getConnection();
            BookDao bookDao = new BookDao();
            int result = bookDao.modifyBook(connection, book);
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "修改成功");
                // 刷新表格
                queryBook(new Book());
            }else {
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

    /**
     * 连接数据库并删除图书
     * @param book 图书对象
     */
    public void deleteBook(Book book) {
        // 创建一个DatabaseConnect 对象
        DatabaseUtils databaseUtils = new DatabaseUtils();

        // 初始化一个 Connection 对象，用于存储数据库连接
        Connection connection = null;

        try {
            // 获取数据库连接
            connection = databaseUtils.getConnection();
            BookDao bookDao = new BookDao();
            int result = bookDao.deleteBook(connection, book);
            if (result>0){
                JOptionPane.showMessageDialog(null, "删除成功");
                // 刷新页面
                queryBook(new Book());
            }else {
                JOptionPane.showMessageDialog(null, "删除失败");
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
