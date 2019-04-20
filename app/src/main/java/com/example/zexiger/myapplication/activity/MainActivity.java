package com.example.zexiger.myapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.adapter.LeftItemAdapter;
import com.example.zexiger.myapplication.base.ActivityCollector;
import com.example.zexiger.myapplication.base.BaseActivity;
import com.example.zexiger.myapplication.base.DefineView;
import com.example.zexiger.myapplication.base.MyApplication;
import com.example.zexiger.myapplication.db.FlagFirst;
import com.example.zexiger.myapplication.db.QQ_messege;
import com.example.zexiger.myapplication.entity.LeftItemMenu;
import com.example.zexiger.myapplication.fragment.Fragment_main_right;
import com.example.zexiger.myapplication.fragment.Fragment_sousuo;
import com.example.zexiger.myapplication.fragment.QQLogin;
import com.example.zexiger.myapplication.widget.DragLayout;
import com.nineoldandroids.view.ViewHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.litepal.crud.DataSupport;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/*
 * 活动的主类
 * */
public class MainActivity extends BaseActivity implements DefineView {
    public static void startActivity(Context context){
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    /*
     * 相当于DrawerLayout
     * */
    private DragLayout drag_layout;
    /*
     * 上面点击跳出侧滑菜单的图标
     * */
    private ImageView top_bar_icon;
    /*
     * 侧滑菜单的列表
     * */
    private SwipeRecyclerView swipeRecyclerView;
    private List<LeftItemMenu> lists_left=new ArrayList<>();
    /*
    * 侧滑菜单的头像、名称、学号
    * */
    @BindView(R.id.iv_bottom)ImageView imageView;
    @BindView(R.id.name)TextView name;
    @BindView(R.id.number)TextView number;
    private static Button button_2;
    private static Button button_3;
    private static Button button_4;

    /*
    * 全局的学号、QQ头像、昵称
    * */
    public String number_public="";
    public String figureurl_qq_1_public="";
    public String nickname_public="";

    /*
    * 给碎片提供Bundle
    * */
    public static Bundle bundle;
    /*
    * 动态添加碎片
    * */
    public static FragmentManager fragmentManager;
    FragmentTransaction transaction;
    /*
    * 信息的两种不同显示界面
    * */
    private static LinearLayout linearLayout_brife;
    private static LinearLayout linearLayout_specific;

    private static RelativeLayout linearLayout_my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        linearLayout_brife=(LinearLayout)findViewById(R.id.line_4);
        linearLayout_specific=(LinearLayout)findViewById(R.id.line_5);
        linearLayout_my=(RelativeLayout)findViewById(R.id.line_my);
        button_2=findViewById(R.id.button_2);
        button_3=findViewById(R.id.button_3);
        button_4=findViewById(R.id.button_4);
        fragmentManager=getSupportFragmentManager();
        bundle=savedInstanceState;
        Log.d("ttttt",sHA1(MainActivity.this));
        setStatusBar();
        initView();
        initValidata();
        initListener();
        bindData();

    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result= hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DragLayout getDrag_layout() {
        return drag_layout;
    }

    public void initView() {
        drag_layout = (DragLayout) findViewById(R.id.drag_layout);
        top_bar_icon = (ImageView) findViewById(R.id.top_bar_icon);
        swipeRecyclerView=(SwipeRecyclerView) findViewById(R.id.lv_left_main);
    }
    @Override
    public void initValidata() {
        //初始化左边的列表
        init_left();

        List<QQ_messege> lists=DataSupport.findAll(QQ_messege.class);
        if (lists.size()!=0){
            /*
            * 取最后一个添加进来的，之所以没有清除数据，我是想保留所有在这一个手机登录的登录者的头像和昵称
            * */
            QQ_messege obj=(QQ_messege)lists.get(lists.size()-1);
            number_public=obj.getNumber();
            figureurl_qq_1_public=obj.getFigureurl_qq_1();
            nickname_public=obj.getNickname();
            name.setText(nickname_public);
            number.setText("学号："+obj.getNumber());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(MainActivity.this).load(figureurl_qq_1_public).into(imageView);
                }
            });
        }else{
            Toast.makeText(MainActivity.this,"查询QQ数据库为空",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initListener() {
        /*
        * 滑动
        * */
        drag_layout.setDragListener(new CustomDragListener());
        /*
        * 点击
        * */
        top_bar_icon.setOnClickListener(new CustomOnClickListener());
    }
    @Override
    public void bindData() {
        Fragment_main_right.startFragment("查全部");
    }

    /*
     * 通过继承接口，
     * 可以重写打开关闭的点击事件，也可以不写，直接调用，用父类里面的东西
     * */
    class CustomDragListener implements DragLayout.DragListener{
        @Override
        public void onOpen() {

        }

        @Override
        public void onClose() {

        }

        /**
         * 界面进行滑动
         * @param percent
         */
        @Override
        public void onDrag(float percent) {
            ViewHelper.setAlpha(top_bar_icon,1-percent);
        }
    }

    private void init_left(){
        lists_left.add(new LeftItemMenu(R.drawable.aaa,"账号信息"));
        lists_left.add(new LeftItemMenu(R.drawable.aaa_wode,"我的发布"));
        lists_left.add(new LeftItemMenu(R.drawable.aaa_yijian,"意见反馈"));
        lists_left.add(new LeftItemMenu(R.drawable.aaa_guanyu,"关于我们"));
        lists_left.add(new LeftItemMenu(R.drawable.aaa_tuichu,"退出登录"));
        LeftItemAdapter adapter=new LeftItemAdapter(lists_left);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        swipeRecyclerView.setLayoutManager(layoutManager);
        swipeRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                switch (position){
                    case 0:
                        drag_layout.close();
                        showLongMessageDialog();
                        break;
                    case 1:
                        drag_layout.close();
                        show_hei();
                        Fragment_main_right.startFragment("我的发布");
                        break;
                    case 2:
                        Intent intent=new Intent(MainActivity.this,SuggestActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        Intent intent2=new Intent(MainActivity.this,AboutActivity.class);
                        startActivity(intent2);
                        break;
                    case 4:
                        drag_layout.close();
                        showMessageNegativeDialog();

/*                        Intent intent3=new Intent(MainActivity.this,Cs.class);
                        startActivity(intent3);*/
                        break;
                    default:
                        Log.d("ttttt","左边的item无法匹配");
                }
            }
        });
        /*
        * setAdapter要放在后面，不然会报错
        * */
        swipeRecyclerView.setAdapter(adapter);
    }

    /*
    * 点击，打开
    * */
    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View arg0) {
            drag_layout.open();
        }
    }

    @OnClick(R.id.button_2)void button_1(){
        button_2.setBackgroundColor(Color.parseColor("#00A8bb"));
        button_3.setBackgroundColor(Color.parseColor("#030000"));
        button_4.setBackgroundColor(Color.parseColor("#030000"));
        Fragment_main_right.startFragment("找失主");
    }
    @OnClick(R.id.button_3)void button_2(){
        button_2.setBackgroundColor(Color.parseColor("#030000"));
        button_3.setBackgroundColor(Color.parseColor("#00A8bb"));
        button_4.setBackgroundColor(Color.parseColor("#030000"));
        Fragment_main_right.startFragment("找失物");
    }
    @OnClick(R.id.button_4)void button_3(){
        button_4.setBackgroundColor(Color.parseColor("#00A8bb"));
        button_2.setBackgroundColor(Color.parseColor("#030000"));
        button_3.setBackgroundColor(Color.parseColor("#030000"));
        Fragment_main_right.startFragment("查全部");
    }

    /*
    * 悬浮按钮
    * */
    @OnClick(R.id.fab)void fab(){
        //Toast.makeText(MainActivity.this,"点击了悬浮按钮",Toast.LENGTH_SHORT).show();
        showMenuDialog();
    }
    /*
    * 搜索按钮
    * */
    @OnClick(R.id.top_bar_search_btn)void button_sousuo(){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment fragment=new Fragment_sousuo();
        transaction.replace(R.id.line_5,fragment);
        transaction.commit();
        show_specific();
    }

    private void showMenuDialog() {
        int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
        final String[] items = new String[]{"我捡到了东西", "我弄丢了东西", "我捡到了校园卡"};
        new QMUIDialog.MenuDialogBuilder(MainActivity.this)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "你选择了 " + items[which], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        SearchActivity.startActivity(MainActivity.this,which);
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    /*
    * 切换，简略和具体显示信心两个碎片,还有搜索的碎片界面
    * */
    public static void show_brife(){
        linearLayout_brife.setVisibility(View.VISIBLE);
        linearLayout_specific.setVisibility(View.GONE);
    }
    public static void show_specific(){
        linearLayout_brife.setVisibility(View.GONE);
        linearLayout_specific.setVisibility(View.VISIBLE);
    }

    public static void show_hei(){
        button_2.setBackgroundColor(Color.parseColor("#030000"));
        button_3.setBackgroundColor(Color.parseColor("#030000"));
        button_4.setBackgroundColor(Color.parseColor("#030000"));
    }

    private void showMessageNegativeDialog() {
        new QMUIDialog.MessageDialogBuilder(MainActivity.this)
                .setTitle("退出登录")
                .setMessage("确定要退出登录吗？")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "退出", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        FlagFirst obj=new FlagFirst();
                        obj.setStr("清空登录");
                        obj.updateAll("str=?","已经登录过了");
                        /*
                         * 注销QQ的第三方登录
                         * */
                        QQLogin.mTencent.logout(MyApplication.getContext());
                        Toast.makeText(MainActivity.this, "退出登录成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        ActivityCollector.finishAll();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    private void showLongMessageDialog() {
        new QMUIDialog.MessageDialogBuilder(MainActivity.this)
                .setTitle("账号信息")
                .setMessage("QQ昵称：" +nickname_public+"\n"+
                        "学  号：" +number_public+"\n")
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }


    /*
     * 点击两次退出功能
     * 声明一个long类型变量：用于存放上一点击“返回键”的时刻
     * */
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //将搜索框收回
            drag_layout.close();
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                ActivityCollector.finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}



/*
* 4月20号晚上七点
* */

/*
* F0:E7:2A:7F:A7:80:17:E2:90:61:36:C9:6D:0F:29:06:82:23:87:B5
*
* F6:84:68:2B:66:75:F9:7B:DA:43:5B:60:0E:5A:E5:8E:28:B7:4B:4F
* F0:E7:2A:7F:A7:80:17:E2:90:61:36:C9:6D:0F:29:06:82:23:87:B5
*
*
* F0:E7:2A:7F:A7:80:17:E2:90:61:36:C9:6D:0F:29:06:82:23:87:B5
* */
