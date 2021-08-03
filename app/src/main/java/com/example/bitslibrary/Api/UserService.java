package com.example.bitslibrary.Api;

import com.example.bitslibrary.Models.BookResponse;
import com.example.bitslibrary.Models.BorrowResponse;
import com.example.bitslibrary.Models.DaftarPinjamanResponse;
import com.example.bitslibrary.Models.DetailPinjamResponse;
import com.example.bitslibrary.Models.LoginRequest;
import com.example.bitslibrary.Models.LoginResponse;
import com.example.bitslibrary.Models.PinjamRequest;
import com.example.bitslibrary.Models.PinjamResponse;
import com.example.bitslibrary.Models.ReturnResponse;
import com.example.bitslibrary.Models.UserRequest;
import com.example.bitslibrary.Models.UserResponse;
import com.example.bitslibrary.Models.Utils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface UserService {


    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @GET("book/index")
    Call<BookResponse> getBook();

    @POST("borrow/create")
    Call<PinjamResponse> pinjam(@Body PinjamRequest pinjamRequest);

    @GET("borrow/view/{borrowId}")
    Call<DetailPinjamResponse> getDetailPinjam(@Path("borrowId") int borrowId);

    @GET("usr/view/{usrId}")
    Call<UserResponse> getUser(@Path("usrId") int usrId);

    @PUT("usr/update/{usrId}")
    Call<UserResponse> updateUser(
            @Path("usrId") int usrId,
            @Body UserRequest userRequest
            );

    @POST("return/{borrowIdReturn}")
    Call<ReturnResponse> returnBook(@Path("borrowIdReturn") int borrowIdReturn);

    @GET("borrow/index/{userId}")
    Call<DaftarPinjamanResponse> getPinjaman(@Path("userId") int userId);

}
