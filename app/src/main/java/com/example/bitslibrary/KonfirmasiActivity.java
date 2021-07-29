package com.example.bitslibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bitslibrary.Api.UserService;
import com.example.bitslibrary.Fragment.MainFragment;
import com.example.bitslibrary.Models.Borrow;
import com.example.bitslibrary.Models.BorrowData;
import com.example.bitslibrary.Models.PinjamRequest;
import com.example.bitslibrary.Models.PinjamResponse;
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

public class KonfirmasiActivity extends AppCompatActivity {
    private TextView txtUser, txtStart;
    private TextInputEditText ePassword;
    private Button btnProses;

    private int usrId, idbook;
    private String tglStart;

    SharedPreferences preferences, preferencesBorrow;
    private static final String shared_pref_name = "myPref";
    private static final int key_usrId = 0;
    private static final String key_api = "api";

    private static final String shared_pref_name_borrow = "borrowPref";
    private static final String tglStartPref = "tglStart";
    private static final String tglEndPref = "tglEnd";
    private static final int usrIdPref = 0;
    private static final String statusPref = "status";
    private static final int totalPref = 0;

    private static final String shared_pref_name_borrowData = "borrowDataPref";
    private static final int idBook_pref = 0;
    private static final int price_pref = 0;
    private static final int qty_pref = 0;
    private static final int subtotal_pref = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);

        iniViews();

        preferences = getSharedPreferences(shared_pref_name, MODE_PRIVATE);
        usrId = preferences.getInt(String.valueOf(key_usrId),0);

//        preferencesBorrow = getSharedPreferences(shared_pref_name_borrow, MODE_PRIVATE);
//        tglStart = preferencesBorrow.getString(tglStartPref, null);
        preferencesBorrow = getSharedPreferences(shared_pref_name_borrow, MODE_PRIVATE);
        idbook = preferencesBorrow.getInt(String.valueOf(idBook_pref), 0);

        txtUser.setText("Hai "+usrId);
        txtStart.setText("id "+String.valueOf(idbook));

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ePassword.getText().toString())){
                    Toast.makeText(KonfirmasiActivity.this, "Password Required", Toast.LENGTH_SHORT).show();
                }else {

                    prosesPinjam();
                }
            }
        });

    }
    
    private void cekUser(){
    }
    
    private void prosesPinjam(){

        preferencesBorrow = getSharedPreferences(shared_pref_name_borrow, MODE_PRIVATE);
        tglStart = preferencesBorrow.getString(tglStartPref, null);
        String tglEnd = preferencesBorrow.getString(tglEndPref, null);
//        int userId = preferencesBorrow.getInt(String.valueOf(usrIdPref),0);
        String status = preferencesBorrow.getString(statusPref, null);
        int total = preferencesBorrow.getInt(String.valueOf(totalPref),0);
        idbook = preferencesBorrow.getInt(String.valueOf(idBook_pref), 0);
        int price = preferencesBorrow.getInt(String.valueOf(price_pref), 0);
        int qty = preferencesBorrow.getInt(String.valueOf(qty_pref),0);
        int subtotal = preferencesBorrow.getInt(String.valueOf(subtotal_pref),0);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
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
                        Toast.makeText(KonfirmasiActivity.this, pinjamResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(KonfirmasiActivity.this, pinjamResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(KonfirmasiActivity.this, "nothing response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PinjamResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(KonfirmasiActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getToken(){
        preferences = getSharedPreferences(shared_pref_name, MODE_PRIVATE);
        String api_key = preferences.getString(key_api, null);
        return api_key;
    }

    private void iniViews() {
        txtUser = findViewById(R.id.idHaiUser);
        ePassword = findViewById(R.id.editTxtPassKonfirm);
        btnProses = findViewById(R.id.idProsesPinjam);

        txtStart = findViewById(R.id.tglStart);
    }
}