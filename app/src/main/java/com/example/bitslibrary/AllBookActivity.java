package com.example.bitslibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

public class AllBookActivity extends AppCompatActivity {
    private static final String TAG = "AllBookActivity";

    private RecyclerView recyclerViewPopuler;
    private RecyclerView recyclerViewTerbaru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_book);

        initViews();
    }

    private void initViews(){
        recyclerViewPopuler = (RecyclerView) findViewById(R.id.idRecViewAllBookPopuler);
        recyclerViewTerbaru = (RecyclerView) findViewById(R.id.idRecViewAllBookTerbaru);
    }
}