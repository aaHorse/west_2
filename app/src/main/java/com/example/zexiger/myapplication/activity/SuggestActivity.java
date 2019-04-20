package com.example.zexiger.myapplication.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.base.BaseActivity;
import com.example.zexiger.myapplication.base.MyApplication;
import com.example.zexiger.myapplication.fragment.Fragment_suggest;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.qmuiteam.qmui.qqface.QMUIQQFaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuggestActivity extends BaseActivity {
    @BindView(R.id.qqface) QMUIQQFaceView mQQFace;
    @BindView(R.id.line_suggest)LinearLayout linearLayout;
    @BindView(R.id.line_qq)LinearLayout linearLayout_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        ButterKnife.bind(this);
        setStatusBar();
        mQQFace.setText("12345678的文本，但是最多只能显示三行；" +
                "这是一段很长很长的文本，但是最多只能显示三行；" +
                        "哈哈哈哈哈哈哈哈哈哈哈哈哈哈或哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或"+
                "哈哈哈哈哈哈哈哈哈哈哈哈哈哈或哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或"+
                "哈哈哈哈哈哈哈哈哈哈哈哈哈哈或哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或"+
                 "哈哈哈哈哈哈哈哈哈哈哈哈哈哈或哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或"+
                "哈哈哈哈哈哈哈哈哈哈哈哈哈哈或哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或"+
                 "哈哈哈哈哈哈哈哈哈哈哈哈哈哈或哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或"+
                "哈哈哈哈哈哈哈哈哈哈哈哈哈哈或哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或"+
                "哈哈哈哈哈哈哈哈哈哈哈哈哈哈或哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或"+
                "哈哈哈哈哈哈哈哈哈哈哈哈哈哈或哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或"+
                "哈哈哈哈哈哈哈哈哈哈哈哈哈哈或哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈或或"
        );


        linearLayout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                mQQFace.setVisibility(View.GONE);
            }
        });
    }

    /*
     *返回
     * */
    @OnClick(R.id.top_bar_icon)void button_back(){
        Intent intent=new Intent(SuggestActivity.this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(SuggestActivity.this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
