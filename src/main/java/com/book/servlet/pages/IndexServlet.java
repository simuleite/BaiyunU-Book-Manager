package com.book.servlet.pages;

import com.book.entity.User;
import com.book.service.BookService;
import com.book.service.Impl.BookServiceImpl;
import com.book.service.Impl.StudentServiceImpl;
import com.book.service.StudentService;
import com.book.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    BookService bookService;
    StudentService studentService;
    @Override
    public void init() throws ServletException {
        bookService = BookServiceImpl.getInstance();
        studentService = StudentServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        User user = (User) req.getSession().getAttribute("user");
        context.setVariable("nickname", user.getNickname());
        context.setVariable("borrow_list", bookService.getBorrowList());
        context.setVariable("book_count", bookService.getBookList().size());
        context.setVariable("student_count", studentService.getStudentList().size());
        ThymeleafUtil.process("index.html", context, resp.getWriter());
    }
}

