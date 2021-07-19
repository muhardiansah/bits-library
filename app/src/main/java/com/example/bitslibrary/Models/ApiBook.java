package com.example.bitslibrary.Models;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitslibrary.LoginActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBook extends AppCompatActivity {
    public static final String BASE_URL = "https://bits-library.herokuapp.com/api/";

//    private static SharedPreferences sp = getApplicationContext().getSharedPreferences("myApi", Context.MODE_PRIVATE);
//    public static final String apiKey = sp.getString("Authorization","");

//    public SharedPreferences getSp(){
//        SharedPreferences sp = getApplicationContext().getSharedPreferences("myApi", Context.MODE_PRIVATE);
//    }
//
//    public getResponse(){
//
//    }

    public static Retrofit getRetrofit(){
//        SharedPreferences sp = getApplicationContext().getSharedPreferences("myApi", Context.MODE_PRIVATE);
//        String apiKey = sp.getString("Authorization","");
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTEsImVtYWlsIjoiYXJkaUBnbWFpbC5jb20iLCJleHAiOjE2MjY2ODU3OTIsImp0aSI6Iks3OEE1MExLMzFXOFdJOUw0MEw0SzVLSzMiLCJpc3MiOiJCSVRTTElCUkFSWSJ9.vee0b0kcwN4Y8KbVLC3m1gP3BuNFWqLJwFL-u_tC9oY");
                        return chain.proceed(builder.build());
                    }
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    public static UserService getUserService(){
        UserService userService = getRetrofit().create(UserService.class);
        return userService;
    }
}
