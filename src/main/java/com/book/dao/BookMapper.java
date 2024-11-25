package com.book.dao;

import com.book.entity.Borrow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public class BookMapper {

//    @Results({
//            @Result(column = "id", property = "id"),
//            @Result(column = "bid", property = "book_id"),
//            @Result(column = "title", property = "book_name"),
//            @Result(column = "borrow_time", property = "borrow_date"),
//            @Result(column = "return_time", property = "return_date"),
//            @Result(column = "bid", property = "book_id"),
//            @Result(column = "sid", property = "student_id")
//    })
//    @Select("select * from book_manage.borrow, book_manage.book, book_manage.student where" +
//            "borrow.bid = book.bid and borrow.sid = student.sid")
//    public List<Borrow> getBorrowList()
}
