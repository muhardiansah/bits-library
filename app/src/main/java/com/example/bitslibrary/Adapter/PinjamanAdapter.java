package com.example.bitslibrary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitslibrary.Models.DaftarPinjamanDataResponse;
import com.example.bitslibrary.R;

import java.util.List;

public class PinjamanAdapter extends RecyclerView.Adapter<PinjamanAdapter.ViewHolder> {
    private Context context;
    private List<DaftarPinjamanDataResponse> listPinjaman;

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
//        holder.txtnameBook.setText(listPinjaman.get(position).get);
        holder.txtStartDate.setText(listPinjaman.get(position).getStart_date()+" - ");
        holder.txtEndDate.setText(listPinjaman.get(position).getEnd_date());
    }

    @Override
    public int getItemCount() {
        return listPinjaman.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtnameBook, txtStartDate, txtEndDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtnameBook = (TextView) itemView.findViewById(R.id.idNameBookDaftarPinjaman);
            txtStartDate = (TextView) itemView.findViewById(R.id.idPinjamStartDate);
            txtEndDate = (TextView) itemView.findViewById(R.id.idPinjamEndDate);
        }
    }
}
