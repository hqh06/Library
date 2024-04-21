package com.qihang.librarymanage.jframe.user;

import com.qihang.librarymanage.dao.BorrowDetailDao;
import com.qihang.librarymanage.pojo.Book;
import com.qihang.librarymanage.pojo.BorrowDetail;
import com.qihang.librarymanage.pojo.User;
import com.qihang.librarymanage.utlis.DatabaseConnect;
import com.qihang.librarymanage.utlis.Theme;

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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Vector;

public class MyInfoPage extends JFrame {
    private final User user;
    private final DefaultTableModel defaultTableModel;

    public MyInfoPage(User user) {
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

        JLabel title = new JLabel("借书详情");
        title.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        title.setBounds(450, 30, 300, 30);
        getContentPane().add(title);

        // 创建一个面板
        JPanel borrowDetailJPanel = new JPanel();
        borrowDetailJPanel.setBounds(40, 100, 900, 500);
        // 设置面板的边框
        borrowDetailJPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f));
        borrowDetailJPanel.setLayout(null);

        // 创建一个下拉框
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("全部");
        comboBox.addItem("在借");
        comboBox.addItem("已还");
        comboBox.setBounds(200, 30, 100, 40);
        comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        borrowDetailJPanel.add(comboBox);

        // 创建一个搜索框
        JTextField searchJTextField = new JTextField();
        searchJTextField.setBounds(320, 30, 200, 40);
        searchJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        borrowDetailJPanel.add(searchJTextField);

        // 创建一个搜索按钮
        JButton searchJButton = new JButton("搜索图书");
        searchJButton.setBounds(540, 30, 115, 40);
        searchJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        borrowDetailJPanel.add(searchJButton);
        // 给搜索按钮添加一个监听
        searchJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 查询借书详情
                queryBorrowDetail( comboBox , searchJTextField);
            }
        });

        JButton goBackJButton = new JButton("返回");
        goBackJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        goBackJButton.setBounds(795, 30, 80, 40);
        borrowDetailJPanel.add(goBackJButton);

        goBackJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭当前页面
                new UserPage(user); // 创建用户页面
            }
        });

        // 创建一个滚动面板
        JScrollPane borrowDetailJScrollPane = new JScrollPane();
        borrowDetailJScrollPane.setBounds(25, 90, 850, 300);
        borrowDetailJPanel.add(borrowDetailJScrollPane); // 将滚动面板添加到JPanel面板中
        getContentPane().add(borrowDetailJPanel);

        // 创建一个表
        JTable borrowDetailTable = new JTable() {
            // 重写isCellEditable方法让表格不能修改只能选中
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // 做一个表头栏数据
        String[] tableTitle = {"编号", "书名", "状态", "借书时间", "还书时间"};
        String[][] date = {}; // 具体的各栏行记录 先用空的二位数组占位
        defaultTableModel = new DefaultTableModel(date, tableTitle);
        borrowDetailTable.setModel(defaultTableModel);

        borrowDetailTable.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 20)); // 设置表头字体
        borrowDetailTable.getTableHeader().setReorderingAllowed(false); // 禁止拖动表的列
        borrowDetailTable.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        borrowDetailTable.setRowHeight(40); // 设置行高
        borrowDetailTable.getColumnModel().getColumn(0).setMaxWidth(100);
        borrowDetailTable.getColumnModel().getColumn(2).setMaxWidth(220);

        borrowDetailTable.setShowHorizontalLines(true); // 设置水平网格线 第三方主题默认没有
        borrowDetailTable.setIntercellSpacing(new Dimension(0, 0)); // 移除单元格间隙

        // 文字居中
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        borrowDetailTable.setDefaultRenderer(Object.class, renderer);
        // 将表添加到滚动面板中
        borrowDetailJScrollPane.setViewportView(borrowDetailTable);

        // 编号
        JLabel bookID = new JLabel("编号:");
        bookID.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        bookID.setBounds(50, 420, 80, 40);
        borrowDetailJPanel.add(bookID);

        JTextField borrowIDJTextField = new JTextField();
        borrowIDJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        borrowIDJTextField.setBounds(110, 420, 80, 40);
        borrowIDJTextField.setEditable(false); // 禁止输入
        borrowDetailJPanel.add(borrowIDJTextField);

        // 书名
        JLabel bookName = new JLabel("书名:");
        bookName.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        bookName.setBounds(200, 420, 80, 40);
        borrowDetailJPanel.add(bookName);

        JTextField bookNameJTextField = new JTextField();
        bookNameJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        bookNameJTextField.setBounds(260, 420, 200, 40);
        bookNameJTextField.setEditable(false); // 禁止输入
        borrowDetailJPanel.add(bookNameJTextField);

        // 状态
        JLabel status = new JLabel("状态:");
        status.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        status.setBounds(470, 420, 80, 40);
        borrowDetailJPanel.add(status);

        JTextField statusJTextField = new JTextField();
        statusJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        statusJTextField.setBounds(530, 420, 200, 40);
        statusJTextField.setEditable(false); // 禁止输入
        borrowDetailJPanel.add(statusJTextField);

        // 还书按钮
        JButton giveBack = new JButton("还书");
        giveBack.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        giveBack.setBounds(785, 419, 90, 42);
        borrowDetailJPanel.add(giveBack);
        // 给还书按钮添加一个监听
        giveBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBorrowDetail(borrowIDJTextField, statusJTextField);
            }
        });

        // 给表格添加一个鼠标监听器
        borrowDetailTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // 获取鼠标点击的行
                int selectedRow = borrowDetailTable.getSelectedRow();
                // 根据行和列获取表格中的值
                Object bookId = borrowDetailTable.getValueAt(selectedRow, 0);
                Object bookName = borrowDetailTable.getValueAt(selectedRow, 1);
                Object bookStatus = borrowDetailTable.getValueAt(selectedRow, 2);
                // 将获取的值加入文本框
                borrowIDJTextField.setText(bookId.toString());
                bookNameJTextField.setText(bookName.toString());
                statusJTextField.setText(bookStatus.toString());
            }
        });
        queryBorrowDetail(null, null);

        setVisible(true);


    }

    /**
     * 查询借阅详情并加载到表格中
     * @param comboBox 选择需要查询的状态（全部，在借，已还）
     * @param searchJTextField 需要查询的类容
     */
    public void queryBorrowDetail(JComboBox<String> comboBox, JTextField searchJTextField) {
        BorrowDetail borrowDetail = new BorrowDetail(); // 借书详情对象
        Book book = new Book(); // 书籍对象
        // 当传过来的值为null是则代表查询所有的数据
        if (comboBox != null && searchJTextField !=null){
            // 获取选择框中的值
            String selectedStatus = String.valueOf(comboBox.getSelectedItem());
            // 获取搜索框中的值
            String searchText = searchJTextField.getText().trim();
            // 如果搜索框中的值不为空，则设置书名
            if (!searchText.isEmpty()) {
                book.setBookName(searchText);
            }

            // 根据选择框中的值设置借书状态
            switch (selectedStatus) {
                case "在借":
                    borrowDetail.setStatus(0);
                    break;
                case "已还":
                    borrowDetail.setStatus(1);
                    break;
            }
        }

        // 设置行数为0每次查询前清空表上一次的数据
        defaultTableModel.setRowCount(0);
        DatabaseConnect connect = new DatabaseConnect();
        Connection connection = null;
        try {
            // 获取数据连接
            connection = connect.getConnection();
            BorrowDetailDao borrowDetailDao = new BorrowDetailDao();

            ResultSet resultSet = borrowDetailDao.queryBorrowDetail(connection, borrowDetail,book, user);
            // 将数据库中查询到的表数据加载到表格中
            while (resultSet.next()) {
                String status = resultSet.getString("status");
                if (status.equals("0")) {
                    status = "在借";
                } else {
                    status = "已还";
                }
                Vector<String> rowData = new Vector<>();
                rowData.add(String.valueOf(resultSet.getInt("id")));
                rowData.add(resultSet.getString("book_name"));
                rowData.add(status);
                rowData.add(resultSet.getString("borrow_time"));
                rowData.add(resultSet.getString("return_time"));
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
     * 更新借书详情状态以及还书时间
     * @param borrowIDJTextField 借书详情表ID
     * @param statusJTextField 图书状体
     */
    public void updateBorrowDetail(JTextField borrowIDJTextField, JTextField statusJTextField){
        if (borrowIDJTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请选择相关书籍");
            return;
        }
        if (statusJTextField.getText().equals("已还")) {
            JOptionPane.showMessageDialog(null, "当前书籍已还");
            return;
        }
        DatabaseConnect databaseConnect = new DatabaseConnect();
        Connection connection =null;
        // 获取当前时间
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        // 借阅详情实体对象
        BorrowDetail borrowDetail = new BorrowDetail();
        borrowDetail.setId(Integer.valueOf(borrowIDJTextField.getText()));
        borrowDetail.setStatus(1); // 设置状态为已还
        borrowDetail.setReturnTime(String.valueOf(timestamp)); // 设置归还时间
        try {
            // 数据库连接
            connection = databaseConnect.getConnection();
            BorrowDetailDao borrowDetailDao = new BorrowDetailDao();
            int result = borrowDetailDao.updateDetail(connection, borrowDetail);
            if (result==0){
                JOptionPane.showMessageDialog(null, "还书失败");
            }else {
                statusJTextField.setText("已还");
                JOptionPane.showMessageDialog(null, "还书成功");
                // 刷新表格
                queryBorrowDetail(null, null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                databaseConnect.closeConnection(connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        User user1 = new User();
        user1.setId(1);
        new MyInfoPage(user1);
    }
}

