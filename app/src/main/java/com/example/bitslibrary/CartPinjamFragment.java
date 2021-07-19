package com.example.bitslibrary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitslibrary.Models.Book;
import com.example.bitslibrary.Models.CartPinjam;
import com.example.bitslibrary.Models.ItemPinjam;

import java.util.List;

public class CartPinjamFragment extends Fragment implements CartPinjamRecviewAdapter.DeleteCartItem {
    private static final String TAG = "CartPinjamFragment";

    @Override
    public void onDelete(Book book) {
        CartPinjam.remove(book);
        adapter.notifyDataSetChanged();
    }

    private RecyclerView recViewCart;
    private CartPinjamRecviewAdapter adapter;
    private List<ItemPinjam> itemPinjamList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_pinjam, container, false);

        initViews(view);

        initRecView();

        return view;
    }

    private void initRecView(){
        Log.d(TAG, "initRecView: started");
        adapter = new CartPinjamRecviewAdapter(this);
        recViewCart.setAdapter(adapter);
        recViewCart.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setItems(itemPinjamList);
    }

    private void initViews(View view){
        recViewCart = view.findViewById(R.id.idRecViewCart);
    }


}
