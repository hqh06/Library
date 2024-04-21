package com.qihang.librarymanage.jframe;

import com.qihang.librarymanage.dao.UserDao;
import com.qihang.librarymanage.pojo.User;
import com.qihang.librarymanage.utlis.DatabaseConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.regex.Pattern;

public class Register extends JFrame {
    public Register() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2; // 计算水平位置
        int y = (screenSize.height - getHeight()) / 2; // 计算垂直位置

        setTitle("注册");
        setBounds(x - 300, y - 300, 600, 600); // 让窗口在屏幕居中
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置点击X结束程序
        getContentPane().setLayout(null);

        JLabel registerTitle = new JLabel("用户注册");
        registerTitle.setBounds(230, 50, 200, 50);
        registerTitle.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        getContentPane().add(registerTitle);

        // 用户名
        JLabel userName = new JLabel("用户名:");
        userName.setBounds(130, 120, 80, 30);
        userName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        getContentPane().add(userName);

        JTextField userText = new JTextField();
        userText.setBounds(210, 120, 200, 30);
        userText.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        userText.setName("userName"); // 给文本框设置一个名字方便后面焦点事件取值
        getContentPane().add(userText);
        // 用户名提示
        JLabel userNameTipsOne = new JLabel();
        userNameTipsOne.setBounds(215, 150, 200, 30);
        userNameTipsOne.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        userNameTipsOne.setForeground(new Color(88, 94, 109));
        getContentPane().add(userNameTipsOne);
        // 用户输入格式正确√错误×默认不显示类容
        JLabel userNameTipsTwo = new JLabel();
        userNameTipsTwo.setBounds(420, 120, 20, 30);
        userNameTipsTwo.setFont(new Font("微软雅黑", Font.BOLD, 20));
        getContentPane().add(userNameTipsTwo);
        // 用户名文本框焦点事件
        userText.addFocusListener(new RegisterMyFocusListener(userText, userNameTipsOne, userNameTipsTwo));

        // 账户
        JLabel userAccount = new JLabel("账户:");
        userAccount.setBounds(150, 180, 80, 30);
        userAccount.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        getContentPane().add(userAccount);

        JTextField userAccountText = new JTextField();
        userAccountText.setBounds(210, 180, 200, 30);
        userAccountText.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        getContentPane().add(userAccountText);
        // 用户账号提示
        JLabel userAccountTipsOne = new JLabel();
        userAccountTipsOne.setBounds(215, 210, 200, 30);
        userAccountTipsOne.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        userAccountTipsOne.setForeground(new Color(88, 94, 109));
        getContentPane().add(userAccountTipsOne);

        // 用户账户输入格式正确√错误×默认不显示类容
        JLabel userAccountTipsTwo = new JLabel();
        userAccountTipsTwo.setBounds(420, 180, 20, 30);
        userAccountTipsTwo.setFont(new Font("微软雅黑", Font.BOLD, 20));
        userAccountText.setName("userAccount"); // 给文本框设置一个名字方便后面焦点事件取值
        getContentPane().add(userAccountTipsTwo);
        // 用户账户文本框焦点事件
        userAccountText.addFocusListener(new RegisterMyFocusListener(userAccountText, userAccountTipsOne, userAccountTipsTwo));

        // 密码
        JLabel userPwd = new JLabel("密码:");
        userPwd.setBounds(150, 240, 80, 30);
        userPwd.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        getContentPane().add(userPwd);

        JTextField userPwdText = new JTextField();
        userPwdText.setBounds(210, 240, 200, 30);
        userPwdText.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        userPwdText.setName("userPwd"); // 给文本框设置一个名字方便后面焦点事件取值
        getContentPane().add(userPwdText);
        // 用户密码提示
        JLabel userPwdTipsOne = new JLabel();
        userPwdTipsOne.setBounds(215, 270, 300, 30);
        userPwdTipsOne.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        userPwdTipsOne.setForeground(new Color(88, 94, 109));
        getContentPane().add(userPwdTipsOne);

        // 用户密码输入格式正确√错误×默认不显示类容
        JLabel userPwdTipsTwo = new JLabel();
        userPwdTipsTwo.setBounds(420, 240, 20, 30);
        userPwdTipsTwo.setFont(new Font("微软雅黑", Font.BOLD, 20));
        getContentPane().add(userPwdTipsTwo);
        userPwdText.addFocusListener(new RegisterMyFocusListener(userPwdText, userPwdTipsOne, userPwdTipsTwo));

        // 手机号
        JLabel phoneNumber = new JLabel("手机号:");
        phoneNumber.setBounds(130, 300, 80, 30);
        phoneNumber.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        getContentPane().add(phoneNumber);

