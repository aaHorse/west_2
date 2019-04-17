package com.example.zexiger.myapplication.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.activity.SearchActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_cs extends Fragment {
    @BindView(R.id.image)ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cs,container,false);
        ButterKnife.bind(this,view);
        Log.d("ttttt","进入了测试界面");
        imageView.setImageBitmap(SearchActivity.bit);
        return view;
    }
}
