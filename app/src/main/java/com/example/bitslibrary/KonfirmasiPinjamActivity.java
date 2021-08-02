package com.example.bitslibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bitslibrary.Api.UserService;
import com.example.bitslibrary.Fragment.KonfirmasiFragment;
import com.example.bitslibrary.Fragment.MainFragment;
import com.example.bitslibrary.Models.Borrow;
import com.example.bitslibrary.Models.BorrowData;
import com.example.bitslibrary.Models.PinjamRequest;
import com.example.bitslibrary.Models.PinjamResponse;
import com.example.bitslibrary.Models.Utils;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
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

public class KonfirmasiPinjamActivity extends AppCompatActivity {

    private Button btnProses;
    private Toolbar toolbar;

    private int usrId, idbook;
    private String tglStart;
    private TextView txtUser;
    private TextInputEditText ePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pinjam);

        iniViews();

        toolbar.setNavigationIcon(R.drawable.ic_back2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.layout_container_konfirmasi, new KonfirmasiFragment());
//        transaction.commit();

        String user =  Utils.getNamaUser();
        txtUser.setText("Hai "+user);

        String getPassKonfirm = Utils.getPassKonfirm();

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ePassword.getText().toString())){
                    ePassword.setError("password required");
                    ePassword.requestFocus();
                    return;
                }
                prosesPinjam();
            }
        });

    }


    private void prosesPinjam(){

        Borrow borrow = Utils.getBorrow();
        List<BorrowData> borrowDList = Utils.getBorrowDataList();
        tglStart = borrow.getStart_date();
        String tglEnd = borrow.getEnd_date();
        String status = borrow.getStatus();
        int total = (int) borrow.getTotal();
        BorrowData borrowData = borrowDList.get(0);
        idbook = borrowData.getBook_id();
        int price = (int) borrowData.getPrice();
        int qty = borrowData.getQty();
        int subtotal = (int) borrowData.getSubtotal();

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

        usrId = Utils.getUsrId();

        Borrow borrowPref = new Borrow(
                tglStart,
                tglEnd,
                usrId,
                status,
                total
        );

        PinjamRequest pinjamRequest = new PinjamRequest();
        List<BorrowData> borrowDataList = new ArrayList<BorrowData>();

        BorrowData borrowDataPref = new BorrowData();
        borrowDataPref.setBook_id(idbook);
        borrowDataPref.setPrice(price);
        borrowDataPref.setQty(qty);
        borrowDataPref.setSubtotal(subtotal);
        borrowDataList.add(borrowDataPref);

        pinjamRequest.setBorrow(borrowPref);
        pinjamRequest.setBorrowd(borrowDataList);

        UserService userService = retrofit.create(UserService.class);
        Call<PinjamResponse> call = userService.pinjam(pinjamRequest);
        call.enqueue(new Callback<PinjamResponse>() {
            @Override
            public void onResponse(Call<PinjamResponse> call, Response<PinjamResponse> response) {
                if (response.isSuccessful()){
                    PinjamResponse pinjamResponse = response.body();
                    if (pinjamResponse.getStatus() == true){
                        Toast.makeText(KonfirmasiPinjamActivity.this, pinjamResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        int idBoorow =  pinjamResponse.getBorrow().getId();
                        Utils.setBorrowId(idBoorow);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(KonfirmasiPinjamActivity.this, SuksesPinjamActivity.class));
                            }
                        },100);

                    }else {
                        Toast.makeText(KonfirmasiPinjamActivity.this, pinjamResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(KonfirmasiPinjamActivity.this, "nothing response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PinjamResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(KonfirmasiPinjamActivity.this, message, Toast.LENGTH_LONG).show();
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

    private void iniViews() {
        btnProses = findViewById(R.id.idProsesPinjam);
        toolbar = findViewById(R.id.toolbarKonfirm);
        txtUser = findViewById(R.id.idHaiUserPinjam);
        ePassword = findViewById(R.id.editTxtPassKonfirmPinjam);
    }

}
