package com.book.listener;

import com.book.dao.xml.BookXMLDAO;
import com.book.dao.xml.StudentXMLDAO;
import com.book.dao.xml.UserXMLDAO;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web应用启动时自动初始化数据的监听器
 */
@WebListener
public class InitDataListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(InitDataListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("=== InitDataListener: Starting automatic data initialization ===");

        try {
            initializeUsers();
            initializeStudents();
            initializeBooks();

            logger.info("=== InitDataListener: Data initialization completed successfully ===");

        } catch (Exception e) {
            logger.error("=== InitDataListener: Data initialization failed ===", e);
        }
    }

    /**
     * 初始化用户数据 - 使用与InitDataLoader相同的数据
     */
    private void initializeUsers() {
        logger.info("InitDataListener: Initializing user data...");

        UserXMLDAO userDAO = new UserXMLDAO();

        // 预期的用户数据���与InitDataLoader.java保持一致）
        String[][] users = {
            {"admin", "admin123456", "系统管理员"},
            {"librarian", "lib123456", "图书管理员"},
            {"user001", "user123456", "张三"},
            {"user002", "user123456", "李四"}
        };

        for (String[] user : users) {
            String username = user[0];
            String password = user[1];
            String nickname = user[2];

            try {
                if (userDAO.getUserByName(username) == null) {
                    userDAO.insertUser(username, password);
                    logger.info("  ✓ Created user: {} ({})", username, nickname);
                } else {
                    // 更新密码确保使用正确的密码
                    var existingUser = userDAO.getUserByName(username);
                    if (existingUser != null) {
                        userDAO.updateUserPassword(existingUser.getId(), password);
                        logger.info("  ✓ Updated password for user: {} ({})", username, nickname);
                    }
                }
            } catch (Exception e) {
                logger.warn("  ⚠️ Failed to process user {}: {}", username, e.getMessage());
            }
        }

        logger.info("InitDataListener: User data initialization completed");
    }

    /**
     * 初始化学生数据
     */
    private void initializeStudents() {
        logger.info("InitDataListener: Initializing student data...");

        StudentXMLDAO studentDAO = new StudentXMLDAO();

        // 预期的学生数据
        String[][] students = {
            {"李柔", "女", "2024"},
            {"秦明", "男", "2023"},
            {"胡歌", "男", "2019"},
            {"柳湘莲", "男", "2019"},
            {"张陶", "男", "2021"},
            {"古龙", "男", "2019"},
            {"晴雯", "女", "2020"},
            {"刘文文", "女", "2023"},
            {"罗紫", "男", "2019"},
            {"汪明珠", "女", "2024"}
        };

        for (String[] student : students) {
            String name = student[0];
            String sex = student[1];
            int grade = Integer.parseInt(student[2]);

            try {
                if (studentDAO.getStudentByName(name) == null) {
                    studentDAO.addStudent(name, sex, grade);
                    logger.info("  ✓ Created student: {} ({}, {}级)", name, sex, grade);
                } else {
                    logger.debug("  ⚠️ Student already exists: {}", name);
                }
            } catch (Exception e) {
                logger.warn("  ❌ Failed to create student {}: {}", name, e.getMessage());
            }
        }

        logger.info("InitDataListener: Student data initialization completed");
    }

    /**
     * 初始化图书数据
     */
    private void initializeBooks() {
        logger.info("InitDataListener: Initializing book data...");

        BookXMLDAO bookDAO = new BookXMLDAO();

        // 预期的图书数据
        String[][] books = {
            {"活着", "2018版", "35.50", "余华", "static/picture/books/ToLive82563.jpg"},
            {"史记", "中华书局注校", "119.80", "司马迁", "static/picture/books/History55386.jpg"},
            {"罪与罚", "汝龙 译", "59.90", "陀思妥耶夫斯基", "static/picture/books/SinAndPanish94467.jpg"},
            {"资治通鉴", "上海古籍出版社", "220.50", "司马光", "static/picture/books/Experiences36198.jpg"},
            {"三体", "刘慈欣 科幻小说", "69.90", "刘慈欣", "static/picture/books/ThreeBody146694.jpg"},
            {"三体典藏版", "全三套", "99.90", "刘慈欣", "static/picture/books/ThreeBody53542.jpg"},
            {"三体1", "普通版", "66.60", "刘慈欣", "static/picture/books/ThreeBody115683.jpg"}
        };

        for (String[] book : books) {
            String title = book[0];
            String desc = book[1];
            double price = Double.parseDouble(book[2]);
            String author = book[3];
            String imagePath = book[4];

            try {
                bookDAO.addBook(title, desc, price, imagePath);
                logger.info("  ✓ Created book: {} (¥{})", title, price);
            } catch (Exception e) {
                logger.warn("  ⚠️ Failed to create book {}: {}", title, e.getMessage());
            }
        }

        logger.info("InitDataListener: Book data initialization completed");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("InitDataListener: Application shutdown");
    }
}