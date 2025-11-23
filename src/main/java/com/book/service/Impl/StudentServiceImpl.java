package com.book.service.Impl;

import com.book.dao.xml.StudentXMLDAO;
import com.book.entity.Student;
import com.book.service.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private static StudentServiceImpl instance = null;
    private final StudentXMLDAO studentXMLDAO;

    private StudentServiceImpl() {
        this.studentXMLDAO = new StudentXMLDAO();
    }

    // 防止多线程创造多个实例
    public static synchronized StudentServiceImpl getInstance() {
        if (instance == null) {
            instance = new StudentServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Student> getStudentList() {
        return studentXMLDAO.getStudentList();
    }
}
