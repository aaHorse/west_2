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
import com.example.zexiger.myapplication.activity.Search2Activity;
import com.example.zexiger.myapplication.activity.SpecificActivity;
import com.example.zexiger.myapplication.adapter.ItemMainAdapter;
import com.example.zexiger.myapplication.db.QQ_messege;
import com.example.zexiger.myapplication.entity.Item_main;
import com.example.zexiger.myapplication.entity.Thing;
import com.example.zexiger.myapplication.http_util.HttpCallbackListener;
import com.example.zexiger.myapplication.http_util.HttpOK;
import com.example.zexiger.myapplication.http_util.HttpURL;
import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIDefaultRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ezy.ui.layout.LoadingLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.zexiger.myapplication.activity.MainActivity.show_specific;

public class Fragment_main_right_2 extends Fragment {

    private Activity activity;
    private Context context;
    private List<Thing.DataBean>lists=new ArrayList<>();
    @BindView(R.id.main)
    QMUIPullRefreshLayout mPullRefreshLayout;
    private SwipeRecyclerView swipeRecyclerView;

    @BindView(R.id.loading)LoadingLayout loadingLayout;

    /*
     * 请求类型
     * */
    private String str="";
    private ItemMainAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_rigth, container, false);
        ButterKnife.bind(this,view);
        activity=(Search2Activity)getActivity();
        context=getContext();
        str=Search2Activity.json;
        swipeRecyclerView=(SwipeRecyclerView)view.findViewById(R.id.rv);
        /*
         * 初始化
         * 两个的顺序不能够反
         * */
        init_list(str);
        return view;
    }

    private void init_list(final String json){
        final String address="http://47.95.3.253:8080/LostAndFound/api/append/similar";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.post_json(address, json, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("ttttt","查询全部信息访问失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final Thing thing=new Gson().fromJson(response.body().string(),Thing.class);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lists.clear();
                                List<Thing.DataBean>list_temp=thing.getData();
                                if(list_temp!=null){
                                    for(int i=list_temp.size()-1;i>=0;i--){
                                        Thing.DataBean bean=list_temp.get(i);
                                        lists.add(bean);
                                    }
                                    init_rv();
                                    init();
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }

    private void init_list_2(final String json){
        final String address="http://47.95.3.253:8080/LostAndFound/api/append/similar";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.post_json(address,json, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("ttttt","查询全部信息访问失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final Thing thing=new Gson().fromJson(response.body().string(),Thing.class);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lists.clear();
                                List<Thing.DataBean>list_temp=thing.getData();
                                if(list_temp!=null){
                                    for(int i=list_temp.size()-1;i>=0;i--){
                                        Thing.DataBean bean=list_temp.get(i);
                                        lists.add(bean);
                                    }
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }


    /*
     * 初始化SwipeRecyclerView
     * */
    private void init_rv(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        adapter=new ItemMainAdapter(lists);
        if(lists==null){
            loadingLayout.showEmpty();
            return ;
        }
        if (lists.size()==0){
            loadingLayout.showEmpty();
            return ;
        }
        loadingLayout.showContent();
        swipeRecyclerView.setLayoutManager(layoutManager);
        swipeRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                /*
                 * 将物体对应的类传过去
                 * */
                SpecificActivity.startActivity(context,lists.get(position));
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
                        func_reflesh();
                        Log.d("ttttt","11131111");
                        //Toast.makeText(context,"刷新",Toast.LENGTH_SHORT).show();
                        mPullRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }
        });
    }

    /*
     * 刷新
     * */
    public void func_reflesh(){
        lists.clear();
        init_list_2(str);
        adapter.notifyDataSetChanged();
    }

}
