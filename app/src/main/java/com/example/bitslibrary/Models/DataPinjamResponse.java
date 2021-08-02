package com.example.bitslibrary.Models;

public class DataPinjamResponse {
    private BorrowResponse borrow;
    private BorrowDataResponse[] borrowd;

    public BorrowResponse getBorrow() { return borrow; }
    public void setBorrow(BorrowResponse value) { this.borrow = value; }

    public BorrowDataResponse[] getBorrowd() { return borrowd; }
    public void setBorrowd(BorrowDataResponse[] value) { this.borrowd = value; }
}
