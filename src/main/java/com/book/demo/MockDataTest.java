package com.book.demo;

import com.book.entity.Book;
import com.book.entity.Student;
import com.book.entity.User;
import com.book.entity.Borrow;
import com.book.utils.XMLDataStore;

/**
 * Mock数据测试类
 * 用于验证Mock数据是否正确初始化
 */
public class MockDataTest {

    public static void main(String[] args) {
        System.out.println("=== Mock数据测试 ===");

        // 测试图书数据
        testBookData();

        // 测试学生数据
        testStudentData();

        // 测试用户数据
        testUserData();

        // 测试借阅数据
        testBorrowData();

        System.out.println("=== 测试完成 ===");
    }

    private static void testBookData() {
        System.out.println("\n--- 测试图书数据 ---");
        var books = XMLDataStore.readAll(Book.class, XMLDataStore.getBooksFile());
        System.out.println("图书总数: " + books.size());

        // 验证特定图书是否存在
        boolean hasToLive = books.stream().anyMatch(book -> book.getTitle().equals("活着"));
        boolean hasThreeBody = books.stream().anyMatch(book -> book.getTitle().contains("三体"));

        System.out.println("是否包含《活着》: " + hasToLive);
        System.out.println("是否包含三体系列: " + hasThreeBody);

        // 打印部分图书信息
        books.stream().limit(3).forEach(book -> {
            System.out.printf("ID: %d, 书名: %s, 价格: %.2f, 作者: %s%n",
                book.getBid(), book.getTitle(), book.getPrice(), book.getAuthor());
        });
    }

    private static void testStudentData() {
        System.out.println("\n--- 测试学生数据 ---");
        var students = XMLDataStore.readAll(Student.class, XMLDataStore.getStudentsFile());
        System.out.println("学生总数: " + students.size());

        // 验证特定学生是否存在
        boolean hasLiRou = students.stream().anyMatch(student -> student.getName().equals("李柔"));
        boolean hasHuGe = students.stream().anyMatch(student -> student.getName().equals("胡歌"));

        System.out.println("是否包含李柔: " + hasLiRou);
        System.out.println("是否包含胡歌: " + hasHuGe);

        // 打印部分学生信息
        students.stream().limit(5).forEach(student -> {
            System.out.printf("ID: %d, 姓名: %s, 性别: %s, 年级: %d%n",
                student.getSid(), student.getName(), student.getSex(), student.getGrade());
        });
    }

    private static void testUserData() {
        System.out.println("\n--- 测试用户数据 ---");
        var users = XMLDataStore.readAll(User.class, XMLDataStore.getUsersFile());
        System.out.println("用户总数: " + users.size());

        // 验证管理员用户
        boolean hasAdmin = users.stream().anyMatch(user -> user.getUsername().equals("admin"));
        System.out.println("是否包含admin用户: " + hasAdmin);

        // 打印部分用户信息
        users.stream().limit(3).forEach(user -> {
            System.out.printf("ID: %d, 用户名: %s, 昵称: %s%n",
                user.getId(), user.getUsername(), user.getNickname());
        });
    }

    private static void testBorrowData() {
        System.out.println("\n--- 测试借阅数据 ---");
        var borrows = XMLDataStore.readAll(Borrow.class, XMLDataStore.getBorrowsFile());
        System.out.println("借阅记录总数: " + borrows.size());

        // 打印借阅记录
        borrows.forEach(borrow -> {
            String renewStatus = borrow.getRenewStatus() == 1 ? "已续借" : "未续借";
            System.out.printf("ID: %d, 学生: %s, 图书: %s, 借阅时间: %s, 应还时间: %s, 状态: %s%n",
                borrow.getId(), borrow.getStudent_name(), borrow.getBook_name(),
                borrow.getBorrowDate(), borrow.getReturnDate(), renewStatus);
        });
    }
}