package com.book.demo;

import com.book.dao.xml.UserXMLDAO;
import com.book.entity.User;
import com.book.service.Impl.UserServiceImpl;
import com.book.utils.MD5Util;

/**
 * 登录功能测试程序
 * 测试servlet登录流程是否正常
 */
public class LoginTest {

    public static void main(String[] args) {
        System.out.println("🔐 测试登录功能...\n");

        // 简化测试：直接测试UserXMLDAO
        testUserXMLDAO();

        // 测试UserService
        testUserService();
    }

    private static void testUserXMLDAO() {
        System.out.println("📝 测试UserXMLDAO (底层DAO):");

        String[][] testUsers = {
            {"admin", "admin123456"},
            {"user001", "user123456"},
            {"librarian", "lib123456"}
        };

        UserXMLDAO userDAO = new UserXMLDAO();

        for (String[] user : testUsers) {
            String username = user[0];
            String password = user[1];

            System.out.println("  👤 测试用户: " + username);

            // 1. 传入明文密码（DAO内部会MD5加密）
            User userFromDAO = userDAO.getUser(username, password);
            System.out.println("    明文密码登录: " + (userFromDAO != null ? "✓ 成功" : "❌ 失败"));

            // 2. 传入MD5密码（会造成双重加密）
            String md5Password = MD5Util.toMD5(password);
            User userFromDAOMD5 = userDAO.getUser(username, md5Password);
            System.out.println("    MD5密码登录: " + (userFromDAOMD5 != null ? "✓ 成功" : "❌ 失败（预期）"));

            System.out.println("    明文MD5: " + MD5Util.toMD5(password));
            System.out.println();
        }
    }

    private static void testUserService() {
        System.out.println("🔐 测试UserService (业务层):");

        String[][] testUsers = {
            {"admin", "admin123456"},
            {"user001", "user123456"}
        };

        UserServiceImpl userService = UserServiceImpl.getInstance();
        MockHttpSession session = new MockHttpSession();

        for (String[] user : testUsers) {
            String username = user[0];
            String password = user[1];

            System.out.println("  👤 测试用户: " + username);

            // 测试servlet实际调用方式：传入MD5加密的密码
            String md5Password = MD5Util.toMD5(password);
            boolean authResult = userService.auth(username, md5Password, session);
            System.out.println("    servlet方式(MD5): " + (authResult ? "✓ 成功" : "❌ 失败"));

            // 清除session
            session.removeAttribute("user");
        }

        // 测试注册功能
        System.out.println("\n📝 测试注册功能:");
        try {
            userService.InsertUser("testuser", "newpass123", new MockHttpSession());
            System.out.println("  ✓ 注册测试用户成功");

            // 验证新用户登录
            UserXMLDAO userDAO = new UserXMLDAO();
            User newUser = userDAO.getUser("testuser", "newpass123");
            System.out.println("  ✓ 新用户登录验证: " + (newUser != null ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("  ❌ 注册失败: " + e.getMessage());
        }
    }

    // 简化的Mock HttpSession
    static class MockHttpSession implements jakarta.servlet.http.HttpSession {
        private java.util.Map<String, Object> attributes = new java.util.HashMap<>();

        public Object getAttribute(String name) { return attributes.get(name); }
        public void setAttribute(String name, Object value) { attributes.put(name, value); }
        public void removeAttribute(String name) { attributes.remove(name); }

        // 其他方法的最小实现
        public long getCreationTime() { return 0; }
        public String getId() { return "mock"; }
        public long getLastAccessedTime() { return 0; }
        public jakarta.servlet.ServletContext getServletContext() { return null; }
        public void setMaxInactiveInterval(int interval) {}
        public int getMaxInactiveInterval() { return 0; }
        public jakarta.servlet.http.HttpSessionContext getSessionContext() { return null; }
        public Object getValue(String name) { return null; }
        public String[] getValueNames() { return new String[0]; }
        public void putValue(String name, Object value) {}
        public void removeValue(String name) {}
        public void invalidate() {}
        public boolean isNew() { return false; }
        public java.util.Enumeration<String> getAttributeNames() { return null; }
    }
}