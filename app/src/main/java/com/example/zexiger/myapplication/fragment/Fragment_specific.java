package com.example.zexiger.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.activity.MainActivity;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment_specific extends Fragment {
    private MainActivity activity;
    private Context context;
    @BindView(R.id.bar_layout) ViewGroup linear_bar;

    //头像
    @BindView(R.id.pic)ImageView imageView;
    @BindView(R.id.name)TextView name;
    @BindView(R.id.date)TextView date;
    @BindView(R.id.isfound)TextView isfound;
    @BindView(R.id.picture)ImageView picture;
    @BindView(R.id.type)TextView type;
    @BindView(R.id.info)TextView info;
    @BindView(R.id.phone)TextView phone;
    @BindView(R.id.line_9)ScrollView scrollView;
    @BindView(R.id.line_8)LinearLayout linearLayout;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_specific,container,false);
        activity=(MainActivity) getActivity();
        context=getContext();
        ButterKnife.bind(this,view);
        setStatusBar();
        Bundle bundle=getArguments();
        String str=bundle.getString("number");
        return view;
    }

    @OnClick(R.id.top_bar_icon)void back_button(){
        Toast.makeText(context,"你想返回！",Toast.LENGTH_SHORT).show();
        activity.show_brife();
    }
    @OnClick(R.id.address)void button_address(){
        Toast.makeText(context,"你想查看详细地址！",Toast.LENGTH_SHORT).show();
        FragmentTransaction transaction=MainActivity.fragmentManager.beginTransaction();
        Fragment fragment=new Fragment_address_2();
        transaction.replace(R.id.line_8,fragment);
        transaction.commit();
        scrollView.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        linear_bar.setVisibility(View.GONE);
    }
    @OnClick(R.id.xiugai)void button_xiugai(){
        Toast.makeText(context,"你想标记信息！",Toast.LENGTH_SHORT).show();
    }













    /**
     * 设置沉浸式状态栏
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final int statusHeight = getStatusBarHeight();
            linear_bar.post(new Runnable() {
                @Override
                public void run() {
                    int titleHeight = linear_bar.getHeight();
                    android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) linear_bar.getLayoutParams();
                    params.height = statusHeight + titleHeight;
                    linear_bar.setLayoutParams(params);
                }
            });
        }
    }
    /**
     * 获取状态栏的高度
     * @return
     */
    protected int getStatusBarHeight(){
        try
        {
            Class<?> c=Class.forName("com.android.internal.R$dimen");
            Object obj=c.newInstance();
            Field field=c.getField("status_bar_height");
            int x=Integer.parseInt(field.get(obj).toString());
            return  getResources().getDimensionPixelSize(x);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }


}
