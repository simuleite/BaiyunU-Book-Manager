package com.book.demo;

import com.book.dao.xml.BookXMLDAO;
import com.book.dao.xml.StudentXMLDAO;
import com.book.dao.xml.UserXMLDAO;

/**
 * 从init-data目录加载初始数据
 */
public class InitDataLoader {

    public static void main(String[] args) {
        System.out.println("📂 从init-data目录加载初始数据...\n");

        try {
            // 使用init-data中的用户账号
            loadUsersFromInitData();
            System.out.println("✓ 用户数据加载完成");

            // 加载学生数据
            loadStudentsFromInitData();
            System.out.println("✓ 学生数据加载完成");

            // 加载图书数据
            loadBooksFromInitData();
            System.out.println("✓ 图书数据加载完成");

            System.out.println("\n🎉 init-data数据加载完成！");

            // 验证登录账号
            verifyInitDataAccounts();

        } catch (Exception e) {
            System.err.println("❌ 数据加载失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 加载init-data中的用户数据
     */
    private static void loadUsersFromInitData() {
        System.out.println("👤 加载用户数据（来自init-data）:");

        UserXMLDAO userDAO = new UserXMLDAO();

        // init-data中定义的用户账号（符合User实体结构）
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
                // 检查用户是否已存在
                if (userDAO.getUserByName(username) == null) {
                    userDAO.insertUser(username, password);
                    System.out.println("  ✓ 创建用户: " + username + " (" + nickname + ")");
                } else {
                    // 更新密码确保使用init-data中的密码
                    var existingUser = userDAO.getUserByName(username);
                    if (existingUser != null) {
                        userDAO.updateUserPassword(existingUser.getId(), password);
                        System.out.println("  ✓ 更新密码: " + username + " (" + nickname + ")");
                    }
                }
            } catch (Exception e) {
                System.out.println("  ⚠️ 用户 " + username + " 处理失败: " + e.getMessage());
            }
        }
    }

    /**
     * 加载init-data中的学生数据
     */
    private static void loadStudentsFromInitData() {
        System.out.println("📚 加载学生数据（来自init-data）:");

        StudentXMLDAO studentDAO = new StudentXMLDAO();

        // init-data中定义的学生数据
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
                // 检查学生是否已存在
                if (studentDAO.getStudentByName(name) == null) {
                    studentDAO.addStudent(name, sex, grade);
                    System.out.println("  ✓ 创建学生: " + name + " (" + sex + ", " + grade + "级)");
                } else {
                    System.out.println("  ⚠️ 学生已存在: " + name);
                }
            } catch (Exception e) {
                System.out.println("  ❌ 学生 " + name + " 创建失败: " + e.getMessage());
            }
        }
    }

    /**
     * 加载init-data中的图书数据
     */
    private static void loadBooksFromInitData() {
        System.out.println("📖 加载图书数据（来自init-data）:");

        BookXMLDAO bookDAO = new BookXMLDAO();

        // init-data中定义的图书数据
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
                // 直接添加图书（因为addBook会自动生成新ID）
                bookDAO.addBook(title, desc, price, imagePath);
                System.out.println("  ✓ 创建图书: " + title + " (¥" + price + ")");
            } catch (Exception e) {
                System.out.println("  ⚠️ 图书 " + title + " 创建失败: " + e.getMessage());
            }
        }
    }

    /**
     * 验证init-data中的账号是否可以正常登录
     */
    private static void verifyInitDataAccounts() {
        System.out.println("\n🔍 验证init-data账号登录:");

        UserXMLDAO userDAO = new UserXMLDAO();

        String[][] testAccounts = {
            {"admin", "admin123456"},
            {"librarian", "lib123456"},
            {"user001", "user123456"},
            {"user002", "user123456"}
        };

        for (String[] account : testAccounts) {
            String username = account[0];
            String password = account[1];

            var user = userDAO.getUser(username, password);
            System.out.println("  " + username + ": " + (user != null ? "✅ 登录成功" : "❌ 登录失败"));
        }

        System.out.println("\n🎯 现在您可以使用init-data中的账号登录:");
        System.out.println("  - 管理员: admin / admin123456");
        System.out.println("  - 图书管理员: librarian / lib123456");
        System.out.println("  - 用户1: user001 / user123456");
        System.out.println("  - 用户2: user002 / user123456");
    }
}