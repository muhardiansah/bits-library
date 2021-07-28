package com.example.bitslibrary.Models;

import android.widget.TextView;

import java.time.LocalDate;

public class Borrow {
    private String start_date;
    private String end_date;
    private int usr_id;
    private String status;
    private long total;

    public Borrow() {
    }

    public Borrow(String start_date, String end_date, int usr_id, String status, long total) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.usr_id = usr_id;
        this.status = status;
        this.total = total;
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

    public long getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(int usr_id) {
        this.usr_id = usr_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

}