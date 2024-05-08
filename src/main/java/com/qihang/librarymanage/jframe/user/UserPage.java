package com.qihang.librarymanage.jframe.user;

import com.qihang.librarymanage.dao.BookDao;
import com.qihang.librarymanage.dao.BookTypeDao;
import com.qihang.librarymanage.dao.BorrowDetailDao;
import com.qihang.librarymanage.pojo.Book;
import com.qihang.librarymanage.pojo.BookType;
import com.qihang.librarymanage.pojo.BorrowDetail;
import com.qihang.librarymanage.pojo.User;
import com.qihang.librarymanage.utils.DatabaseUtils;
import com.qihang.librarymanage.utils.Theme;

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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

public class UserPage extends JFrame {
    private final User user;
    private final DefaultTableModel defaultTableModel;

    public UserPage(User user) {
        // 保存用户登录的信息
        this.user = user;
        new Theme(1);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2; // 计算水平位置
        int y = (screenSize.height - getHeight()) / 2; // 计算垂直位置

        setTitle("用户页面");
        setBounds(x - 500, y - 350, 1000, 700); // 让窗口在屏幕居中
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 设置点击X结束程序
        getContentPane().setLayout(null);

        JLabel title = new JLabel("图书借阅");
        title.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        title.setBounds(450, 30, 300, 30);
        getContentPane().add(title);

        // 创建一个面板
        JPanel bookInfoJPanel = new JPanel();
        bookInfoJPanel.setBounds(40, 100, 900, 500);
        // 设置面板的边框
        bookInfoJPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f));
        bookInfoJPanel.setLayout(null);

        // 创建一个下拉框
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("书名");
        comboBox.addItem("作者");
        comboBox.setBounds(200, 30, 100, 40);
        comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookInfoJPanel.add(comboBox);

        // 创建一个搜索框
        JTextField searchJTextField = new JTextField();
        searchJTextField.setBounds(320, 30, 200, 40);
        searchJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookInfoJPanel.add(searchJTextField);

        // 创建一个搜索按钮
        JButton searchJButton = new JButton("搜索");
        searchJButton.setBounds(540, 30, 80, 40);
        searchJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        bookInfoJPanel.add(searchJButton);
        // 给搜索按钮添加一个监听
        searchJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 查询书籍
                queryBook(comboBox, searchJTextField);
            }
        });

        JButton myJButton = new JButton("我的");
        myJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        myJButton.setBounds(795, 30, 80, 40);
        bookInfoJPanel.add(myJButton);
        // 给我的按钮添加一个监听
        myJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭当前页面
                new MyInfoPage(user); // 创建我的信息页面
            }
        });

        // 创建一个滚动面板
        JScrollPane bookInfoJScrollPane = new JScrollPane();
        bookInfoJScrollPane.setBounds(25, 90, 850, 300);
        bookInfoJPanel.add(bookInfoJScrollPane); // 将滚动面板添加到JPanel面板中
        getContentPane().add(bookInfoJPanel);

        // 创建一个表
        JTable bookInfoTable = new JTable() {
            // 重写isCellEditable方法让表格不能修改只能选中
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // 做一个表头栏数据
        String[] tableTitle = {"编号", "书名", "作者", "类型", "出版"};
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

        // 文字居中
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        bookInfoTable.setDefaultRenderer(Object.class, renderer);
        // 将表添加到滚动面板中
        bookInfoJScrollPane.setViewportView(bookInfoTable);

        // 编号
        JLabel bookID = new JLabel("编号:");
        bookID.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        bookID.setBounds(50, 420, 80, 40);
        bookInfoJPanel.add(bookID);

        JTextField bookIDJTextField = new JTextField();
        bookIDJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        bookIDJTextField.setBounds(110, 420, 80, 40);
        bookIDJTextField.setEditable(false); // 禁止输入
        bookInfoJPanel.add(bookIDJTextField);

        // 书名
        JLabel bookName = new JLabel("书名:");
        bookName.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        bookName.setBounds(200, 420, 80, 40);
        bookInfoJPanel.add(bookName);

        JTextField bookNameJTextField = new JTextField();
        bookNameJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        bookNameJTextField.setBounds(260, 420, 200, 40);
        bookNameJTextField.setEditable(false); // 禁止输入
        bookInfoJPanel.add(bookNameJTextField);

        // 作者
        JLabel author = new JLabel("作者:");
        author.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        author.setBounds(470, 420, 80, 40);
        bookInfoJPanel.add(author);

        JTextField authorJTextField = new JTextField();
        authorJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        authorJTextField.setBounds(530, 420, 200, 40);
        authorJTextField.setEditable(false); // 禁止输入
        bookInfoJPanel.add(authorJTextField);

        // 借阅按钮
        JButton borrowing = new JButton("借阅");
        borrowing.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        borrowing.setBounds(785, 419, 90, 42);
        bookInfoJPanel.add(borrowing);
        // 给借阅按钮添加一个监听
        borrowing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertBorrowDetail(bookIDJTextField);
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
                // 将获取的值加入文本框
                bookIDJTextField.setText(bookId.toString());
                bookNameJTextField.setText(bookName.toString());
                authorJTextField.setText(bookAuthor.toString());
            }
        });
        // 初始化表格 null代表查询所有数据
        queryBook(null, null);
        setVisible(true);
    }

    /**
     * 查询书籍并加载到表格中
     *
     * @param comboBox         需要查询的书籍或者作者
     * @param searchJTextField 需要查询的类容
     */
    public void queryBook(JComboBox<String> comboBox, JTextField searchJTextField) {
        Book book = new Book();
        if (comboBox != null && searchJTextField != null) {
            // 获取选择框中的值
            String result = String.valueOf(comboBox.getSelectedItem());
            // 获取搜索框中的值
            String text = searchJTextField.getText().trim();
            // 如果搜索框中的值不为空，则根据选择框的值设置书名或作者
            if (!text.isEmpty()) {
                switch (result) {
                    case "书名":
                        book.setBookName(text);
                        break;
                    case "作者":
                        book.setAuthor(text);
                        break;
                }
            }
        }

        // 设置行数为0每次查询前清空表上一次的数据
        defaultTableModel.setRowCount(0);
        DatabaseUtils connect = new DatabaseUtils();
        Connection connection = null;
        try {
            // 获取数据连接
            connection = connect.getConnection();
            BookDao bookDao = new BookDao();
            BookTypeDao bookTypeDao = new BookTypeDao();
            ArrayList<Book> books = bookDao.queryBook(connection, book);
            ArrayList<BookType> bookTypes = bookTypeDao.queryBookType(connection, new BookType());// 传一个空对象查询所有
            // 将数据库中查询到的表数据加载到表格中
            for (Book bookTemp : books) {
                Vector<String> rowData = new Vector<>();
                rowData.add(String.valueOf(bookTemp.getId()));
                rowData.add(bookTemp.getBookName());
                rowData.add(bookTemp.getAuthor());
                for (BookType bookType : bookTypes) {
                    if (Objects.equals(bookType.getId(), bookTemp.getTypeId()))
                        rowData.add(bookType.getTypeName());
                }
                rowData.add(bookTemp.getPublish());
                defaultTableModel.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connect.closeConnection(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 插入借阅详情
     *
     * @param bookIDJTextField 图书Id
     */
    public void insertBorrowDetail(JTextField bookIDJTextField) {
        if (bookIDJTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请选择相关书籍");
            return;
        }
        // 获取当前时间
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        // 获取图书ID
        String bookId = bookIDJTextField.getText();
        Integer userId = user.getId();
        // 构造图书详情实体
        BorrowDetail borrowDetail = new BorrowDetail();
        borrowDetail.setUserId(userId); // 用户Id
        borrowDetail.setBookId(Integer.valueOf(bookId)); // 书籍Id
        borrowDetail.setStatus(0); // 0在借状态 1已还状态
        borrowDetail.setBorrowTime(String.valueOf(timestamp)); // 借阅时间

        // 数据库连接对象
        DatabaseUtils databaseUtils = new DatabaseUtils();
        Connection connection = null;
        try {
            // 获取数据连接
            connection = databaseUtils.getConnection();
            BorrowDetailDao borrowDetailDao = new BorrowDetailDao();
            int result = borrowDetailDao.addDetail(connection, borrowDetail);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "该书已借");
            } else if (result == 1) {
                JOptionPane.showMessageDialog(null, "借书成功");
            } else {
                JOptionPane.showMessageDialog(null, "借书失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                databaseUtils.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        User user1 = new User();
        user1.setId(1);
        new UserPage(user1);
    }
}
