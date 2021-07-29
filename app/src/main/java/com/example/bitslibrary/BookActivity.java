package com.example.bitslibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.example.bitslibrary.Models.ApiBook;
import com.example.bitslibrary.Adapter.ViewAdapterTabLayout;
import com.example.bitslibrary.Fragment.FragmentTabSinopsis;
import com.example.bitslibrary.Fragment.FragmentTabTentangBuku;
import com.example.bitslibrary.Fragment.MainFragment;
import com.example.bitslibrary.Models.Book;
import com.example.bitslibrary.Models.BookResponse;
import com.example.bitslibrary.Api.UserService;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DecimalFormat;
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

public class BookActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView;
    private TextView txtName, txtAuthor, txtPrice;
    private TextView stokBuku, statuStok, txtToolbook;

    private Toolbar toolbar;

    private Button btnPinjam;
    private Book incomingBook;
    private Context context;
    private int id;

    SharedPreferences preferences;
    private static final String shared_pref_name = "myPref";
    private static final String key_api = "api";

    private ProgressBar spinner;

    public BookActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        try {
            id = intent.getIntExtra("bookId",0);
            spinner.setVisibility(View.VISIBLE);
            setViewsValues();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    private void setViewsValues(){
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
                    Toast.makeText(BookActivity.this, "detail response gagal", Toast.LENGTH_SHORT).show();
                    return;
                }
                BookResponse bookResponse = response.body();

                spinner.setVisibility(View.GONE);

                Toast.makeText(BookActivity.this, "detail response berhasil", Toast.LENGTH_SHORT).show();
                List<Book> bookList = new ArrayList<>(Arrays.asList(bookResponse.getData()));
                for (Book b: bookList){
                    if (b.getId() == id){
                        DecimalFormat dformt = new DecimalFormat();
                        incomingBook = b;
                        txtToolbook.setText(b.getName());
                        txtName.setText(b.getName());
                        txtAuthor.setText(b.getAuthor());
                        txtPrice.setText("Rp "+dformt.format(b.getPrice()));
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

    public String getToken(){
        preferences = getSharedPreferences(shared_pref_name, MODE_PRIVATE);
        String api_key = preferences.getString(key_api, null);
        return api_key;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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

        spinner = findViewById(R.id.idProgbar);
        toolbar = findViewById(R.id.toolbarBook);
        txtToolbook = findViewById(R.id.idTxtToolBook);
    }
}