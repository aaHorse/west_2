package com.example.zexiger.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.entity.Item_main;

import java.io.BufferedReader;
import java.util.List;

import butterknife.ButterKnife;

public class ItemMainAdapter extends RecyclerView.Adapter<ItemMainAdapter.ViewHolder> {
    private ViewHolder viewHolder;
    private List<Item_main> lists;


    public ItemMainAdapter(List<Item_main>lists){
        this.lists=lists;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv,
                viewGroup,false);
        viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Item_main item_main=(Item_main)lists.get(i);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}