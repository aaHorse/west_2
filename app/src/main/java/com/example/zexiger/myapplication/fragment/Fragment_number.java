package com.example.zexiger.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.activity.MainActivity;
import com.example.zexiger.myapplication.base.ActivityCollector;
import com.example.zexiger.myapplication.base.MyApplication;
import com.example.zexiger.myapplication.db.FlagFirst;
import com.example.zexiger.myapplication.db.QQ_messege;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

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
        }else if(!isNumericZidai(number)){
            Toast.makeText(context,"输入错误",Toast.LENGTH_SHORT).show();
        }else if(number.length()!=9){
            Toast.makeText(context,"该软件仅允许福州大学的学生使用",Toast.LENGTH_SHORT).show();
        } else{
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

    public static boolean isNumericZidai(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    showMessageNegativeDialog();
                    return true;
                }
                return false;
            }
        });
    }

    private void showMessageNegativeDialog() {
        new QMUIDialog.MessageDialogBuilder(getActivity())
                .setTitle("注意")
                .setMessage("是否执行取消登录？")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        /*
                         * 注销QQ的第三方登录
                         * */
                        QQLogin.mTencent.logout(MyApplication.getContext());
                        ActivityCollector.finishAll();
                        Toast.makeText(getActivity(), "登录取消", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }
}
