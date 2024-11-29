package com.book.dao;

import com.book.entity.Book;
import com.book.entity.Borrow;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "bid", property = "book_id"),
            @Result(column = "title", property = "book_name"),
            @Result(column = "sid", property = "student_id"),
            @Result(column = "name", property = "student_name"),
            @Result(column = "borrow_time", property = "borrowDate"),
            @Result(column = "return_time", property = "returnDate"),
    })
    @Select("select * from borrow, student, book where borrow.bid = book.bid and borrow.sid = student.sid")
    List<Borrow> getBorrowList();

    @Delete("delete from borrow where id = #{id}")
    void deleteBorrow(String id);

    @Result(column = "image_path", property = "imagePath")
    @Select("select * from book")
    List<Book> getBookList();

    @Insert("insert into borrow(sid, bid, borrow_time, return_time) values (#{sid}, #{bid}, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH))")
    void addBorrow(@Param("sid") int sid, @Param("bid") int bid);

    @Delete("delete from book where bid = #{bid}")
    void deleteBook(int bid);

    @Insert("insert into book(title, `desc`, price, image_path) values (#{title}, #{desc}, #{price}, #{imagePath})")
    void addBook(@Param("title") String title, @Param("desc") String desc, @Param("price") double price, @Param("imagePath") String imagePath);

    @Update("update borrow set return_time = DATE_ADD(return_time, INTERVAL 1 MONTH) where id = #{id}")
    void renewBook(@Param("id") String id);

    @Select("select * from book where title like concat('%', #{title}, '%')")
    Book getBookByTitle(String title);

    @Select("select * from borrow where bid = #{bid}")
    Borrow getBorrowByBid(int bid);
}
