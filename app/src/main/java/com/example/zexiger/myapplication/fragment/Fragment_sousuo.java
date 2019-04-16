package com.example.zexiger.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.activity.MainActivity;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment_sousuo extends Fragment {
    private MainActivity activity;
    private Context context;
    @BindView(R.id.bar_layout) ViewGroup linear_bar;
    @BindView(R.id.line_2)LinearLayout relativeLayout_2;
    @BindView(R.id.line_33)RelativeLayout relativeLayout_3;

    /*
     * 标记东西是否已经找到
     * */
    private int flag;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sousuo,container,false);
        activity=(MainActivity) getActivity();
        context=getContext();
        ButterKnife.bind(this,view);
        setStatusBar();
        return view;
    }

    @OnClick(R.id.top_bar_icon)void button_back(){
        Toast.makeText(context,"你想返回！",Toast.LENGTH_SHORT).show();
        activity.show_brife();
    }
/*    @OnClick(R.id.button)void button(){
        FragmentTransaction transaction=MainActivity.fragmentManager.beginTransaction();
        Fragment fragment=new Fragment_main_right();
        Bundle bundle=new Bundle();
        bundle.putString("flag","123");
        fragment.setArguments(bundle);
        transaction.replace(R.id.line_33,fragment);
        transaction.commit();
        relativeLayout_2.setVisibility(View.GONE);
        relativeLayout_3.setVisibility(View.VISIBLE);
        //linear_bar.setVisibility(View.GONE);
    }*/





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
