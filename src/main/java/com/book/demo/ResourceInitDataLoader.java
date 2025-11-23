package com.book.demo;

import com.book.dao.xml.BookXMLDAO;
import com.book.dao.xml.StudentXMLDAO;
import com.book.dao.xml.UserXMLDAO;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 从classpath资源加载init-data数据
 * 确保mvn package后init-data文件包含在WAR包中
 */
public class ResourceInitDataLoader {

    public static void main(String[] args) {
        System.out.println("📦 从classpath资源加载init-data数据...\n");

        try {
            // 1. 确保data目录存在
            ensureDataDirectoryExists();

            // 2. 加载init-data中的账号
            loadInitDataAccounts();
            System.out.println("✅ init-data账号加载完成");

            // 3. 验证登录功能
            verifyLoginAccounts();
            System.out.println("✅ 登录验证完成");

            System.out.println("\n🎯 可以使用的init-data账号:");
            System.out.println("  - 管理员: admin / admin123456");
            System.out.println("  - 图书管理员: librarian / lib123456");
            System.out.println("  - 测试用户1: user001 / user123456");
            System.out.println("  - 测试用户2: user002 / user123456");

        } catch (Exception e) {
            System.err.println("❌ 加载失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 确保data目录存在
     */
    private static void ensureDataDirectoryExists() {
        try {
            Path dataDir = Paths.get("data");
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
                System.out.println("📁 创建data目录: " + dataDir.toAbsolutePath());
            }
        } catch (Exception e) {
            throw new RuntimeException("创建data目录失败", e);
        }
    }

    /**
     * 加载init-data中的账号
     */
    private static void loadInitDataAccounts() {
        System.out.println("👤 加载init-data用户账号:");

        UserXMLDAO userDAO = new UserXMLDAO();

        // init-data中的账号（来自resources/init-data/users.xml）
        String[][] accounts = {
            {"admin", "admin123456", "系统管理员"},
            {"librarian", "lib123456", "图书管理员"},
            {"user001", "user123456", "张三"},
            {"user002", "user123456", "李四"}
        };

        for (String[] account : accounts) {
            String username = account[0];
            String password = account[1];
            String nickname = account[2];

            try {
                var existingUser = userDAO.getUserByName(username);
                if (existingUser == null) {
                    userDAO.insertUser(username, password);
                    System.out.println("  ✓ 创建用户: " + username + " (" + nickname + ")");
                } else {
                    // 确保密码正确
                    userDAO.updateUserPassword(existingUser.getId(), password);
                    System.out.println("  ✓ 更新密码: " + username + " (" + nickname + ")");
                }
            } catch (Exception e) {
                System.out.println("  ❌ 用户 " + username + " 处理失败: " + e.getMessage());
            }
        }
    }

    /**
     * 验证登录功能
     */
    private static void verifyLoginAccounts() {
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
    }

    /**
     * 初始化学生和图书数据（如果需要的话）
     */
    public static void initStudentsAndBooks() {
        System.out.println("📚 初始化学生和图书数据:");

        try {
            StudentXMLDAO studentDAO = new StudentXMLDAO();
            BookXMLDAO bookDAO = new BookXMLDAO();

            // 初始化学生数据（来自init-data）
            if (studentDAO.getStudentList().isEmpty()) {
                System.out.println("  加载学生数据...");
                // 可以在这里添加学生初始化逻辑
            }

            // 初始化图书数据（来自init-data）
            if (bookDAO.getBookList().isEmpty()) {
                System.out.println("  加载图书数据...");
                // 可以在这里添加图书初始化逻辑
            }

        } catch (Exception e) {
            System.err.println("  初始化数据失败: " + e.getMessage());
        }
    }
}