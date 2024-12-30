package com.book.entity;

import lombok.Data;

@Data
public class Borrow {

    int id;
    int book_id;
    String book_name;
    int student_id;
    String student_name;
    String borrowDate;
    String returnDate;
    int renewStatus;
}
