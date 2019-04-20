package com.example.zexiger.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.base.BaseActivity;
import com.example.zexiger.myapplication.fragment.Fragment_cs;
import com.example.zexiger.myapplication.fragment.Fragment_main_right;
import com.example.zexiger.myapplication.fragment.Fragment_main_right_2;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class Search2Activity extends BaseActivity {
    public static void startActivity(Context context,String json){
        Intent intent=new Intent(context,Search2Activity.class);
        intent.putExtra("json",json);
        context.startActivity(intent);
    }

    public static String json="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        setStatusBar();
        json=intent.getStringExtra("json");
        Log.d("ttttt","json为"+json);

        /*
        * 调到Fragment_main_right碎片，但是跳过前面的startFreament,
        * 直接在onCreate里面调用
        * */
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment fragment=new Fragment_main_right_2();
        /*
        *
        * */
        transaction.replace(R.id.line_4,fragment);
        transaction.commit();
    }

    /*
     *返回
     * */
    @OnClick(R.id.top_bar_icon)void button_back(){
        MainActivity.startActivity(this);
    }
    @Override
    public void onBackPressed() {
        MainActivity.startActivity(this);
        super.onBackPressed();
    }
}
