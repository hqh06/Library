package com.qihang.librarymanage.dao;

import com.qihang.librarymanage.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    /**
     * 登录校验
     *
     * @param connection 数据库的连接对象
     * @param user       实体类的对象
     * @return 返回一个User对象
     * @throws SQLException 抛出sql异常
     */
    public ArrayList<User> loginUser(Connection connection, User user) throws SQLException {
        String sql = "select * from user where user_account=? and password=? and role=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUserAccount());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getRole());
        // 查询
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<User> users = new ArrayList<>();
        // 如果查询的值不为空则往resultUser对象中保存查询到的值
        while (resultSet.next()) {
            User userTemp = new User();
            userTemp.setId(resultSet.getInt("id"));
            userTemp.setUserAccount(resultSet.getString("user_account"));
            userTemp.setUserName(resultSet.getString("user_name"));
            userTemp.setRole(resultSet.getInt("role"));
            userTemp.setSex(resultSet.getInt("sex"));
            userTemp.setPhone(resultSet.getString("phone"));
            users.add(userTemp);
        }
        return users;

    }

    /**
     * 注册校验
     * @param connection 数据库的连接对象
     * @param user       实体类的对象
     * @return 0注册失败  1注册成功 2代表已注册
     * @throws SQLException 抛出sql异常
     */

    public int registerUser(Connection connection, User user) throws SQLException {
        // 查询账户是否已经注册
        String findSql = "select * from user where user_account=?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(findSql);
        preparedStatement1.setString(1, user.getUserAccount());
        ResultSet resultSet = preparedStatement1.executeQuery();
        if (resultSet.next()) {
            return 2;
        }
        String addSql = "insert into user (user_name,user_account,password,role,sex,phone) value (?,?,?,?,?,?)";
        PreparedStatement preparedStatement2 = connection.prepareStatement(addSql);
        preparedStatement2.setString(1, user.getUserName());
        preparedStatement2.setString(2, user.getUserAccount());
        preparedStatement2.setString(3, user.getPassword());
        preparedStatement2.setInt(4, user.getRole());
        preparedStatement2.setInt(5, user.getSex());
        preparedStatement2.setString(6, user.getPhone());
        // 返回0:当执行的SQL语句没有影响任何行
        // 返回正整数:当执行的SQL语句影响了一定数量的行
        return preparedStatement2.executeUpdate();
    }
}
