package com.book.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.book.demo.MockDataInitializer;

/**
 * XML数据存储工具类
 * 提供基于本地XML文件的CRUD操作
 */
public class XMLDataStore {
    private static final String DATA_DIR = "data";
    private static final String BOOKS_FILE = DATA_DIR + "/books.xml";
    private static final String STUDENTS_FILE = DATA_DIR + "/students.xml";
    private static final String USERS_FILE = DATA_DIR + "/users.xml";
    private static final String BORROWS_FILE = DATA_DIR + "/borrows.xml";

    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final XmlMapper xmlMapper;

    static {
        xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 确保数据目录存在
        try {
            Path dataPath = Paths.get(DATA_DIR);
            if (!Files.exists(dataPath)) {
                Files.createDirectories(dataPath);
            }
            // 初始化XML文件
            initializeFile(BOOKS_FILE);
            initializeFile(STUDENTS_FILE);
            initializeFile(USERS_FILE);
            initializeFile(BORROWS_FILE);

            // 初始化Mock数据
            initializeMockData();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize data store", e);
        }
    }

    private static void initializeFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            xmlMapper.writeValue(file, new XMLWrapper<>());
        }
    }

    public static <T> List<T> readAll(Class<T> clazz, String filename) {
        lock.readLock().lock();
        try {
            File file = new File(filename);
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }
            XMLWrapper<T> wrapper = xmlMapper.readValue(file,
                xmlMapper.getTypeFactory().constructParametricType(XMLWrapper.class, clazz));
            return wrapper.getItems();
        } catch (IOException e) {
            System.err.println("Error reading from " + filename + ": " + e.getMessage());
            return new ArrayList<>();
        } finally {
            lock.readLock().unlock();
        }
    }

    public static <T> void writeAll(List<T> data, String filename) {
        lock.writeLock().lock();
        try {
            XMLWrapper<T> wrapper = new XMLWrapper<>(data);
            xmlMapper.writeValue(new File(filename), wrapper);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to " + filename, e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static <T> void add(T item, Class<T> clazz, String filename) {
        lock.writeLock().lock();
        try {
            List<T> items = readAll(clazz, filename);
            items.add(item);
            writeAll(items, filename);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static <T> T findById(int id, String idField, Class<T> clazz, String filename) {
        return readAll(clazz, filename).stream()
            .filter(item -> {
                try {
                    return (int) clazz.getMethod("get" + capitalize(idField)).invoke(item) == id;
                } catch (Exception e) {
                    return false;
                }
            })
            .findFirst()
            .orElse(null);
    }

    public static <T> boolean delete(int id, String idField, Class<T> clazz, String filename) {
        lock.writeLock().lock();
        try {
            List<T> items = readAll(clazz, filename);
            boolean removed = items.removeIf(item -> {
                try {
                    return (int) clazz.getMethod("get" + capitalize(idField)).invoke(item) == id;
                } catch (Exception e) {
                    return false;
                }
            });
            if (removed) {
                writeAll(items, filename);
            }
            return removed;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String getBooksFile() {
        return BOOKS_FILE;
    }

    public static String getStudentsFile() {
        return STUDENTS_FILE;
    }

    public static String getUsersFile() {
        return USERS_FILE;
    }

    public static String getBorrowsFile() {
        return BORROWS_FILE;
    }

    /**
     * 初始化Mock数据
     */
    private static void initializeMockData() {
        try {
            MockDataInitializer.initializeAllMockData();
        } catch (Exception e) {
            System.err.println("Mock数据初始化失败: " + e.getMessage());
            // 不抛出异常，允许系统继续运行
        }
    }
}