package com.example.bitslibrary.Models;

import java.io.Serializable;

public class BookResponse {
    private Book[] data;

    public Book[] getData() {
        return data;
    }

    public void setData(Book[] data) {
        this.data = data;
    }
}
