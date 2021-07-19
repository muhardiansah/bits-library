package com.example.bitslibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bitslibrary.Models.ApiBook;
import com.example.bitslibrary.Models.ApiClient;
import com.example.bitslibrary.Models.Book;
import com.example.bitslibrary.Models.BookResponse;
import com.example.bitslibrary.Models.CartPinjam;
import com.example.bitslibrary.Models.ItemPinjam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinjamActivity extends AppCompatActivity {
    private TextView txtName, txtAuthor, txId, txtNameBiaya, txtBiaya, txtTotal, txtHarga, statusN;
    private ImageView imgBook;
    private EditText edtTgPinjam, edtTglAkhirPinjam;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;
    private TableLayout tableLayout;

    private Book incomingBookPinjam;

    private TextView txtJmlBuku;
    int count = 1;
    private Button btnAdd, btnLess, btnTambahBuku, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinjam);

        initViews();

        addCart();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_cart, new CartPinjamFragment());
        transaction.commit();

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//        Intent intent = getIntent();
//        int id = intent.getIntExtra("bookId",0);

//        Call<BookResponse> call = ApiBook.getUserService().getBook();
//        call.enqueue(new Callback<BookResponse>() {
//            @Override
//            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
//                if (!response.isSuccessful()){
//                    Toast.makeText(PinjamActivity.this, "pinjam response gagal", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                BookResponse bookResponse = response.body();
//                Toast.makeText(PinjamActivity.this, "pinjam response berhasil", Toast.LENGTH_SHORT).show();
//                List<Book> bookList = new ArrayList<>(Arrays.asList(bookResponse.getData()));
//                for (Book b: bookList){
//                    if (b.getId() == id){
//                        incomingBookPinjam = b;
//                        txId.setText(String.valueOf(b.getId()));
//                        txtName.setText(b.getName());
//                        txtAuthor.setText(b.getAuthor());
//                        txtNameBiaya.setText(b.getName());
//                        txtBiaya.setText("Rp "+String.valueOf(b.getPrice()));
//                        Glide.with(PinjamActivity.this).load("https://hpmedia.bloomsbury.com/rep/s/9781408855904_309575.jpeg")
//                                .into(imgBook);
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<BookResponse> call, Throwable t) {
//                Toast.makeText(PinjamActivity.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
//            }
//        });

        btnTambahBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PinjamActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                count++;
//                txtJmlBuku.setText(""+count);
//            }
//        });
//        btnLess.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (count <= 1) count=1;
//                else count--;
//                txtJmlBuku.setText(""+count);
//            }
//        });

        edtTgPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialogPinjam();
            }
        });
        edtTglAkhirPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialogAkhirPinjam();
            }
        });


        fillData();

    }

    public void addCart(){
        try {
            Intent intent = getIntent();
            Book book = (Book) intent.getSerializableExtra("bookId");
            if (!CartPinjam.isExist(book)){
                CartPinjam.insert(new ItemPinjam(book,1));
            }else {
                CartPinjam.update(book);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void fillData(){
        for (ItemPinjam itemPinjam: CartPinjam.contents()){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));

//            Button buttonImg = new Button(this);
//            buttonImg.setPadding(0,0,0,0);
//            buttonImg.layout(5,15,5,15);
//            buttonImg.setBackgroundResource(R.drawable.ic_delete);
//            buttonImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                    builder.setTitle("Confirm");
//                    builder.setMessage("Are you sure ? ");
//                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            CartPinjam.remove(itemPinjam.getBook());
//                            tableLayout.removeAllViews();
//                            fillData();
//                        }
//                    });
//                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//                    builder.show();
//                }
//            });
//            tableRow.addView(buttonImg);

//            TextView textViewId = new TextView(this);
//            textViewId.setText(String.valueOf(itemPinjam.getBook().getId()));
//            textViewId.setTypeface(Typeface.DEFAULT);
//            textViewId.setPadding(5,5,5,0);
//            tableRow.addView(textViewId);


//            btnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                    builder.setTitle("Confirm");
//                    builder.setMessage("Are you sure ? ");
//                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            CartPinjam.remove(itemPinjam.getBook());
//                            tableLayout.removeAllViews();
//                            fillData();
//                        }
//                    });
//                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//                    builder.show();
//                }
//            });
//            tableRow.addView(btnDelete);

            TextView textName  = new TextView(this);
            textName.setText(itemPinjam.getBook().getName());
            textName.setTypeface(Typeface.DEFAULT);
            textName.setPadding(5,5,5,0);
            tableRow.addView(textName);

            TextView textAuthor = new TextView(this);
            textAuthor.setText(itemPinjam.getBook().getAuthor());
            textAuthor.setTypeface(Typeface.DEFAULT);
            textAuthor.setPadding(5,5,5,0);
            tableRow.addView(textAuthor);

            TextView textHarga = new TextView(this);
            textHarga.setText(String.valueOf(itemPinjam.getBook().getPrice()));
            textHarga.setTypeface(Typeface.DEFAULT);
            textHarga.setPadding(5,5,5,0);
            tableRow.addView(textHarga);

            TextView textViewQty = new TextView(this);
            textViewQty.setText(String.valueOf(itemPinjam.getQty()));
            textViewQty.setTypeface(Typeface.DEFAULT);
            textViewQty.setPadding(5,5,5,0);
            tableRow.addView(textViewQty);

            TextView textSubtotal = new TextView(this);
            textSubtotal.setText(String.valueOf(itemPinjam.getBook().getPrice() * itemPinjam.getQty()));
            textSubtotal.setTypeface(Typeface.DEFAULT);
            textSubtotal.setPadding(5,5,5,0);
            tableRow.addView(textSubtotal);

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));
        }

        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));

        TextView textViewTotal = new TextView(this);
        textViewTotal.setText("Total : ");
        textViewTotal.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewTotal.setPadding(5,5,5,0);
        tableRow.addView(textViewTotal);

        TextView textViewTotalVal = new TextView(this);
        textViewTotalVal.setText(String.valueOf(CartPinjam.total()));
        textViewTotalVal.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewTotalVal.setPadding(5,5,5,0);
        tableRow.addView(textViewTotalVal);
        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

