package com.book.demo;

import com.book.entity.Book;
import com.book.entity.Student;
import com.book.entity.User;
import com.book.entity.Borrow;
import com.book.utils.XMLDataStore;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * XML文件验证器
 * 验证生成的XML文件的正确性和完整性
 */
public class XMLFileValidator {

    public static void main(String[] args) {
        System.out.println("=== XML文件验证报告 ===");
        System.out.println("验证时间: " + LocalDateTime.now());
        System.out.println("验证器版本: v1.0\n");

        validateAllXMLFiles();
    }

    private static void validateAllXMLFiles() {
        System.out.println("--- XML文件结构验证 ---");

        // 验证books.xml
        validateBooksXML();

        // 验证students.xml
        validateStudentsXML();

        // 验证users.xml
        validateUsersXML();

        // 验证borrows.xml
        validateBorrowsXML();

        // 验证数据关联性
        validateDataRelationships();

        // 验证文件完整性
        validateFileIntegrity();
    }

    private static void validateBooksXML() {
        System.out.println("\n📚 books.xml 验证:");
        File booksFile = new File(XMLDataStore.getBooksFile());

        if (!booksFile.exists()) {
            System.out.println("  ❌ books.xml 文件不存在");
            return;
        }

        System.out.println("  ✅ 文件存在: " + booksFile.getAbsolutePath());
        System.out.println("  📄 文件大小: " + booksFile.length() + " 字节");

        List<Book> books = XMLDataStore.readAll(Book.class, XMLDataStore.getBooksFile());
        System.out.println("  📊 图书数量: " + books.size());

        boolean allValid = true;
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            boolean valid = book.getBid() > 0 &&
                           book.getTitle() != null && !book.getTitle().trim().isEmpty() &&
                           book.getPrice() >= 0;

            if (valid) {
                System.out.println("    ✅ 图书 " + (i+1) + ": " + book.getTitle() + " (ID: " + book.getBid() + ", 价格: ¥" + book.getPrice() + ")");
            } else {
                System.out.println("    ❌ 图书 " + (i+1) + ": 数据不完整或无效");
                allValid = false;
            }
        }

