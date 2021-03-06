package com.example.bitslibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.bitslibrary.Cart.CartPinjam;
import com.example.bitslibrary.Models.ItemPinjam;

public class SuksesPengembalianActivity extends AppCompatActivity {
    private Button btnKembali;
    private ItemPinjam itemPinjam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sukses_pengembalian);

        btnKembali = findViewById(R.id.idKembaliBeranda);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        CartPinjam.remove(itemPinjam.getBook());
//                        new PinjamActivity().tabLayoutCart.removeAllViews();
//                        new PinjamActivity().tabLayoutDetail.removeAllViews();
                        startActivity(new Intent(SuksesPengembalianActivity.this, MainActivity.class));
                    }
                },500);
            }
        });

    }
}