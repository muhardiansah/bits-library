package com.example.bitslibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bitslibrary.Models.ApiBook;
import com.example.bitslibrary.Models.ApiClient;
import com.example.bitslibrary.Models.Book;
import com.example.bitslibrary.Models.BookResponse;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView;
    private TextView txtName, txtAuthor, txtPrice;
    private TextView stokBuku, statuStok;

    private Button btnPinjam;
    private Book incomingBook;
    private Context context;
    private int id;

    private PinjamActivity pinjamActivity;

    public BookActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

        Intent intent = getIntent();
        try {
            id = intent.getIntExtra("bookId",0);
            setViewsValues();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

//        final int position = 0;

    }

    private void setViewsValues(){
        Call<BookResponse> call = ApiBook.getUserService().getBook();
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(BookActivity.this, "detail response gagal", Toast.LENGTH_SHORT).show();
                    return;
                }
                BookResponse bookResponse = response.body();
                Toast.makeText(BookActivity.this, "detail response berhasil", Toast.LENGTH_SHORT).show();
                List<Book> bookList = new ArrayList<>(Arrays.asList(bookResponse.getData()));
                for (Book b: bookList){
                    if (b.getId() == id){
                        incomingBook = b;
                        txtName.setText(b.getName());
                        txtAuthor.setText(b.getAuthor());
                        txtPrice.setText(String.valueOf(b.getPrice()));
                        Glide.with(BookActivity.this).load("https://upload.wikimedia.org/wikipedia/id/2/28/Koala_Kumal.jpg")
                                .into(imageView);
                    }
                }

                btnPinjam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent  = new Intent(BookActivity.this, PinjamActivity.class);
                        intent.putExtra("bookId", incomingBook);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(BookActivity.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });


        tabLayout.setupWithViewPager(viewPager);
        ViewAdapterTabLayout viewAdapterTabLayout = new ViewAdapterTabLayout(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewAdapterTabLayout.addFragment(new FragmentTabSinopsis(),"SINOPSIS");
        viewAdapterTabLayout.addFragment(new FragmentTabTentangBuku(),"TENTANG BUKU");

        viewPager.setAdapter(viewAdapterTabLayout);
    }

    private void initViews(){
        tabLayout = findViewById(R.id.idTabLayout);
        viewPager = findViewById(R.id.idViewPager);
        imageView = findViewById(R.id.idBookImg);
        txtName = findViewById(R.id.idBookName);
        txtAuthor = findViewById(R.id.idBookAuth);
        txtPrice = findViewById(R.id.idPriceBook);


        stokBuku = findViewById(R.id.idStokBuku);
        statuStok = findViewById(R.id.idStatusStok);

        btnPinjam = findViewById(R.id.idBtnPinjamBuku);
    }
}