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

public class Fragment_specific extends Fragment {
    private MainActivity activity;
    private Context context;
    @BindView(R.id.bar_layout) ViewGroup linear_bar;

    //头像
    @BindView(R.id.pic)ImageView imageView;
    @BindView(R.id.name)TextView name;
    @BindView(R.id.date)TextView date;
    @BindView(R.id.isfound_line)LinearLayout linearLayout_2;
    @BindView(R.id.isfound)TextView isfound;
    @BindView(R.id.picture)ImageView picture;
    @BindView(R.id.type)TextView type;
    @BindView(R.id.info)TextView info;
    @BindView(R.id.phone)TextView phone;
    @BindView(R.id.line_9)ScrollView scrollView;
    @BindView(R.id.line_8)LinearLayout linearLayout;

    /*
    * 标记东西是否已经找到
    * */
    private int flag;



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
        flag=0;
        showSingleChoiceDialog(flag);
    }


    private void showSingleChoiceDialog(int n) {
        final String[] items = new String[]{"东西已经找到", "东西尚未找到"};
        new QMUIDialog.CheckableDialogBuilder(getActivity())
                .setCheckedIndex(n)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "你选择了 " + items[which], Toast.LENGTH_SHORT).show();
                        flag=which;
                        dialog.dismiss();
                        /*
                        * 修改界面
                        * */
                        set_isfound(flag);
                        /*
                        * 通知服务器
                        * */
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    /*
    * 用户标记了已经找到之后，对提示进行修改
    * */
    private void set_isfound(int flag){
        if (flag==0){
            isfound.setText("已找到");
            linearLayout_2.setBackgroundColor(Color.parseColor("#4EEE94"));
        }else{
            isfound.setText("未找到");
            linearLayout_2.setBackgroundColor(Color.parseColor("#FF8C00"));
        }
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
