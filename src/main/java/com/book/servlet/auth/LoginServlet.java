package com.book.servlet.auth;

import com.book.service.Impl.UserServiceImpl;
import com.book.service.UserService;
import com.book.utils.MD5Util;
import com.book.utils.MybatisUtil;
import com.book.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    UserService userService;
    @Override
    public void init() throws ServletException {
        userService = UserServiceImpl.getInstance();
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
                if (userService.auth(username, password, req.getSession())) {
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

        // 使用MD5加密用户输入的密码
        String encryptedPassword = MD5Util.toMD5(password);
        System.out.println(encryptedPassword);

        if (userService.auth(username, encryptedPassword, req.getSession())) {
            if(remember != null){   //若勾选了记住我选项，则设置Cookie
                Cookie cookie_username = new Cookie("username", username);
                int week = 60 * 60 * 24 * 7;
                cookie_username.setMaxAge(week);
                Cookie cookie_password = new Cookie("password", encryptedPassword); // 这里应该是加密后的密码
                cookie_password.setMaxAge(week);
                resp.addCookie(cookie_username);
                resp.addCookie(cookie_password);
            }
            resp.sendRedirect("index");
        } else {
            req.getSession().setAttribute("login-failure", new Object());
            this.doGet(req, resp);
        }
    }
}
