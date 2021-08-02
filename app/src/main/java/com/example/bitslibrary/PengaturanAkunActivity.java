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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bitslibrary.Api.UserService;
import com.example.bitslibrary.Fragment.MainFragment;
import com.example.bitslibrary.Models.UserResponse;
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

public class PengaturanAkunActivity extends AppCompatActivity {
    private EditText edtNama, edtNoHp, edtEmail, edtAlamat;
    private TextInputEditText edtPassbaru, edtUlangiPass;
    private Toolbar toolbar;
    private Button btnSimpan;
    private int usrId;

    private String nama, nohp, email, alamat;
    public String passBaru, ulangiPass;

    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_akun);

        initVIews();

        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        spinner.setVisibility(View.VISIBLE);

        nama = Utils.getNamaUser();
        nohp = Utils.getNohpUser();
        email = Utils.getEmailUser();
        alamat = Utils.getAlamatUser();

        edtNama.setText(nama);
        edtNoHp.setText(nohp);
        edtEmail.setText(email);
        edtAlamat.setText(alamat);

//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(httpLoggingInterceptor)
//                .addInterceptor(new Interceptor() {
//                    @NotNull
//                    @Override
//                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
//                        Request.Builder builder = chain.request().newBuilder();
//                        builder.addHeader("Authorization", "Bearer "+ Utils.getToken());
//                        return chain.proceed(builder.build());
//                    }
//
//                }).build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(new MainFragment().BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        usrId = Utils.getUsrId();
//
//        UserService userService = retrofit.create(UserService.class);
//        Call<UserResponse> call = userService.getUser(usrId);
//        call.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                if (response.isSuccessful()){
//                    UserResponse userResponse = response.body();
//                    spinner.setVisibility(View.GONE);
//                    if (userResponse.getStatus() == true){
//                        Toast.makeText(PengaturanAkunActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
//
//
//                    }else {
//                        Toast.makeText(PengaturanAkunActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(PengaturanAkunActivity.this, "nothing response", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Toast.makeText(PengaturanAkunActivity.this,t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//            }
//        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtNama.getText().toString())){
                    edtNama.setError("Nama required");
                    edtNama.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(edtNoHp.getText().toString())){
                    edtNoHp.setError("No hp required");
                    edtNoHp.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(edtEmail.getText().toString())){
                    edtEmail.setError("email required");
                    edtEmail.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(edtEmail.getText().toString())){
                    edtAlamat.setError("alamat required");
                    edtAlamat.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(edtPassbaru.getText().toString())){
                    edtPassbaru.setError("password baru required");
                    edtPassbaru.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(edtUlangiPass.getText().toString())){
                    edtUlangiPass.setError("ulangi password required");
                    edtUlangiPass.requestFocus();
                    return;
                }

                passBaru = edtPassbaru.getText().toString().trim();
                ulangiPass = edtUlangiPass.getText().toString().trim();

                if (!passBaru.equals(ulangiPass)){
                    Toast.makeText(PengaturanAkunActivity.this, "password tidak cocok", Toast.LENGTH_SHORT).show();
//                    return;
                }else {
                    Toast.makeText(PengaturanAkunActivity.this, "pass cocok", Toast.LENGTH_SHORT).show();
                    simpanPerubahan();
                }

            }
        });
    }

    private void viewData(){

    }

    private void simpanPerubahan(){

        String namaPref = edtNama.getText().toString();
        String nohpPref = edtNoHp.getText().toString();
        String emailPref = edtEmail.getText().toString();
        String alamatPref = edtAlamat.getText().toString();
        String passBaruPref = edtPassbaru.getText().toString();
        String ulangiPassPref = edtUlangiPass.getText().toString();

        Utils.setNamaUser(namaPref);
        Utils.setNohpUser(nohpPref);
        Utils.setEmailUser(emailPref);
        Utils.setAlamatUser(alamatPref);
        Utils.setPassBaru(passBaruPref);
        Utils.setUlangiPass(ulangiPassPref);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(PengaturanAkunActivity.this, KonfirmasiPerubahanActivity.class));
            }
        },200);

//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(httpLoggingInterceptor)
//                .addInterceptor(new Interceptor() {
//                    @NotNull
//                    @Override
//                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
//                        Request.Builder builder = chain.request().newBuilder();
//                        builder.addHeader("Authorization", "Bearer "+ Utils.getToken());
//                        return chain.proceed(builder.build());
//                    }
//
//                }).build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(new MainFragment().BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        UserService userService = retrofit.create(UserService.class);
//        Call<UserResponse> callUpdate = userService.updateUser(usrId, nama, nohp, email, alamat);
//        callUpdate.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                Toast.makeText(PengaturanAkunActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Toast.makeText(PengaturanAkunActivity.this,t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                super.onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initVIews(){

        edtNama = findViewById(R.id.idDetailNamaAkun);
        edtNoHp = findViewById(R.id.idDetailNoHpAkun);
        edtEmail = findViewById(R.id.idDetailEmailAkun);
        edtAlamat = findViewById(R.id.idDetailAlamatAkun);
        edtPassbaru = findViewById(R.id.idPasswordBaru);
        edtUlangiPass = findViewById(R.id.idUlangiPasswordBaru);
        toolbar = findViewById(R.id.toolbarPengaturan);
        btnSimpan = findViewById(R.id.idBtnSimpanPerubahanAkun);
//        spinner = findViewById(R.id.idProgbar);
    }
}