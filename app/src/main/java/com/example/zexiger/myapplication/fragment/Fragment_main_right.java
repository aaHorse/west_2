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

public class Fragment_main_right extends Fragment {
    /*
    * 参数str用于识别是触发哪一个跳过来的
    * ,
    * 注意，有一个不是通过这里跳过来的，而是直接在自己的活动里面跳，Search2Activity
    * */
    public static void startFragment(String str){
        FragmentTransaction transaction=MainActivity.fragmentManager.beginTransaction();
        switch(str){
            case "查全部":
                Fragment fragment_4_5=new Fragment_main_right();
                Bundle bundle_4_5=new Bundle();
                bundle_4_5.putString("flag","查全部");
                fragment_4_5.setArguments(bundle_4_5);
                transaction.replace(R.id.line_3,fragment_4_5);
                transaction.commit();
                break;
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
            case "我的发布":
                Fragment fragment_3=new Fragment_main_right();
                Bundle bundle_3=new Bundle();
                bundle_3.putString("flag","我的发布");
                fragment_3.setArguments(bundle_3);
                transaction.replace(R.id.line_3,fragment_3);
                transaction.commit();
                break;
            case "电子产品":
                Fragment fragment_4_1=new Fragment_main_right();
                Bundle bundle_4_1=new Bundle();
                bundle_4_1.putString("flag","电子产品");
                fragment_4_1.setArguments(bundle_4_1);
                transaction.replace(R.id.line_3,fragment_4_1);
                transaction.commit();
                break;
            case "衣物":
                Fragment fragment_4_2=new Fragment_main_right();
                Bundle bundle_4_2=new Bundle();
                bundle_4_2.putString("flag","衣物");
                fragment_4_2.setArguments(bundle_4_2);
                transaction.replace(R.id.line_3,fragment_4_2);
                transaction.commit();
                break;
            case "其他":
                Fragment fragment_4_3=new Fragment_main_right();
                Bundle bundle_4_3=new Bundle();
                bundle_4_3.putString("flag","其他");
                fragment_4_3.setArguments(bundle_4_3);
                transaction.replace(R.id.line_3,fragment_4_3);
                transaction.commit();
                break;
            case "校园卡":
                Fragment fragment_4_4=new Fragment_main_right();
                Bundle bundle_4_4=new Bundle();
                bundle_4_4.putString("flag","校园卡");
                fragment_4_4.setArguments(bundle_4_4);
                transaction.replace(R.id.line_3,fragment_4_4);
                transaction.commit();
                break;
                default:
                    Log.d("ttttt","碎片的跳转没有匹配");
        }
    }


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
        activity=(MainActivity)getActivity();
        context=getContext();
        Bundle bundle=getArguments();
        str=bundle.getString("flag");
        swipeRecyclerView=(SwipeRecyclerView)view.findViewById(R.id.rv);
        /*
        * 初始化
        * 两个的顺序不能够反
        * */
        init_list(str);
        return view;
    }

    /*
    * 下面这一个和上面一个可以实现相同的功能
    * */
    private void init_list(final String str){
        switch(str){
            case "查全部":
                func_8();
                break;
            case "找失主":
                func_1();
                break;
            case "找失物":
                func_2();
                break;
            case "我的发布":
                func_3();
                break;
            case "电子产品":
                func_4();
                break;
            case "衣物":
                func_5();
                break;
            case "其他":
                func_6();
                break;
            case "校园卡":
                func_7();
                break;
            default:
                Log.d("ttttt","相似度匹配");
        }

    }

    /*
    * 分别对应上面的功能
    * */
    private void func_1(){

        //final String address="http://192.168.43.61:8080/query/list";
        final String address="http://47.95.3.253:8080/LostAndFound/query/list";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
                                        if(bean.getIsfound()==1){
                                            lists.add(bean);
                                        }
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
    private void func_8(){
        //final String address="http://192.168.43.61:8080/query/list";
        final String address="http://47.95.3.253:8080/LostAndFound/query/list";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
    private void func_2(){
        final String address="http://47.95.3.253:8080/LostAndFound/query/list";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
                                        if(bean.getIsfound()==0){
                                            lists.add(bean);
                                        }
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
    private void func_3(){
        List<QQ_messege>list_qq=DataSupport.findAll(QQ_messege.class);
        QQ_messege obj=list_qq.get(list_qq.size()-1);
        String number=obj.getNumber();
        final String address="http://47.95.3.253:8080/LostAndFound/query/list/name/"+number;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("ttttt","查询用户上传信息访问失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final Thing thing=new Gson().fromJson(response.body().string(),Thing.class);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lists.clear();
                                List<Thing.DataBean>list_temp=thing.getData();
                                if (list_temp!=null){
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
    private void func_4(){
        final String address="http://47.95.3.253:8080/LostAndFound/query/list/电子产品";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
    private void func_5(){
        final String address="http://47.95.3.253:8080/LostAndFound/query/list/衣物";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
    private void func_6(){
        final String address="http://47.95.3.253:8080/LostAndFound/query/list/其他";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
    private void func_7(){
        final String address="http://47.95.3.253:8080/LostAndFound/query/list/校园卡";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
        switch(str) {
            case "查全部":
                func_81();
                break;
            case "找失主":
                func_11();
                break;
            case "找失物":
                func_21();
                break;
            case "我的发布":
                func_31();
                break;
            case "电子产品":
                func_41();
                break;
            case "衣物":
                func_51();
                break;
            case "其他":
                func_61();
                break;
            case "校园卡":
                func_71();
                break;
            default:
                Log.d("ttttt", "匹配");
        }
        adapter.notifyDataSetChanged();
    }

    /*
    * 刷新调用的函数
    * */
    private void func_81(){
        final String address="http://47.95.3.253:8080/LostAndFound/query/list";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }
    private void func_11(){
        final String address="http://47.95.3.253:8080/LostAndFound/query/list";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
                                        if(bean.getIsfound()==1){
                                            lists.add(bean);
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }
    private void func_21(){
        final String address="http://47.95.3.253:8080/LostAndFound/query/list";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
                                        if(bean.getIsfound()==0){
                                            lists.add(bean);
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }
    private void func_31(){
        List<QQ_messege>list_qq=DataSupport.findAll(QQ_messege.class);
        QQ_messege obj=list_qq.get(list_qq.size()-1);
        String number=obj.getNumber();
        final String address="http://47.95.3.253:8080/LostAndFound/query/list/name/"+number;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("ttttt","查询用户上传信息访问失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final Thing thing=new Gson().fromJson(response.body().string(),Thing.class);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lists.clear();
                                List<Thing.DataBean>list_temp=thing.getData();
                                if (list_temp!=null){
                                    for(int i=list_temp.size()-1;i>=0;i--){
                                        Thing.DataBean bean=list_temp.get(i);
                                        if(bean.getIsfound()==1){
                                            lists.add(bean);
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }
    private void func_41(){
        final String address="http://47.95.3.253:8080/LostAndFound/query/list/电子产品";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }
    private void func_51(){
        final String address="http://47.95.3.253:8080/LostAndFound/query/list/衣物";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }
    private void func_61(){
        final String address="http://47.95.3.253:8080/LostAndFound/query/list/其他";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }
    private void func_71(){
        final String address="http://47.95.3.253:8080/LostAndFound/query/list/校园卡";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpOK.getData(address, new okhttp3.Callback() {
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
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }
}
