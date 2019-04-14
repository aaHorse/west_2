package com.example.zexiger.myapplication.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.base.BaseActivity;
import com.example.zexiger.myapplication.db.FlagFirst;
import com.example.zexiger.myapplication.fragment.QQLogin;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.zexiger.myapplication.fragment.QQLogin.mIUiListener;

public class FirstActivity extends BaseActivity {

    @BindView(R.id.frame_layout)FrameLayout frameLayout;
    @BindView(R.id.line)FrameLayout frameLayout_2;
    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<FlagFirst> lists=DataSupport.findAll(FlagFirst.class);
                if(lists.size()!=0){
                    FlagFirst flag_first=(FlagFirst)lists.get(lists.size()-1);
                    if(flag_first.getStr().equals("已经登录过了")){
                        /*
                         * 如果已经登录过了，就直接跳转，不用再调用QQ进行登录
                         * */
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
}
