package com.example.zexiger.myapplication.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.classichu.lineseditview.LinesEditView;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.activity.MainActivity;
import com.example.zexiger.myapplication.activity.Search2Activity;
import com.example.zexiger.myapplication.activity.SearchActivity;
import com.example.zexiger.myapplication.db.QQ_messege;
import com.example.zexiger.myapplication.entity.Data;
import com.example.zexiger.myapplication.entity.Thing;
import com.example.zexiger.myapplication.http_util.HttpOK;
import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class Fragment_search extends Fragment {
    private SearchActivity activity;
    private Context context;
    @BindView(R.id.top_bar_title)TextView qmuiTopBarLayout;
    @BindView(R.id.et_add)LinesEditView linesEditView;
    @BindView(R.id.phone)EditText editText;
    @BindView(R.id.line)RelativeLayout relativeLayout_1;
    @BindView(R.id.line_2)RelativeLayout relativeLayout_2;
    @BindView(R.id.bar_layout)ViewGroup relativeLayout_3;

    private String info="0";
    private String type="其他";
    private String address="0";
    private String pic="0";
    private String phone="0";
    private String date="0";

    double latitude=26.0557538219;
    double longtitude=119.1974286674;

    /*
    * 标记从哪里过来的
    * */
    int n=-1;

    String date_1;

    /*
    * 用于选择类型的时候，默认类型
    * */
    int checkedIndex = 0;
    /*
    * 标记用户是否上传了图片
    * */
    int picture_flag=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search,container,false);
        activity=(SearchActivity)getActivity();
        context=getContext();
        ButterKnife.bind(this,view);
        setStatusBar();
        Bundle bundle=getArguments();
        /*
        * 0:捡到东西
        * 1：弄丢东西
        * 2：捡到校园卡
        * */
        n=bundle.getInt("flag");
        init_title(n);
        /*
        * 做一个时间戳
        * */
        Date now=new Date();
        date_1=getStringDate_1(now);
        date=getStringDate(now);
        return view;
    }

    @OnClick(R.id.top_bar_icon)void back(){
        showMessageNegativeDialog();
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

    private void init_title(int n){
        switch(n){
            case 0:
                qmuiTopBarLayout.setText("我捡到了东西");
                break;
            case 1:
                qmuiTopBarLayout.setText("我弄丢了东西");
                break;
            case 2:
                qmuiTopBarLayout.setText("我捡到了校园卡");
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
    /*
    * 标记地址确认按钮
    * */
    @OnClick(R.id.sure)void button_sure(){
        latitude=activity.getLatitude();
        longtitude=activity.getLongtitude();
        Log.d("ttttt","latitude:"+latitude+",longtitude"+longtitude);
        relativeLayout_2.setVisibility(View.GONE);
        relativeLayout_1.setVisibility(View.VISIBLE);
    }
    /*
    * 上传照片
    * */
    @OnClick(R.id.pic)void button_pic(){
        activity.get_pic();
        picture_flag=1;
        /*
        * 进行上传图片操作
        * */
        pic=date_1+".jpg";
    }
    /*
    * 发布信息
    * */
    @OnClick(R.id.fabu)void button_fabu(){
        Log.d("ttttt","点击了发布按钮");
        /*
        * 未做检查
        * */
        info=linesEditView.getContentText().toString();
        //type
        address=latitude+","+longtitude;
        //pic
        phone=editText.getText().toString();
        if(info.isEmpty()){
            showLongMessageDialog();
        }else{
            Gson gson=new Gson();
            final Data data=new Data();
            data.setInfo(info);
            data.setType(type);
            data.setAddress(address);
            data.setDate(date);
            data.setImage("null");
            /*
            * 未找到
            * */
            data.setIsfound(0);
            if(n==0||n==2){
                /*
                * 捡到
                * */
                data.setIsexist(1);
            }else if(n==1){
                /*
                 * 丢了
                 * */
                data.setIsexist(0);
            }else{
                Log.d("ttttt","Fragment——search标记出错");
            }
            //
            if (phone.isEmpty()){
                data.setPhone("0");
            }else{
                data.setPhone(phone);
            }
            //
            List<QQ_messege>qq_messegeList=DataSupport.findAll(QQ_messege.class);
            QQ_messege obj=qq_messegeList.get(qq_messegeList.size()-1);
            String nickname=obj.getNickname();
            String figureurl_qq_1=obj.getFigureurl_qq_1();
            String number=obj.getNumber();
            data.setQq_name(nickname);
            data.setQq_image(figureurl_qq_1);
            data.setName(number);
            //
            if(n==0||n==2){
                data.setIsfound(1);
            }else if(n==1){
                data.setIsfound(0);
            }else{
                Log.d("ttttt","Fragment_search匹配出错了");
            }
            //
            data.setIsexist(0);


            final String json=gson.toJson(data);
            /*
            * 给服务器发送提交
            * */
            Log.d("ttttt",json);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String address="http://192.168.43.61:8080/api/append";
                    //提交
                    HttpOK.post_json(address,json,new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("ttttt","请求失败");
                        }
                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   try{
                                       Search2Activity.startActivity(context,type);
                                   }catch (Exception e){
                                       e.printStackTrace();
                                       Log.d("ttttt","Fragment_search跳转到Search2Activity出错");
                                   }
                                }
                            });
                        }
                    });
                }
            }).start();

            /*
            * 将图片提交到服务器
            * */
            if(picture_flag==1){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(Environment.getExternalStorageDirectory()+"//liliyuan//temp.jpg");
                        String address="http://192.168.43.61:8080/upload/setFileUpload";
                        HttpOK.post_file(address,file,date, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("ttttt","上传图片失败");
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Log.d("ttttt","上传图片成功");
                            }
                        });
                    }
                }).start();
            }
        }
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
                .setTitle("注意")
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
                        MainActivity.startActivity(context);
                        Toast.makeText(getActivity(), "成功退出", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    private void showLongMessageDialog() {
        new QMUIDialog.MessageDialogBuilder(getActivity())
                .setTitle("无法发布")
                .setMessage("发布的东西必须进行必要的描述")
                .addAction("我知道了", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    public String getStringDate(Date now){
        SimpleDateFormat sdfd =new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String str=sdfd.format(now);
        return str;
    }

    public String getStringDate_1(Date now){
        SimpleDateFormat sdfd =new SimpleDateFormat("yyy_MM_dd_HH_mm_ss");
        String str=sdfd.format(now);
        return str;
    }

    /**
     * 设置沉浸式状态栏
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final int statusHeight = getStatusBarHeight();
            relativeLayout_3.post(new Runnable() {
                @Override
                public void run() {
                    int titleHeight = relativeLayout_3.getHeight();
                    android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) relativeLayout_3.getLayoutParams();
                    params.height = statusHeight + titleHeight;
                    relativeLayout_3.setLayoutParams(params);
                }
            });
        }
    }
    /**
     * 获取状态栏的高度
     * @return
     */
    protected int getStatusBarHeight(){
        try
        {
            Class<?> c=Class.forName("com.android.internal.R$dimen");
            Object obj=c.newInstance();
            Field field=c.getField("status_bar_height");
            int x=Integer.parseInt(field.get(obj).toString());
            return  getResources().getDimensionPixelSize(x);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

}
