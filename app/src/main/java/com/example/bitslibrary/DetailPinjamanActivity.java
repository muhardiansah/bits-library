package com.example.bitslibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    private Button btnKembalikan;
    private Book book;
    private int borrowId, intentId;
    private List<BorrowDataResponse> bDataList;
    private TableLayout tabDetailPinjam;
    private DecimalFormat dformt = new DecimalFormat();
    private RelativeLayout rlDetailPinjam;
    private List<Book> bookListDetail;

    private ProgressBar spinner;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pinjaman);

        initViews();

        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        btnKembalikan.setEnabled(false);

        getIntent();
        try {
//            intentId = intent.getIntExtra("borrowId",0);
            spinner.setVisibility(View.VISIBLE);
            setViewsValues();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    private void setViewsValues(){
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

        borrowId  = Utils.getBorrowId();


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
                        String status = detailPinjamResponse.getData().getBorrow().getStatus();
                        int total = detailPinjamResponse.getData().getBorrow().getTotal();
//                        Toast.makeText(DetailPinjamanActivity.this, status, Toast.LENGTH_SHORT).show();

                        String dateNowStart = tgl_start;
                        SimpleDateFormat format_now = new SimpleDateFormat("yyyy-MM-dd");
                        Date new_Date = null;
                        String dateViewStart = null;
                        try {
                            new_Date = format_now.parse(dateNowStart);
                            format_now = new SimpleDateFormat("dd MMM yyyy");
                            dateViewStart = format_now.format(new_Date);
                        }catch (ParseException e){
                            e.printStackTrace();
                        }

                        String dateNowEnd = tgl_end;
                        SimpleDateFormat format_now2 = new SimpleDateFormat("yyyy-MM-dd");
                        Date new_Date2 = null;
                        String dateViewEnd = null;
                        try {
                            new_Date2 = format_now2.parse(dateNowEnd);
                            format_now2 = new SimpleDateFormat("dd MMM yyyy");
                            dateViewEnd = format_now2.format(new_Date2);
                        }catch (ParseException e){
                            e.printStackTrace();
                        }

                        tglStart.setText(dateViewStart);
                        tglEnd.setText(dateViewEnd);

                        if (status.equals("N")){
                            btnKembalikan.setBackgroundResource(R.drawable.custom_button);
                            btnKembalikan.setTextColor(getResources().getColor(R.color.white));
                            btnKembalikan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    konfirmasiKembalikan();
                                }
                            });
                        }else if(status.equals("F")){
                            btnKembalikan.setEnabled(false);
                            btnKembalikan.setText("Sudah Dikembalikan");
                            tglStart.setTextColor(getResources().getColor(R.color.black));
                            tglEnd.setTextColor(getResources().getColor(R.color.black));
                            totalBayar.setTextColor(getResources().getColor(R.color.black));;
                        }

                        bDataList =  new ArrayList<>(Arrays.asList(detailPinjamResponse.getData().getBorrowd()));

                        boolean cekData = bDataList.isEmpty();
                        if (cekData == true){
                            TextView textViewEmpty = new TextView(getApplicationContext());
                            textViewEmpty.setText("Data Kosong");
                            textViewEmpty.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                            textViewEmpty.setTextSize(16);
                            textViewEmpty.setPadding(250,0,0,0);
                            tabDetailPinjam.addView(textViewEmpty);
                            totalBayar.setText("Rp "+String.valueOf(0));
                        }else {
                            fillDetailPinjam();
                            totalBayar.setText("Rp "+dformt.format(total));
                        }



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

    private void fillDetailPinjam(){
        LayoutInflater inflaterDetailPinjam = LayoutInflater.from(this);

        for (BorrowDataResponse bRes : bDataList){

            TableRow tableRowDetailPinjam = new TableRow(this);
            tableRowDetailPinjam.setLayoutParams(new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));

            rlDetailPinjam = (RelativeLayout) inflaterDetailPinjam.inflate(R.layout.rel_buku_pinjaman,null);
            bookName = (TextView) rlDetailPinjam.findViewById(R.id.idNameBookPinjamDetail);
            author = (TextView) rlDetailPinjam.findViewById(R.id.idAuthorPinjamDetail);
            subtotal = (TextView) rlDetailPinjam.findViewById(R.id.idBiayaPinjamDetail);
            jmlPcs = (TextView) rlDetailPinjam.findViewById(R.id.idPcsBookDetail);

            bookListDetail = Utils.getBookList();
//            Book bookdata = bookListDetail.get(0);
//            int bookId = bookdata.getId();
//            String nameBookDetail = bookdata.getName();
//            String authorBook = bookdata.getAuthor();

            for (Book bo:bookListDetail){
                if (bRes.getBook_id() == bo.getId()){
                    bookName.setText(bo.getName());
                    author.setText("Oleh:"+bo.getAuthor());
                }

            }



            jmlPcs.setText(String.valueOf(bRes.getQty())+" pcs");
            subtotal.setText("Rp "+dformt.format(bRes.getSubtotal())+",");

            tableRowDetailPinjam.addView(rlDetailPinjam);
            tabDetailPinjam.addView(tableRowDetailPinjam);
        }
    }

    private void konfirmasiKembalikan(){
//        int borrowId = Utils.getBorrowId();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(DetailPinjamanActivity.this, KonfirmasiPengembalianActivity.class));
            }
        },500);
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
        toolbar = findViewById(R.id.toolbarDetailPinjam);
        totalBayar = findViewById(R.id.idTotalBayar);
        btnKembalikan = findViewById(R.id.idKembalikanBuku);
        tabDetailPinjam = findViewById(R.id.idTabDetailPinjam);

        spinner = (ProgressBar) findViewById(R.id.idProgbar);
    }
}