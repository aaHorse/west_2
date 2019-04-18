package com.example.zexiger.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.http_util.HttpOK;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Cs extends AppCompatActivity {
    @BindView(R.id.image)ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_1)void button_1(){
        //HttpOK.post_file();
    }
    @OnClick(R.id.button_2)void button_2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //HttpOK.getData();
            }
        }).start();
    }
    @OnClick(R.id.button_3)void button_3(){
        Glide.with(Cs.this).load("http://192.168.43.61:8080/img/1.png").into(imageView);
    }
    @OnClick(R.id.button_4)void button_4(){

    }

}
