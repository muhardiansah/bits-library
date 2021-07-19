package com.example.bitslibrary;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bitslibrary.Models.ApiBook;
import com.example.bitslibrary.Models.ApiClient;
import com.example.bitslibrary.Models.Book;
import com.example.bitslibrary.Models.BookResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTabTentangBuku extends Fragment {

    private TextView isbn, isbn13, genre, bahasa, tglTerbit, jmlHalaman;
    private Book incomingBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tab_tentang_buku, container, false);

        isbn = view.findViewById(R.id.idISBN);
        isbn13 = view.findViewById(R.id.idISBN13);
        genre = view.findViewById(R.id.idGenre);
        bahasa = view.findViewById(R.id.idBahasa);
        tglTerbit = view.findViewById(R.id.idTglTerbit);
        jmlHalaman = view.findViewById(R.id.idHalaman);

        FragmentActivity intent = getActivity();
        Intent tentangBuku = intent.getIntent();
        int id = tentangBuku.getIntExtra("bookId",0);

        Call<BookResponse> call = ApiBook.getUserService().getBook();
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), "detail response gagal", Toast.LENGTH_SHORT).show();
                    return;
                }
                BookResponse bookResponse = response.body();
                Toast.makeText(getActivity(), "detail response berhasil", Toast.LENGTH_SHORT).show();
                List<Book> bookList = new ArrayList<>(Arrays.asList(bookResponse.getData()));
                for (Book b: bookList){
                    if (b.getId() == id){
                        incomingBook = b;
                        isbn.setText(b.getIsbn());
                        isbn13.setText(b.getIsbn_13());
                        genre.setText(b.getGenre());
                        bahasa.setText(b.getLanguage());
                        tglTerbit.setText(b.getDate_pub());
                        jmlHalaman.setText(String.valueOf(b.getPages()));
                    }
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(getActivity(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}