package com.example.bitslibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bitslibrary.Api.UserService;
import com.example.bitslibrary.Fragment.MainFragment;
import com.example.bitslibrary.Models.Book;
import com.example.bitslibrary.Models.BorrowDataResponse;
import com.example.bitslibrary.Models.BorrowResponse;
import com.example.bitslibrary.Models.DetailPinjamResponse;
import com.example.bitslibrary.Models.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailPinjamanActivity extends AppCompatActivity {

    private TextView tglStart, tglEnd, txtTglPinjam, txtTglKembali, bookName, author, subtotal
            , jmlPcs, totalBayar;
    private ImageView imgView;
    private Toolbar toolbar;
    private Book book;
    private final int position = 0;

    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pinjaman);

        initViews();

        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        spinner.setVisibility(View.VISIBLE);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("Authorization", "Bearer "+Utils.getToken());
                        return chain.proceed(builder.build());
                    }

                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new MainFragment().BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        int borrowId  = Utils.getBorrowId();
        DecimalFormat dformt = new DecimalFormat();

        UserService userService = retrofit.create(UserService.class);
        Call<DetailPinjamResponse> call = userService.getDetailPinjam(borrowId);
        call.enqueue(new Callback<DetailPinjamResponse>() {
            @Override
            public void onResponse(Call<DetailPinjamResponse> call, Response<DetailPinjamResponse> response) {
                if (response.isSuccessful()){
                    DetailPinjamResponse detailPinjamResponse = response.body();
                    spinner.setVisibility(View.GONE);
                    if (detailPinjamResponse.getStatus() == true ){
                        Toast.makeText(DetailPinjamanActivity.this, detailPinjamResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        String tgl_start =  detailPinjamResponse.getData().getBorrow().getStart_date();
                        String tgl_end = detailPinjamResponse.getData().getBorrow().getEnd_date();
                        int total = detailPinjamResponse.getData().getBorrow().getTotal();
                        tglStart.setText(tgl_start);
                        tglEnd.setText(tgl_end);
                        List<BorrowDataResponse> bDataList =  new ArrayList<>(Arrays.asList(detailPinjamResponse.getData().getBorrowd()));
                        List<Book> bookList;
                        for (BorrowDataResponse bRes : bDataList){
//                            if (bRes.getBook_id() == book.getId()){
//                                book.getName();
//                                book.getAuthor();
//                            }
                            jmlPcs.setText(String.valueOf(bRes.getQty())+" pcs");
                            subtotal.setText("Rp "+dformt.format(bRes.getSubtotal())+", ");
                        }
                        totalBayar.setText("Rp "+dformt.format(total));
                    }else {
                        Toast.makeText(DetailPinjamanActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(DetailPinjamanActivity.this, "nothing response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DetailPinjamResponse> call, Throwable t) {
                Toast.makeText(DetailPinjamanActivity.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

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
        tglStart = findViewById(R.id.idTglPinjamDetail);
        tglEnd = findViewById(R.id.idTglKembaliDetail);
        bookName = findViewById(R.id.idNameBookPinjamDetail);
        author = findViewById(R.id.idAuthorPinjamDetail);
        subtotal = findViewById(R.id.idBiayaPinjamDetail);
        jmlPcs = findViewById(R.id.idPcsBookDetail);
        toolbar = findViewById(R.id.toolbarDetailPinjam);
        totalBayar = findViewById(R.id.idTotalBayar);

        spinner = (ProgressBar) findViewById(R.id.idProgbar);
    }
}