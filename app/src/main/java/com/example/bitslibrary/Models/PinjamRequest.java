package com.example.bitslibrary.Models;

public class PinjamRequest {
    private String start_date, end_date, status;
    private int usr_id, book_id, qty;
    private double total, price, subtotal;

    public PinjamRequest(String start_date, String end_date, String status, int usr_id, int book_id, int qty, double total, double price, double subtotal) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.usr_id = usr_id;
        this.book_id = book_id;
        this.qty = qty;
        this.total = total;
        this.price = price;
        this.subtotal = subtotal;
    }

    public PinjamRequest() {
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(int usr_id) {
        this.usr_id = usr_id;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "PinjamRequest{" +
                "start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
