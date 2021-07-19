package com.example.bitslibrary;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bitslibrary.Models.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>  {
    private static final String TAG = "BookAdapter";

    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    public BookAdapter(RecyclerView recViewPopuler) {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Book book = bookList.get(position);
        holder.txtName.setText(book.getName());
        holder.txtAuthor.setText(book.getAuthor());
        holder.txtPrice.setText(String.valueOf(book.getPrice()));

        Glide.with(context).load("https://upload.wikimedia.org/wikipedia/id/2/28/Koala_Kumal.jpg")
                .into(holder.imageView);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context, BookActivity.class);
                intent.putExtra("bookId", bookList.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txtName;
        private TextView txtAuthor;
        private TextView txtPrice;
        private CardView parent;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.idImgList);
            txtName = (TextView) itemView.findViewById(R.id.idNameBookList);
            txtAuthor = (TextView) itemView.findViewById(R.id.idAuthorList);
            txtPrice = (TextView) itemView.findViewById(R.id.idPriceList);
            parent = (CardView) itemView.findViewById(R.id.idParent);
        }
    }

//    public void setItemBook(ArrayList<Book> itemBook) {
//        this.bookList = itemBook;
//        notifyDataSetChanged();
//    }
}
