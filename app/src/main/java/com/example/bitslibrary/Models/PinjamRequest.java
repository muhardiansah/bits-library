package com.example.bitslibrary.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PinjamRequest {
    private Borrow borrow;
    private List<BorrowData> borrowd = new ArrayList<BorrowData>();

    public PinjamRequest() {
    }

    public PinjamRequest(Borrow borrow) {
        this.borrow = borrow;
    }

    public List<BorrowData> borrowDataList(){
        return borrowd;
    }

    public Borrow getBorrow() {
        return borrow;
    }

    public void setBorrow(Borrow borrow) {
        this.borrow = borrow;
    }

    public List<BorrowData> getBorrowd() {
        return borrowd;
    }

    public void setBorrowd(List<BorrowData> borrowd) {
        this.borrowd = borrowd;
    }
}
