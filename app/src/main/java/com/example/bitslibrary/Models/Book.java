package com.example.bitslibrary.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Book implements Serializable {
    @SerializedName("id")
    private int id;
    private String name;
    private String imgUrl;
    private String author;
    private String isbn;
    private String isbn_13;
    private String genre;
    private String language;
    private String date_pub;
    private int pages;
    private String sinopsis;
    private double price;
    private int fineamt;

    public Book(int id, String name, String imgUrl, String author, String isbn, String isbn_13,
                String genre, String language, String date_pub, int pages, String sinopsis,
                double price, int fineamt) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.author = author;
        this.isbn = isbn;
        this.isbn_13 = isbn_13;
        this.genre = genre;
        this.language = language;
        this.date_pub = date_pub;
        this.pages = pages;
        this.sinopsis = sinopsis;
        this.price = price;
        this.fineamt = fineamt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn_13() {
        return isbn_13;
    }

    public void setIsbn_13(String isbn_13) {
        this.isbn_13 = isbn_13;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDate_pub() {
        return date_pub;
    }

    public void setDate_pub(String date_pub) {
        this.date_pub = date_pub;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFineamt() {
        return fineamt;
    }

    public void setFineamt(int fineamt) {
        this.fineamt = fineamt;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", isbn_13='" + isbn_13 + '\'' +
                ", genre='" + genre + '\'' +
                ", language='" + language + '\'' +
                ", date_pub='" + date_pub + '\'' +
                ", pages=" + pages +
                ", sinopsis='" + sinopsis + '\'' +
                ", price=" + price +
                ", fineamt=" + fineamt +
                '}';
    }

    //    public Book get(int position) {
//    }
}
