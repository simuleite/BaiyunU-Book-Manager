package com.book.service.Impl;

import com.book.dao.xml.UserXMLDAO;
import com.book.entity.User;
import com.book.service.UserService;
import jakarta.servlet.http.HttpSession;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance = null;
    private final UserXMLDAO userXMLDAO;

    private UserServiceImpl() {
        this.userXMLDAO = new UserXMLDAO();
    }

    public static synchronized UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean auth(String username, String password, HttpSession session) {
        User user = userXMLDAO.getUser(username, password);
        if (user == null) return false;
        session.setAttribute("user", user);
        return true;
    }

    @Override
    public boolean AlreadyUsername(String username, HttpSession session) {
        User user = userXMLDAO.getUserByName(username);
        if (user == null) return false;
        return true;
    }

    public User getUserBy(String username, HttpSession session){
        return userXMLDAO.getUserByName(username);
    }

    @Override
    public void InsertUser(String username, String password, HttpSession session) {
        userXMLDAO.insertUser(username, password);
        User user = getUserBy(username, session);
        session.setAttribute("user", user);
    }
}
