package com.example.bitslibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.example.bitslibrary.Fragment.MainFragment;
import com.example.bitslibrary.Models.ReturnResponse;
import com.example.bitslibrary.Models.Utils;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KonfirmasiPengembalianActivity extends AppCompatActivity {

    private Button btnProses;
    private Toolbar toolbar;

    private TextView txtUser;
    private TextInputEditText ePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pengembalian);

        iniViews();

        toolbar.setNavigationIcon(R.drawable.ic_back2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        String user =  Utils.getNamaUser();
        txtUser.setText("Hai "+user);

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ePassword.getText().toString())){
                    ePassword.setError("password required");
                    ePassword.requestFocus();
                    return;
                }

                prosesPengembalian();
            }
        });

    }

    private void prosesPengembalian(){
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

        int borrowIdReturn = Utils.getBorrowId();

        UserService userService = retrofit.create(UserService.class);
        Call<ReturnResponse> call = userService.returnBook(borrowIdReturn);
        call.enqueue(new Callback<ReturnResponse>() {
            @Override
            public void onResponse(Call<ReturnResponse> call, Response<ReturnResponse> response) {
                if (response.isSuccessful()){
                    ReturnResponse returnResponse = response.body();
                    if (returnResponse.isStatus() == true){
                        Toast.makeText(KonfirmasiPengembalianActivity.this, returnResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(KonfirmasiPengembalianActivity.this, SuksesPengembalianActivity.class));
                            }
                        },500);

                    }else {
                        Toast.makeText(KonfirmasiPengembalianActivity.this, returnResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(KonfirmasiPengembalianActivity.this, "nothing response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(KonfirmasiPengembalianActivity.this, message, Toast.LENGTH_LONG).show();
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

    private void iniViews(){
        btnProses = findViewById(R.id.idProsesPengembalian);
        toolbar = findViewById(R.id.toolbarKonfirm);
        txtUser = findViewById(R.id.idHaiUserReturn);
        ePassword = findViewById(R.id.editTxtPassKonfirmReturn);
    }
}