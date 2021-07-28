package com.example.bitslibrary.Models;

public class BorrowData {
    private int book_id;
    private int qty;
    private int price;
    private int subtotal;

    public BorrowData() {
    }

    public BorrowData(int book_id, int qty, int price, int subtotal) {
        this.book_id = book_id;
        this.qty = qty;
        this.price = price;
        this.subtotal = subtotal;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
}
