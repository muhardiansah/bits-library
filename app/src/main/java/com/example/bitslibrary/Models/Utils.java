package com.example.bitslibrary.Models;

import java.util.List;

public class Utils {
    public static Borrow borrow;
    public static List<BorrowData> borrowDataList;
    public static int borrowId;
    public static String token;
    public static int usrId;
    public static String namaUser, nohpUser, emailUser, alamatUser, passBaru, ulangiPass;
    public static String passKonfirm, namaBuku, authorBuku;
    public static List<Book> bookList;
    public static int durasiPinjam;


    public static int getUsrId() {
        return usrId;
    }

    public static void setUsrId(int usrId) {
        Utils.usrId = usrId;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Utils.token = token;
    }

    public static int getBorrowId() {
        return borrowId;
    }

    public static void setBorrowId(int borrowId) {
        Utils.borrowId = borrowId;
    }

    public static Borrow getBorrow() {
        return borrow;
    }

    public static void setBorrow(Borrow borrow) {
        Utils.borrow = borrow;
    }

    public static List<BorrowData> getBorrowDataList() {
        return borrowDataList;
    }

    public static void setBorrowDataList(List<BorrowData> borrowDataList) {
        Utils.borrowDataList = borrowDataList;
    }

    public static String getNamaUser() {
        return namaUser;
    }

    public static void setNamaUser(String namaUser) {
        Utils.namaUser = namaUser;
    }

    public static String getNohpUser() {
        return nohpUser;
    }

    public static void setNohpUser(String nohpUser) {
        Utils.nohpUser = nohpUser;
    }

    public static String getEmailUser() {
        return emailUser;
    }

    public static void setEmailUser(String emailUser) {
        Utils.emailUser = emailUser;
    }

    public static String getAlamatUser() {
        return alamatUser;
    }

    public static void setAlamatUser(String alamatUser) {
        Utils.alamatUser = alamatUser;
    }

    public static String getPassBaru() {
        return passBaru;
    }

    public static void setPassBaru(String passBaru) {
        Utils.passBaru = passBaru;
    }

    public static String getUlangiPass() {
        return ulangiPass;
    }

    public static void setUlangiPass(String ulangiPass) {
        Utils.ulangiPass = ulangiPass;
    }

    public static String getPassKonfirm() {
        return passKonfirm;
    }

    public static void setPassKonfirm(String passKonfirm) {
        Utils.passKonfirm = passKonfirm;
    }

    public static List<Book> getBookList() {
        return bookList;
    }

    public static void setBookList(List<Book> bookList) {
        Utils.bookList = bookList;
    }

    public static String getNamaBuku() {
        return namaBuku;
    }

    public static void setNamaBuku(String namaBuku) {
        Utils.namaBuku = namaBuku;
    }

    public static String getAuthorBuku() {
        return authorBuku;
    }

    public static void setAuthorBuku(String authorBuku) {
        Utils.authorBuku = authorBuku;
    }

    public static int getDurasiPinjam() {
        return durasiPinjam;
    }

    public static void setDurasiPinjam(int durasiPinjam) {
        Utils.durasiPinjam = durasiPinjam;
    }
}
