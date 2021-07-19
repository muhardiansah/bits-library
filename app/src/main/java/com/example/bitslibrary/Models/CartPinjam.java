package com.example.bitslibrary.Models;

import java.util.ArrayList;
import java.util.List;

public class CartPinjam {
    private static List<ItemPinjam> itemPinjams = new ArrayList<>();

    public static void insert(ItemPinjam itemPinjam){
        itemPinjams.add(itemPinjam);
    }

    public static void update(Book book){
        int index = getIndex(book);
        int qty = itemPinjams.get(index).getQty() + 1;
        itemPinjams.get(index).setQty(qty);
    }

    public static void remove(Book book){
        int index = getIndex(book);
        itemPinjams.remove(index);
    }

    public static List<ItemPinjam> contents(){
        return itemPinjams;
    }

    public static double total(){
        double s = 0;
        for (ItemPinjam itemPinjam: itemPinjams){
            s += itemPinjam.getBook().getPrice() * itemPinjam.getQty();
        }
        return s;
    }

    public static boolean isExist(Book book){
        return getIndex(book) != -1;
    }

    private static int getIndex(Book book){
        for (int i = 0; i < itemPinjams.size(); i++){
            if (itemPinjams.get(i).getBook().getId() == book.getId()){
                return i;
            }
        }
        return -1;
    }
}
