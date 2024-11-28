package com.book.servlet.manage;

import com.book.service.BookService;
import com.book.service.Impl.BookServiceImpl;
import com.book.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet("/renew-book")
public class RenewBookServlet extends HttpServlet {

    private BookService bookService;
    @Override
    public void init() throws ServletException {
        bookService = BookServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        context.setVariable("book_list", bookService.getActiveBookList());
        ThymeleafUtil.process("index", context, resp.getWriter());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("bid");
        boolean b = bookService.renewBook(id);
        if(b){
            req.setAttribute("renew_list_status",true);
        }

        resp.sendRedirect("index");
    }
}
