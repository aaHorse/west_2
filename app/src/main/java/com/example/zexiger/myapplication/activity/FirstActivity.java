package com.example.zexiger.myapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.base.BaseActivity;
import com.example.zexiger.myapplication.db.FlagFirst;
import com.example.zexiger.myapplication.fragment.Fragment_cs;
import com.example.zexiger.myapplication.fragment.Fragment_number;
import com.example.zexiger.myapplication.fragment.QQLogin;
import com.example.zexiger.myapplication.http_util.HttpCallbackListener;
import com.example.zexiger.myapplication.http_util.HttpURL;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.zexiger.myapplication.fragment.QQLogin.mIUiListener;

public class FirstActivity extends BaseActivity {

    @BindView(R.id.frame_layout)FrameLayout frameLayout;
    @BindView(R.id.line)FrameLayout frameLayout_2;
    private Fragment fragment;
    private static FragmentManager fragmentManager;
    private static LinearLayout linearLayout_4;
    private static LinearLayout linearLayout_5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
        fragmentManager= getSupportFragmentManager();
        linearLayout_4=findViewById(R.id.line_4);
        linearLayout_5=findViewById(R.id.line_5);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<FlagFirst> lists=DataSupport.findAll(FlagFirst.class);
                if(lists.size()!=0){
                    FlagFirst flag_first=(FlagFirst)lists.get(lists.size()-1);
                    if(flag_first.getStr().equals("已经登录过了")){
                        /* 如果已经登录过了，就直接跳转，不用再调用QQ进行登录*/

                        MainActivity.startActivity(FirstActivity.this);
                        finish();
                    }else{
                        frameLayout_2.setVisibility(View.VISIBLE);
                    }
                }else{
                    frameLayout_2.setVisibility(View.VISIBLE);
                }
            }
        });

        fragment=(QQLogin)getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ttttt","在result中");
        if(requestCode == Constants.REQUEST_LOGIN){
            Log.d("ttttt","在result中");
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

  /*  * 用输入学号界面替换掉QQ点击登录界面
    * */

    public static void replace_QQ(){
        linearLayout_4.setVisibility(View.GONE);
        linearLayout_5.setVisibility(View.VISIBLE);
    }
}

