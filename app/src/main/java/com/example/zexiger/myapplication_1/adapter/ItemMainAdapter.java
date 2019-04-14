package com.example.zexiger.myapplication_1.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zexiger.myapplication_1.R;
import com.example.zexiger.myapplication_1.entity.Item_main;

import java.util.List;

public class ItemMainAdapter extends RecyclerView.Adapter<ItemMainAdapter.ViewHolder> {
    private ViewHolder viewHolder;
    private List<Item_main> lists;

    public ItemMainAdapter(List<Item_main>lists){
        this.lists=lists;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ViewHolder(View view){
            super(view);
            textView=view.findViewById(R.id.text);
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
        viewHolder.textView.setText(item_main.getStr());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
