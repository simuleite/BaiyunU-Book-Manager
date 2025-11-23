package com.book.dao.xml;

import com.book.entity.User;
import com.book.utils.XMLDataStore;
import com.book.utils.MD5Util;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户XML数据访问对象，替代原来的UserMapper
 */
public class UserXMLDAO {

    private static final AtomicInteger userIdGenerator = new AtomicInteger(1);

    public User getUser(String username, String password) {
        return XMLDataStore.readAll(User.class, XMLDataStore.getUsersFile()).stream()
            .filter(user -> user.getUsername() != null &&
                          user.getUsername().equals(username) &&
                          user.getPassword() != null &&
                          user.getPassword().equals(MD5Util.toMD5(password)))
            .findFirst()
            .orElse(null);
    }

    public User getUserByName(String username) {
        return XMLDataStore.readAll(User.class, XMLDataStore.getUsersFile()).stream()
            .filter(user -> user.getUsername() != null && user.getUsername().equals(username))
            .findFirst()
            .orElse(null);
    }

    public void insertUser(String username, String password) {
        // 检查用户名是否已存在
        if (getUserByName(username) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }

        List<User> users = XMLDataStore.readAll(User.class, XMLDataStore.getUsersFile());

        // 生成新的用户ID
        int newId = users.stream()
            .mapToInt(user -> user.getId())
            .max()
            .orElse(0) + 1;

        User user = new User();
        user.setId(newId);
        user.setUsername(username);
        user.setNickname("图书管理员"); // 默认昵称
        user.setPassword(MD5Util.toMD5(password));

        XMLDataStore.add(user, User.class, XMLDataStore.getUsersFile());
    }

    public List<User> getAllUsers() {
        return XMLDataStore.readAll(User.class, XMLDataStore.getUsersFile());
    }

    public User getUserById(int id) {
        return XMLDataStore.findById(id, "id", User.class, XMLDataStore.getUsersFile());
    }

    public boolean deleteUser(int id) {
        return XMLDataStore.delete(id, "id", User.class, XMLDataStore.getUsersFile());
    }

    public void updateUser(User user) {
        if (user == null || user.getId() <= 0) {
            throw new IllegalArgumentException("Invalid user data");
        }

        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                XMLDataStore.writeAll(users, XMLDataStore.getUsersFile());
                return;
            }
        }
        throw new IllegalArgumentException("User not found with ID: " + user.getId());
    }

    public void updateUserPassword(int userId, String newPassword) {
        User user = getUserById(userId);
        if (user != null) {
            user.setPassword(MD5Util.toMD5(newPassword));
            updateUser(user);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }
}