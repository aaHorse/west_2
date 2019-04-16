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
import com.example.zexiger.myapplication.fragment.Fragment_search;

public class SearchActivity extends AppCompatActivity {
    public static void startActivity(Context context,int n){
        Intent intent=new Intent(context,SearchActivity.class);
        intent.putExtra("flag",n);
        context.startActivity(intent);
    }

    public static FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    /*
     * 给碎片提供Bundle
     * */
    public static Bundle bundle;

    /*
    * 经纬度
    * */
    private static double latitude;
    private static double longtitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        fragmentManager=getSupportFragmentManager();
        bundle=savedInstanceState;

        Intent intent=getIntent();
        int n=intent.getIntExtra("flag",-1);
        switch (n){
            case 0:
                init_fragment(0);
                break;
            case 1:
                init_fragment(1);
                break;
            case 2:
                init_fragment(2);
                break;
            default:
                Log.d("ttttt","SearchActivity出错");
        }
    }

    private void init_fragment(int n){
        Fragment fragment=new Fragment_search();
        Bundle bundle=new Bundle();
        bundle.putInt("flag",n);
        fragment.setArguments(bundle);
        transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment,fragment);
        transaction.commit();
    }

    public static void setLocaltion(double a,double b){
        latitude=a;
        longtitude=b;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static double getLongtitude() {
        return longtitude;
    }
}
