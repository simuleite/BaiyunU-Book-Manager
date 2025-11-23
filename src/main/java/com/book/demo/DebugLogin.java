package com.book.demo;

import com.book.dao.xml.UserXMLDAO;
import com.book.entity.User;
import com.book.utils.MD5Util;

/**
 * 调试init-data账号登录问题
 */
public class DebugLogin {

    public static void main(String[] args) {
        System.out.println("🔍 调试init-data账号登录问题\n");

        UserXMLDAO userDAO = new UserXMLDAO();

        // init-data中的账号
        String[][] testAccounts = {
            {"admin", "admin123456"},
            {"librarian", "lib123456"},
            {"user001", "user123456"},
            {"user002", "user123456"}
        };

        System.out.println("📋 检查当前XML文件中的用户:");
        var allUsers = userDAO.getAllUsers();
        for (User user : allUsers) {
            System.out.printf("  - %s (ID: %d, 昵称: %s)\n",
                user.getUsername(), user.getId(), user.getNickname());
        }

        System.out.println("\n🔐 测试登录:");

        for (String[] account : testAccounts) {
            String username = account[0];
            String password = account[1];

            System.out.println("\n👤 测试账号: " + username);
            System.out.println("  明文密码: " + password);
            System.out.println("  MD5密码: " + MD5Util.toMD5(password));

            // 1. 检查用户是否存在
            User user = userDAO.getUserByName(username);
            if (user == null) {
                System.out.println("  ❌ 用户不存在");
                continue;
            }

            System.out.println("  ✅ 用户存在 - ID: " + user.getId());
            System.out.println("  📝 XML中的密码: " + user.getPassword());

            // 2. 测试登录（UserXMLDAO.getUser内部会MD5加密）
            User loggedInUser = userDAO.getUser(username, password);
            if (loggedInUser != null) {
                System.out.println("  ✅ 登录成功");
            } else {
                System.out.println("  ❌ 登录失败");

                // 额外调试：检查密码是否匹配
                String inputMD5 = MD5Util.toMD5(password);
                boolean passwordMatch = inputMD5.equals(user.getPassword());
                System.out.println("  🔍 MD5匹配: " + (passwordMatch ? "✅ 是" : "❌ 否"));

                if (!passwordMatch) {
                    System.out.println("  ⚠️ 密码不匹配，可能需要重新初始化");
                }
            }
        }

        System.out.println("\n💡 解决方案:");
        System.out.println("如果登录失败，运行以下命令重新初始化init-data账号:");
        System.out.println("mvn compile exec:java -Dexec.mainClass=\"com.book.demo.ResourceInitDataLoader\"");
    }
}