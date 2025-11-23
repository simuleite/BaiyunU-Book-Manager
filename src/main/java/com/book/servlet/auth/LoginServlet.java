package com.book.servlet.auth;

import com.book.dao.xml.UserXMLDAO;
import com.book.entity.User;
import com.book.service.Impl.UserServiceImpl;
import com.book.service.UserService;
import com.book.utils.MD5Util;
import com.book.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    UserService userService;

    @Override
    public void init() throws ServletException {
        userService = UserServiceImpl.getInstance();
        logger.info("LoginServlet initialized");

        // 添加InitData验证日志
        logInitDataStatus();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            String username = null;
            String password = null;
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("username")) username = cookie.getValue();
                if(cookie.getName().equals("password")) password = cookie.getValue();
            }
            if(username != null && password != null){
                // Cookie中存储的是MD5密码，直接用于验证（不再加密）
                UserXMLDAO userDAO = new UserXMLDAO();
                User user = userDAO.getUserByName(username);
                if(user != null && password.equals(user.getPassword())){
                    req.getSession().setAttribute("user", user);
                    resp.sendRedirect("index");
                    return;
                }
            }
        }

        Context context = new Context();
        if (req.getSession().getAttribute("login-failure") != null) {
            context.setVariable("failure", true);
            req.getSession().removeAttribute("login-failure");
        }
        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect("index");
            return;
        }
        ThymeleafUtil.process("login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember-me");

        logger.info("Login attempt - Username: {}, Password length: {}, Remember: {}",
                    username, password != null ? password.length() : 0, remember);

        // 添加详细的认证调试日志
        logAuthenticationDetails(username, password);

        // 直接传入明文密码，让DAO层处理MD5加密，避免双重加密问题
        boolean authResult = userService.auth(username, password, req.getSession());
        logger.info("Auth result for {}: {}", username, authResult);

        if (authResult) {
            logger.info("User {} logged in successfully", username);

            if(remember != null){   //若勾选了记住我选项，则设置Cookie
                logger.info("Setting remember-me cookies for user {}", username);
                Cookie cookie_username = new Cookie("username", username);
                int week = 60 * 60 * 24 * 7;
                cookie_username.setMaxAge(week);
                // Cookie中的密码需要加密，因为DAO期望接收明文
                String passwordMD5 = MD5Util.toMD5(password);
                Cookie cookie_password = new Cookie("password", passwordMD5);
                cookie_password.setMaxAge(week);
                resp.addCookie(cookie_username);
                resp.addCookie(cookie_password);
                logger.debug("Cookie password MD5: {}", passwordMD5);
            }
            resp.sendRedirect("index");
        } else {
            logger.warn("Login failed for user: {}", username);
            req.getSession().setAttribute("login-failure", new Object());
            this.doGet(req, resp);
        }
    }

    /**
     * 记录InitData状态，用于调试登录问题
     */
    private void logInitDataStatus() {
        try {
            UserXMLDAO userDAO = new UserXMLDAO();
            logger.info("=== InitData Status Check ===");

            // 检查所有用户
            var allUsers = userDAO.getAllUsers();
            logger.info("Total users in system: {}", allUsers.size());

            // 检查预期的测试用户
            String[] testUsernames = {"admin", "librarian", "user001", "user002"};
            for (String testUser : testUsernames) {
                var user = userDAO.getUserByName(testUser);
                if (user != null) {
                    logger.info("Found user: {} (ID: {}, Nickname: {}, Password Hash: {})",
                               testUser, user.getId(), user.getNickname(), user.getPassword());
                } else {
                    logger.warn("Missing test user: {}", testUser);
                }
            }

            // 测试预期的账号密码组合
            logger.info("=== Testing Expected Credentials ===");
            String[][] testCredentials = {
                {"admin", "admin123456"},
                {"librarian", "lib123456"},
                {"user001", "user123456"},
                {"user002", "user123456"}
            };

            for (String[] cred : testCredentials) {
                String username = cred[0];
                String password = cred[1];
                var user = userDAO.getUser(username, password);
                logger.info("Credential test - {}: {} (Expected: success)", username, user != null ? "SUCCESS" : "FAILED");
                if (user != null) {
                    logger.debug("  User details: ID={}, Nickname={}", user.getId(), user.getNickname());
                }
            }

            logger.info("=== End InitData Status Check ===");

        } catch (Exception e) {
            logger.error("Error during InitData status check", e);
        }
    }

    /**
     * 记录详细的认证调试信息
     */
    private void logAuthenticationDetails(String username, String password) {
        if (username == null || password == null) {
            logger.warn("Authentication failed - username or password is null");
            return;
        }

        try {
            UserXMLDAO userDAO = new UserXMLDAO();
            var user = userDAO.getUserByName(username);

            if (user == null) {
                logger.warn("User not found in system: {}", username);
                return;
            }

            String inputPasswordMD5 = MD5Util.toMD5(password);
            String storedPasswordMD5 = user.getPassword();

            logger.info("Authentication details for {}:", username);
            logger.info("  Input password: {} (length: {})", password, password.length());
            logger.info("  Input MD5: {}", inputPasswordMD5);
            logger.info("  Stored MD5: {}", storedPasswordMD5);
            logger.info("  Password match: {}", inputPasswordMD5.equals(storedPasswordMD5));
            logger.info("  User ID: {}, Nickname: {}", user.getId(), user.getNickname());

        } catch (Exception e) {
            logger.error("Error during authentication details logging for user: " + username, e);
        }
    }
}
