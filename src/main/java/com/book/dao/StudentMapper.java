package com.book.dao;

import com.book.entity.Student;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentMapper {
    @Select("select * from book_manage.student")
    List<Student> getStudentList();
}
