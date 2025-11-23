package com.book.demo;

import com.book.entity.Book;
import com.book.entity.Student;
import com.book.entity.User;
import com.book.entity.Borrow;
import com.book.utils.XMLDataStore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock数据初始化类
 * 用于在系统启动时初始化一些测试数据
 */
public class MockDataInitializer {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 初始化所有Mock数据
     */
    public static void initializeAllMockData() {
        initializeMockBooks();
        initializeMockStudents();
        initializeMockUsers();
        initializeMockBorrows();
    }

    /**
     * 初始化Mock图书数据
     */
    public static void initializeMockBooks() {
        List<Book> mockBooks = new ArrayList<>();

        // 添加图书数据
        mockBooks.add(createBook(33, "活着", "2018版", 35.50, "余华", "static/picture/books/ToLive82563.jpg"));
        mockBooks.add(createBook(34, "史记", "中华书局注校", 119.80, "司马迁", "static/picture/books/History55386.jpg"));
        mockBooks.add(createBook(35, "罪与罚", "汝龙 译", 59.90, "陀思妥耶夫斯基", "static/picture/books/SinAndPanish94467.jpg"));
        mockBooks.add(createBook(36, "资治通鉴", "上海古籍出版社", 220.50, "司马光", "static/picture/books/Experiences36198.jpg"));
        mockBooks.add(createBook(38, "三体", "刘慈欣", 69.90, "刘慈欣", "static/picture/books/ThreeBody146694.jpg"));
        mockBooks.add(createBook(40, "三体典藏版", "全三套", 99.90, "刘慈欣", "static/picture/books/ThreeBody53542.jpg"));
        mockBooks.add(createBook(43, "三体1", "普通版", 66.60, "刘慈欣", "static/picture/books/ThreeBody115683.jpg"));

        // 检查是否已有数据，如果没有则初始化
        List<Book> existingBooks = XMLDataStore.readAll(Book.class, XMLDataStore.getBooksFile());
        if (existingBooks.isEmpty()) {
            XMLDataStore.writeAll(mockBooks, XMLDataStore.getBooksFile());
            System.out.println("Mock图书数据初始化完成，共 " + mockBooks.size() + " 本图书");
        }
    }

    /**
     * 初始化Mock学生数据（使用真实数据）
     */
    public static void initializeMockStudents() {
        List<Student> mockStudents = new ArrayList<>();

        // 使用真实的学生数据
        mockStudents.add(createStudent(10, "李柔", "女", 2024));
        mockStudents.add(createStudent(11, "秦明", "男", 2023));
        mockStudents.add(createStudent(13, "胡歌", "男", 2019));
        mockStudents.add(createStudent(21, "柳湘莲", "男", 2019));
        mockStudents.add(createStudent(24, "张陶", "男", 2021));
        mockStudents.add(createStudent(33, "古龙", "男", 2019));
        mockStudents.add(createStudent(36, "晴雯", "女", 2020));
        mockStudents.add(createStudent(38, "刘文文", "女", 2023));
        mockStudents.add(createStudent(42, "罗紫", "男", 2019));
        mockStudents.add(createStudent(90, "汪明珠", "女", 2024));

        List<Student> existingStudents = XMLDataStore.readAll(Student.class, XMLDataStore.getStudentsFile());
        if (existingStudents.isEmpty()) {
            XMLDataStore.writeAll(mockStudents, XMLDataStore.getStudentsFile());
            System.out.println("Mock学生数据初始化完成，共 " + mockStudents.size() + " 名学生");
        }
    }

    /**
     * 初始化Mock用户数据
     */
    public static void initializeMockUsers() {
        List<User> mockUsers = new ArrayList<>();

        // 添加管理员用户
        mockUsers.add(createUser(1, "admin", "admin123", "系统管理员"));
        mockUsers.add(createUser(2, "librarian", "lib123", "图书管理员"));

        // 添加学生用户
        mockUsers.add(createUser(3, "zhangsan", "zs123", "张三"));
        mockUsers.add(createUser(4, "lisi", "ls123", "李四"));
        mockUsers.add(createUser(5, "wangwu", "ww123", "王五"));

        List<User> existingUsers = XMLDataStore.readAll(User.class, XMLDataStore.getUsersFile());
        if (existingUsers.isEmpty()) {
            XMLDataStore.writeAll(mockUsers, XMLDataStore.getUsersFile());
            System.out.println("Mock用户数据初始化完成，共 " + mockUsers.size() + " 个用户");
        }
    }

    /**
     * 初始化Mock借阅数据
     */
    public static void initializeMockBorrows() {
        List<Borrow> mockBorrows = new ArrayList<>();

        // 创建一些借阅记录
        LocalDateTime now = LocalDateTime.now();

        mockBorrows.add(createBorrow(1, 1, 33, "张三", "活着",
            now.minusDays(10).format(dateFormatter),
            now.plusDays(20).format(dateFormatter), 0));

        mockBorrows.add(createBorrow(2, 2, 35, "李四", "罪与罚",
            now.minusDays(5).format(dateFormatter),
            now.plusDays(25).format(dateFormatter), 0));

        mockBorrows.add(createBorrow(3, 3, 38, "王五", "三体",
            now.minusDays(2).format(dateFormatter),
            now.plusDays(28).format(dateFormatter), 1));

        List<Borrow> existingBorrows = XMLDataStore.readAll(Borrow.class, XMLDataStore.getBorrowsFile());
        if (existingBorrows.isEmpty()) {
            XMLDataStore.writeAll(mockBorrows, XMLDataStore.getBorrowsFile());
            System.out.println("Mock借阅数据初始化完成，共 " + mockBorrows.size() + " 条记录");
        }
    }

    /**
     * 创建图书对象
     */
    private static Book createBook(int bid, String title, String desc, double price, String author, String imagePath) {
        Book book = new Book();
        book.setBid(bid);
        book.setTitle(title);
        book.setDesc(desc);
        book.setPrice(price);
        book.setAuthor(author);
        book.setImagePath(imagePath);
        return book;
    }

    /**
     * 创建学生对象
     */
    private static Student createStudent(int sid, String name, String sex, int grade) {
        Student student = new Student();
        student.setSid(sid);
        student.setName(name);
        student.setSex(sex);
        student.setGrade(grade);
        return student;
    }

    /**
     * 创建用户对象
     */
    private static User createUser(int id, String username, String password, String nickname) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        return user;
    }

    /**
     * 创建借阅对象
     */
    private static Borrow createBorrow(int id, int studentId, int bookId, String studentName, String bookName,
                                     String borrowDate, String returnDate, int renewStatus) {
        Borrow borrow = new Borrow();
        borrow.setId(id);
        borrow.setStudent_id(studentId);
        borrow.setBook_id(bookId);
        borrow.setStudent_name(studentName);
        borrow.setBook_name(bookName);
        borrow.setBorrowDate(borrowDate);
        borrow.setReturnDate(returnDate);
        borrow.setRenewStatus(renewStatus);
        return borrow;
    }
}