package com.example.bitslibrary.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PinjamRequest {
    private Borrow borrow;
//    private List<BorrowData> borrowd = new ArrayList<BorrowData>();
    private List<ItemPinjam> borrowd = new ArrayList<ItemPinjam>();
    public PinjamRequest() {
    }

    public PinjamRequest(Borrow borrow) {
        this.borrow = borrow;
    }

//    public List<BorrowData> borrowDataList(){
//        return borrowd;
//    }

    public Borrow getBorrow() {
        return borrow;
    }

    public void setBorrow(Borrow borrow) {
        this.borrow = borrow;
    }

//    public List<BorrowData> getBorrowd() {
//        return borrowd;
//    }

//    public void setBorrowd(List<BorrowData> borrowd) {
//        this.borrowd = borrowd;
//    }


    public List<ItemPinjam> getBorrowd() {
        return borrowd;
    }

    public void setBorrowd(List<ItemPinjam> borrowd) {
        this.borrowd = borrowd;
    }
}
