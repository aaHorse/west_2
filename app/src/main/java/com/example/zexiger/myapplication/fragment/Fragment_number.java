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
import android.widget.EditText;
import android.widget.Toast;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.activity.MainActivity;
import com.example.zexiger.myapplication.db.FlagFirst;
import com.example.zexiger.myapplication.db.QQ_messege;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.zexiger.myapplication.fragment.QQLogin.figureurl_qq_1;

public class Fragment_number extends Fragment {
    private Activity activity;
    private Context context;

    @BindView(R.id.number)EditText editText;

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

    @OnClick(R.id.button)void button(){
        String number=editText.getText().toString();
        if (number.isEmpty()){
            Toast.makeText(context,"输入不能为空",Toast.LENGTH_SHORT).show();
        }else{
            QQ_messege obj=new QQ_messege();
            obj.setNickname(QQLogin.nickname);
            obj.setFigureurl_qq_1(QQLogin.figureurl_qq_1);
            obj.setNumber(number);
            obj.save();

            FlagFirst flag_first=new FlagFirst();
            flag_first.setStr("已经登录过了");
            flag_first.save();

            MainActivity.startActivity(context);
            activity.finish();
        }
    }

}
