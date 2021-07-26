package com.example.bitslibrary.Models;

import java.io.Serializable;

public class ItemPinjam implements Serializable {
    private Book book;
    private int qty;

    public ItemPinjam() {
    }

    public ItemPinjam(Book book, int qty) {
        this.book = book;
        this.qty = qty;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "ItemPinjam{" +
                "book=" + book +
                ", qty=" + qty +
                '}';
    }
}
