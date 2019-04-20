package com.example.zexiger.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.base.BaseActivity;
import com.example.zexiger.myapplication.entity.Thing;
import com.example.zexiger.myapplication.fragment.Fragment_specific;
import com.example.zexiger.myapplication.http_util.HttpOK;

import java.io.IOException;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SpecificActivity extends BaseActivity {
    public static void startActivity(Context context, Thing.DataBean bean){
        Intent intent=new Intent(context,SpecificActivity.class);
        intent.putExtra("flag",bean);
        context.startActivity(intent);
    }

    public static FragmentManager fragmentManager;
    public static Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);
        ButterKnife.bind(this);

        fragmentManager=getSupportFragmentManager();
        Intent intent=getIntent();
        Thing.DataBean flag= (Thing.DataBean) intent.getSerializableExtra("flag");
        Bundle bundle=new Bundle();
        bundle.putSerializable("flag",flag);
        Fragment fragment=new Fragment_specific();
        fragment.setArguments(bundle);
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.line,fragment);
        transaction.commit();
        //Toast.makeText(this,"对象传过来了",Toast.LENGTH_SHORT).show();
    }
}
