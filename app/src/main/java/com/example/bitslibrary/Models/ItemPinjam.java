package com.example.bitslibrary.Models;

import java.io.Serializable;

public class ItemPinjam implements Serializable {
    private Book book;
    private int qty;
    private int subtotal;
    private int book_id;
    private int price;

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

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemPinjam{" +
                "book=" + book +
                ", qty=" + qty +
                '}';
    }
}
