package com.book.demo;

import com.book.dao.xml.UserXMLDAO;
import com.book.entity.User;

/**
 * 修复用户密码
 */
public class FixUserPasswords {

    public static void main(String[] args) {
        System.out.println("🔧 修复用户密码...\n");

        UserXMLDAO userDAO = new UserXMLDAO();

        // 修复admin密码
        User admin = userDAO.getUserByName("admin");
        if (admin != null) {
            userDAO.updateUserPassword(admin.getId(), "admin123456");
            System.out.println("✅ 修复admin密码");
        }

        // 修复librarian密码
        User librarian = userDAO.getUserByName("librarian");
        if (librarian != null) {
            userDAO.updateUserPassword(librarian.getId(), "lib123456");
            System.out.println("✅ 修复librarian密码");
        }

        // 验证修复结果
        System.out.println("\n🔍 验证修复结果:");
        verifyLogin(userDAO, "admin", "admin123456");
        verifyLogin(userDAO, "librarian", "lib123456");
        verifyLogin(userDAO, "user001", "user123456");

        System.out.println("\n🎉 密码修复完成！");
    }

    private static void verifyLogin(UserXMLDAO userDAO, String username, String password) {
        User user = userDAO.getUser(username, password);
        System.out.println("  " + username + ": " + (user != null ? "✅ 登录成功" : "❌ 登录失败"));
    }
}