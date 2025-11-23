package com.book.demo;

import com.book.dao.xml.BookXMLDAO;
import com.book.dao.xml.StudentXMLDAO;
import com.book.dao.xml.UserXMLDAO;

/**
 * 数据验证程序
 * 验证数据是否正确导入
 */
public class DataVerification {

    public static void main(String[] args) {
        System.out.println("🔍 验证数据导入结果...\n");

        verifyStudents();
        verifyBooks();
        verifyUsers();

        System.out.println("✅ 数据验证完成！");
    }

    private static void verifyStudents() {
        System.out.println("📚 学生数据验证:");
        StudentXMLDAO studentDAO = new StudentXMLDAO();

        try {
            var students = studentDAO.getStudentList();
            System.out.println("  总学生数: " + students.size());

            // 显示前5个学生
            int count = Math.min(5, students.size());
            for (int i = 0; i < count; i++) {
                var s = students.get(i);
                System.out.printf("  %d. %s (ID: %d, %s, %d级)\n",
                    i+1, s.getName(), s.getSid(), s.getSex(), s.getGrade());
            }
        } catch (Exception e) {
            System.err.println("  ❌ 验证失败: " + e.getMessage());
        }
        System.out.println();
    }

    private static void verifyBooks() {
        System.out.println("📖 图书数据验证:");
        BookXMLDAO bookDAO = new BookXMLDAO();

        try {
            var books = bookDAO.getBookList();
            System.out.println("  总图书数: " + books.size());

            // 显示前5本书
            int count = Math.min(5, books.size());
            for (int i = 0; i < count; i++) {
                var b = books.get(i);
                System.out.printf("  %d. %s (ID: %d, ¥%.2f)\n",
                    i+1, b.getTitle(), b.getBid(), b.getPrice());
            }
        } catch (Exception e) {
            System.err.println("  ❌ 验证失败: " + e.getMessage());
        }
        System.out.println();
    }

    private static void verifyUsers() {
        System.out.println("👤 用户数据验证:");
        UserXMLDAO userDAO = new UserXMLDAO();

        try {
            var users = userDAO.getAllUsers();
            System.out.println("  总用户数: " + users.size());

            // 显示所有用户
            for (int i = 0; i < users.size(); i++) {
                var u = users.get(i);
                System.out.printf("  %d. %s (ID: %d)\n",
                    i+1, u.getUsername(), u.getId());
            }

            // 测试登录
            System.out.println("\n  🔐 登录测试:");
            var admin = userDAO.getUser("admin", "admin123456");
            System.out.println("    管理员登录: " + (admin != null ? "✓ 成功" : "❌ 失败"));

            var testUser = userDAO.getUser("user001", "user123456");
            System.out.println("    测试用户登录: " + (testUser != null ? "✓ 成功" : "❌ 失败"));

        } catch (Exception e) {
            System.err.println("  ❌ 验证失败: " + e.getMessage());
        }
        System.out.println();
    }
}