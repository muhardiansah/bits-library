package com.example.bitslibrary.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bitslibrary.Api.UserService;
import com.example.bitslibrary.MainActivity;
import com.example.bitslibrary.Models.UserResponse;
import com.example.bitslibrary.Models.Utils;
import com.example.bitslibrary.R;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KonfirmasiFragment extends Fragment {
    private TextView txtUser;
    private TextInputEditText ePassword;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_konfirmasi, container, false);
        initViews(view);

        String user =  Utils.getNamaUser();
        txtUser.setText("Hai "+user);

//        Utils.setPassKonfirm(ePassword.getText().toString());

        return view;
    }

    private void initViews(View view){
        txtUser = (TextView) view.findViewById(R.id.idHaiUser);
        ePassword = (TextInputEditText) view.findViewById(R.id.editTxtPassKonfirm);
    }
}