        JTextField phoneNumberText = new JTextField();
        phoneNumberText.setBounds(210, 300, 200, 30);
        phoneNumberText.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        phoneNumberText.setName("phoneNumber"); // 给文本框设置一个名字方便后面焦点事件取值
        getContentPane().add(phoneNumberText);
        // 用户手机号提示
        JLabel phoneNumberTipsOne = new JLabel();
        phoneNumberTipsOne.setBounds(215, 330, 200, 30);
        phoneNumberTipsOne.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        phoneNumberTipsOne.setForeground(new Color(88, 94, 109));
        getContentPane().add(phoneNumberTipsOne);

        // 用户手机号输入格式正确√错误×默认不显示类容
        JLabel phoneNumberTipsTwo = new JLabel();
        phoneNumberTipsTwo.setBounds(420, 300, 20, 30);
        phoneNumberTipsTwo.setFont(new Font("微软雅黑", Font.BOLD, 20));
        getContentPane().add(phoneNumberTipsTwo);
        phoneNumberText.addFocusListener(new RegisterMyFocusListener(phoneNumberText, phoneNumberTipsOne, phoneNumberTipsTwo));

        // 性别
        JLabel userGender = new JLabel("性别:");
        userGender.setBounds(150, 360, 80, 30);
        userGender.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        getContentPane().add(userGender);

        JRadioButton selectMan = new JRadioButton("男");
        selectMan.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        selectMan.setBounds(220, 360, 60, 30);
        selectMan.setSelected(true); // 默认用户为第一选项
        JRadioButton selectWoman = new JRadioButton("女");
        selectWoman.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        selectWoman.setBounds(310, 360, 60, 30);
        // 把selectMan和selectWoman加入同一个组实现单选
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(selectMan);
        buttonGroup.add(selectWoman);
        // 加入面板
        getContentPane().add(selectMan);
        getContentPane().add(selectWoman);

        JButton registerButton = new JButton("注册");
        registerButton.setBounds(150, 420, 120, 40);
        registerButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        getContentPane().add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerEvent(userText, userAccountText, userPwdText, phoneNumberText, selectMan);
            }
        });

        JButton loginButton = new JButton("登录");
        loginButton.setBounds(300, 420, 120, 40);
        loginButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        getContentPane().add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginEvent();
            }
        });

        setVisible(true);
    }

    /**
     * 用户注册事件
     *
     * @param userText        用户名
     * @param userAccountText 用户账户
     * @param userPwdText     用户密码
     * @param phoneNumberText 用户手机号
     * @param selectMan       性别
     */
    public void registerEvent(JTextField userText, JTextField userAccountText, JTextField userPwdText,
                              JTextField phoneNumberText, JRadioButton selectMan) {
        // 唉，一行代码写需求十行代码防刁民
        // 在注册前需要再次检查用户输入是否正确
        if (userText.getText().isEmpty() || userAccountText.getText().isEmpty()
                || userPwdText.getText().isEmpty() || phoneNumberText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请输入相关信息");
            return;
        }
        // 不允许包含空格的正则
        boolean userRex = Pattern.matches("^(?!.*\\s).+$", userText.getText());
        if (!userRex) {
            JOptionPane.showMessageDialog(null, "用户名不能有空格");
            return;
        }
        boolean accountRex = Pattern.matches("^[a-zA-Z0-9_]+$", userAccountText.getText());
        if (!accountRex) {
            JOptionPane.showMessageDialog(null, "只能使用字母、数字、下划线");
            return;
        }
        boolean pwdRex = Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*\\s).{8,16}$", userPwdText.getText());
        if (!pwdRex) {
            JOptionPane.showMessageDialog(null, "8-16个字符需包含大、小写字母和数字且不包含空格");
            return;
        }
        boolean phoneNumberRex = Pattern.matches("^1[34578]\\d{9}$", phoneNumberText.getText());
        if (!phoneNumberRex) {
            JOptionPane.showMessageDialog(null, "请输入正确的手机号码");
            return;
        }
        int gender;
        // true为男
        if (selectMan.isSelected()) {
            gender = 1;
        } else {
            gender = 0;
        }
        // new一个User对象存储值
        User user = new User();
        user.setUserName(userText.getText());
        user.setUserAccount(userAccountText.getText());
        user.setPassword(userPwdText.getText());
        user.setPhone(phoneNumberText.getText());
        user.setSex(gender);
        user.setRole(1); // 所有用户默认为普通用户管理员不可注册

        DatabaseConnect databaseConnect = new DatabaseConnect();
        try {
            // 获取一个数据连接
            Connection connection = databaseConnect.getConnection();
            UserDao userDao = new UserDao();
            int result = userDao.registerUser(connection, user);
            // 如果返回值为2代表该账号已经注册
            if (2 == result) {
                JOptionPane.showMessageDialog(null, "该用户已注册");
            } else if (0 == result) {
                JOptionPane.showMessageDialog(null, "注册失败");
            } else {
                JOptionPane.showMessageDialog(null, "注册成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 登录事件
     */
    public void loginEvent() {
        // 关闭注册页面
        this.dispose();
        // 创建登录页面
        new Login();
    }

}


