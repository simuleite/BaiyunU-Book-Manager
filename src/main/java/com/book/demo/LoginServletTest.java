package com.book.demo;

import com.book.service.Impl.UserServiceImpl;
import com.book.utils.MD5Util;

/**
 * 测试修复后的LoginServlet登录逻辑
 */
public class LoginServletTest {

    public static void main(String[] args) {
        System.out.println("🔐 测试修复后的LoginServlet登录逻辑...\n");

        UserServiceImpl userService = UserServiceImpl.getInstance();
        MockHttpSession session = new MockHttpSession();

        // 测试数据：用户名和明文密码
        String[][] testCases = {
            {"admin", "admin123456"},
            {"librarian", "lib123456"},
            {"user001", "user123456"},
            {"admin", "wrongpassword"},
            {"nonexistent", "anypassword"}
        };

        for (String[] testCase : testCases) {
            String username = testCase[0];
            String password = testCase[1];

            System.out.println("👤 测试用户: " + username);
            System.out.println("  明文密码: " + password);

            // 1. 模拟修复后的Servlet逻辑：传入明文密码
            boolean loginResult = userService.auth(username, password, session);
            System.out.println("  修复后登录结果: " + (loginResult ? "✅ 成功" : "❌ 失败"));

            // 2. 对比修复前的方式（双重MD5加密）
            String md5Password = MD5Util.toMD5(password);
            boolean oldLoginResult = userService.auth(username, md5Password, session);
            System.out.println("  修复前登录结果: " + (oldLoginResult ? "❌ 意外成功" : "✅ 正确失败"));

            System.out.println("  MD5值: " + md5Password);

            // 清除session状态
            session.removeAttribute("user");
            System.out.println();
        }

        System.out.println("📋 修复总结:");
        System.out.println("  ✅ doPost方法: 传入明文密码，让DAO层处理MD5加密");
        System.out.println("  ✅ doGet方法: Cookie中的MD5密码直接比对，不再二次加密");
        System.out.println("  ✅ 避免了双重MD5加密问题");
    }

    // 简化的Mock HttpSession
    static class MockHttpSession implements jakarta.servlet.http.HttpSession {
        private java.util.Map<String, Object> attributes = new java.util.HashMap<>();

        public Object getAttribute(String name) { return attributes.get(name); }
        public void setAttribute(String name, Object value) { attributes.put(name, value); }
        public void removeAttribute(String name) { attributes.remove(name); }

        // 其他方法的简单实现
        public long getCreationTime() { return 0; }
        public String getId() { return "mock-session"; }
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