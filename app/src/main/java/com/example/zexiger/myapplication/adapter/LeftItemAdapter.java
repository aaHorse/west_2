package com.example.zexiger.myapplication.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.entity.LeftItemMenu;


import java.util.List;


public class LeftItemAdapter extends RecyclerView.Adapter<LeftItemAdapter.ViewHolder> {
    private ViewHolder viewHolder;
    private List<LeftItemMenu> lists;

    public LeftItemAdapter(List<LeftItemMenu>lists){
        this.lists=lists;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public ViewHolder(View view){
            super(view);
            imageView=view.findViewById(R.id.item_left_view_img);
            textView=view.findViewById(R.id.item_left_view_title);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_left_menu_layout,
                viewGroup,false);
        viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        LeftItemMenu leftItemMenu=(LeftItemMenu)lists.get(i);
        viewHolder.imageView.setImageResource(leftItemMenu.getLeftIcon());
        viewHolder.textView.setText(leftItemMenu.getTitle());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
