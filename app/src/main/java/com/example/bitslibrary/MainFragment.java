package com.example.bitslibrary;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.bitslibrary.Models.Book;
import com.example.bitslibrary.Models.Book;
import com.example.bitslibrary.Models.BookResponse;
import com.example.bitslibrary.Api.UserService;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    public static final String BASE_URL = "https://bits-library.herokuapp.com/api/";

    private TextView textViewApi, textViewId;
    private SliderView sliderView;
    private RecyclerView recViewPopuler, recViewTerbaru;
    private BookAdapter bookPopulerAdapter, bookTerbaruAdapter;
    List<Book> bookList;
    private int[] images = {R.drawable.book1,
            R.drawable.book2,
            R.drawable.book3,
            R.drawable.book4
    };

    SharedPreferences preferences;
    private static final String shared_pref_name = "myPref";
    private static final String key_api = "api";

    private ProgressBar spinner;
//    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

//        int userId = preferences.getInt(String.valueOf(key_usrId),0);
//        progressDialog= new ProgressDialog(getContext());
//        progressDialog.show();
//        progressDialog.setContentView(R.layout.progress_dialog);
//        progressDialog.getWindow().setBackgroundDrawableResource(
//                android.R.color.transparent
//        );

        spinner.setVisibility(View.VISIBLE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("Authorization", "Bearer "+getToken());
                        return chain.proceed(builder.build());
                    }

                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        UserService userService = retrofit.create(UserService.class);
        Call<BookResponse> call  = userService.getBook();
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: gagal response");
                    Toast.makeText(getActivity(), "response failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                BookResponse bookResponse = response.body();

                spinner.setVisibility(View.GONE);

                bookList = new ArrayList<>(Arrays.asList(bookResponse.getData()));

                bookPopulerAdapter = new BookAdapter(getActivity(), bookList);
                bookTerbaruAdapter = new BookAdapter(getActivity(), bookList);

                recViewPopuler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                recViewTerbaru.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

                recViewPopuler.setAdapter(bookPopulerAdapter);
                recViewTerbaru.setAdapter(bookTerbaruAdapter);
//                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(getActivity(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private String getToken(){
        preferences = getContext().getSharedPreferences(shared_pref_name, MODE_PRIVATE);
        String api_key = preferences.getString(key_api, null);
        return api_key;
    }

    private void initViews(View view){
        sliderView = (SliderView) view.findViewById(R.id.idImg_slider);
        recViewPopuler = (RecyclerView) view.findViewById(R.id.idRecViewAllBookPopuler);
        recViewTerbaru = (RecyclerView) view.findViewById(R.id.idRecViewAllBookTerbaru);
        textViewApi = (TextView) view.findViewById(R.id.apikey);
        textViewId = (TextView) view.findViewById(R.id.userId);

        spinner = (ProgressBar) view.findViewById(R.id.idProgbar);
    }

}
