package com.example.bitslibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.bitslibrary.Api.UserService;
import com.example.bitslibrary.Fragment.MainFragment;
import com.example.bitslibrary.Models.LoginResponse;
import com.example.bitslibrary.Models.UserResponse;
import com.example.bitslibrary.Models.Utils;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private TextView txtUser, txtEmail;
    private View hView;

    LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        initViews();

         setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        hView = navigationView.getHeaderView(0);

        txtUser = (TextView) hView.findViewById(R.id.idUser);
        txtEmail = (TextView) hView.findViewById(R.id.idEmail);

        viewUser();


        Intent intent = getIntent();
        if (intent.getExtras() != null){
            loginResponse = (LoginResponse) intent.getSerializableExtra("data");

            Log.e(TAG, "onCreate: "+loginResponse.getMessage());
        }
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_container, new MainFragment());
        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_nav_menu, menu);
        return true;
//        MenuItem menuItem = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setQueryHint("Search..");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.beranda:
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.pinjaman:
                Intent intentDaftarPinjam = new Intent(MainActivity.this, DaftarPinjamanActivity.class);
                intentDaftarPinjam.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentDaftarPinjam);
                break;
            case R.id.pengaturan:
                Intent intentPengaturan = new Intent(MainActivity.this, PengaturanAkunActivity.class);
                intentPengaturan.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentPengaturan);
                break;
            default:
                break;
        }
        return false;
    }

    private void viewUser(){
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

        int usrId = Utils.getUsrId();

        UserService userService = retrofit.create(UserService.class);
        Call<UserResponse> call = userService.getUser(usrId);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    UserResponse userResponse = response.body();

                    if (userResponse.getStatus() == true){
                        Toast.makeText(MainActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        String user = userResponse.getData().getName();
                        String email = userResponse.getData().getEmail();
                        String nohp = userResponse.getData().getMobile();
                        String alamat = userResponse.getData().getAddress();
                        String password = userResponse.getData().getPassword();

                        txtUser.setText(user);
                        txtEmail.setText(email);

                        Utils.setNamaUser(user);
                        Utils.setEmailUser(email);
                        Utils.setNohpUser(nohp);
                        Utils.setAlamatUser(alamat);
                        Utils.setPassKonfirm(password);

                    }else {
                        Toast.makeText(MainActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "nothing response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initViews(){
        drawerLayout = (DrawerLayout) findViewById(R.id.idDrawer);
        navigationView = (NavigationView) findViewById(R.id.idNavigationDrawer);
        toolbar = (Toolbar) findViewById(R.id.idToolbar);
    }


}