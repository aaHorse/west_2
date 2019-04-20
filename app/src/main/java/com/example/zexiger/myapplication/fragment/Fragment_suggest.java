package com.example.zexiger.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zexiger.myapplication.R;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class Fragment_suggest extends Fragment {
    private Activity activity;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_suggest,container,false);
        ButterKnife.bind(this,view);
        activity=getActivity();
        context=getContext();
        return view;
    }

    @OnClick(R.id.send)void button_send(){
        /*
        * 点击了发送
        * */
        final QMUITipDialog tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord("发送成功")
                .create();
        tipDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(new Date());
                    try {
                        Thread.sleep(3000);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tipDialog.cancel();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
