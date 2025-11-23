package com.book.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * XML数据包装类，用于Jackson序列化
 */
public class XMLWrapper<T> {
    private List<T> items = new ArrayList<>();

    public XMLWrapper() {}

    public XMLWrapper(List<T> items) {
        this.items = items != null ? items : new ArrayList<>();
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items != null ? items : new ArrayList<>();
    }

    public void addItem(T item) {
        if (item != null) {
            this.items.add(item);
        }
    }
}