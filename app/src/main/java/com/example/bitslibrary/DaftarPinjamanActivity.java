package com.example.bitslibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bitslibrary.Adapter.ViewAdapterTabLayout;
import com.example.bitslibrary.Fragment.FragmentDikembalikan;
import com.example.bitslibrary.Fragment.FragmentDipinjam;
import com.google.android.material.tabs.TabLayout;

public class DaftarPinjamanActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pinjaman);

        initViews();

        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#17C3B2"));

        tabLayout.setupWithViewPager(viewPager);
        ViewAdapterTabLayout viewAdapterTabLayout = new ViewAdapterTabLayout(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewAdapterTabLayout.addFragment(new FragmentDipinjam(),"Sedang Dipinjam");
        viewAdapterTabLayout.addFragment(new FragmentDikembalikan(),"Sudah Dikembalikan");

        viewPager.setAdapter(viewAdapterTabLayout);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews(){
        tabLayout = findViewById(R.id.idTabLayoutDaftarPinjaman);
        viewPager = findViewById(R.id.idViewPagerDaftarPinjaman);
        toolbar = findViewById(R.id.toolbarPinjamList);

    }

}