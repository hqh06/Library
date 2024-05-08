package com.qihang.librarymanage.dao;

import com.qihang.librarymanage.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    /**
     * 这个方法用于在数据库中登录用户。
     * 它会根据提供的用户账户、密码和角色，从 user 表中选择匹配的记录。
     * 如果找到匹配的记录，那么它会返回一个包含这些记录的用户列表。
     *
     * @param connection 数据库连接对象
     * @param user       包含用户账户、密码和角色的用户对象
     * @return 返回查询到的用户列表
     * @throws SQLException 如果执行查询操作时发生错误
     */
    public ArrayList<User> loginUser(Connection connection, User user) throws SQLException {
        // 定义一个 SQL 查询语句，用于从 user 表中选择匹配的记录
        String sql = "select * from user where user_account=? and password=? and role=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        // 设置查询参数，这里是用户的账户名、密码和角色
        stmt.setString(1, user.getUserAccount());
        stmt.setString(2, user.getPassword());
        stmt.setInt(3, user.getRole());
        // 执行查询，返回结果集
        ResultSet resultSet = stmt.executeQuery();
        // 创建一个ArrayList对象，用于存储查询结果
        ArrayList<User> users = new ArrayList<>();
        // 遍历结果集，获取查询到的所有用户信息
        while (resultSet.next()) {
            User userTemp = new User();
            userTemp.setId(resultSet.getInt("id"));
            userTemp.setUserAccount(resultSet.getString("user_account"));
            userTemp.setUserName(resultSet.getString("user_name"));
            userTemp.setRole(resultSet.getInt("role"));
            userTemp.setSex(resultSet.getInt("sex"));
            userTemp.setPhone(resultSet.getString("phone"));
            // 将查询到的用户信息添加到ArrayList中
            users.add(userTemp);
        }
        // 返回查询结果
        return users;
    }

    /**
     * 这个方法用于在数据库中注册一个新的用户。
     * 首先，它会检查用户账户是否已经注册。如果用户账户已经注册，那么它会返回2。
     * 如果用户账户未注册，那么它会在数据库中添加一个新的用户。
     *
     * @param connection 数据库连接对象
     * @param user       要注册的用户对象
     * @return 如果用户账户已经注册，返回2；否则，返回执行插入操作后影响的行数
     * @throws SQLException 如果执行查询或插入操作时发生错误
     */
    public int registerUser(Connection connection, User user) throws SQLException {
        // 定义一个查询SQL语句，用于检查用户账户是否已经注册
        String findSql = "select * from user where user_account=?";
        PreparedStatement queryStmt = connection.prepareStatement(findSql);
        // 设置查询参数，这里是用户账户
        queryStmt.setString(1, user.getUserAccount());
        // 执行查询，返回结果集
        ResultSet resultSet = queryStmt.executeQuery();
        // 如果结果集中有数据，说明用户账户已存在，返回2
        if (resultSet.next()) {
            return 2;
        }

        // 定义一个插入SQL语句，用于添加新的用户
        String addSql = "insert into user (user_name,user_account,password,role,sex,phone) value (?,?,?,?,?,?)";
        // 创建一个PreparedStatement对象，用于执行插入SQL语句
        PreparedStatement stmt = connection.prepareStatement(addSql);
        // 设置插入参数，这里是用户的各种属性
        stmt.setString(1, user.getUserName());
        stmt.setString(2, user.getUserAccount());
        stmt.setString(3, user.getPassword());
        stmt.setInt(4, user.getRole());
        stmt.setInt(5, user.getSex());
        stmt.setString(6, user.getPhone());
        // 执行插入操作，返回影响的行数
        return stmt.executeUpdate();
    }

    /**
     * 这个方法用于向数据库中添加一个新的用户。
     * 首先，它会检查用户是否已经存在。如果用户已经存在，那么它会返回2。
     * 如果用户不存在，那么它会创建一个新的用户，并将其添加到数据库中。
     *
     * @param connection 数据库连接对象
     * @param user       要添加的用户对象
     * @return 如果用户已经存在，返回2；否则，返回执行更新操作后影响的行数
     * @throws SQLException 如果执行查询或更新操作时发生错误
     */
    public int addUser(Connection connection, User user) throws SQLException {
        // 查询SQL语句，用于检查用户是否已经存在
        String querySql = "select * from user where user_account=?";
        PreparedStatement queryPreparedStatement = connection.prepareStatement(querySql);
        queryPreparedStatement.setString(1, user.getUserAccount());
        ResultSet resultSet = queryPreparedStatement.executeQuery();
        // 如果用户已经存在，返回2
        if (resultSet.next()) {
            return 2;
        }

        // 插入SQL语句，用于添加新的用户
        String sql = "insert into user(user_account, user_name, password, role, sex, phone) values (?,?,?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, user.getUserAccount());
        stmt.setString(2, user.getUserName());
        stmt.setString(3, user.getPassword());
        stmt.setInt(4, user.getRole());
        stmt.setInt(5, user.getSex());
        stmt.setString(6, user.getPhone());
        // 返回执行更新操作后影响的行数
        return stmt.executeUpdate();
    }

    /**
     * 这个方法用于从数据库中删除一个用户。
     * 它会根据提供的用户ID，执行删除操作。
     *
     * @param connection 数据库连接对象
     * @param user       要删除的用户对象
     * @return 返回执行删除操作后影响的行数
     * @throws SQLException 如果执行删除操作时发生错误
     */
    public int deleteUser(Connection connection, User user) throws SQLException {
        // 删除SQL语句，用于删除指定ID的用户
        String sql = "delete from user where id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, user.getId());
        // 返回执行删除操作后影响的行数
        return stmt.executeUpdate();
    }

    /**
     * 这个方法用于修改数据库中的一个用户。
     * 首先，它会检查除当前用户外是否存在具有相同用户账号的用户。如果存在，那么它会返回2。
     * 如果不存在，那么它会更新用户的信息。
     *
     * @param connection 数据库连接对象
     * @param user       要修改的用户对象
     * @return 如果存在具有相同用户账号的用户，返回2；否则，返回执行更新操作后影响的行数
     * @throws SQLException 如果执行查询或更新操作时发生错误
     */
    public int modifyUser(Connection connection, User user) throws SQLException {
        // 查询SQL语句，用于检查除当前用户外是否存在具有相同用户账号的用户
        String querySql = "select * from user where user_account=? and id !=?";
        PreparedStatement queryPreparedStatement = connection.prepareStatement(querySql);
        queryPreparedStatement.setString(1, user.getUserAccount());
        queryPreparedStatement.setInt(2, user.getId());
        ResultSet resultSet = queryPreparedStatement.executeQuery();
        // 如果存在具有相同用户账号的用户，返回2
        if (resultSet.next()) {
            return 2;
        }

        // 更新SQL语句，用于修改用户的信息
        String sql = "update user set user_name=?, user_account=?, sex=?, role=?, phone=? where id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, user.getUserName());
        stmt.setString(2, user.getUserAccount());
        stmt.setInt(3, user.getSex());
        stmt.setInt(4, user.getRole());
        stmt.setString(5, user.getPhone());
        stmt.setInt(6, user.getId());
        // 返回执行更新操作后影响的行数
        return stmt.executeUpdate();
    }

    /**
     * 这个方法用于查询数据库中的用户。
     * 它会根据提供的用户名进行模糊查询。如果没有提供用户名，那么它会查询所有的用户。
     *
     * @param connection 数据库连接对象
     * @param user       包含要查询的用户名的用户对象
     * @return 返回查询到的用户列表
     * @throws SQLException 如果执行查询操作时发生错误
     */
    public ArrayList<User> queryUser(Connection connection, User user) throws SQLException {
        // 查询SQL语句，用于查询用户
        String sql = "select * from user";
        // 如果提供了用户名，那么在SQL语句中添加条件
        if (user.getUserName() != null && !user.getUserName().isEmpty()) {
            sql += " where user_name like ?";
        }
        PreparedStatement stmt = connection.prepareStatement(sql);
        // 如果提供了用户名，那么设置查询参数
        if (user.getUserName() != null && !user.getUserName().isEmpty()) {
            stmt.setString(1, "%" + user.getUserName() + "%");
        }

        ResultSet resultSet = stmt.executeQuery();
        // 创建用户列表
        ArrayList<User> users = new ArrayList<>();
        // 遍历查询结果
        while (resultSet.next()) {
            // 创建用户对象，并设置属性
            User userTemp = new User();
            userTemp.setId(resultSet.getInt("id"));
            userTemp.setUserAccount(resultSet.getString("user_account"));
            userTemp.setUserName(resultSet.getString("user_name"));
            userTemp.setRole(resultSet.getInt("role"));
            userTemp.setSex(resultSet.getInt("sex"));
            userTemp.setPhone(resultSet.getString("phone"));
            // 将用户对象添加到列表中
            users.add(userTemp);
        }
        // 返回用户列表
        return users;
    }

}
