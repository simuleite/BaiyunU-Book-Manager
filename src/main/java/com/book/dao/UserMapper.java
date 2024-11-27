package com.book.dao;

import com.book.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from admin where username = #{username} and password = #{password}")
    User getUser(@Param("username") String username, @Param("password") String password);

    @Select("select * from admin where username = #{username}")
    User getUserByName(@Param("username") String username);

    @Insert("insert into admin(username, nickname, password) values(#{username}, '图书管理员', #{password})")
    void InsertUser(@Param("username") String username, @Param("password") String password);
}
