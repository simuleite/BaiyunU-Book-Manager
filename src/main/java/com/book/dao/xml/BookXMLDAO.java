package com.book.dao.xml;

import com.book.entity.Book;
import com.book.entity.Borrow;
import com.book.entity.Student;
import com.book.utils.XMLDataStore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 图书XML数据访问对象，替代原来的BookMapper
 */
public class BookXMLDAO {

    private static final AtomicInteger bookIdGenerator = new AtomicInteger(1);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<Book> getBookList() {
        return XMLDataStore.readAll(Book.class, XMLDataStore.getBooksFile());
    }

    public void addBook(String title, String desc, double price, String imagePath) {
        List<Book> books = getBookList();

        // 生成新的book_id
        int newId = books.stream()
            .mapToInt(book -> book.getBid())
            .max()
            .orElse(0) + 1;

        Book book = new Book();
        book.setBid(newId);
        book.setTitle(title);
        book.setDesc(desc);
        book.setPrice(price);
        book.setImagePath(imagePath);
        book.setAuthor("Unknown"); // 设置默认值

        XMLDataStore.add(book, Book.class, XMLDataStore.getBooksFile());
    }

    public void deleteBook(int bid) {
        XMLDataStore.delete(bid, "bid", Book.class, XMLDataStore.getBooksFile());

        // 同时删除相关的借阅记录
        List<Borrow> borrows = XMLDataStore.readAll(Borrow.class, XMLDataStore.getBorrowsFile());
        List<Borrow> updatedBorrows = borrows.stream()
            .filter(borrow -> borrow.getBook_id() != bid)
            .collect(Collectors.toList());
        XMLDataStore.writeAll(updatedBorrows, XMLDataStore.getBorrowsFile());
    }

    public List<Book> getBookByTitle(String title) {
        return getBookList().stream()
            .filter(book -> book.getTitle() != null && book.getTitle().contains(title))
            .collect(Collectors.toList());
    }

    public void addBorrow(int sid, int bid) {
        List<Student> students = XMLDataStore.readAll(Student.class, XMLDataStore.getStudentsFile());
        List<Book> books = XMLDataStore.readAll(Book.class, XMLDataStore.getBooksFile());

        // 检查学生和图书是否存在
        boolean studentExists = students.stream().anyMatch(s -> s.getSid() == sid);
        boolean bookExists = books.stream().anyMatch(b -> b.getBid() == bid);

        if (!studentExists || !bookExists) {
            throw new IllegalArgumentException("学生或图书不存在");
        }

        // 获取学生和图书信息
        Student student = students.stream().filter(s -> s.getSid() == sid).findFirst().orElse(null);
        Book book = books.stream().filter(b -> b.getBid() == bid).findFirst().orElse(null);

        List<Borrow> borrows = XMLDataStore.readAll(Borrow.class, XMLDataStore.getBorrowsFile());

        // 生成新的借阅记录ID
        int newId = borrows.stream()
            .mapToInt(borrow -> borrow.getId())
            .max()
            .orElse(0) + 1;

        Borrow borrow = new Borrow();
        borrow.setId(newId);
        borrow.setBook_id(bid);
        borrow.setBook_name(book != null ? book.getTitle() : "");
        borrow.setStudent_id(sid);
        borrow.setStudent_name(student != null ? student.getName() : "");
        borrow.setBorrowDate(LocalDateTime.now().format(dateFormatter));
        borrow.setReturnDate(LocalDateTime.now().plusMonths(1).format(dateFormatter));
        borrow.setRenewStatus(0);

        XMLDataStore.add(borrow, Borrow.class, XMLDataStore.getBorrowsFile());
    }

    public Borrow getBorrowByBid(int bid) {
        List<Borrow> borrows = XMLDataStore.readAll(Borrow.class, XMLDataStore.getBorrowsFile());
        return borrows.stream()
            .filter(borrow -> borrow.getBook_id() == bid)
            .findFirst()
            .orElse(null);
    }

    public void renewBook(String bid) {
        try {
            int bookId = Integer.parseInt(bid);
            List<Borrow> borrows = XMLDataStore.readAll(Borrow.class, XMLDataStore.getBorrowsFile());

            for (Borrow borrow : borrows) {
                if (borrow.getBook_id() == bookId) {
                    // 延长归还时间1个月
                    LocalDateTime currentReturnDate = LocalDateTime.parse(borrow.getReturnDate(), dateFormatter);
                    borrow.setReturnDate(currentReturnDate.plusMonths(1).format(dateFormatter));
                    borrow.setRenewStatus(1);
                    break;
                }
            }

            XMLDataStore.writeAll(borrows, XMLDataStore.getBorrowsFile());
        } catch (Exception e) {
            throw new RuntimeException("续借失败: " + e.getMessage());
        }
    }

    public int getBookStatus(String bid) {
        try {
            int bookId = Integer.parseInt(bid);
            Borrow borrow = getBorrowByBid(bookId);
            return borrow != null ? borrow.getRenewStatus() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public void deleteBorrow(String id) {
        try {
            int borrowId = Integer.parseInt(id);
            XMLDataStore.delete(borrowId, "id", Borrow.class, XMLDataStore.getBorrowsFile());
        } catch (Exception e) {
            throw new RuntimeException("删除借阅记录失败: " + e.getMessage());
        }
    }

    public List<Borrow> getBorrowList() {
        List<Borrow> borrows = XMLDataStore.readAll(Borrow.class, XMLDataStore.getBorrowsFile());
        List<Student> students = XMLDataStore.readAll(Student.class, XMLDataStore.getStudentsFile());
        List<Book> books = XMLDataStore.readAll(Book.class, XMLDataStore.getBooksFile());

        // 关联查询，填充学生和图书信息
        for (Borrow borrow : borrows) {
            // 查找学生信息
            students.stream()
                .filter(s -> s.getSid() == borrow.getStudent_id())
                .findFirst()
                .ifPresent(s -> borrow.setStudent_name(s.getName()));

            // 查找图书信息
            books.stream()
                .filter(b -> b.getBid() == borrow.getBook_id())
                .findFirst()
                .ifPresent(b -> borrow.setBook_name(b.getTitle()));
        }

        return borrows;
    }

    public List<Book> getRnewBookList() {
        List<Borrow> borrows = XMLDataStore.readAll(Borrow.class, XMLDataStore.getBorrowsFile());
        List<Book> allBooks = getBookList();

        // 返回所有已借出的图书（有借阅记录的）
        return allBooks.stream()
            .filter(book -> borrows.stream().anyMatch(borrow -> borrow.getBook_id() == book.getBid()))
            .collect(Collectors.toList());
    }
}