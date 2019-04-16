package com.example.zexiger.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.classichu.lineseditview.LinesEditView;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.activity.SearchActivity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment_search extends Fragment {
    private SearchActivity activity;
    private Context context;
    @BindView(R.id.topbar)QMUITopBarLayout qmuiTopBarLayout;
    @BindView(R.id.et_add)LinesEditView linesEditView;
    @BindView(R.id.phone)EditText editText;
    @BindView(R.id.line)RelativeLayout relativeLayout_1;
    @BindView(R.id.line_2)RelativeLayout relativeLayout_2;

    private String info="is_null";
    private String type="is_null";
    private String address="is_null";
    private String pic="is_null";
    private String phone="is_null";

    /*
    * 用于选择类型的时候，默认类型
    * */
    int checkedIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search,container,false);
        activity=(SearchActivity)getActivity();
        context=getContext();
        ButterKnife.bind(this,view);
        Bundle bundle=getArguments();
        int n=bundle.getInt("flag");
        init_title(n);
        return view;
    }

    @OnClick(R.id.imagebutton)void back(){
        showMessageNegativeDialog();
        //Toast.makeText(activity, "你想返回！", Toast.LENGTH_SHORT).show();
    }

    private void init_title(int n){
        switch(n){
            case 0:
                qmuiTopBarLayout.setTitle("我捡到了东西");
                break;
            case 1:
                qmuiTopBarLayout.setTitle("我弄丢了东西");
                break;
            case 2:
                qmuiTopBarLayout.setTitle("我捡到了校园卡");
                break;
            default:
                Log.d("ttttt","Fragment_search,出错");
        }
    }

    /*
    * 标记类型
    * */
    @OnClick(R.id.type)void button_type(){
        showSingleChoiceDialog();
    }
    /*
    * 标记地址
    * */
    @OnClick(R.id.address)void button_address(){
        FragmentTransaction transaction=SearchActivity.fragmentManager.beginTransaction();
        Fragment fragment=new Fragment_address();
        transaction.replace(R.id.line_3,fragment);
        transaction.commit();
        relativeLayout_1.setVisibility(View.GONE);
        relativeLayout_2.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.sure)void button_sure(){
        double latitude=activity.getLatitude();
        double longtitude=activity.getLongtitude();
        Log.d("ttttt","latitude:"+latitude+",longtitude"+longtitude);
        relativeLayout_2.setVisibility(View.GONE);
        relativeLayout_1.setVisibility(View.VISIBLE);
    }
    /*
    * 上传照片
    * */
    @OnClick(R.id.pic)void button_pic(){

    }
    /*
    * 发布信息
    * */
    @OnClick(R.id.fabu)void button_fabu(){
        /*
        * 未做检查
        * */
        info=linesEditView.getContentText().toString();
        phone=editText.getText().toString();
    }

    private void showSingleChoiceDialog() {
        final String[] items = new String[]{"其他", "校园卡", "电子产品","衣物"};
        new QMUIDialog.CheckableDialogBuilder(getActivity())
                .setCheckedIndex(checkedIndex)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        type=items[which];
                        checkedIndex=which;
                        Toast.makeText(getActivity(), "你选择了 " + type, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    private void showMessageNegativeDialog() {
        new QMUIDialog.MessageDialogBuilder(getActivity())
                .setTitle("！！！")
                .setMessage("你编辑的东西还没发布，确定要退出？")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "退出", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        Toast.makeText(getActivity(), "成功退出", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }
}
