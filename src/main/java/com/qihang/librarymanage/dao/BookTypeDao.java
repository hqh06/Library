package com.qihang.librarymanage.dao;

import com.qihang.librarymanage.pojo.BookType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookTypeDao {
    /**
     * 添加图书类型
     *
     * @param connection 数据库连接
     * @param bookType   图书类型对象
     * @return 1 成功 2 重复
     * @throws SQLException 插入失败抛出 SQLException
     */
    public int bookTypeAdd(Connection connection, BookType bookType) throws SQLException {
        // 查询图书类型是否已存在
        String sqlQuery = "select * from book_type where type_name=?";
        PreparedStatement queryStatement = connection.prepareStatement(sqlQuery);
        queryStatement.setString(1, bookType.getTypeName());
        ResultSet resultSet = queryStatement.executeQuery();
        if (resultSet.next()) {
            return 2;
        }

        // 添加图书类型
        String sqlAdd = "insert into book_type(type_name, type_remark) values(?,?)";
        PreparedStatement addStatement = connection.prepareStatement(sqlAdd);
        addStatement.setString(1, bookType.getTypeName());
        addStatement.setString(2, bookType.getTypeRemark());
        return addStatement.executeUpdate();
    }


    /**
     * 查询图书类型
     *
     * @param connection 数据库连接对象
     * @param bookType   图书类型对象
     * @return 返回查询结果
     * @throws SQLException 查询失败抛出 SQLException
     */
    public ResultSet bookTypeQuery(Connection connection, BookType bookType) throws SQLException {
        // 定义一个默认的 SQL 查询语句，用于从 book_type 表中选择所有记录
        String sql = "select * from book_type";

        if (bookType.getTypeName() != null && !bookType.getTypeName().isEmpty()) {
            // 根据类型名查询
            sql += " where type_name like ?";
        }


        PreparedStatement preparedStatement = connection.prepareStatement(sql);


        if (bookType.getTypeName() != null && !bookType.getTypeName().isEmpty()) {
            // 如果非空则设置需要查询的类型名
            preparedStatement.setString(1, "%" + bookType.getTypeName() + "%");
        }

        // 执行 SQL 查询，并返回查询结果
        return preparedStatement.executeQuery();
    }

    /**
     * 修改图书类型
     *
     * @param connection 数据库连接对象
     * @param bookType   图书类型对象
     * @return 1 成功 0 失败
     * @throws SQLException 修改失败抛出 SQLException
     */
    public int bookTypeModify(Connection connection, BookType bookType) throws SQLException {
        // 定义一个 SQL 更新语句，用于更新 book_type 表中的记录
        String sql = "update book_type set type_name=?,type_remark=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, bookType.getTypeName());
        preparedStatement.setString(2, bookType.getTypeRemark());
        preparedStatement.setInt(3, bookType.getId());
        return preparedStatement.executeUpdate();

    }
}
