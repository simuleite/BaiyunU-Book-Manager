package com.book.demo;

import com.book.dao.xml.BookXMLDAO;
import com.book.dao.xml.StudentXMLDAO;
import com.book.dao.xml.UserXMLDAO;

/**
 * 简化的XML数据初始化程序
 * 直接使用封装好的DAO方法插入数据
 */
public class SimpleXMLInitializer {

    public static void main(String[] args) {
        System.out.println("🚀 开始使用DAO方法初始化数据...");

        try {
            // 初始化学生数据
            initStudents();
            System.out.println("✓ 学生数据初始化完成");

            // 初始化图书数据
            initBooks();
            System.out.println("✓ 图书数据初始化完成");

            // 初始化用户数据
            initUsers();
            System.out.println("✓ 用户数据初始化完成");

            System.out.println("🎉 所有数据初始化完成！");

        } catch (Exception e) {
            System.err.println("❌ 数据初始化失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 初始化学生数据 - 使用StudentXMLDAO的业务方法
     */
    private static void initStudents() {
        System.out.println("正在初始化学生数据...");

        StudentXMLDAO studentDAO = new StudentXMLDAO();

        // 使用addStudent方法插入学生数据
        studentDAO.addStudent("李柔", "女", 2024);
        studentDAO.addStudent("秦明", "男", 2023);
        studentDAO.addStudent("胡歌", "男", 2019);
        studentDAO.addStudent("柳湘莲", "男", 2019);
        studentDAO.addStudent("张陶", "男", 2021);
        studentDAO.addStudent("古龙", "男", 2019);
        studentDAO.addStudent("晴雯", "女", 2020);
        studentDAO.addStudent("刘文文", "女", 2023);
        studentDAO.addStudent("罗紫", "男", 2019);
        studentDAO.addStudent("汪明珠", "女", 2024);

        System.out.println("  已添加10名学生");
    }

    /**
     * 初始化图书数据 - 使用BookXMLDAO的业务方法
     */
    private static void initBooks() {
        System.out.println("正在��始化图书数据...");

        BookXMLDAO bookDAO = new BookXMLDAO();

        // 使用addBook方法插入图书数据
        bookDAO.addBook("活着", "2018版", 35.50, "static/picture/books/ToLive82563.jpg");
        bookDAO.addBook("史记", "中华书局注校", 119.80, "static/picture/books/History55386.jpg");
        bookDAO.addBook("罪与罚", "汝龙 译", 59.90, "static/picture/books/SinAndPanish94467.jpg");
        bookDAO.addBook("资治通鉴", "上海古籍出版社", 220.50, "static/picture/books/Experiences36198.jpg");
        bookDAO.addBook("三体", "刘慈欣 科幻小说", 69.90, "static/picture/books/ThreeBody146694.jpg");
        bookDAO.addBook("三体典藏版", "全三套", 99.90, "static/picture/books/ThreeBody53542.jpg");
        bookDAO.addBook("三体1", "普通版", 66.60, "static/picture/books/ThreeBody115683.jpg");

        System.out.println("  已添加7本图书");
    }

    /**
     * 初始化用户数据 - 使用UserXMLDAO的业务方法
     */
    private static void initUsers() {
        System.out.println("正在初始化用户数据...");

        UserXMLDAO userDAO = new UserXMLDAO();

        // 使用insertUser方法插入用户数据（密码会自动转换为MD5）
        try {
            userDAO.insertUser("admin", "admin123456");
            System.out.println("  ✓ 添加管理员用户");
        } catch (Exception e) {
            System.out.println("  ⚠️ 管理员用户可能已存在");
        }

        try {
            userDAO.insertUser("librarian", "lib123456");
            System.out.println("  ✓ 添加图书管理员用户");
        } catch (Exception e) {
            System.out.println("  ⚠️ 图书管理员用户可能已存在");
        }

        try {
            userDAO.insertUser("user001", "user123456");
            System.out.println("  ✓ 添加测试用户001");
        } catch (Exception e) {
            System.out.println("  ⚠️ 测试用户001可能已存在");
        }

        try {
            userDAO.insertUser("user002", "user123456");
            System.out.println("  ✓ 添加测试用户002");
        } catch (Exception e) {
            System.out.println("  ⚠️ 测试用户002可能已存在");
        }
    }
}