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


public class BorrowDetailDao {
    /**
     * 这个方法用于在数据库中添加一个新的借书详情。
     * 首先，它会检查借书详情是否已存在。如果借书详情已存在并且图书在借阅状态中，那么它会返回0。
     * 如果借书详情不存在或者图书不在借阅状态中，那么它会在数据库中添加一个新的借书详情。
     *
     * @param connection   数据库连接对象
     * @param borrowDetail 包含要添加的借书详情的借书详情对象
     * @return 如果借书详情已存在并且图书在借阅状态中，返回0；否则，返回执行插入操作后影响的行数
     * @throws SQLException 如果执行查询或插入操作时发生错误
     */
    public int addDetail(Connection connection, BorrowDetail borrowDetail) throws SQLException {
        // 定义一个查询SQL语句，用于检查借书详情是否已存在
        String sql1 = "select status from borrow_detail where user_id=? and book_id=?";
        PreparedStatement stmtQuery = connection.prepareStatement(sql1);
        // 设置查询参数，这里是用户ID和图书ID
        stmtQuery.setInt(1, borrowDetail.getUserId());
        stmtQuery.setInt(2, borrowDetail.getBookId());
        // 执行查询，返回结果集
        ResultSet resultSet = stmtQuery.executeQuery();
        // 遍历结果集，检查图书的借阅状态
        while (resultSet.next()) {
            int status = resultSet.getInt("status");
            // 如果状态为0，表示图书在借阅状态中，返回0
            if (status == 0) {
                return 0;
            }
        }
        // 定义一个插入SQL语句，用于添加新的借书详情
        String sql = "insert into borrow_detail(user_id, book_id, status, borrow_time) values (?,?,?,?)";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, borrowDetail.getUserId());
        stmt.setInt(2, borrowDetail.getBookId());
        stmt.setInt(3, borrowDetail.getStatus());
        stmt.setString(4, borrowDetail.getBorrowTime());
        // 执行插入操作，返回影响的行数
        return stmt.executeUpdate();
    }


    /**
     * 这个方法用于在数据库中修改借书详情。
     * 它会根据提供的借书详情对象，更新 borrow_detail 表中的相应记录。
     *
     * @param connection   数据库连接对象
     * @param borrowDetail 包含要修改的借书详情的借书详情对象
     * @return 返回执行更新操作后影响的行数
     * @throws SQLException 如果执行更新操作时发生错误
     */
    public int modifyDetail(Connection connection, BorrowDetail borrowDetail) throws SQLException {
        // 定义一个 SQL 更新语句，用于更新 borrow_detail 表中的记录
        String sql = "update borrow_detail set status=?, return_time=? where id=?";

        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setInt(1, borrowDetail.getStatus());
        stmt.setString(2, borrowDetail.getReturnTime());
        stmt.setInt(3, borrowDetail.getId());
        // 执行更新操作，返回影响的行数
        return stmt.executeUpdate();
    }


    /**
     * 这个方法用于查询数据库中的借书详情。
     * 它会根据提供的用户ID、借书详情的状态和图书的书名，从 borrow_detail 表和 book 表中选择匹配的记录。
     * 如果找到匹配的记录，那么它会返回一个包含这些记录的借书详情列表。
     *
     * @param connection   数据库连接对象
     * @param borrowDetail 包含借书详情状态的借书详情对象
     * @param book         包含书名的图书对象
     * @param user         包含用户ID的用户对象
     * @return 返回查询到的借书详情列表
     * @throws SQLException 如果执行查询操作时发生错误
     */
    public ArrayList<BookBorrowDetail> queryBorrowDetail(Connection connection, BorrowDetail borrowDetail, Book book, User user) throws SQLException {
        // 创建一个StringBuilder对象，用于构建SQL查询语句
        StringBuilder sql = new StringBuilder("select b.id,book.book_name,b.status,b.borrow_time," +
                "b.return_time from borrow_detail as b " +
                "inner join book on b.book_id=book.id WHERE b.user_id=?");
        // 如果借书详情的状态不为空，添加状态查询条件
        if (borrowDetail.getStatus() != null) {
            sql.append(" and b.status=?");
        }
        // 如果图书的书名不为空，添加书名查询条件
        if (book.getBookName() != null) {
            sql.append(" and book.book_name like ?");
        }

        PreparedStatement stmt = connection.prepareStatement(sql.toString());
        // 创建一个parameterIndex变量，用于跟踪下一个参数的索引
        int parameterIndex = 1;
        // 设置用户ID查询参数
        stmt.setInt(parameterIndex++, user.getId());
        // 如果借书详情的状态不为空，设置状态查询参数
        if (borrowDetail.getStatus() != null) {
            stmt.setInt(parameterIndex++, borrowDetail.getStatus());
        }
        // 如果图书的书名不为空，设置书名查询参数
        if (book.getBookName() != null) {
            stmt.setString(parameterIndex, "%" + book.getBookName() + "%");
        }
        // 执行 SQL 查询，并返回结果集
        ResultSet resultSet = stmt.executeQuery();
        // 创建一个ArrayList对象，用于存储查询结果
        ArrayList<BookBorrowDetail> bookBorrowDetails = new ArrayList<>();
        // 遍历结果集，获取查询到的所有借书详情
        while (resultSet.next()) {
            String status = resultSet.getString("status");
            // 如果状态为0，表示图书在借阅状态中，否则表示图书已还
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
            // 将查询到的借书详情添加到ArrayList中
            bookBorrowDetails.add(bookBorrowDetail);
        }
        // 返回查询结果
        return bookBorrowDetails;
    }

}
