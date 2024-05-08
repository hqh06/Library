package com.qihang.librarymanage.dao;

import com.qihang.librarymanage.pojo.BookType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookTypeDao {
    /**
     * 这个方法用于在数据库中添加一个新的图书类型。
     * 首先，它会检查图书类型是否已存在。如果图书类型已存在，那么它会返回2。
     * 如果图书类型不存在，那么它会在数据库中添加一个新的图书类型。
     *
     * @param connection 数据库连接对象
     * @param bookType   包含要添加的图书类型的图书类型对象
     * @return 如果图书类型已存在，返回2；否则，返回执行插入操作后影响的行数
     * @throws SQLException 如果执行查询或插入操作时发生错误
     */
    public int addBookType(Connection connection, BookType bookType) throws SQLException {
        // 定义一个查询SQL语句，用于检查图书类型是否已存在
        String sqlQuery = "select * from book_type where type_name=?";

        PreparedStatement queryStatement = connection.prepareStatement(sqlQuery);
        queryStatement.setString(1, bookType.getTypeName());
        ResultSet resultSet = queryStatement.executeQuery();
        // 如果结果集中有数据，说明图书类型已存在，返回2
        if (resultSet.next()) {
            return 2;
        }

        // 定义一个插入SQL语句，用于添加新的图书类型
        String sqlAdd = "insert into book_type(type_name, type_remark) values(?,?)";
        PreparedStatement addStatement = connection.prepareStatement(sqlAdd);
        addStatement.setString(1, bookType.getTypeName());
        addStatement.setString(2, bookType.getTypeRemark());
        // 执行插入操作，返回影响的行数
        return addStatement.executeUpdate();
    }

    /**
     * 这个方法用于从数据库中删除指定的图书类型。
     * 最后，执行这个SQL语句，并返回执行结果，即删除的行数。
     *
     * @param connection 数据库连接对象
     * @param bookType   包含了要删除的图书类型信息的对象
     * @return 返回删除的行数
     * @throws SQLException 如果在执行SQL语句时出现错误，会抛出SQLException
     */
    public int deleteBookType(Connection connection, BookType bookType) throws SQLException {
        // 创建一个SQL语句，用于删除指定ID的图书类型
        String sql = "delete from book_type where id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        // 设置要删除的图书类型的ID
        stmt.setInt(1, bookType.getId());
        // 执行SQL语句，并返回删除的行数
        return stmt.executeUpdate();
    }

    /**
     * 这个方法用于在数据库中修改图书类型。
     * 它会根据提供的图书类型对象，更新 book_type 表中的相应记录。
     *
     * @param connection 数据库连接对象
     * @param bookType   包含要修改的图书类型的图书类型对象
     * @return 返回执行更新操作后影响的行数
     * @throws SQLException 如果执行更新操作时发生错误
     */
    public int modifyBookType(Connection connection, BookType bookType) throws SQLException {
        // 定义一个 SQL 更新语句，用于更新 book_type 表中的记录
        String sql = "update book_type set type_name=?,type_remark=? where id=?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, bookType.getTypeName());
        stmt.setString(2, bookType.getTypeRemark());
        stmt.setInt(3, bookType.getId());
        // 执行更新操作，返回影响的行数
        return stmt.executeUpdate();
    }

    /**
     * 这个方法用于在数据库中查询图书类型。
     * 它会根据提供的图书类型名，从 book_type 表中选择匹配的记录。
     * 如果找到匹配的记录，那么它会返回一个包含这些记录的图书类型列表。
     *
     * @param connection 数据库连接对象
     * @param bookType   包含图书类型名的图书类型对象
     * @return 返回查询到的图书类型列表
     * @throws SQLException 如果执行查询操作时发生错误
     */
    public ArrayList<BookType> queryBookType(Connection connection, BookType bookType) throws SQLException {
        // 定义一个默认的 SQL 查询语句，用于从 book_type 表中选择所有记录
        String sql = "select * from book_type";

        // 如果类型名不为空，添加类型名查询条件
        if (bookType.getTypeName() != null && !bookType.getTypeName().isEmpty()) {
            sql += " where type_name like ?";
        }

        PreparedStatement stmt = connection.prepareStatement(sql);

        // 如果类型名不为空，设置类型名查询参数
        if (bookType.getTypeName() != null && !bookType.getTypeName().isEmpty()) {
            stmt.setString(1, "%" + bookType.getTypeName() + "%");
        }

        ResultSet resultSet = stmt.executeQuery();

        // 创建一个ArrayList对象，用于存储查询结果
        ArrayList<BookType> bookTypes = new ArrayList<>();

        // 遍历结果集，获取查询到的所有图书类型
        while (resultSet.next()) {
            BookType bookTypeTemp = new BookType();
            bookTypeTemp.setId(resultSet.getInt("id"));
            bookTypeTemp.setTypeName(resultSet.getString("type_name"));
            bookTypeTemp.setTypeRemark(resultSet.getString("type_remark"));
            // 将查询到的图书类型添加到ArrayList中
            bookTypes.add(bookTypeTemp);
        }

        // 返回查询结果
        return bookTypes;
    }


}
