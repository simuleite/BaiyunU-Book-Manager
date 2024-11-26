package com.book.service.Impl;

import com.book.dao.BookMapper;
import com.book.dao.StudentMapper;
import com.book.entity.Student;
import com.book.service.StudentService;
import com.book.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private static StudentServiceImpl instance = null;

    private StudentServiceImpl() {
    }

    public static synchronized StudentServiceImpl getInstance() {
        if (instance == null) {
            instance = new StudentServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Student> getStudentList() {
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            return mapper.getStudentList();
        }
    }
}
