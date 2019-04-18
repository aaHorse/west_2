package com.example.zexiger.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.base.MyApplication;
import com.example.zexiger.myapplication.entity.Item_main;
import com.example.zexiger.myapplication.entity.Thing;

import java.io.BufferedReader;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemMainAdapter extends RecyclerView.Adapter<ItemMainAdapter.ViewHolder> {
    private ViewHolder viewHolder;
    private List<Thing.DataBean> lists;

    @BindView(R.id.pic)ImageView pic;//头像
    @BindView(R.id.name)TextView name;//名字
    @BindView(R.id.date)TextView date;//时间
    @BindView(R.id.isfound)TextView isfound;//是否找到了，与数据库的不一样
    @BindView(R.id.type)TextView type;
    @BindView(R.id.info)TextView info;
    @BindView(R.id.see)TextView see;
    @BindView(R.id.isfound_line)LinearLayout linearLayout;



    public ItemMainAdapter(List<Thing.DataBean>lists){
        this.lists=lists;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View view){
            super(view);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv,
                viewGroup,false);
        ButterKnife.bind(this,view);
        viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Thing.DataBean item_main=(Thing.DataBean)lists.get(i);
        //
        if (!item_main.getQq_image().isEmpty()){
            //如果QQ头像的图片链接存在
            Glide.with(MyApplication.getContext()).load(item_main.getQq_image()).into(pic);
        }else{
            //维持默认图片
        }
        //
        if (!item_main.getQq_name().isEmpty()){
            //如果QQ昵称存在，显示
            name.setText(item_main.getQq_name());
        }else{
            //维持原状
        }
        //
        date.setText(item_main.getDate());
        //
        if (item_main.getIsexist()==0){
            //未找到
            isfound.setText("未找到");
        }else{
            isfound.setText("已找到");
        }
        //
        type.setText(item_main.getType());
        //
        info.setText(item_main.getInfo());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
