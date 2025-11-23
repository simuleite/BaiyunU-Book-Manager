package com.book.demo;

import com.book.utils.XMLDataStore;
import com.book.entity.Book;
import com.book.entity.Student;
import com.book.entity.User;
import com.book.entity.Borrow;

import java.util.List;

/**
 * 简化的XML存储演示
 */
public class SimpleXMLDemo {
    public static void main(String[] args) {
        System.out.println("=== 简化XML存储系统演示 ===");

        // 演示基本XML读写
        testBasicXMLStorage();

        System.out.println("=== 演示完成 ===");
    }

    private static void testBasicXMLStorage() {
        System.out.println("\n--- 基本XML存储测试 ---");

        try {
            // 创建测试图书
            Book book = new Book();
            // 使用反射设置字段值，避免直接访问私有字段
            Book.class.getField("bid").setInt(book, 1);
            Book.class.getField("title").set(book, "测试图书");
            Book.class.getField("author").set(book, "测试作者");
            Book.class.getField("desc").set(book, "这是一本测试图书");
            Book.class.getField("price").setDouble(book, 29.99);
            Book.class.getField("imagePath").set(book, "/images/test.jpg");

            // 添加到XML存储
            XMLDataStore.add(book, Book.class, XMLDataStore.getBooksFile());
            System.out.println("✓ 图书添加成功");

            // 读取图书列表
            List<Book> books = XMLDataStore.readAll(Book.class, XMLDataStore.getBooksFile());
            System.out.println("✓ 读取到 " + books.size() + " 本图书");

            if (!books.isEmpty()) {
                Book firstBook = books.get(0);
                System.out.println("  第一本图书信息:");
                System.out.println("    ID: " + Book.class.getField("bid").getInt(firstBook));
                System.out.println("    标题: " + Book.class.getField("title").get(firstBook));
                System.out.println("    价格: ¥" + Book.class.getField("price").getDouble(firstBook));
            }

        } catch (Exception e) {
            System.err.println("XML存储测试失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}