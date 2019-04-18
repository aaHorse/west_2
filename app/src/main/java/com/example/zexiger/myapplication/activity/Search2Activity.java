package com.example.zexiger.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.base.BaseActivity;
import com.example.zexiger.myapplication.fragment.Fragment_cs;
import com.example.zexiger.myapplication.fragment.Fragment_main_right;

public class Search2Activity extends BaseActivity {
    public static void startActivity(Context context,String type){
        Intent intent=new Intent(context,Search2Activity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        Intent intent=getIntent();
        String type=intent.getStringExtra("type");
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment fragment=new Fragment_main_right();
        Bundle bundle=new Bundle();
        bundle.putString("flag",type);
        fragment.setArguments(bundle);
        transaction.replace(R.id.button_4,fragment);
    }
}
