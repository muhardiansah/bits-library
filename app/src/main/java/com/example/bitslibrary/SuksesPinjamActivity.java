package com.example.bitslibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bitslibrary.Models.Utils;

public class SuksesPinjamActivity extends AppCompatActivity {

    private Button btnDetail;
    private TextView txtBorrowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sukses_pinjam);

        btnDetail = findViewById(R.id.idLihatDetail);
        txtBorrowId = findViewById(R.id.borrowId);

//        int borrow = Utils.getBorrowId();
//
//        txtBorrowId.setText("id "+borrow);

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SuksesPinjamActivity.this, DetailPinjamanActivity.class));
                    }
                },500);
            }
        });
    }
}