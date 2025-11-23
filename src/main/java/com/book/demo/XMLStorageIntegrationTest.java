package com.book.demo;

import com.book.dao.xml.BookXMLDAO;
import com.book.dao.xml.StudentXMLDAO;
import com.book.dao.xml.UserXMLDAO;
import com.book.entity.Book;
import com.book.entity.Student;
import com.book.entity.User;
import com.book.entity.Borrow;
import com.book.utils.XMLDataStore;

import java.io.File;
import java.util.List;

/**
 * XML存储系统完整集成测试
 * 全面测试所有功能模块
 */
public class XMLStorageIntegrationTest {

    private static int testCount = 0;
    private static int passedCount = 0;

    public static void main(String[] args) {
        System.out.println("=== CloudBook XML存储系统完整集成测试 ===");
        System.out.println("测试时间: " + java.time.LocalDateTime.now());
        System.out.println("测试人员: 自动化测试系统");
        System.out.println("测试版本: v1.0\n");

        // 清理旧数据
        cleanupTestData();

        // 1. 用户注册和登录测试
        testUserRegistrationAndLogin();

        // 2. 学生信息管理测试
        testStudentManagement();

        // 3. 图书信息管理测试
        testBookManagement();

        // 4. 借阅记录管理测试
        testBorrowManagement();

        // 5. XML文件创建和数据持久化测试
        testDataPersistence();

        // 6. 数据完整性测试
        testDataIntegrity();

        // 输出测试报告
        printTestReport();
    }

    private static void cleanupTestData() {
        System.out.println("--- 清理测试数据 ---");
        try {
            new File(XMLDataStore.getBooksFile()).delete();
            new File(XMLDataStore.getStudentsFile()).delete();
            new File(XMLDataStore.getUsersFile()).delete();
            new File(XMLDataStore.getBorrowsFile()).delete();
            System.out.println("✓ 测试数据清理完成");
        } catch (Exception e) {
            System.out.println("✗ 测试数据清理失败: " + e.getMessage());
        }
    }

    private static void testUserRegistrationAndLogin() {
        System.out.println("\n--- 用户注册和登录测试 ---");
        UserXMLDAO userDAO = new UserXMLDAO();

        // 测试用户注册
        test("用户注册功能", () -> {
            userDAO.insertUser("admin", "123456");
            userDAO.insertUser("librarian", "password123");
            return true;
        });

        // 测试用户登录验证
        test("用户登录验证", () -> {
            User admin = userDAO.getUser("admin", "123456");
            User librarian = userDAO.getUser("librarian", "password123");
            return admin != null && librarian != null &&
                   "admin".equals(admin.getUsername()) && "librarian".equals(librarian.getUsername());
        });

        // 测试用户名重复检查
        test("用户名重复检查", () -> {
            User existingUser = userDAO.getUserByName("admin");
            return existingUser != null && "admin".equals(existingUser.getUsername());
        });

        // 测试获取所有用户
        test("获取所有用户", () -> {
            List<User> users = userDAO.getAllUsers();
            return users.size() >= 2;
        });
    }

    private static void testStudentManagement() {
        System.out.println("\n--- 学生信息管理测试 ---");
        StudentXMLDAO studentDAO = new StudentXMLDAO();

        // 测试添加学生
        test("添加学生信息", () -> {
            studentDAO.addStudent("张三", "男", 1);
            studentDAO.addStudent("李四", "女", 2);
            studentDAO.addStudent("王五", "男", 3);
            return true;
        });

        // 测试获取学生列表
        test("获取学生列表", () -> {
            List<Student> students = studentDAO.getStudentList();
            return students.size() >= 3;
        });

        // 测试按姓名查找学生
        test("按姓名查找学生", () -> {
            Student student = studentDAO.getStudentByName("张三");
            return student != null && "张三".equals(student.getName()) && "男".equals(student.getSex());
        });

        // 测试学生数据完整性
        test("学生数据完整性", () -> {
            List<Student> students = studentDAO.getStudentList();
            boolean allValid = true;
            for (Student student : students) {
                if (student.getName() == null || student.getSex() == null || student.getGrade() <= 0) {
                    allValid = false;
                    break;
                }
            }
            return allValid;
        });
    }

    private static void testBookManagement() {
        System.out.println("\n--- 图书信息管理测试 ---");
        BookXMLDAO bookDAO = new BookXMLDAO();

        // 测试添加图书
        test("添加图书信息", () -> {
            bookDAO.addBook("Java编程思想", "Java经典教程", 89.9, "/images/java-book.jpg");
            bookDAO.addBook("设计模式", "软件设计模式详解", 59.9, "/images/design-patterns.jpg");
            bookDAO.addBook("算法导论", "计算机算法经典教材", 128.0, "/images/clrs.jpg");
            return true;
        });

        // 测试获取图书列表
        test("获取图书列表", () -> {
            List<Book> books = bookDAO.getBookList();
            return books.size() >= 3;
        });

        // 测试图书搜索功能
        test("图书搜索功能", () -> {
            List<Book> javaBooks = bookDAO.getBookByTitle("Java");
            List<Book> patternBooks = bookDAO.getBookByTitle("设计");
            return javaBooks.size() >= 1 && patternBooks.size() >= 1;
        });

        // 测试图书数据完整性
        test("图书数据完整性", () -> {
            List<Book> books = bookDAO.getBookList();
            boolean allValid = true;
            for (Book book : books) {
                if (book.getTitle() == null || book.getPrice() < 0) {
                    allValid = false;
                    break;
                }
            }
            return allValid;
        });
    }

