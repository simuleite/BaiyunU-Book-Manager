package com.book.service.Impl;

import com.book.dao.UserMapper;
import com.book.entity.User;
import com.book.service.UserService;
import com.book.utils.MybatisUtil;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance = null;

    private UserServiceImpl() {
    }

    public static synchronized UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean auth(String username, String password, HttpSession session) {
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.getUser(username, password);
            if (user == null) return false;
            session.setAttribute("user", user);
            return true;
        }
    }

    @Override
    public boolean AlreadyUsername(String username, HttpSession session) {
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.getUserByName(username);
            if (user == null) return false;
            return true;
        }
    }

    public User getUserBy(String username, HttpSession session){
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.getUserByName(username);
        }
    }

    @Override
    public void InsertUser(String username, String password, HttpSession session) {
        try (SqlSession sqlSession = MybatisUtil.getSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.InsertUser(username, password);
            User user = getUserBy(username, session);
            session.setAttribute("user", user);
        }
    }
}
