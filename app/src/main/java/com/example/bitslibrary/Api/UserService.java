package com.example.bitslibrary.Api;

import com.example.bitslibrary.Models.BookResponse;
import com.example.bitslibrary.Models.LoginRequest;
import com.example.bitslibrary.Models.LoginResponse;
import com.example.bitslibrary.Models.PinjamRequest;
import com.example.bitslibrary.Models.PinjamResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @GET("book/index")
    Call<BookResponse> getBook();

    @POST("borrow/create")
    Call<PinjamResponse> pinjam(@Body PinjamRequest pinjamRequest);

//    @POST("borrow/create")
//    Call<Borrow> pinjam(@Body Borrow borrow);
}
