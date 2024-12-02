package com.book.servlet.manage;

import com.book.service.BookService;
import com.book.service.Impl.BookServiceImpl;
import com.book.utils.ThymeleafUtil;
import com.book.utils.UniqueCodeUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/add-book")
public class AddBookServlet extends HttpServlet {

    final String RUNTIME_PATH = "../webapps/book/static/picture/books/";
    final String DB_PATH = "static/picture/books/";

    private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = BookServiceImpl.getInstance(); // 使用单例
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        if (req.getSession().getAttribute("login-failure") != null) {
            context.setVariable("failure", true);
            req.getSession().removeAttribute("login-failure");
        }

        ThymeleafUtil.process("add-book.html", context, resp.getWriter());
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!JakartaServletFileUpload.isMultipartContent(req)) {
            resp.sendRedirect("add-book");
            return;
        }

        String title = null;
        String author = null;
        String desc = null;
        double price = 0.0;
        String imageName = null;

        DiskFileItemFactory factory = DiskFileItemFactory.builder().get();
        JakartaServletFileUpload upload = new JakartaServletFileUpload(factory);

        List<FileItem> files = upload.parseRequest(req);
        for (FileItem fileItem : files) {
            if (!fileItem.isFormField()) {
                imageName = UniqueCodeUtil.UniqueSuffix(fileItem.getName());
                if (imageName.isEmpty()) break;
                Path path = Paths.get(RUNTIME_PATH + imageName);
                fileItem.write(path);
                Path currentWorkingDirectory = Paths.get("").toAbsolutePath();
                System.out.println("当前工作目录: " + currentWorkingDirectory);
            } else {
                String fieldName = fileItem.getFieldName();
                String fieldValue = fileItem.getString(StandardCharsets.UTF_8);
                if ("title".equals(fieldName)) {
                    title = fieldValue;
                } else if ("author".equals(fieldName)) {
                    author = fieldValue;
                } else if ("desc".equals(fieldName)) {
                    desc = fieldValue;
                } else if ("price".equals(fieldName) && !fieldValue.isEmpty()) {
                    price = Double.parseDouble(fieldValue);
                }
            }
        }

        if (title.equals("") || author.equals("") || price == 0.0 || imageName.equals("")) {
            req.getSession().setAttribute("login-failure", new Object());
            this.doGet(req, resp);
            return;
        }

        bookService.addBook(title, desc, price, DB_PATH + imageName);
        resp.sendRedirect("books");
    }
}
