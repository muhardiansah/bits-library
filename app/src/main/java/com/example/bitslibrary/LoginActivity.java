package com.example.bitslibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bitslibrary.Models.ApiClient;
import com.example.bitslibrary.Models.LoginRequest;
import com.example.bitslibrary.Models.LoginResponse;
import com.example.bitslibrary.Models.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText edtTxtEmail, edtTxtPassword;
    private Button btnLogin;
    SharedPreferences preferences;
    String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWidgets();
        setOnClick();

    }

    private void setOnClick(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtTxtEmail.getText().toString()) || TextUtils.isEmpty(edtTxtPassword.getText().toString())){
                    Toast.makeText(LoginActivity.this, "User/Email required", Toast.LENGTH_LONG).show();
                }else {
                    login();
                }
            }
        });
    }

    public void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(edtTxtEmail.getText().toString());
        loginRequest.setPassword(edtTxtPassword.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getStatus() == true){
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();

                        apiKey = loginResponse.getApikey_account();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("Authorization", apiKey);
                        editor.commit();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        },700);
                    }else {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "Nothing response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String massage = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this, massage, Toast.LENGTH_LONG).show();
            }
        });
    }


    private static String token;

    private void initWidgets(){
        edtTxtEmail = findViewById(R.id.idEdtTxtEmail);
        edtTxtPassword = findViewById(R.id.idEdtTxtPassword);
        btnLogin = findViewById(R.id.idBtnLogin);

        preferences = getSharedPreferences("myApi", Context.MODE_PRIVATE);
    }
}