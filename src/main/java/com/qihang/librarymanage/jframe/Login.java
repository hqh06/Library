package com.qihang.librarymanage.jframe;


import com.qihang.librarymanage.dao.UserDao;
import com.qihang.librarymanage.jframe.user.UserPage;
import com.qihang.librarymanage.pojo.User;
import com.qihang.librarymanage.utils.DatabaseUtils;
import com.qihang.librarymanage.utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    public Login() {
        new Theme(1);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2; // 计算水平位置
        int y = (screenSize.height - getHeight()) / 2; // 计算垂直位置

        setTitle("图书管理器");
        setBounds(x - 300, y - 300, 600, 600); // 让窗口在屏幕居中
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 设置点击X结束程序
        getContentPane().setLayout(null);

        JLabel account = new JLabel("账号:");
        account.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        account.setBounds(150,230,60,30);
        getContentPane().add(account);
        // 账号文本框
        JTextField accountText = new JTextField();
        accountText.setBounds(210, 230, 200, 30);
        getContentPane().add(accountText);

        JLabel password = new JLabel("密码:");
        password.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        password.setBounds(150, 280, 60, 30);
        getContentPane().add(password);
        // 密码文本框
        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(210, 280, 200, 30);
        getContentPane().add(passwordText);

        // 角色选择
        JRadioButton selectUser = new JRadioButton("用户");
        selectUser.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        selectUser.setBounds(210, 330, 100, 30);
        selectUser.setSelected(true); // 默认用户为第一选项
        JRadioButton selectAdmin = new JRadioButton("管理员");
        selectAdmin.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        selectAdmin.setBounds(310, 330, 100, 30);
        // 把admin和user加入同一个组实现单选
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(selectAdmin);
        buttonGroup.add(selectUser);
        // 加入面板
        getContentPane().add(selectUser);
        getContentPane().add(selectAdmin);


        JButton loginButton = new JButton("登录");
        loginButton.setBounds(150,380,120,40);
        loginButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        getContentPane().add(loginButton);
        // 监听登录事件
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginEvent(accountText, passwordText, selectUser);
            }
        });

        JButton registerButton = new JButton("注册");
        registerButton.setBounds(300,380,120,40);
        registerButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        getContentPane().add(registerButton);
        // 监听注册事件
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerEvent();
            }
        });

        setVisible(true);


    }

    /**
     * 登录事件
     *
     * @param accountText  用户账户信息
     * @param passwordText 用户密码信息
     * @param selectUser   角色选择中的普通用户
     */
    public void loginEvent(JTextField accountText, JTextField passwordText,
                           JRadioButton selectUser) {
        // 定义一个值来存储角色信息 0管理员 1普通用户
        int roleInfo;
        // 账户信息
        String userInfo = accountText.getText();
        // 密码信息
        String passwordInfo = passwordText.getText();
        // 角色选择信息
        boolean selectInfo = selectUser.isSelected();
        // 账户信息为空or去掉前后空格也为空
        if (userInfo.isEmpty() || userInfo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "账户不能为空");
            return; // 检测到账户为空时不在继续执行下方代码
        }
        // 密码信息为空or去掉前后空格也为空
        if (passwordInfo.isEmpty() || passwordInfo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "密码不能为空");
            return;// 检测到密码为空时不在继续执行下方代码
        }
        // 如果selectInfo值为true则用户选择的是普通用户false则为管理员用户
        if (selectInfo) {
            roleInfo = 1; // 普通用户
        } else {
            roleInfo = 0; // 管理员
        }

        User user = new User();
        user.setUserAccount(userInfo);
        user.setPassword(passwordInfo);
        user.setRole(roleInfo);

        // 创建一个连接对象
        DatabaseUtils dbConnect = new DatabaseUtils();
        Connection connection = null;
        try {
            // 获取一个数据库连接
            connection = dbConnect.getConnection();
            // 查询用户
            UserDao userDao = new UserDao();
            ResultSet resultSet = userDao.loginUser(connection, user);
            User resultUser = new User();
            // 如果查询的值不为空则往resultUser对象中保存查询到的值
            if (resultSet.next()) {
                resultUser.setId(resultSet.getInt("id"));
                resultUser.setUserAccount(resultSet.getString("user_account"));
                resultUser.setUserName(resultSet.getString("user_name"));
                resultUser.setRole(resultSet.getInt("role"));
                resultUser.setSex(resultSet.getInt("sex"));
                resultUser.setPhone(resultSet.getString("phone"));
            }
            // 如果对象中的值为null则在数据库中未查到该用户
            if (resultUser.getId() == null) {
                JOptionPane.showMessageDialog(null, "账号或密码错误");
            } else {
                // 根据返回值判断是管理员还是普通用户则执行不同的页面
                if (resultUser.getRole() == 0) {
                    System.out.println("管理员");
                } else {
                    this.dispose(); // 关闭登录页面
                    new UserPage(resultUser); // 创建用户页面
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接
                dbConnect.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }

    /**
     * 注册事件
     */
    public void registerEvent() {
        // 关闭登录页面
        this.dispose();
        // 创建注册页面
        new Register();

    }

}
