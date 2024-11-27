package com.book.servlet.manage;

import com.book.service.BookService;
import com.book.service.Impl.BookServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/renew-book")
public class RenewBookServlet extends HttpServlet {

    private BookService bookService;
    @Override
    public void init() throws ServletException {
        bookService = BookServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getAttribute("");

        String id = req.getParameter("id");
        bookService.renewBook(id);
        resp.sendRedirect("index");
    }
}