package com.book.entity;

import lombok.Data;


import java.util.Date;
@Data
public class Borrow {
    int id;
    int book_id;
    String book_name;
    int student_id;
    String student_name;
    Date borrow_date;
    Date return_date;
}
