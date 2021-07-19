package com.example.bitslibrary.Models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {
    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @GET("book/index")
    Call<BookResponse> getBook();

//    @POST("borrow/create")

}
