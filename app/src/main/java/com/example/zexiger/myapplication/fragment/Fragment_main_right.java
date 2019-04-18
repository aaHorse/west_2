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
import okhttp3.Response;

import static com.example.zexiger.myapplication.activity.MainActivity.show_my;
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
                show_my();
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
                bundle_4_2.putString("flag","电子产品");
                fragment_4_2.setArguments(bundle_4_2);
                transaction.replace(R.id.line_3,fragment_4_2);
                transaction.commit();
                break;
            case "其他":
                Fragment fragment_4_3=new Fragment_main_right();
                Bundle bundle_4_3=new Bundle();
                bundle_4_3.putString("flag","电子产品");
                fragment_4_3.setArguments(bundle_4_3);
                transaction.replace(R.id.line_3,fragment_4_3);
                transaction.commit();
                break;
            case "校园卡":
                Fragment fragment_4_4=new Fragment_main_right();
                Bundle bundle_4_4=new Bundle();
                bundle_4_4.putString("flag","电子产品");
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
        init_list(str);
        init_rv();
        init();
        return view;
    }

    /*
    * 初始化list
    * */
    private void init_list(final String str){
/*        for(int i=0;i<20;i++){
            Item_main item_main=new Item_main();
            item_main.setStr(""+i);
            lists.add(item_main);
        }*/

        final String address="http://192.168.43.61:8080/query/list";
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
                                switch(str) {
                                    case "找失主":
                                        for(int i=list_temp.size()-1;i>=0;i--){
                                            Thing.DataBean bean=list_temp.get(i);
                                            if(bean.getIsfound()==1){
                                                lists.add(bean);
                                            }
                                        }
                                        break;
                                    case "找失物":
                                        for(int i=list_temp.size()-1;i>=0;i--){
                                            Thing.DataBean bean=list_temp.get(i);
                                            if(bean.getIsfound()==0){
                                                lists.add(bean);
                                            }
                                        }
                                        break;
                                    case "我的发布":
                                        List<QQ_messege>list_number=DataSupport.findAll(QQ_messege.class);
                                        QQ_messege obj=list_number.get(list_number.size()-1);
                                        for(int i=list_temp.size()-1;i>=0;i--){
                                            Thing.DataBean bean=list_temp.get(i);
                                            if(bean.getName().equals(obj.getNumber())){
                                                lists.add(bean);
                                            }
                                        }
                                        break;
                                    case "电子产品":
                                        for(int i=list_temp.size()-1;i>=0;i--){
                                            Thing.DataBean bean=list_temp.get(i);
                                            if(bean.getType().equals("电子产品")){
                                                lists.add(bean);
                                            }
                                        }
                                        break;
                                    case "衣物":
                                        for(int i=list_temp.size()-1;i>=0;i--){
                                            Thing.DataBean bean=list_temp.get(i);
                                            if(bean.getType().equals("衣物")){
                                                lists.add(bean);
                                            }
                                        }
                                        break;
                                    case "其他":
                                        for(int i=list_temp.size()-1;i>=0;i--){
                                            Thing.DataBean bean=list_temp.get(i);
                                            if(bean.getType().equals("其他")){
                                                lists.add(bean);
                                            }
                                        }
                                        break;
                                    case "校园卡":
                                        for(int i=list_temp.size()-1;i>=0;i--){
                                            Thing.DataBean bean=list_temp.get(i);
                                            if(bean.getType().equals("校园卡")){
                                                lists.add(bean);
                                            }
                                        }
                                        break;
                                    default:
                                        Log.d("ttttt", "碎片的跳转没有匹配");
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
        init_list(str);
        adapter.notifyDataSetChanged();
    }
}
