package com.qihang.librarymanage.utils;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

/**
 * 第三方主题
 */
public class Theme {
    private LookAndFeel style;

    public Theme(int value) {
        // 判断输入的值是否合法
        if (!(value >= 1 && value <= 4)) {
            // 抛出异常
            throw new IllegalArgumentException("Invalid value: " + value);
        }
        // 四种不同的主题
        switch (value) {
            case 1:
                style = new FlatIntelliJLaf();
                break;
            case 2:
                style = new FlatDarculaLaf();
                break;
            case 3:
                style = new FlatLightLaf();
                break;
            case 4:
                style = new FlatDarkLaf();
                break;
        }

        FlatLightLaf.install();
        try {
            UIManager.setLookAndFeel(style);
        } catch (Exception e) {
            System.err.println("主题未初始化");
        }
    }
}
