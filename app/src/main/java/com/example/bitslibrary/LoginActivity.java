package com.example.bitslibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bitslibrary.Api.ApiClient;
import com.example.bitslibrary.Models.LoginRequest;
import com.example.bitslibrary.Models.LoginResponse;
import com.example.bitslibrary.Models.Utils;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText edtTxtEmail, edtTxtPassword;
    private Button btnLogin;
//    Context context;

    SharedPreferences preferences;
    private static final String shared_pref_name = "myPref";
    private static final String key_api = "api";
//    private static final int key_usrId = 0;

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

                        int userId = (int) loginResponse.getUsr_id();
                        String apiKey = loginResponse.getApikey_account();

                        Utils.setToken(apiKey);
                        Utils.setUsrId(userId);

//                        PreferenceManager.getDefaultSharedPreferences(get).edit().putString(getResources().getString(R.string.cmpname), mListDatabase.getCmpname()).apply();

                        preferences = getSharedPreferences(shared_pref_name,MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
//                        editor.putInt(String.valueOf(key_usrId), userId);
                        editor.putString(key_api, apiKey);
                        editor.apply();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        },500);
                    }else {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "Nothing response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initWidgets(){
        edtTxtEmail = findViewById(R.id.idEdtTxtEmail);
        edtTxtPassword = findViewById(R.id.idEdtTxtPassword);
        btnLogin = findViewById(R.id.idBtnLogin);

//        preferences = getSharedPreferences("myApi", Context.MODE_PRIVATE);
    }
}