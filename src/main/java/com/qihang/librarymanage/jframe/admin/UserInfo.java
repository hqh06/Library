package com.qihang.librarymanage.jframe.admin;

import com.qihang.librarymanage.dao.UserDao;
import com.qihang.librarymanage.pojo.User;
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
import java.util.regex.Pattern;

public class UserInfo {
    private final Container CONTENTPANE;
    private DefaultTableModel defaultTableModel;

    public UserInfo(Container getContentPane) {
        this.CONTENTPANE = getContentPane;
    }

    /**
     * 初始化查询用户页面
     *
     * @return 表对象
     */

    public JTable query() {
        // 创建一个面板
        JPanel userInfoJPanel = new JPanel();
        userInfoJPanel.setBounds(40, 100, 700, 500);
        // 设置面板的边框
        userInfoJPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f));
        userInfoJPanel.setLayout(null);
        CONTENTPANE.add(userInfoJPanel);

        // 用户名
        JLabel userName = new JLabel("用户名:");
        userName.setBounds(150, 50, 100, 40);
        userName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userInfoJPanel.add(userName);

        // 创建一个搜索框
        JTextField searchJTextField = new JTextField();
        searchJTextField.setBounds(230, 50, 200, 40);
        searchJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userInfoJPanel.add(searchJTextField);

        // 创建一个搜索按钮
        JButton searchJButton = new JButton("搜索");
        searchJButton.setBounds(450, 50, 80, 40);
        searchJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userInfoJPanel.add(searchJButton);
        searchJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 实例化User对象并添加值
                User user = new User();
                user.setUserName(searchJTextField.getText().trim());
                queryUser(user);
            }
        });

        // 创建一个滚动面板
        JScrollPane userJScrollPane = new JScrollPane();
        userJScrollPane.setBounds(25, 120, 650, 330);
        userInfoJPanel.add(userJScrollPane); // 将滚动面板添加到JPanel面板中


        // 创建一个表
        JTable userInfoTable = new JTable() {
            // 重写isCellEditable方法让表格不能修改只能选中
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // 做一个表头栏数据
        String[] tableTitle = {"编号", "昵称", "账号", "性别", "角色", "电话"};
        String[][] date = {}; // 具体的各栏行记录 先用空的二位数组占位
        defaultTableModel = new DefaultTableModel(date, tableTitle);
        userInfoTable.setModel(defaultTableModel);

        userInfoTable.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 20)); // 设置表头字体
        userInfoTable.getTableHeader().setReorderingAllowed(false); // 禁止拖动表的列
        userInfoTable.setFont(new Font("微软雅黑", Font.PLAIN, 17));
        userInfoTable.setRowHeight(40); // 设置行高
        userInfoTable.getColumnModel().getColumn(0).setMaxWidth(100);

        userInfoTable.setShowHorizontalLines(true); // 设置水平网格线 第三方主题默认没有
        userInfoTable.setIntercellSpacing(new Dimension(0, 0)); // 移除单元格间隙

        // 设置表中文字居中
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        userInfoTable.setDefaultRenderer(Object.class, renderer);
        // 将表添加到滚动面板中
        userJScrollPane.setViewportView(userInfoTable);
        queryUser(new User()); // 初始化表

        return userInfoTable;
    }

    /**
     * 初始化添加用户页面
     */

    public void add() {
        JLabel title = new JLabel("用户添加");
        title.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        title.setBounds(600, 30, 300, 32);
        CONTENTPANE.add(title);
        query(); // 加载查询到的用户

        // 创建一个面板
        JPanel userInfoJPanel = new JPanel();
        userInfoJPanel.setBounds(760, 100, 490, 500);
        // 设置面板的边框
        userInfoJPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f));
        userInfoJPanel.setLayout(null);
        CONTENTPANE.add(userInfoJPanel);


        // 用户名
        JLabel userName = new JLabel("昵称:");
        userName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userName.setBounds(50, 30, 100, 40);
        userInfoJPanel.add(userName);
        // 用户名单行文本框
        JTextField userNameJTextField = new JTextField();
        userNameJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userNameJTextField.setBounds(130, 30, 300, 40);
        userInfoJPanel.add(userNameJTextField);
        // 用户账户
        JLabel userAccount = new JLabel("账户:");
        userAccount.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userAccount.setBounds(50, 100, 100, 40);
        userInfoJPanel.add(userAccount);
        // 用户账户单行文本框
        JTextField userAccountJTextField = new JTextField();
        userAccountJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userAccountJTextField.setBounds(130, 100, 300, 40);
        userInfoJPanel.add(userAccountJTextField);

        // 密码
        JLabel userPwd = new JLabel("密码:");
        userPwd.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        userPwd.setBounds(50, 170, 80, 40);
        userInfoJPanel.add(userPwd);

        // 密码文本框
        JTextField userPwdJTextField = new JTextField();
        userPwdJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        userPwdJTextField.setBounds(130, 170, 300, 40);
        userInfoJPanel.add(userPwdJTextField);

        // 性别
        JLabel userGender = new JLabel("性别:");
        userGender.setBounds(50, 230, 80, 30);
        userGender.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userInfoJPanel.add(userGender);

        JRadioButton selectMan = new JRadioButton("男");
        selectMan.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        selectMan.setBounds(180, 230, 60, 30);
        selectMan.setSelected(true); // 默认用户为第一选项
        JRadioButton selectWoman = new JRadioButton("女");
        selectWoman.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        selectWoman.setBounds(300, 230, 60, 30);
        // 把selectMan和selectWoman加入同一个组实现单选
        ButtonGroup genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(selectMan);
        genderButtonGroup.add(selectWoman);
        // 加入面板
        userInfoJPanel.add(selectMan);
        userInfoJPanel.add(selectWoman);

        // 角色
        JLabel role = new JLabel("角色:");
        role.setBounds(50, 290, 80, 30);
        role.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userInfoJPanel.add(role);

        // 角色选择
        JRadioButton selectUser = new JRadioButton("用户");
        selectUser.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        selectUser.setBounds(180, 290, 100, 30);
        selectUser.setSelected(true); // 默认用户为第一选项
        JRadioButton selectAdmin = new JRadioButton("管理员");
        selectAdmin.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        selectAdmin.setBounds(300, 290, 100, 30);
        // 把admin和user加入同一个组实现单选
        ButtonGroup roleButtonGroup = new ButtonGroup();
        roleButtonGroup.add(selectAdmin);
        roleButtonGroup.add(selectUser);
        // 加入面板
        userInfoJPanel.add(selectUser);
        userInfoJPanel.add(selectAdmin);

        // 用户电话
        JLabel userPhone = new JLabel("电话:");
        userPhone.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userPhone.setBounds(50, 350, 100, 40);
        userInfoJPanel.add(userPhone);
        // 用户电话单行文本框
        JTextField userPhoneJTextField = new JTextField();
        userPhoneJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userPhoneJTextField.setBounds(130, 350, 300, 40);
        userInfoJPanel.add(userPhoneJTextField);


        // 添加按钮
        JButton addJButton = new JButton("添加");
        addJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        addJButton.setBounds(130, 410, 140, 40);
        userInfoJPanel.add(addJButton);
        // 添加按钮监听
        addJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userNameJTextField.getText().trim().isEmpty() ||
                        userAccountJTextField.getText().trim().isEmpty() ||
                        userPwdJTextField.getText().trim().isEmpty() ||
                        userPhoneJTextField.getText().trim().isEmpty()
                ) {
                    JOptionPane.showMessageDialog(null, "请填写相关信息");
                    return;
                }
                int gender = selectMan.isSelected() ? 1 : 0; // 男 1 女 0
                int role = selectUser.isSelected() ? 1 : 0; // 用户 1 管理员 0
                // 实例化User对象并设置对象属性
                User user = new User();
                user.setUserName(userNameJTextField.getText().trim());
                user.setUserAccount(userAccountJTextField.getText().trim());
                user.setPassword(userPwdJTextField.getText().trim());
                user.setSex(gender);
                user.setRole(role);
                user.setPhone(userPhoneJTextField.getText().trim());
                addUser(user);
            }
        });

        // 重置按钮
        JButton resetJButton = new JButton("重置");
        resetJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        resetJButton.setBounds(290, 410, 140, 40);
        userInfoJPanel.add(resetJButton);
        // 重置按钮监听
        resetJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 清空输入框中的内容
                userNameJTextField.setText("");
                userAccountJTextField.setText("");
                userPwdJTextField.setText("");
                selectMan.setSelected(true);
                selectUser.setSelected(true);
                userPhoneJTextField.setText("");
            }
        });

    }

    /**
     * 初始化修改用户页面
     */
    public void modify() {
        JLabel title = new JLabel("用户更新");
        title.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        title.setBounds(600, 30, 300, 32);
        CONTENTPANE.add(title);

        JTable userInfoTable = query(); // 加载查询到的图书

        // 创建一个面板
        JPanel userInfoJPanel = new JPanel();
        userInfoJPanel.setBounds(760, 100, 490, 500);
        // 设置面板的边框
        userInfoJPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f));
        userInfoJPanel.setLayout(null);
        CONTENTPANE.add(userInfoJPanel);


        // 编号
        JLabel userId = new JLabel("编号:");
        userId.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        userId.setBounds(50, 30, 80, 40);
        userInfoJPanel.add(userId);

        // 编号文本框
        JTextField userIdJTextField = new JTextField();
        userIdJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        userIdJTextField.setBounds(130, 30, 300, 40);
        userIdJTextField.setEditable(false); // 禁止输入
        userInfoJPanel.add(userIdJTextField);

        // 用户名
        JLabel userName = new JLabel("昵称:");
        userName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userName.setBounds(50, 100, 100, 40);
        userInfoJPanel.add(userName);
        // 用户名单行文本框
        JTextField userNameJTextField = new JTextField();
        userNameJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userNameJTextField.setBounds(130, 100, 300, 40);
        userInfoJPanel.add(userNameJTextField);
        // 用户账户
        JLabel userAccount = new JLabel("账户:");
        userAccount.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userAccount.setBounds(50, 170, 100, 40);
        userInfoJPanel.add(userAccount);
        // 用户账户单行文本框
        JTextField userAccountJTextField = new JTextField();
        userAccountJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userAccountJTextField.setBounds(130, 170, 300, 40);
        userInfoJPanel.add(userAccountJTextField);

        // 性别
        JLabel userGender = new JLabel("性别:");
        userGender.setBounds(50, 230, 80, 30);
        userGender.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userInfoJPanel.add(userGender);

        JRadioButton selectMan = new JRadioButton("男");
        selectMan.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        selectMan.setBounds(180, 230, 60, 30);
        selectMan.setSelected(true); // 默认用户为第一选项
        JRadioButton selectWoman = new JRadioButton("女");
        selectWoman.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        selectWoman.setBounds(300, 230, 60, 30);
        // 把selectMan和selectWoman加入同一个组实现单选
        ButtonGroup genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(selectMan);
        genderButtonGroup.add(selectWoman);
        // 加入面板
        userInfoJPanel.add(selectMan);
        userInfoJPanel.add(selectWoman);

        // 角色
        JLabel role = new JLabel("角色:");
        role.setBounds(50, 290, 80, 30);
        role.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userInfoJPanel.add(role);

        // 角色选择
        JRadioButton selectUser = new JRadioButton("用户");
        selectUser.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        selectUser.setBounds(180, 290, 100, 30);
        selectUser.setSelected(true); // 默认用户为第一选项
        JRadioButton selectAdmin = new JRadioButton("管理员");
        selectAdmin.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        selectAdmin.setBounds(300, 290, 100, 30);
        // 把admin和user加入同一个组实现单选
        ButtonGroup roleButtonGroup = new ButtonGroup();
        roleButtonGroup.add(selectAdmin);
        roleButtonGroup.add(selectUser);
        // 加入面板
        userInfoJPanel.add(selectUser);
        userInfoJPanel.add(selectAdmin);

        // 用户电话
        JLabel userPhone = new JLabel("电话:");
        userPhone.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userPhone.setBounds(50, 350, 100, 40);
        userInfoJPanel.add(userPhone);
        // 用户电话单行文本框
        JTextField userPhoneJTextField = new JTextField();
        userPhoneJTextField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userPhoneJTextField.setBounds(130, 350, 300, 40);
        userInfoJPanel.add(userPhoneJTextField);


        // 给表格添加一个鼠标监听器
        userInfoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // 获取鼠标点击的行
                int selectedRow = userInfoTable.getSelectedRow();
                // 根据行和列获取表格中的值
                Object userId = userInfoTable.getValueAt(selectedRow, 0);
                Object userName = userInfoTable.getValueAt(selectedRow, 1);
                Object userAccount = userInfoTable.getValueAt(selectedRow, 2);
                Object sex = userInfoTable.getValueAt(selectedRow, 3);
                Object role = userInfoTable.getValueAt(selectedRow, 4);
                Object phone = userInfoTable.getValueAt(selectedRow, 5);
                // 将获取的值加入文本框
                userIdJTextField.setText(userId.toString());
                userNameJTextField.setText(userName.toString());
                userAccountJTextField.setText(userAccount.toString());
                if (sex == "男") {
                    selectMan.setSelected(true);
                } else {
                    selectWoman.setSelected(true);
                }
                if (role == "用户") {
                    selectUser.setSelected(true);
                } else {
                    selectAdmin.setSelected(true);
                }
                userPhoneJTextField.setText(phone.toString());
            }
        });

        JButton modifyJButton = new JButton("修改");
        modifyJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        modifyJButton.setBounds(130, 430, 140, 40);
        userInfoJPanel.add(modifyJButton);
        modifyJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 实例化用户对象并设置对象属性
                User user = new User();
                user.setId(Integer.valueOf(userIdJTextField.getText().trim()));
                user.setUserName(userNameJTextField.getText().trim());
                user.setUserAccount(userAccountJTextField.getText().trim());
                if (selectMan.isSelected()) {
                    user.setSex(1); // 男
                } else {
                    user.setSex(0); // 女
                }
                if (selectUser.isSelected()) {
                    user.setRole(1); // 用户
                } else {
                    user.setRole(0); // 管理员
                }
                user.setPhone(userPhoneJTextField.getText().trim());

                modifyUser(user);
            }
        });
        // 删除按钮
        JButton deleteJButton = new JButton("删除");
        deleteJButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        deleteJButton.setBounds(290, 430, 140, 40);
        userInfoJPanel.add(deleteJButton);
        // 删除按钮监听
        deleteJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setId(Integer.valueOf(userIdJTextField.getText().trim()));
                deleteUser(user);
            }
        });

    }

    /**
     * 连接数据库查询用户信息
     *
     * @param user 用户对象
     */
    public void queryUser(User user) {
        // 设置行数为0每次查询前清空表上一次的数据
        defaultTableModel.setRowCount(0);

        // 创建一个DatabaseConnect 对象
        DatabaseUtils databaseUtils = new DatabaseUtils();

        // 初始化一个 Connection 对象，用于存储数据库连接
        Connection connection = null;

        try {
            // 获取数据库连接
            connection = databaseUtils.getConnection();

            UserDao userDao = new UserDao();
            ArrayList<User> users = userDao.queryUser(connection, user);
            for (User userTemp : users) {
                Vector<String> rowData = new Vector<>();
                rowData.add(String.valueOf(userTemp.getId()));
                rowData.add(userTemp.getUserName());
                rowData.add(userTemp.getUserAccount());
                if (userTemp.getSex() == 1) {
                    rowData.add("男");
                } else {
                    rowData.add("女");
                }
                if (userTemp.getRole() == 1) {
                    rowData.add("用户");
                } else {
                    rowData.add("管理员");
                }
                rowData.add(userTemp.getPhone());
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
     * 连接数据库添加用户信息
     *
     * @param user 用户对象
     */
    public void addUser(User user) {

        // 不允许包含空格的正则
        boolean userRex = Pattern.matches("^(?!.*\\s).+$", user.getUserName());
        if (!userRex) {
            JOptionPane.showMessageDialog(null, "用户名不能有空格");
            return;
        }
        boolean accountRex = Pattern.matches("^[a-zA-Z0-9_]+$", user.getUserAccount());
        if (!accountRex) {
            JOptionPane.showMessageDialog(null, "账户只能使用字母、数字、下划线");
            return;
        }
        boolean pwdRex = Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*\\s).{8,16}$", user.getPassword());
        if (!pwdRex) {
            JOptionPane.showMessageDialog(null, "密码8-16个字符需包含大、小写字母和数字且不包含空格");
            return;
        }
        boolean phoneNumberRex = Pattern.matches("^1[34578]\\d{9}$", user.getPhone());
        if (!phoneNumberRex) {
            JOptionPane.showMessageDialog(null, "请输入正确的手机号码");
            return;
        }
        // 创建一个DatabaseConnect 对象
        DatabaseUtils databaseUtils = new DatabaseUtils();

        // 初始化一个 Connection 对象，用于存储数据库连接
        Connection connection = null;

        try {
            // 获取数据库连接
            connection = databaseUtils.getConnection();
            UserDao userDao = new UserDao();
            int result = userDao.addUser(connection, user);
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "添加成功");
                // 刷新表格
                queryUser(new User());
            } else if (result == 2) {
                JOptionPane.showMessageDialog(null, "账号已注册");
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
     * 连接数据库修改用户信息
     *
     * @param user 用户对象
     */
    public void modifyUser(User user) {
        // 不允许包含空格的正则
        boolean userRex = Pattern.matches("^(?!.*\\s).+$", user.getUserName());
        if (!userRex) {
            JOptionPane.showMessageDialog(null, "用户名不能有空格");
            return;
        }
        boolean accountRex = Pattern.matches("^[a-zA-Z0-9_]+$", user.getUserAccount());
        if (!accountRex) {
            JOptionPane.showMessageDialog(null, "账户只能使用字母、数字、下划线");
            return;
        }
//        boolean pwdRex = Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*\\s).{8,16}$", user.getPassword());
//        if (!pwdRex) {
//            JOptionPane.showMessageDialog(null, "密码8-16个字符需包含大、小写字母和数字且不包含空格");
//            return;
//        }
        boolean phoneNumberRex = Pattern.matches("^1[34578]\\d{9}$", user.getPhone());
        if (!phoneNumberRex) {
            JOptionPane.showMessageDialog(null, "请输入正确的手机号码");
            return;
        }

        // 创建一个DatabaseConnect 对象
        DatabaseUtils databaseUtils = new DatabaseUtils();

        // 初始化一个 Connection 对象，用于存储数据库连接
        Connection connection = null;

        try {
            // 获取数据库连接
            connection = databaseUtils.getConnection();
            UserDao userDao = new UserDao();
            int result = userDao.modifyUser(connection, user);
            if (result == 2) {
                JOptionPane.showMessageDialog(null, "账户已存在");

            } else if (result > 0) {
                JOptionPane.showMessageDialog(null, "修改成功");
                // 刷新表格
                queryUser(new User());
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

    /**
     * 连接数据库删除用户信息
     *
     * @param user 用户对象
     */
    public void deleteUser(User user) {
        // 创建一个DatabaseConnect 对象
        DatabaseUtils databaseUtils = new DatabaseUtils();

        // 初始化一个 Connection 对象，用于存储数据库连接
        Connection connection = null;

        try {
            // 获取数据库连接
            connection = databaseUtils.getConnection();
            UserDao userDao = new UserDao();
            int result = userDao.deleteUser(connection, user);
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "删除成功");
                // 刷新表格
                queryUser(new User());
            } else {
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
