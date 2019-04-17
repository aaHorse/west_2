package com.example.zexiger.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.base.BaseActivity;

public class SpecificActivity extends BaseActivity {
    public static void startActivity(Context context,String str){
        Intent intent=new Intent(context,SpecificActivity.class);
        intent.putExtra("flag",str);
        context.startActivity(intent);
    }

    public static FragmentManager fragmentManager;
    public static Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);
        fragmentManager=getSupportFragmentManager();
        bundle=savedInstanceState;
        Intent intent=getIntent();
        String flag=intent.getStringExtra("flag");
        Toast.makeText(this,flag,Toast.LENGTH_SHORT).show();
    }
}
