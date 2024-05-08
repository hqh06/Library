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
     *
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

    /**
     * 用户查询
     *
     * @param connection 数据库的连接对象
     * @param user       实体类的对象
     * @return 返回一个User对象
     * @throws SQLException 抛出sql异常
     */
    public ArrayList<User> queryUser(Connection connection, User user) throws SQLException {
        String sql = "select * from user";
        if (user.getUserName() != null && !user.getUserName().isEmpty()) {
            sql += " where user_name like ?";
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (user.getUserName() != null && !user.getUserName().isEmpty()) {
            preparedStatement.setString(1, "%" + user.getUserName() + "%");
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<User> users = new ArrayList<>();
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
     * 用户添加
     *
     * @param connection 数据库的连接对象
     * @param user       实体类的对象
     * @return 1 成功 2 重复
     * @throws SQLException 抛出sql异常
     */

    public int addUser(Connection connection, User user) throws SQLException {
        String querySql = "select * from user where user_account=?";
        PreparedStatement queryPreparedStatement = connection.prepareStatement(querySql);
        queryPreparedStatement.setString(1, user.getUserAccount());
        ResultSet resultSet = queryPreparedStatement.executeQuery();
        if (resultSet.next()) {
            return 2;
        }

        String sql = "insert into user(user_account, user_name, password, role, sex, phone) values (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUserAccount());
        preparedStatement.setString(2, user.getUserName());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setInt(4, user.getRole());
        preparedStatement.setInt(5, user.getSex());
        preparedStatement.setString(6, user.getPhone());
        return preparedStatement.executeUpdate();
    }

    /**
     * 用户修改
     *
     * @param connection 数据库的连接对象
     * @param user       实体类的对象
     * @return 1 成功 2 重复
     * @throws SQLException 抛出sql异常
     */
    public int modifyUser(Connection connection, User user) throws SQLException {
        String querySql = "select * from user where user_account=? and id !=?";
        PreparedStatement queryPreparedStatement = connection.prepareStatement(querySql);
        queryPreparedStatement.setString(1, user.getUserAccount());
        queryPreparedStatement.setInt(2, user.getId());
        ResultSet resultSet = queryPreparedStatement.executeQuery();
        if (resultSet.next()) {
            return 2;
        }

        String sql = "update user set user_name=?, user_account=?, sex=?, role=?, phone=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getUserAccount());
        preparedStatement.setInt(3, user.getSex());
        preparedStatement.setInt(4, user.getRole());
        preparedStatement.setString(5, user.getPhone());
        preparedStatement.setInt(6, user.getId());
        return preparedStatement.executeUpdate();
    }

    /**
     * 用户删除
     *
     * @param connection 数据库的连接对象
     * @param user       实体类的对象
     * @return 1 成功 2 重复
     * @throws SQLException 抛出sql异常
     */
    public int deleteUser(Connection connection, User user) throws SQLException {
        String sql = "delete from user where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user.getId());
        return preparedStatement.executeUpdate();
    }
}