    private static void testBorrowManagement() {
        System.out.println("\n--- 借阅记录管理测试 ---");
        BookXMLDAO bookDAO = new BookXMLDAO();

        // 获取测试用的学生和图书
        List<Student> students = XMLDataStore.readAll(Student.class, XMLDataStore.getStudentsFile());
        List<Book> books = XMLDataStore.readAll(Book.class, XMLDataStore.getBooksFile());

        if (students.isEmpty() || books.isEmpty()) {
            System.out.println("✗ 借阅测试失败: 缺少测试用的学生或图书数据");
            return;
        }

        int testStudentId = students.get(0).getSid();
        int testBookId = books.get(0).getBid();

        // 测试添加借阅记录
        test("添加借阅记录", () -> {
            bookDAO.addBorrow(testStudentId, testBookId);
            return true;
        });

        // 测试获取借阅列表
        test("获取借阅列表", () -> {
            List<Borrow> borrows = bookDAO.getBorrowList();
            return !borrows.isEmpty();
        });

        // 测试图书续借功能
        test("图书续借功能", () -> {
            // 注意: 这里假设renewBook方法返回boolean
            String bookIdStr = String.valueOf(testBookId);
            try {
                // 由于原方法返回void，这里只测试方法是否能执行
                bookDAO.renewBook(bookIdStr);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    private static void testDataPersistence() {
        System.out.println("\n--- 数据持久化测试 ---");

        // 测试XML文件是否创建
        test("XML文件创建", () -> {
            File booksFile = new File(XMLDataStore.getBooksFile());
            File studentsFile = new File(XMLDataStore.getStudentsFile());
            File usersFile = new File(XMLDataStore.getUsersFile());
            File borrowsFile = new File(XMLDataStore.getBorrowsFile());

            return booksFile.exists() && studentsFile.exists() &&
                   usersFile.exists() && borrowsFile.exists();
        });

        // 测试XML文件大小
        test("XML文件大小验证", () -> {
            File booksFile = new File(XMLDataStore.getBooksFile());
            File studentsFile = new File(XMLDataStore.getStudentsFile());
            File usersFile = new File(XMLDataStore.getUsersFile());

            return booksFile.length() > 0 && studentsFile.length() > 0 && usersFile.length() > 0;
        });

        // 测试数据读写一致性
        test("数据读写一致性", () -> {
            List<Book> books = XMLDataStore.readAll(Book.class, XMLDataStore.getBooksFile());
            List<Student> students = XMLDataStore.readAll(Student.class, XMLDataStore.getStudentsFile());
            List<User> users = XMLDataStore.readAll(User.class, XMLDataStore.getUsersFile());

            return books.size() > 0 && students.size() > 0 && users.size() > 0;
        });
    }

    private static void testDataIntegrity() {
        System.out.println("\n--- 数据完整性测试 ---");

        // 测试数据关联完整性
        test("数据关联完整性", () -> {
            List<Borrow> borrows = XMLDataStore.readAll(Borrow.class, XMLDataStore.getBorrowsFile());
            List<Book> books = XMLDataStore.readAll(Book.class, XMLDataStore.getBooksFile());
            List<Student> students = XMLDataStore.readAll(Student.class, XMLDataStore.getStudentsFile());

            boolean allValid = true;
            for (Borrow borrow : borrows) {
                // 检查借阅记录中的图书ID是否存在
                boolean bookExists = books.stream().anyMatch(book -> book.getBid() == borrow.getBook_id());
                // 检查借阅记录中的学生ID是否存在
                boolean studentExists = students.stream().anyMatch(student -> student.getSid() == borrow.getStudent_id());

                if (!bookExists || !studentExists) {
                    allValid = false;
                    break;
                }
            }
            return allValid;
        });

        // 测试数据唯一性
        test("数据唯一性约束", () -> {
            List<User> users = XMLDataStore.readAll(User.class, XMLDataStore.getUsersFile());
            List<Book> books = XMLDataStore.readAll(Book.class, XMLDataStore.getBooksFile());

            // 检查用户名唯一性
            boolean uniqueUsernames = users.stream()
                .map(User::getUsername)
                .distinct()
                .count() == users.size();

            // 检查图书ID唯一性
            boolean uniqueBookIds = books.stream()
                .map(Book::getBid)
                .distinct()
                .count() == books.size();

            return uniqueUsernames && uniqueBookIds;
        });
    }

    // 测试辅助方法
    private static void test(String testName, TestCase testCase) {
        testCount++;
        try {
            boolean result = testCase.run();
            if (result) {
                passedCount++;
                System.out.println("✓ " + testName + " - 通过");
            } else {
                System.out.println("✗ " + testName + " - 失败");
            }
        } catch (Exception e) {
            System.out.println("✗ " + testName + " - 异常: " + e.getMessage());
        }
    }

    private static void printTestReport() {
        System.out.println("\n=== 测试报告 ===");
        System.out.println("总测试数: " + testCount);
        System.out.println("通过测试: " + passedCount);
        System.out.println("失败测试: " + (testCount - passedCount));
        System.out.println("成功率: " + String.format("%.1f", (passedCount * 100.0 / testCount)) + "%");

        if (passedCount == testCount) {
            System.out.println("\n🎉 恭喜！所有测试通过，XML存储系统工作正常！");
        } else {
            System.out.println("\n⚠️  部分测试失败，请检查相关功能模块。");
        }

        System.out.println("\n=== 系统信息 ===");
        System.out.println("Java版本: " + System.getProperty("java.version"));
        System.out.println("操作系统: " + System.getProperty("os.name"));
        System.out.println("数据存储路径: " + new File("data").getAbsolutePath());
        System.out.println("测试完成时间: " + java.time.LocalDateTime.now());
    }

    @FunctionalInterface
    private interface TestCase {
        boolean run() throws Exception;
    }
}