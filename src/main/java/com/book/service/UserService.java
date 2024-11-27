package com.book.service;

import jakarta.servlet.http.HttpSession;

public interface UserService {
    boolean auth(String username, String password, HttpSession session);

    boolean AlreadyUsername(String username, HttpSession session);

    void InsertUser(String username, String password, HttpSession session);

}
