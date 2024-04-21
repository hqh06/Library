package com.qihang.librarymanage.jframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Pattern;

/**
 * 这个类主要用于注册页面文本框
 * 获取焦点失去焦点所显示的不同类容
 */
class RegisterMyFocusListener implements FocusListener {
    private final JTextField jTextField;
    private final JLabel jLabelOne;
    private final JLabel jLabelTwo;
    private String pwdContent = "需包含大、小写字母和数字"; // 之所以定义这变量是为了方便根据不同的场景实现不同的内容

    /**
     * 自建焦点事件
     *
     * @param jTextField 文本框对象
     * @param jLabelOne  提示文本
     * @param jLabelTwo  提示状态
     */
    public RegisterMyFocusListener(JTextField jTextField, JLabel jLabelOne, JLabel jLabelTwo) {
        this.jTextField = jTextField;
        this.jLabelOne = jLabelOne;
        this.jLabelTwo = jLabelTwo;
    }

    // 获取焦点（当鼠标光标点击进入文本框时发生的事件）
    @Override
    public void focusGained(FocusEvent e) {
        // 根据不同的文本框实现不同的任务
        switch (jTextField.getName()) {
            case "userName": {
                jLabelOne.setText("用户名不能有空格");
                break;
            }
            case "userAccount": {
                jLabelOne.setText("只能使用字母、数字、下划线");
                break;
            }
            case "userPwd": {
                jLabelOne.setText(pwdContent);
                break;
            }
            case "phoneNumber": {
                jLabelOne.setText("仅支持大陆手机号");
                break;
            }
        }
    }

    // 失去焦点（当鼠标光标不在文本框内时发生的事件）
    @Override
    public void focusLost(FocusEvent e) {
        // 根据不同的文本框实现不同的任务
        switch (jTextField.getName()) {
            case "userName": {
                // 不允许包含空格的正则
                boolean matches = Pattern.matches("^(?!.*\\s).+$", jTextField.getText());
                judgment(matches);
                break;
            }
            case "userAccount": {
                boolean matches = Pattern.matches("^[a-zA-Z0-9_]+$", jTextField.getText());
                judgment(matches);
                break;
            }
            case "userPwd": {
                // 之所以写把正则拆开写是因为显示不了太多字符只能拆开写以便提升用户的体验
                // 包含大小写字母和数字的正则
                boolean matches1 = Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", jTextField.getText());
                if (this.jTextField.getText().trim().isEmpty() || !matches1) {
                    this.jLabelTwo.setText("×");
                    this.jLabelTwo.setForeground(Color.red);
                    this.pwdContent = "需包含大、小写字母和数字";
                    this.jLabelOne.setText("需包含大、小写字母和数字");
                    this.jLabelOne.setForeground(Color.red);
                    return;
                } else {
                    // 当规则匹配正确时提示文本消失颜色设为原本的颜色防止用户再次点击时还是红色
                    this.jLabelOne.setText("");
                    this.jLabelOne.setForeground(new Color(88, 94, 109));
                    // 匹配正确设置状态位√
                    this.jLabelTwo.setText("√");
                    this.jLabelTwo.setForeground(Color.green);
                    this.pwdContent = "需包含大、小写字母和数字";
                }
                // 长度8-16位的正则
                boolean matches2 = Pattern.matches("^.{8,16}$", jTextField.getText());

                if (!matches2) {
                    this.jLabelTwo.setText("×");
                    this.jLabelTwo.setForeground(Color.red);
                    this.pwdContent = "密码长度应为8-16个字符";
                    this.jLabelOne.setText("密码长度应为8-16个字符");
                    this.jLabelOne.setForeground(Color.red);
                    return;
                }
                // 不包含空格的正则
                boolean matches3 = Pattern.matches("^(?!.*\\s).+$", jTextField.getText());
                if (!matches3) {
                    this.jLabelTwo.setText("×");
                    this.jLabelTwo.setForeground(Color.red);
                    this.pwdContent = "密码不能包含空格";
                    this.jLabelOne.setText("密码不能包含空格");
                    this.jLabelOne.setForeground(Color.red);
                }
                break;
            }
            case "phoneNumber": {
                // 手机号正则
                boolean matches = Pattern.matches("^1[34578]\\d{9}$", jTextField.getText());
                judgment(matches);
                break;
            }
        }
    }

    public void judgment(boolean matches) {
        // 判断用户名输入是否为空(去掉字符前后空格)
        if (this.jTextField.getText().trim().isEmpty() || !matches) {
            this.jLabelTwo.setText("×");
            this.jLabelTwo.setForeground(Color.red);
            this.jLabelOne.setForeground(Color.red);
        } else {
            // 当规则匹配正确时提示文本消失颜色设为原本的颜色防止用户再次点击时还是红色
            this.jLabelOne.setText("");
            this.jLabelOne.setForeground(new Color(88, 94, 109));
            // 匹配正确设置状态位√
            this.jLabelTwo.setText("√");
            this.jLabelTwo.setForeground(Color.green);
        }
    }

}

