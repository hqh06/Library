package com.qihang.librarymanage.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {
    public Connection getConnection() throws Exception{
        // 注册数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/library?serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String password = "root";
        // 返回一个连接对象
        return DriverManager.getConnection(url, username, password);
    }

    public void closeConnection(Connection connection) throws SQLException {
        // 关闭连接
        if (connection != null){
            connection.close();
        }

    }
}
