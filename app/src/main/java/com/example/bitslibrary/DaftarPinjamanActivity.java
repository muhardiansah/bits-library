package com.example.bitslibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class DaftarPinjamanActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pinjaman);

        initViews();

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.action_bar_layout);


        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#17C3B2"));

        tabLayout.setupWithViewPager(viewPager);
        ViewAdapterTabLayout viewAdapterTabLayout = new ViewAdapterTabLayout(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewAdapterTabLayout.addFragment(new FragmentDipinjam(),"Sedang Dipinjam");
        viewAdapterTabLayout.addFragment(new FragmentDikembalikan(),"Sudah Dikembalikan");

        viewPager.setAdapter(viewAdapterTabLayout);
    }

    private void initViews(){
        tabLayout = findViewById(R.id.idTabLayoutDaftarPinjaman);
        viewPager = findViewById(R.id.idViewPagerDaftarPinjaman);
        toolbar = findViewById(R.id.idToolbarPinjam);

    }

}