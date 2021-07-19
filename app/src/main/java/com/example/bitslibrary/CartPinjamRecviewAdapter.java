package com.example.bitslibrary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bitslibrary.Models.Book;
import com.example.bitslibrary.Models.CartPinjam;
import com.example.bitslibrary.Models.ItemPinjam;

import java.util.List;

public class CartPinjamRecviewAdapter extends RecyclerView.Adapter<CartPinjamRecviewAdapter.ViewHolder> {
    private static final String TAG = "CartPinjamRecviewAdapter";

    public interface DeleteCartItem{
        void onDelete(Book book);
    }

    DeleteCartItem deleteCartItem;

    private Fragment fragment;

    public CartPinjamRecviewAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    private List<ItemPinjam> itemPinjamList;

    public CartPinjamRecviewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recview_cart_pinjam_selected, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        for (ItemPinjam itemPinjam: CartPinjam.contents()){
            holder.txtName.setText(itemPinjam.getBook().getName());
            holder.txtAuthor.setText(itemPinjam.getBook().getAuthor());
            holder.txtJmlBuku.setText(itemPinjam.getQty());

            deleteCartItem = (DeleteCartItem) fragment;

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure ? ");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteCartItem.onDelete(itemPinjam.getBook());
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemPinjamList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName, txtAuthor;
        private ImageView imgBook;

        private TextView txtJmlBuku;
        private Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.idNameBookPinjam);
            txtAuthor = itemView.findViewById(R.id.idAuthorPinjam);
            imgBook = itemView.findViewById(R.id.idImgPinjam);
            txtJmlBuku = itemView.findViewById(R.id.idTxtJumlahBuku);
            btnDelete = itemView.findViewById(R.id.idBtnDelete);
        }
    }

    public void setItems(List<ItemPinjam> itemPinjamList){
        this.itemPinjamList =itemPinjamList;
        notifyDataSetChanged();
    }
}