//        try {
//            for (ItemPinjam itemPinjam : CartPinjam.contents()){
//                txId.setText(String.valueOf(itemPinjam.getBook().getId()));
//                txtName.setText(itemPinjam.getBook().getName());
//                txtAuthor.setText(itemPinjam.getBook().getAuthor());
//                txtHarga.setText(String.valueOf(itemPinjam.getBook().getPrice()));//hargaPerBuku
//                txtJmlBuku.setText(String.valueOf(itemPinjam.getQty()));//qty
//                txtBiaya.setText(String.valueOf(itemPinjam.getBook().getPrice() * itemPinjam.getQty()));//subtotal
//                Glide.with(PinjamActivity.this).load("https://hpmedia.bloomsbury.com/rep/s/9781408855904_309575.jpeg")
//                        .into(imgBook);
//                txtTotal.setText(String.valueOf(CartPinjam.total()));//total
//            }
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
//        }
    }

    private void showDateDialogPinjam() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                edtTgPinjam.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showDateDialogAkhirPinjam() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                edtTglAkhirPinjam.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void initViews(){
        imgBook = findViewById(R.id.idImgPinjam);
        txtName = findViewById(R.id.idNameBookPinjam);
        txtAuthor = findViewById(R.id.idAuthorPinjam);
        txId = findViewById(R.id.idIdBook);
        txtNameBiaya = findViewById(R.id.idNameBookPinjamBiaya);
//        txtHarga = findViewById(R.id.idHargaBukuPinjam);
        txtBiaya = findViewById(R.id.idPricePinjam);
        txtTotal = findViewById(R.id.idTotalPricePinjam);

        edtTgPinjam = findViewById(R.id.idEdtTanggalPinjam);
        edtTglAkhirPinjam = findViewById(R.id.idEdtTanggalAkhirPinjam);

        btnAdd = findViewById(R.id.idBtnAdd);
        btnLess = findViewById(R.id.idBtnLess);
        txtJmlBuku = findViewById(R.id.idTxtJumlahBuku);
        btnDelete = findViewById(R.id.idBtnDelete);

        btnTambahBuku = findViewById(R.id.idBtnTambahBukuLain);
        tableLayout = findViewById(R.id.idTableLayout);

        statusN = findViewById(R.id.idStatusN);
    }
}