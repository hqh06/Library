package com.qihang.librarymanage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookTypeDao {
    /**
     * 添加图书类型
     * @param connection 数据库连接
     * @param category 类别名称
     * @param categoryDescribe 类别描述信息
     * @return 1 成功 2 重复
     * @throws SQLException 插入失败
     */
    public int categoryAdd(Connection connection, String category, String categoryDescribe) throws SQLException {
        // 查询图书类型是否已存在
        String sqlQuery = "select * from book_type where type_name=?";
        PreparedStatement queryStatement = connection.prepareStatement(sqlQuery);
        queryStatement.setString(1, category);
        ResultSet resultSet = queryStatement.executeQuery();
        if (resultSet.next()){
            return 2;
        }

        // 添加图书类型
        String sqlAdd = "insert into book_type(type_name, type_remark) values(?,?)";
        PreparedStatement addStatement = connection.prepareStatement(sqlAdd);
        addStatement.setString(1, category);
        addStatement.setString(2,categoryDescribe);
        return addStatement.executeUpdate();
    }
}
