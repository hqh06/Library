package com.qihang.librarymanage.dao;

import com.qihang.librarymanage.pojo.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDao {
    /**
     * 根据用户搜索查询书籍
     * @param connection 数据库连接对象
     * @param book 书籍实体对象
     * @return 查询到所有的书籍
     * @throws SQLException 抛出sql异常
     */

    public ResultSet queryBook(Connection connection, Book book) throws SQLException {
        StringBuilder sql = new StringBuilder("select book.id,book_name,author,publish,number,book_remark,type_name,type_id" +
                " from book join book_type on book.type_id=book_type.id where 1=1 ");
        // 书名不为空根据书名查询类容
        if (book.getBookName() != null){
            sql.append(" and book.book_name like ?");
        }
        // 作者不为空根据作者查询类容
        if (book.getAuthor() != null){
            sql.append(" and book.author like ?");
        }
        // 当书名和作者都为空时则查询所有类容
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        // 设置parameterIndex变量来跟踪下一个参数的索引。
        // 每次设置一个参数后，都会增加 parameterIndex 的值
        // 这样无论有多少参数，都可以正确地设置它们
        int parameterIndex = 1;

        if (book.getBookName() != null){
            preparedStatement.setString(parameterIndex++, "%" + book.getBookName() + "%");
        }
        if (book.getAuthor() != null){
            preparedStatement.setString(parameterIndex, "%" + book.getAuthor() + "%");
        }

        return preparedStatement.executeQuery();
    }


    public int addBook(Connection connection, Book book) throws SQLException {
        String querySql = "select * from book where book_name=?";
        PreparedStatement qeryPreparedStatement = connection.prepareStatement(querySql);
        qeryPreparedStatement.setString(1, book.getBookName());
        ResultSet resultSet = qeryPreparedStatement.executeQuery();
        if (resultSet.next()){
            return 2;
        }
        String sql = "insert into book(book_name,author,publish,number,book_remark,type_id) values(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, book.getBookName());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getPublish());
        preparedStatement.setInt(4, book.getNumber());
        preparedStatement.setString(5, book.getBookRemark());
        preparedStatement.setInt(6, book.getTypeId());
        return preparedStatement.executeUpdate();

    }

    public int modifyBook(Connection connection, Book book) throws SQLException {
        String sql = "update book set book_name=?, author=?, publish=?, number=?, book_remark=?, type_id=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, book.getBookName());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getPublish());
        preparedStatement.setInt(4, book.getNumber());
        preparedStatement.setString(5, book.getBookRemark());
        preparedStatement.setInt(6, book.getTypeId());
        preparedStatement.setInt(7, book.getId());
        return preparedStatement.executeUpdate();

    }

    public int deleteBook(Connection connection, Book book) throws SQLException {
        String sql = "delete from book where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, book.getId());
        return preparedStatement.executeUpdate();
    }

}
