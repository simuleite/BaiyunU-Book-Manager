package com.book.servlet.manage;

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

@WebServlet("/add-borrow")
public class AddBorrowServlet extends HttpServlet {

    private BookService bookService;
    private StudentService studentService;
    @Override
    public void init() throws ServletException {
        bookService = BookServiceImpl.getInstance();
        studentService = StudentServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        context.setVariable("book_list", bookService.getActiveBookList());
        context.setVariable("student_list", studentService.getStudentList());
        ThymeleafUtil.process("add-borrow.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String student_id = req.getParameter("student");
        int sid = Integer.parseInt(student_id);
        String book_id = req.getParameter("book");
        int bid = Integer.parseInt(book_id);
        bookService.addBorrow(sid, bid);
        resp.sendRedirect("index");
    }
}
