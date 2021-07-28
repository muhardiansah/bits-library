package com.example.bitslibrary.Models;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class PinjamResponse {
    private BorrowResponse borrow;
    private String message;
    private boolean status;

    public BorrowResponse getBorrow() { return borrow; }
    public void setBorrow(BorrowResponse value) { this.borrow = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public boolean getStatus() { return status; }
    public void setStatus(boolean value) { this.status = value; }

    @Override
    public String toString() {
        return "PinjamResponse{" +
                "borrow=" + borrow +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}

