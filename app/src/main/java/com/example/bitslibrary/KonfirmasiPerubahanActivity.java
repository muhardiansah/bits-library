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
import com.example.bitslibrary.Models.UserRequest;
import com.example.bitslibrary.Models.UserResponse;
import com.example.bitslibrary.Models.Utils;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KonfirmasiPerubahanActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button;
    private TextView txtUser;
    private TextInputEditText ePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_perubahan);

        initViews();

        toolbar.setNavigationIcon(R.drawable.ic_back2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.layout_container_konfirmasi, new KonfirmasiFragment());
//        transaction.commit();

        String user =  Utils.getNamaUser();
        txtUser.setText("Hai "+user);

        String pass = ePassword.getText().toString().trim();
        String passKonfirm = Utils.getPassKonfirm().trim();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ePassword.getText().toString())){
                    ePassword.setError("password required");
                    ePassword.requestFocus();
                    return;
                }
                prosesUbah();
            }
        });

    }

    private void prosesUbah(){
        String nama = Utils.getNamaUser();
        String nohp = Utils.getNohpUser();
        String email = Utils.getEmailUser();
        String alamat = Utils.getAlamatUser();
        String passBaru = Utils.getPassBaru();
        String ulangiPass = Utils.getUlangiPass();

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

        int usrId = Utils.getUsrId();

        UserRequest userRequest = new UserRequest();
        userRequest.setName(nama);
        userRequest.setMobile(nohp);
        userRequest.setEmail(email);
        userRequest.setAddress(alamat);
        userRequest.setPassword(ulangiPass);

        UserService userService = retrofit.create(UserService.class);
        Call<UserResponse> callUpdate = userService.updateUser(usrId, userRequest);
        callUpdate.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus() == true){
                        Toast.makeText(KonfirmasiPerubahanActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(KonfirmasiPerubahanActivity.this, MainActivity.class));
                            }
                        },500);
                    }else {
                        Toast.makeText(KonfirmasiPerubahanActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(KonfirmasiPerubahanActivity.this, "nothing response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(KonfirmasiPerubahanActivity.this,t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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
        toolbar = findViewById(R.id.toolbarKonfirm);
        button = findViewById(R.id.idProsesPerubahan);
        txtUser = (TextView) findViewById(R.id.idHaiUserUbah);
        ePassword = (TextInputEditText) findViewById(R.id.editTxtPassKonfirmUbah);
    }
}