package com.book.service.impl;


import com.book.dao.BookMapper;
import com.book.dao.UserMapper;
import com.book.entity.Borrow;
import com.book.entity.User;
import com.book.service.BookServlet;
import com.book.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BookServletImpl implements BookServlet {
    @Override
    public List<Borrow> getBorrowList() {
        try(SqlSession sqlSession = MybatisUtil.getSession();) {
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            return mapper.getBorrowList();
        }
    }
}
