package com.book.dao.xml;

import com.book.entity.Student;
import com.book.utils.XMLDataStore;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 学生XML数据访问对象，替代原来的StudentMapper
 */
public class StudentXMLDAO {

    private static final AtomicInteger studentIdGenerator = new AtomicInteger(1);

    public List<Student> getStudentList() {
        return XMLDataStore.readAll(Student.class, XMLDataStore.getStudentsFile());
    }

    public void addStudent(String name, String sex, int grade) {
        List<Student> students = getStudentList();

        // 生成新的学生ID
        int newId = students.stream()
            .mapToInt(student -> student.getSid())
            .max()
            .orElse(0) + 1;

        Student student = new Student();
        student.setSid(newId);
        student.setName(name);
        student.setSex(sex);
        student.setGrade(grade);

        XMLDataStore.add(student, Student.class, XMLDataStore.getStudentsFile());
    }

    public Student getStudentById(int sid) {
        return XMLDataStore.findById(sid, "sid", Student.class, XMLDataStore.getStudentsFile());
    }

    public boolean deleteStudent(int sid) {
        return XMLDataStore.delete(sid, "sid", Student.class, XMLDataStore.getStudentsFile());
    }

    public Student getStudentByName(String name) {
        return getStudentList().stream()
            .filter(student -> student.getName() != null && student.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

    public void updateStudent(Student student) {
        if (student == null || student.getSid() <= 0) {
            throw new IllegalArgumentException("Invalid student data");
        }

        List<Student> students = getStudentList();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getSid() == student.getSid()) {
                students.set(i, student);
                XMLDataStore.writeAll(students, XMLDataStore.getStudentsFile());
                return;
            }
        }
        throw new IllegalArgumentException("Student not found with ID: " + student.getSid());
    }
}