        if (allValid) {
            System.out.println("  🎉 所有图书数据验证通过");
        } else {
            System.out.println("  ⚠️  发现图书数据问题");
        }
    }

    private static void validateStudentsXML() {
        System.out.println("\n👥 students.xml 验证:");
        File studentsFile = new File(XMLDataStore.getStudentsFile());

        if (!studentsFile.exists()) {
            System.out.println("  ❌ students.xml 文件不存在");
            return;
        }

        System.out.println("  ✅ 文件存在: " + studentsFile.getAbsolutePath());
        System.out.println("  📄 文件大小: " + studentsFile.length() + " 字节");

        List<Student> students = XMLDataStore.readAll(Student.class, XMLDataStore.getStudentsFile());
        System.out.println("  📊 学生数量: " + students.size());

        boolean allValid = true;
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            boolean valid = student.getSid() > 0 &&
                           student.getName() != null && !student.getName().trim().isEmpty() &&
                           student.getSex() != null &&
                           student.getGrade() > 0;

            if (valid) {
                System.out.println("    ✅ 学生 " + (i+1) + ": " + student.getName() + " (ID: " + student.getSid() + ", 年级: " + student.getGrade() + ", 性别: " + student.getSex() + ")");
            } else {
                System.out.println("    ❌ 学生 " + (i+1) + ": 数据不完整或无效");
                allValid = false;
            }
        }

        if (allValid) {
            System.out.println("  🎉 所有学生数据验证通过");
        } else {
            System.out.println("  ⚠️  发现学生数据问题");
        }
    }

    private static void validateUsersXML() {
        System.out.println("\n👤 users.xml 验证:");
        File usersFile = new File(XMLDataStore.getUsersFile());

        if (!usersFile.exists()) {
            System.out.println("  ❌ users.xml 文件不存在");
            return;
        }

        System.out.println("  ✅ 文件存在: " + usersFile.getAbsolutePath());
        System.out.println("  📄 文件大小: " + usersFile.length() + " 字节");

        List<User> users = XMLDataStore.readAll(User.class, XMLDataStore.getUsersFile());
        System.out.println("  📊 用户数量: " + users.size());

        boolean allValid = true;
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            boolean valid = user.getId() > 0 &&
                           user.getUsername() != null && !user.getUsername().trim().isEmpty() &&
                           user.getPassword() != null && !user.getPassword().trim().isEmpty();

            if (valid) {
                System.out.println("    ✅ 用户 " + (i+1) + ": " + user.getUsername() + " (ID: " + user.getId() + ", 昵称: " + user.getNickname() + ")");
            } else {
                System.out.println("    ❌ 用户 " + (i+1) + ": 数据不完整或无效");
                allValid = false;
            }
        }

        if (allValid) {
            System.out.println("  🎉 所有用户数据验证通过");
        } else {
            System.out.println("  ⚠️  发现用户数据问题");
        }
    }

    private static void validateBorrowsXML() {
        System.out.println("\n📖 borrows.xml 验证:");
        File borrowsFile = new File(XMLDataStore.getBorrowsFile());

        if (!borrowsFile.exists()) {
            System.out.println("  ❌ borrows.xml 文件不存在");
            return;
        }

        System.out.println("  ✅ 文件存在: " + borrowsFile.getAbsolutePath());
        System.out.println("  📄 文件大小: " + borrowsFile.length() + " 字节");

        List<Borrow> borrows = XMLDataStore.readAll(Borrow.class, XMLDataStore.getBorrowsFile());
        System.out.println("  📊 借阅记录数量: " + borrows.size());

        boolean allValid = true;
        for (int i = 0; i < borrows.size(); i++) {
            Borrow borrow = borrows.get(i);
            boolean valid = borrow.getId() > 0 &&
                           borrow.getBook_id() > 0 &&
                           borrow.getStudent_id() > 0 &&
                           borrow.getBorrowDate() != null && !borrow.getBorrowDate().trim().isEmpty();

            if (valid) {
                System.out.println("    ✅ 借阅 " + (i+1) + ": " + borrow.getBook_name() + " -> " + borrow.getStudent_name() + " (借阅时间: " + borrow.getBorrowDate() + ")");
            } else {
                System.out.println("    ❌ 借阅 " + (i+1) + ": 数据不完整或无效");
                allValid = false;
            }
        }

        if (allValid) {
            System.out.println("  🎉 所有借阅记录数据验证通过");
        } else {
            System.out.println("  ⚠️  发现借阅记录数据问题");
        }
    }

    private static void validateDataRelationships() {
        System.out.println("\n🔗 数据关联性验证:");

        List<Book> books = XMLDataStore.readAll(Book.class, XMLDataStore.getBooksFile());
        List<Student> students = XMLDataStore.readAll(Student.class, XMLDataStore.getStudentsFile());
        List<Borrow> borrows = XMLDataStore.readAll(Borrow.class, XMLDataStore.getBorrowsFile());

        boolean allRelationshipsValid = true;

        for (Borrow borrow : borrows) {
            // 检查图书是否存在
            boolean bookExists = books.stream().anyMatch(book -> book.getBid() == borrow.getBook_id());
            // 检查学生是否存在
            boolean studentExists = students.stream().anyMatch(student -> student.getSid() == borrow.getStudent_id());

            if (bookExists && studentExists) {
                System.out.println("  ✅ 借阅记录 " + borrow.getId() + ": 数据关联正确");
            } else {
                System.out.println("  ❌ 借阅记录 " + borrow.getId() + ": 数据关联错误 (图书存在: " + bookExists + ", 学生存在: " + studentExists + ")");
                allRelationshipsValid = false;
            }
        }

        if (allRelationshipsValid) {
            System.out.println("  🎉 所有数据关联性验证通过");
        } else {
            System.out.println("  ⚠️  发现数据关联性问题");
        }
    }

    private static void validateFileIntegrity() {
        System.out.println("\n🛡️  文件完整性验证:");

        File[] xmlFiles = {
            new File(XMLDataStore.getBooksFile()),
            new File(XMLDataStore.getStudentsFile()),
            new File(XMLDataStore.getUsersFile()),
            new File(XMLDataStore.getBorrowsFile())
        };

        String[] fileNames = {"books.xml", "students.xml", "users.xml", "borrows.xml"};

        boolean allFilesValid = true;

        for (int i = 0; i < xmlFiles.length; i++) {
            File file = xmlFiles[i];
            String fileName = fileNames[i];

            System.out.println("  📁 验证文件: " + fileName);

            // 检查文件是否存在
            if (!file.exists()) {
                System.out.println("    ❌ 文件不存在");
                allFilesValid = false;
                continue;
            }

            // 检查文件是否可读
            if (!file.canRead()) {
                System.out.println("    ❌ 文件不可读");
                allFilesValid = false;
                continue;
            }

            // 检查文件大小
            if (file.length() == 0) {
                System.out.println("    ❌ 文件为空");
                allFilesValid = false;
                continue;
            }

            // 检查XML格式（简单验证）
            try {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                if (content.trim().startsWith("<?xml") || content.contains("<XMLWrapper>")) {
                    System.out.println("    ✅ XML格式有效 (大小: " + file.length() + " 字节)");
                } else {
                    System.out.println("    ❌ XML格式无效");
                    allFilesValid = false;
                }
            } catch (Exception e) {
                System.out.println("    ❌ 文件读取错误: " + e.getMessage());
                allFilesValid = false;
            }
        }

        if (allFilesValid) {
            System.out.println("  🎉 所有文件完整性验证通过");
        } else {
            System.out.println("  ⚠️  发现文件完整性问题");
        }

        // 输出存储统计信息
        System.out.println("\n📈 存储统计信息:");
        for (int i = 0; i < xmlFiles.length; i++) {
            File file = xmlFiles[i];
            String fileName = fileNames[i];
            System.out.println("  📄 " + fileName + ": " + file.length() + " 字节");
        }

        long totalSize = 0;
        for (File file : xmlFiles) {
            totalSize += file.length();
        }
        System.out.println("  💾 总存储大小: " + totalSize + " 字节 (" + String.format("%.2f", totalSize / 1024.0) + " KB)");
    }
}