package com.qihang.librarymanage.dao;

import com.qihang.librarymanage.pojo.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDao {
    /**
     * 这个方法用于在数据库中添加一个新的图书。
     * 首先，它会检查图书名是否已存在。如果图书名已存在，那么它会返回2。
     * 如果图书名不存在，那么它会在数据库中添加一个新的图书。
     *
     * @param connection 数据库连接对象
     * @param book       包含要添加的图书信息的图书对象
     * @return 如果图书名已存在，返回2；否则，返回执行插入操作后影响的行数
     * @throws SQLException 如果执行查询或插入操作时发生错误
     */
    public int addBook(Connection connection, Book book) throws SQLException {
        // 定义一个查询SQL语句，用于检查书名是否已存在
        String querySql = "select * from book where book_name=?";

        PreparedStatement qeryPreparedStatement = connection.prepareStatement(querySql);
        qeryPreparedStatement.setString(1, book.getBookName());
        // 执行查询，返回结果集
        ResultSet resultSet = qeryPreparedStatement.executeQuery();
        // 如果结果集中有数据，说明书名已存在，返回2
        if (resultSet.next()) {
            return 2;
        }
        // 定义一个插入SQL语句，用于添加新的书籍
        String sql = "insert into book(book_name,author,publish,number,book_remark,type_id) values(?,?,?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, book.getBookName());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getPublish());
        stmt.setInt(4, book.getNumber());
        stmt.setString(5, book.getBookRemark());
        stmt.setInt(6, book.getTypeId());
        // 执行插入操作，返回影响的行数
        return stmt.executeUpdate();
    }

    /**
     * 这个方法用于在数据库中删除图书。
     * 它会根据提供的图书对象的ID，从 book 表中删除相应的记录。
     *
     * @param connection 数据库连接对象
     * @param book       包含要删除的图书ID的图书对象
     * @return 返回执行删除操作后影响的行数
     * @throws SQLException 如果执行删除操作时发生错误
     */
    public int deleteBook(Connection connection, Book book) throws SQLException {
        // 定义一个删除SQL语句，用于删除书籍
        String sql = "delete from book where id=?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, book.getId());
        // 执行删除操作，返回影响的行数
        return stmt.executeUpdate();
    }

    /**
     * 这个方法用于在数据库中修改图书的信息。
     * 它会根据提供的图书对象，更新 book 表中的相应记录。
     *
     * @param connection 数据库连接对象
     * @param book       包含要修改的图书信息的图书对象
     * @return 返回执行更新操作后影响的行数
     * @throws SQLException 如果执行更新操作时发生错误
     */
    public int modifyBook(Connection connection, Book book) throws SQLException {
        // 定义一个更新SQL语句，用于修改书籍的信息
        String sql = "update book set book_name=?, author=?, publish=?, number=?, book_remark=?, type_id=? where id=?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        // 设置更新参数，这里是书籍的各种属性
        stmt.setString(1, book.getBookName());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getPublish());
        stmt.setInt(4, book.getNumber());
        stmt.setString(5, book.getBookRemark());
        stmt.setInt(6, book.getTypeId());
        stmt.setInt(7, book.getId());
        // 执行更新操作，返回影响的行数
        return stmt.executeUpdate();
    }

    /**
     * 这个方法用于在数据库中查询图书。
     * 它会根据提供的图书名和作者，从 book 表中选择匹配的记录。
     * 如果找到匹配的记录，那么它会返回一个包含这些记录的图书列表。
     *
     * @param connection 数据库连接对象
     * @param book       包含图书名和作者的图书对象
     * @return 返回查询到的图书列表
     * @throws SQLException 如果执行查询操作时发生错误
     */
    public ArrayList<Book> queryBook(Connection connection, Book book) throws SQLException {
        // 创建一个StringBuilder对象，用于构建SQL查询语句
        StringBuilder sql = new StringBuilder("select * from book where 1=1");
        // 如果书名不为空，添加书名查询条件
        if (book.getBookName() != null) {
            sql.append(" and book_name like ?");
        }
        // 如果作者不为空，添加作者查询条件
        if (book.getAuthor() != null) {
            sql.append(" and author like ?");
        }

        PreparedStatement stmt = connection.prepareStatement(sql.toString());
        // 创建一个parameterIndex变量，用于跟踪下一个参数的索引
        int parameterIndex = 1;
        // 如果书名不为空，设置书名查询参数
        if (book.getBookName() != null) {
            stmt.setString(parameterIndex++, "%" + book.getBookName() + "%");
        }
        // 如果作者不为空，设置作者查询参数
        if (book.getAuthor() != null) {
            stmt.setString(parameterIndex, "%" + book.getAuthor() + "%");
        }
        // 执行查询，返回结果集
        ResultSet resultSet = stmt.executeQuery();
        // 创建一个ArrayList对象，用于存储查询结果
        ArrayList<Book> books = new ArrayList<>();
        // 遍历结果集，获取查询到的所有书籍
        while (resultSet.next()) {
            Book bookTemp = new Book();
            bookTemp.setId(resultSet.getInt("id"));
            bookTemp.setBookName(resultSet.getString("book_name"));
            bookTemp.setAuthor(resultSet.getString("author"));
            bookTemp.setPublish(resultSet.getString("publish"));
            bookTemp.setTypeId(resultSet.getInt("type_id"));
            bookTemp.setNumber(resultSet.getInt("number"));
            bookTemp.setBookRemark(resultSet.getString("book_remark"));
            // 将查询到的书籍添加到ArrayList中
            books.add(bookTemp);
        }
        // 返回查询结果
        return books;
    }

}
