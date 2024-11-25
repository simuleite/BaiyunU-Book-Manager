package com.book.service;

import com.book.entity.Borrow;

import java.util.List;

public interface BookServlet {
    List<Borrow> getBorrowList();
}
