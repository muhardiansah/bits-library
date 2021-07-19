package com.example.bitslibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.bitslibrary.Models.Book;
import com.example.bitslibrary.Models.ApiBook;
import com.example.bitslibrary.Models.Book;
import com.example.bitslibrary.Models.BookResponse;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    private TextView textViewApi;
    private SliderView sliderView;
    private RecyclerView recViewPopuler, recViewTerbaru;
    private BookAdapter bookPopulerAdapter, bookTerbaruAdapter;
    List<Book> bookList;
    private int[] images = {R.drawable.book1,
            R.drawable.book2,
            R.drawable.book3,
            R.drawable.book4
    };

    public static SharedPreferences preferences;
    private static final String shared_pref_name = "myPref";
    private static final String key_api = "api";

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

//        preferences = getContext().getSharedPreferences(shared_pref_name, MODE_PRIVATE);
//        String api_key = preferences.getString(key_api, null);
//
//        textViewApi.setText("token : "+api_key);

        Call<BookResponse> call  = ApiBook.getUserService().getBook();
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: gagal response");
                    Toast.makeText(getActivity(), "response failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                BookResponse bookResponse = response.body();
//                Toast.makeText(getActivity(), "response berhasil", Toast.LENGTH_SHORT).show();
                bookList = new ArrayList<>(Arrays.asList(bookResponse.getData()));



                bookPopulerAdapter = new BookAdapter(getActivity(), bookList);
                bookTerbaruAdapter = new BookAdapter(getActivity(), bookList);

                recViewPopuler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                recViewTerbaru.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

                recViewPopuler.setAdapter(bookPopulerAdapter);
                recViewTerbaru.setAdapter(bookTerbaruAdapter);
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(getActivity(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


    private void initViews(View view){
        sliderView = (SliderView) view.findViewById(R.id.idImg_slider);
        recViewPopuler = (RecyclerView) view.findViewById(R.id.idRecViewAllBookPopuler);
        recViewTerbaru = (RecyclerView) view.findViewById(R.id.idRecViewAllBookTerbaru);
        textViewApi = (TextView) view.findViewById(R.id.apikey);
    }

}
