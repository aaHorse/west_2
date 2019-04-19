package com.example.zexiger.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.base.BaseActivity;
import com.qmuiteam.qmui.qqface.QMUIQQFaceView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuggestActivity extends BaseActivity {
    @BindView(R.id.qqface) QMUIQQFaceView mQQFace;

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
    }
}
