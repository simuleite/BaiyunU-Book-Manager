package com.book.servlet.auth;

import com.book.entity.User;
import com.book.service.Impl.UserServiceImpl;
import com.book.service.UserService;
import com.book.utils.MD5Util;
import com.book.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    UserService userService;
    @Override
    public void init() throws ServletException {
        userService = UserServiceImpl.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        if (req.getSession().getAttribute("register-failure") != null) {
            context.setVariable("failure", true);
            req.getSession().removeAttribute("register-failure");
        }
        ThymeleafUtil.process("register.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirm_password = req.getParameter("confirm_password");
        String encryptedPassword = MD5Util.toMD5(password);
        if(password.length() > 8){
            if(password.equals(confirm_password) && !userService.AlreadyUsername(username, req.getSession())){
                userService.InsertUser(username, encryptedPassword, req.getSession());
                resp.sendRedirect("index");
            }else {
                req.getSession().setAttribute("register-failure", new Object());
                this.doGet(req, resp);
            }
        }else {
            req.getSession().setAttribute("register-failure", new Object());
            this.doGet(req, resp);
        }
    }
}
