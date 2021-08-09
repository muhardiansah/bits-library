package com.example.bitslibrary.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitslibrary.Api.UserService;
import com.example.bitslibrary.BookActivity;
import com.example.bitslibrary.DetailPinjamanActivity;
import com.example.bitslibrary.Fragment.MainFragment;
import com.example.bitslibrary.Models.Book;
import com.example.bitslibrary.Models.BorrowDataResponse;
import com.example.bitslibrary.Models.DaftarPinjamanDataResponse;
import com.example.bitslibrary.Models.DetailPinjamResponse;
import com.example.bitslibrary.Models.Utils;
import com.example.bitslibrary.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PinjamanAdapter extends RecyclerView.Adapter<PinjamanAdapter.ViewHolder> {
    private Context context;
    private List<DaftarPinjamanDataResponse> listPinjaman;
    private List<Book> lisBook;
    private List<BorrowDataResponse> listDetail;
    private int idBorrow;
    private ProgressBar spinner;

    public PinjamanAdapter(Context context, List<DaftarPinjamanDataResponse> listPinjaman) {
        this.context = context;
        this.listPinjaman = listPinjaman;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pinjaman, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.txtnameBook.setText(Utils.getNamaBuku());
//        String nameBook = lisBook.get(position).getName();
//        spinner.setVisibility(View.VISIBLE);
        String dateNowStart = listPinjaman.get(position).getStart_date();
        SimpleDateFormat format_now = new SimpleDateFormat("yyyy-MM-dd");
        Date new_Date = null;
        String dateView = null;

        try {
            new_Date = format_now.parse(dateNowStart);
            format_now = new SimpleDateFormat("dd MMM yyyy");
            dateView = format_now.format(new_Date);
        }catch (ParseException e){
            e.printStackTrace();
        }

        String dateNowEnd = listPinjaman.get(position).getEnd_date();
        SimpleDateFormat format_now2 = new SimpleDateFormat("yyyy-MM-dd");
        Date new_Date2 = null;
        String dateViewEnd = null;

        try {
            new_Date2 = format_now2.parse(dateNowEnd);
            format_now2 = new SimpleDateFormat("dd MMM yyyy");
            dateViewEnd = format_now2.format(new_Date2);
        }catch (ParseException e){
            e.printStackTrace();
        }

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

        idBorrow = listPinjaman.get(position).getId();

        lisBook = Utils.getBookList();

        UserService userService = retrofit.create(UserService.class);
        Call<DetailPinjamResponse> call = userService.getDetailPinjam(idBorrow);
        call.enqueue(new Callback<DetailPinjamResponse>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<DetailPinjamResponse> call, Response<DetailPinjamResponse> response) {
                if (response.isSuccessful()){
                    DetailPinjamResponse detailPinjamResponse = response.body();
                    if (detailPinjamResponse.getStatus() == true){
                        LayoutInflater inflaterBuku = LayoutInflater.from(context);
                        listDetail = new ArrayList<>(Arrays.asList(detailPinjamResponse.getData().getBorrowd()));
                        boolean cekData = listDetail.isEmpty();
                        if (cekData == true){
                            TableRow tableRowDetailPinjam = new TableRow(context);
                            tableRowDetailPinjam.setLayoutParams(new TableLayout.LayoutParams(
                                    TableRow.LayoutParams.FILL_PARENT,
                                    TableRow.LayoutParams.WRAP_CONTENT
                            ));
                            holder.relativeLayout = (RelativeLayout) inflaterBuku.inflate(R.layout.list_buku_pinjaman, null);
                            holder.textViewBook = (TextView) holder.relativeLayout.findViewById(R.id.idNameBookDaftarPinjaman);
                            holder.textViewBook.setText("Buku tidak ada");
                            tableRowDetailPinjam.addView(holder.relativeLayout);
                            holder.tabListBuku.addView(tableRowDetailPinjam);
                        }else {

                            TableRow tableRowDetailPinjam = new TableRow(context);
                            tableRowDetailPinjam.setLayoutParams(new TableLayout.LayoutParams(
                                    TableRow.LayoutParams.FILL_PARENT,
                                    TableRow.LayoutParams.WRAP_CONTENT
                            ));


                            for (BorrowDataResponse bData : listDetail){
                                holder.relativeLayout = (RelativeLayout) inflaterBuku.inflate(R.layout.list_buku_pinjaman, null);
                                holder.textViewBook = (TextView) holder.relativeLayout.findViewById(R.id.idNameBookDaftarPinjaman);

                                for (Book book:lisBook){
                                    if (bData.getBook_id() == book.getId()){
                                        holder.textViewBook.setText(book.getName()+" ");
                                    }
                                }

                                tableRowDetailPinjam.addView(holder.relativeLayout);
                            }
                            holder.tabListBuku.addView(tableRowDetailPinjam);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<DetailPinjamResponse> call, Throwable t) {

            }
        });

        holder.txtStartDate.setText(dateView+" - ");
        holder.txtEndDate.setText(dateViewEnd);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toast = String.valueOf(listPinjaman.get(position).getId());
//                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                int idBorrow = listPinjaman.get(position).getId();
                Utils.setBorrowId(idBorrow);
                Intent intent  = new Intent(context, DetailPinjamanActivity.class);
//                intent.putExtra("borrowId", listPinjaman.get(position).getId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listPinjaman.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtnameBook, txtStartDate, txtEndDate, txtIdBorrow, textViewBook;
        private CardView parent;
        private TableLayout tabListBuku;
        private TableRow rowListBuku;
        private RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            txtnameBook = (TextView) itemView.findViewById(R.id.idNameBookDaftarPinjaman);
            txtStartDate = (TextView) itemView.findViewById(R.id.idPinjamStartDate);
            txtEndDate = (TextView) itemView.findViewById(R.id.idPinjamEndDate);
            parent = (CardView) itemView.findViewById(R.id.parentListPinjam);
            tabListBuku = (TableLayout) itemView.findViewById(R.id.tabListBuku);
//            rowListBuku = (TableRow) itemView.findViewById(R.id.tblRowList);
//            spinner = (ProgressBar) itemView.findViewById(R.id.idProgbar);
        }
    }
}
