package com.book.demo;

import com.book.dao.xml.BookXMLDAO;
import com.book.dao.xml.StudentXMLDAO;
import com.book.dao.xml.UserXMLDAO;
import com.book.entity.Book;
import com.book.entity.Student;
import com.book.entity.User;
import com.book.utils.MD5Util;

import java.util.List;

/**
 * XML存储演示程序
 */
public class XMLStorageDemo {
    public static void main(String[] args) {
        System.out.println("=== XML存储系统演示 ===");

        // 演示用户功能
        demoUserOperations();

        // 演示学生功能
        demoStudentOperations();

        // 演示图书功能
        demoBookOperations();

        System.out.println("=== 演示完成 ===");
    }

    private static void demoUserOperations() {
        System.out.println("\n--- 用户操作演示 ---");
        UserXMLDAO userDAO = new UserXMLDAO();

        // 添加用户
        try {
            userDAO.insertUser("admin", "123456");
            System.out.println("✓ 用户添加成功");
        } catch (Exception e) {
            System.out.println("用户可能已存在: " + e.getMessage());
        }

        // 验证用户登录
        User user = userDAO.getUser("admin", "123456");
        if (user != null) {
            System.out.println("✓ 用户登录验证成功");
            System.out.println("  用户名: " + user.getUsername());
            System.out.println("  昵称: " + user.getNickname());
        }

        // 获取所有用户
        List<User> users = userDAO.getAllUsers();
        System.out.println("✓ 当前用户总数: " + users.size());
    }

    private static void demoStudentOperations() {
        System.out.println("\n--- 学生操作演示 ---");
        StudentXMLDAO studentDAO = new StudentXMLDAO();

        // 添加学生
        studentDAO.addStudent("张三", "男", 1);
        studentDAO.addStudent("李四", "女", 2);
        System.out.println("✓ 学生添加成功");

        // 获取所有学生
        List<Student> students = studentDAO.getStudentList();
        System.out.println("✓ 当前学生总数: " + students.size());
        for (Student student : students) {
            System.out.println("  学生: " + student.getName() + " (年级: " + student.getGrade() + ")");
        }
    }

    private static void demoBookOperations() {
        System.out.println("\n--- 图书操作演示 ---");
        BookXMLDAO bookDAO = new BookXMLDAO();

        // 添加图书
        try {
            bookDAO.addBook("Java编程思想", "Java经典教程", 89.9, "/images/java-book.jpg");
            bookDAO.addBook("设计模式", "软件设计模式详解", 59.9, "/images/design-patterns.jpg");
            System.out.println("✓ 图书添加成功");
        } catch (Exception e) {
            System.out.println("图书添加错误: " + e.getMessage());
        }

        // 获取所有图书
        List<Book> books = bookDAO.getBookList();
        System.out.println("✓ 当前图书总数: " + books.size());
        for (Book book : books) {
            System.out.println("  图书: " + book.getTitle() + " (价格: ¥" + book.getPrice() + ")");
        }

        // 搜索图书
        List<Book> searchResults = bookDAO.getBookByTitle("Java");
        System.out.println("✓ 搜索'Java'的结果: " + searchResults.size() + " 本");
    }
}