package com.example.zexiger.myapplication.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.activity.MainActivity;
import com.example.zexiger.myapplication.activity.SpecificActivity;
import com.example.zexiger.myapplication.adapter.ItemMainAdapter;
import com.example.zexiger.myapplication.entity.Item_main;
import com.example.zexiger.myapplication.http_util.HttpCallbackListener;
import com.example.zexiger.myapplication.http_util.HttpURL;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIDefaultRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_main_right extends Fragment {
    /*
    * 参数str用于识别是触发哪一个跳过来的
    * */
    public static void startFragment(String str){
        FragmentTransaction transaction=MainActivity.fragmentManager.beginTransaction();
        switch(str){
            case "找失主":
                Fragment fragment=new Fragment_main_right();
                Bundle bundle=new Bundle();
                bundle.putString("flag","找失主");
                fragment.setArguments(bundle);
                transaction.replace(R.id.line_3,fragment);
                transaction.commit();
                break;
            case "找失物":
                Fragment fragment_2=new Fragment_main_right();
                Bundle bundle_2=new Bundle();
                bundle_2.putString("flag","找失物");
                fragment_2.setArguments(bundle_2);
                transaction.replace(R.id.line_3,fragment_2);
                transaction.commit();
                break;
            case "搜索":
                Fragment fragment_3=new Fragment_main_right();
                Bundle bundle_3=new Bundle();
                bundle_3.putString("flag","搜索");
                fragment_3.setArguments(bundle_3);
                transaction.replace(R.id.line_3,fragment_3);
                transaction.commit();
                break;
            case "我的发布":
                Fragment fragment_4=new Fragment_main_right();
                Bundle bundle_4=new Bundle();
                bundle_4.putString("flag","我的发布");
                fragment_4.setArguments(bundle_4);
                transaction.replace(R.id.line_3,fragment_4);
                transaction.commit();
                break;
                default:
                    Log.d("ttttt","碎片的跳转没有匹配");
        }
    }


    private MainActivity activity;
    private Context context;
    private List<Item_main>lists=new ArrayList<>();
    @BindView(R.id.main)
    QMUIPullRefreshLayout mPullRefreshLayout;
    private SwipeRecyclerView swipeRecyclerView;
    private String str;
    private ItemMainAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_rigth, container, false);
        ButterKnife.bind(this,view);
        activity=(MainActivity)getActivity();
        context=getContext();
        Bundle bundle=getArguments();
        str=bundle.getString("flag");
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
        swipeRecyclerView=(SwipeRecyclerView)view.findViewById(R.id.rv);
        /*
        * 初始化
        * 两个的顺序不能够反
        * */
        init_list();
        init_rv();
        init();
        return view;
    }

    /*
    * 初始化list
    * */
    private void init_list(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                * 查询全部
                * */
                String address="";
                HttpURL.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        }).start();

        for(int i=0;i<20;i++){
            Item_main item_main=new Item_main();
            item_main.setStr(""+i);
            lists.add(item_main);
        }
    }

    /*
    * 初始化SwipeRecyclerView
    * */
    private void init_rv(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        adapter=new ItemMainAdapter(lists);
        swipeRecyclerView.setLayoutManager(layoutManager);
        swipeRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                //打开具体显示的界面
/*                FragmentTransaction transaction=MainActivity.fragmentManager.beginTransaction();
                Bundle bundle=new Bundle();
                bundle.putString("number","信息对应的唯一id");
                Fragment fragment=new Fragment_specific();
                fragment.setArguments(bundle);
                transaction.replace(R.id.line_5,fragment);
                transaction.commit();*/
                //activity.show_specific();
                SpecificActivity.startActivity(context,"显示具体信息");
            }
        });
        swipeRecyclerView.setAdapter(adapter);
    }

    /*
    * 活动菜单监听
    * */
    private void init(){
        mPullRefreshLayout.setRefreshOffsetCalculator(new QMUIDefaultRefreshOffsetCalculator());
        mPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {
                Log.d("ttttt","11111111");
            }

            @Override
            public void onMoveRefreshView(int offset) {
                Log.d("ttttt","11111211");
            }

            @Override
            public void onRefresh() {
                mPullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onDataLoaded();
                        Log.d("ttttt","11131111");
                        Toast.makeText(context,"你点击了刷新",Toast.LENGTH_SHORT).show();
                        mPullRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }
        });
    }

    private void onDataLoaded() {
        Collections.shuffle(lists);
        adapter.notifyDataSetChanged();
    }
}
