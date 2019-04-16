package com.example.zexiger.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zexiger.myapplication.R;

import butterknife.ButterKnife;

public class Fragment_number extends Fragment {
    private Activity activity;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_number,container,false);
        Log.d("ttttt","进入了Fragment_number界面");
        activity=getActivity();
        context=getContext();
        ButterKnife.bind(this,view);
        return view;
    }


}
