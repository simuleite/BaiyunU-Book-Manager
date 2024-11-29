package com.book.servlet.pages;

import com.book.entity.Book;
import com.book.entity.User;
import com.book.service.BookService;
import com.book.service.Impl.BookServiceImpl;
import com.book.service.Impl.UserServiceImpl;
import com.book.service.UserService;
import com.book.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

    private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = BookServiceImpl.getInstance(); // 使用单例
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        User user = (User) req.getSession().getAttribute("user");
        context.setVariable("nickname", user.getNickname());
        String search = req.getParameter("search");
        Map<Book, Boolean> map;
        if (search != null && !search.equals("")) {
            map = bookService.getBookByTitle(search);
            context.setVariable("book_list", map.keySet());
            context.setVariable("book_list_status", new ArrayList<>(map.values()));
        } else {
            map = bookService.getBookList();
            context.setVariable("book_list", map.keySet());
            context.setVariable("book_list_status", new ArrayList<>(map.values()));
        }
        ThymeleafUtil.process("books.html", context, resp.getWriter());
    }
}

