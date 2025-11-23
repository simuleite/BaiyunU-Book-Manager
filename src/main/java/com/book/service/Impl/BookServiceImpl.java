package com.book.service.Impl;

import com.book.dao.xml.BookXMLDAO;
import com.book.entity.Book;
import com.book.entity.Borrow;
import com.book.service.BookService;

import java.util.*;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    // 静态变量，保存单例实例
    private static BookServiceImpl instance = null;
    private final BookXMLDAO bookXMLDAO;

    // 私有构造函数，防止外部直接实例化
    private BookServiceImpl() {
        this.bookXMLDAO = new BookXMLDAO();
    }

    // 公共的静态方法，返回单例实例
    public static synchronized BookServiceImpl getInstance() {
        if (instance == null) {
            instance = new BookServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Borrow> getBorrowList() {
        return bookXMLDAO.getBorrowList();
    }

    @Override
    public void returnBook(String id) {
        bookXMLDAO.deleteBorrow(id);
    }

    @Override
    public List<Book> getActiveBookList() {
        Set<Integer> set = new HashSet<>();
        getBorrowList().forEach(borrow -> set.add(borrow.getBook_id()));
        return bookXMLDAO.getBookList()
                .stream()
                .filter(book -> !set.contains(book.getBid()))
                .collect(Collectors.toList());
    }

    @Override
    public void addBorrow(int sid, int bid) {
        bookXMLDAO.addBorrow(sid, bid);
    }

    @Override
    public Map<Book, Boolean> getBookList() {
        Set<Integer> set = new HashSet<>();
        getBorrowList().forEach(borrow -> set.add(borrow.getBook_id()));
        Map<Book, Boolean> map = new HashMap<>();
        bookXMLDAO.getBookList().forEach(book -> {
            map.put(book, set.contains(book.getBid()));
        });
        return map;
    }

    @Override
    public void deleteBook(int bid) {
        bookXMLDAO.deleteBook(bid);
    }

    @Override
    public void addBook(String title, String desc, double price, String imagePath) {
        bookXMLDAO.addBook(title, desc, price, imagePath);
    }

    @Override
    public boolean renewBook(String bid) {
        int bookStatus = bookXMLDAO.getBookStatus(bid);
        if(bookStatus == 0){
            bookXMLDAO.renewBook(bid);
            return true;
        }else{
            System.out.println("该书已被借出，不能续借！");
            return false;
        }
    }

    @Override
    public Map<Book, Boolean> getBookByTitle(String title) {
        List<Book> books = bookXMLDAO.getBookByTitle(title);
        Map<Book, Boolean> res = new HashMap<>();
        for (Book book: books) {
            if (book != null) res.put(book, bookXMLDAO.getBorrowByBid(book.getBid()) != null);
        }
        return res;
    }

}
