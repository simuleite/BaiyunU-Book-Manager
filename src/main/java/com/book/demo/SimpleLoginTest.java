package com.book.demo;

import com.book.dao.xml.UserXMLDAO;
import com.book.entity.User;
import com.book.utils.MD5Util;

/**
 * 简化的登录测试程序
 * 测试登录认证逻辑
 */
public class SimpleLoginTest {

    public static void main(String[] args) {
        System.out.println("🔐 测试登录功能...\n");

        UserXMLDAO userDAO = new UserXMLDAO();

        String[][] testUsers = {
            {"admin", "admin123456"},
            {"user001", "user123456"},
            {"librarian", "lib123456"}
        };

        for (String[] user : testUsers) {
            String username = user[0];
            String password = user[1];

            System.out.println("👤 测试用户: " + username);

            // 测试1: 传入明文密码（DAO内部会MD5加密）
            User userFromDAO = userDAO.getUser(username, password);
            System.out.println("  明文密码登录: " + (userFromDAO != null ? "✅ 成功" : "❌ 失败"));

            // 测试2: 直接调用UserXMLDAO.getUser方法，观察MD5处理
            String passwordMD5 = MD5Util.toMD5(password);
            System.out.println("  明文: " + password);
            System.out.println("  MD5: " + passwordMD5);

            // 检查XML文件中存储的密码格式
            User storedUser = userDAO.getUserByName(username);
            if (storedUser != null) {
                System.out.println("  XML存储密码: " + storedUser.getPassword());

                // 验证MD5是否匹配
                boolean md5Match = passwordMD5.equals(storedUser.getPassword());
                System.out.println("  MD5匹配: " + (md5Match ? "✅ 是" : "❌ 否"));
            }

            System.out.println();
        }

        System.out.println("📝 结论分析:");
        System.out.println("  1. 如果所有明文密码登录成功，说明DAO内部MD5加密正确");
        System.out.println("  2. servlet应该在调用DAO前不要MD5加密，或者DAO内部不加密");
        System.out.println("  3. 当前实现中LoginServlet第66行传入MD5密码会造诚双重加密");
    }
}