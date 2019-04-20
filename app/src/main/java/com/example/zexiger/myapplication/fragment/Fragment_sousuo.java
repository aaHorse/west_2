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
import android.view.KeyEvent;
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

import static com.example.zexiger.myapplication.activity.MainActivity.show_hei;

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
        MainActivity.startActivity(context);
    }
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    MainActivity.startActivity(context);
                    return true;
                }
                return false;
            }
        });
    }

    /*
    * 电子产品
    * */
    @OnClick(R.id.button_11)void button_11(){
        Fragment_main_right.startFragment("电子产品");
        show_hei();
        activity.show_brife();
/*        relativeLayout_2.setVisibility(View.GONE);
        relativeLayout_3.setVisibility(View.VISIBLE);*/
    }
    @OnClick(R.id.button_12)void button_12(){
        Fragment_main_right.startFragment("衣物");
        show_hei();
        activity.show_brife();
/*        relativeLayout_2.setVisibility(View.GONE);
        relativeLayout_3.setVisibility(View.VISIBLE);*/
    }
    @OnClick(R.id.button_13)void button_13(){
        Fragment_main_right.startFragment("其他");
        show_hei();
        activity.show_brife();
/*        relativeLayout_2.setVisibility(View.GONE);
        relativeLayout_3.setVisibility(View.VISIBLE);*/
    }
    @OnClick(R.id.button_14)void button_14(){
        Fragment_main_right.startFragment("校园卡");
        show_hei();
        activity.show_brife();
/*        relativeLayout_2.setVisibility(View.GONE);
        relativeLayout_3.setVisibility(View.VISIBLE);*/
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
