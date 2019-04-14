package com.example.zexiger.myapplication.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.adapter.ItemMainAdapter;
import com.example.zexiger.myapplication.entity.Item_main;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIDefaultRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_main_right extends Fragment {
    private Activity activity;
    private Context context;
    private List<Item_main>lists=new ArrayList<>();
    @BindView(R.id.main)
    QMUIPullRefreshLayout mPullRefreshLayout;
    private SwipeRecyclerView swipeRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_rigth, container, false);
        ButterKnife.bind(this,view);
        activity=getActivity();
        context=getContext();
        swipeRecyclerView=(SwipeRecyclerView)view.findViewById(R.id.rv);
        /*
        * 初始化
        * 两个的顺序不能够反
        * */
        init_list();
        init_rv();
        return view;
    }

    /*
    * 初始化list
    * */
    private void init_list(){
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
        ItemMainAdapter adapter=new ItemMainAdapter(lists);
        swipeRecyclerView.setLayoutManager(layoutManager);
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

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                mPullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //onDataLoaded();
                        mPullRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }
        });
    }
}
