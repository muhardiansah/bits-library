package com.example.bitslibrary.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bitslibrary.Adapter.PinjamanAdapter;
import com.example.bitslibrary.Api.UserService;
import com.example.bitslibrary.Models.DaftarPinjamanDataResponse;
import com.example.bitslibrary.Models.DaftarPinjamanResponse;
import com.example.bitslibrary.Models.Utils;
import com.example.bitslibrary.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class FragmentDipinjam extends Fragment {

    private RecyclerView recViewDipinjam;
    private PinjamanAdapter pinjamanAdapter;
    private List<DaftarPinjamanDataResponse> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_dipinjam, container, false);

        initViews(view);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("Authorization", "Bearer "+ Utils.getToken());
                        return chain.proceed(builder.build());
                    }

                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new MainFragment().BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        int userId = Utils.getUsrId();

        UserService userService = retrofit.create(UserService.class);
        Call<DaftarPinjamanResponse> call = userService.getPinjaman(userId);
        call.enqueue(new Callback<DaftarPinjamanResponse>() {
            @Override
            public void onResponse(Call<DaftarPinjamanResponse> call, Response<DaftarPinjamanResponse> response) {
                if (response.isSuccessful()){
                    DaftarPinjamanResponse daftarPinjamanResponse = response.body();
                    if (daftarPinjamanResponse.isStatus() == true){

                        Toast.makeText(getActivity(), daftarPinjamanResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        dataList = new ArrayList<>(Arrays.asList(daftarPinjamanResponse.getData()));

                        pinjamanAdapter = new PinjamanAdapter(getActivity(), dataList);

                        recViewDipinjam.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));

                        recViewDipinjam.setAdapter(pinjamanAdapter);

                    }else {
                        Toast.makeText(getActivity(), daftarPinjamanResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "nothing response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DaftarPinjamanResponse> call, Throwable t) {
                Toast.makeText(getActivity(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void initViews(View view){
        recViewDipinjam = (RecyclerView) view.findViewById(R.id.idRecViewDipinjam);
    }
}