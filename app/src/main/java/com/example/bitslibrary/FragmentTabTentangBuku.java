package com.example.bitslibrary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.bitslibrary.Models.ApiBook;
import com.example.bitslibrary.Models.Book;
import com.example.bitslibrary.Models.BookResponse;
import com.example.bitslibrary.Api.UserService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class FragmentTabTentangBuku extends Fragment {

    private TextView isbn, isbn13, genre, bahasa, tglTerbit, jmlHalaman;
    private Book incomingBook;

    SharedPreferences preferences;
    private static final String shared_pref_name = "myPref";
    private static final String key_api = "api";

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

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("Authorization", "Bearer "+getToken());
                        return chain.proceed(builder.build());
                    }

                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new MainFragment().BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        UserService userService = retrofit.create(UserService.class);
        Call<BookResponse> call = userService.getBook();
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

    private String getToken(){
        preferences = getContext().getSharedPreferences(shared_pref_name, MODE_PRIVATE);
        String api_key = preferences.getString(key_api, null);
        return api_key;
    }

}