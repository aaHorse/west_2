package com.example.zexiger.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.activity.MainActivity;
import com.example.zexiger.myapplication.activity.SpecificActivity;
import com.example.zexiger.myapplication.base.MyApplication;
import com.example.zexiger.myapplication.entity.Thing;
import com.example.zexiger.myapplication.http_util.HttpOK;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.io.IOException;
import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fragment_specific extends Fragment {
    private SpecificActivity activity;
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

    private Thing.DataBean bean;

    /*
    * 标记东西是否已经找到
    * */
    private int flag;
    private int id;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_specific,container,false);
        activity=(SpecificActivity) getActivity();
        context=getContext();
        ButterKnife.bind(this,view);
        Bundle bundle=getArguments();
        bean= (Thing.DataBean) bundle.getSerializable("flag");
        setStatusBar();
        init();
        xiugai();
        return view;
    }

    private void init(){
        //
        if (!bean.getQq_image().isEmpty()){
            //如果QQ头像的图片链接存在
            Glide.with(MyApplication.getContext()).load(bean.getQq_image()).into(imageView);
        }else{
            //维持默认图片
        }
        //
        if (!bean.getQq_name().isEmpty()){
            //如果QQ昵称存在，显示
            name.setText(bean.getQq_name());
        }else{
            //维持原状
        }
        //
        date.setText(bean.getDate());
        //
        if (bean.getIsexist()==0){
            //未找到
            isfound.setText("未找到");
        }else{
            isfound.setText("已找到");
        }
        //
        type.setText(bean.getType());
        //
        info.setText(bean.getInfo());
        //
        if(bean.getImage()=="null"){

        } else{
            Log.d("ttttt","图片的链接为"+bean.getImage());
            //图片链接存在
            Glide.with(MyApplication.getContext()).load(bean.getImage()).into(picture);
        }
        id=bean.getId();
    }


    @OnClick(R.id.top_bar_icon)void back_button(){
        Toast.makeText(context,"你想返回！",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }



    @OnClick(R.id.address)void button_address(){
        Toast.makeText(context,"你想查看详细地址！",Toast.LENGTH_SHORT).show();
        FragmentTransaction transaction=SpecificActivity.fragmentManager.beginTransaction();
        Fragment fragment=new Fragment_address_2();
        Bundle bundle=new Bundle();
        bundle.putString("flag",bean.getAddress());
        fragment.setArguments(bundle);
        transaction.replace(R.id.line_8,fragment);
        transaction.commit();
        scrollView.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        linear_bar.setVisibility(View.GONE);
    }

    @OnClick(R.id.xiugai)void button_xiugai(){
        Toast.makeText(context,"你想标记信息！",Toast.LENGTH_SHORT).show();
        flag=0;
        /*
        * 打开选择菜单进行标记
        * */
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
            funcf_isexits(1+"");
        }else{
            isfound.setText("未找到");
            linearLayout_2.setBackgroundColor(Color.parseColor("#FF8C00"));
            funcf_isexits(0+"");
        }
    }

    private void funcf_isexits(final String exist){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String address="http://192.168.43.61:8080/api/append/isexist";
                HttpOK.func_xiugai_2(address, id + "",exist ,new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("ttttt","修改问题是否解决失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("ttttt","修改问题是否解决成功");
                    }
                });
            }
        }).start();
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


    /*
     * 修改浏览次数
     * */
    private void xiugai(){

        final String id=""+bean.getId();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String address="http://192.168.43.61:8080/api/append/visits";
                HttpOK.func_xiugai(address,id, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("ttttt","修改浏览次数失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("ttttt","成功修改浏览次数");
                    }
                });
            }
        }).start();
    }
}
