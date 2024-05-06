package com.qihang.librarymanage.dao;

import com.qihang.librarymanage.pojo.Book;
import com.qihang.librarymanage.pojo.BookBorrowDetail;
import com.qihang.librarymanage.pojo.BorrowDetail;
import com.qihang.librarymanage.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class BorrowDetailDao {
    /**
     * 插入借书信息
     *
     * @param connection   数据库连接对象
     * @param borrowDetail 借书详情实体对象
     * @return 0 书籍已借并未还 1 借书成功
     * @throws SQLException 抛出sql异常
     */
    public int insertDetail(Connection connection, BorrowDetail borrowDetail) throws SQLException {
        // 查询借书详情是否存在
        String sql1 = "select status from borrow_detail where user_id=? and book_id=?";
        PreparedStatement preparedStatementQuery = connection.prepareStatement(sql1);
        preparedStatementQuery.setInt(1, borrowDetail.getUserId());
        preparedStatementQuery.setInt(2, borrowDetail.getBookId());
        ResultSet resultSet = preparedStatementQuery.executeQuery();
        // 查询的结果有可能会有多条，因为同一本书借完后还了，还可以再借
        while (resultSet.next()) {
            int status = resultSet.getInt("status");
            if (status == 0){ // 状态为0代表图书在借状态中
                return 0;
            }
        }
        // 插入借书详情
        String sql = "insert into borrow_detail(user_id, book_id, status, borrow_time) values (?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, borrowDetail.getUserId());
        preparedStatement.setInt(2, borrowDetail.getBookId());
        preparedStatement.setInt(3, borrowDetail.getStatus());
        preparedStatement.setString(4, borrowDetail.getBorrowTime());
        return preparedStatement.executeUpdate();
    }

    public int updateDetail(Connection connection, BorrowDetail borrowDetail) throws SQLException {
        String sql = "update borrow_detail set status=?, return_time=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, borrowDetail.getStatus());
        preparedStatement.setString(2, borrowDetail.getReturnTime());
        preparedStatement.setInt(3, borrowDetail.getId());
        return preparedStatement.executeUpdate();
    }

    /**
     * 根据不同的状态查询相对应的内容
     *
     * @param connection   数据库连接对象
     * @param borrowDetail 借书详情实体对象
     * @param book         书籍实体对象
     * @param user         用户实体对象
     * @return 查询到的所有借书详情
     * @throws SQLException 抛出sql异常
     */

    public ArrayList<BookBorrowDetail> queryBorrowDetail(Connection connection, BorrowDetail borrowDetail, Book book, User user) throws SQLException {
        StringBuilder sql = new StringBuilder("select b.id,book.book_name,b.status,b.borrow_time," +
                "b.return_time from borrow_detail as b " +
                "inner join book on b.book_id=book.id WHERE b.user_id=?");
        // 根据状态查询详情
        if (borrowDetail.getStatus() != null) {
            sql.append(" and b.status=?");
        }
        if (book.getBookName() != null) {
            sql.append(" and book.book_name like ?");
        }
        // 当借阅详情状态为空和书名为空是则代表查询当前用户所有借阅内容
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        // 设置parameterIndex变量来跟踪下一个参数的索引。
        // 每次设置一个参数后，都会增加 parameterIndex 的值
        // 这样无论有多少参数，都可以正确地设置它们
        int parameterIndex = 1;
        preparedStatement.setInt(parameterIndex++, user.getId()); // 设置sql中的用户Id

        // 设置sql中的借书详情状态
        if (borrowDetail.getStatus() != null) {
            preparedStatement.setInt(parameterIndex++, borrowDetail.getStatus());
        }
        // 设置sql中的图书名
        if (book.getBookName() != null) {
            preparedStatement.setString(parameterIndex, "%" + book.getBookName() + "%");
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<BookBorrowDetail> bookBorrowDetails = new ArrayList<>();
        while (resultSet.next()){
            String status = resultSet.getString("status");
            if (status.equals("0")) {
                status = "在借";
            } else {
                status = "已还";
            }
            BookBorrowDetail bookBorrowDetail = new BookBorrowDetail();
            bookBorrowDetail.setId(resultSet.getInt("id"));
            bookBorrowDetail.setBookName(resultSet.getString("book_name"));
            bookBorrowDetail.setStatus(status);
            bookBorrowDetail.setBorrowTime(resultSet.getString("borrow_time"));
            bookBorrowDetail.setReturnTime(resultSet.getString("return_time"));
            bookBorrowDetails.add(bookBorrowDetail);
        }
        return bookBorrowDetails;

    }
}
