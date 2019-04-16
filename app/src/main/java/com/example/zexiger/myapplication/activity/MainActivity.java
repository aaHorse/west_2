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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.adapter.LeftItemAdapter;
import com.example.zexiger.myapplication.base.BaseActivity;
import com.example.zexiger.myapplication.base.DefineView;
import com.example.zexiger.myapplication.db.QQ_messege;
import com.example.zexiger.myapplication.entity.LeftItemMenu;
import com.example.zexiger.myapplication.fragment.Fragment_main_right;
import com.example.zexiger.myapplication.fragment.Fragment_sousuo;
import com.example.zexiger.myapplication.widget.DragLayout;
import com.nineoldandroids.view.ViewHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
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
    * 侧滑菜单的头像、名称
    * */
    @BindView(R.id.iv_bottom)ImageView imageView;
    @BindView(R.id.name)TextView name;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        linearLayout_brife=(LinearLayout)findViewById(R.id.line_4);
        linearLayout_specific=(LinearLayout)findViewById(R.id.line_5);
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
            String nickname=obj.getNickname();
            final String figureurl_qq_1=obj.getFigureurl_qq_1();
            name.setText(nickname);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(MainActivity.this).load(figureurl_qq_1).into(imageView);
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
        Fragment fragment=new Fragment_main_right();
        Bundle bundle=new Bundle();
        bundle.putString("flag","找失主");
        fragment.setArguments(bundle);
        transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.line_3,fragment);
        transaction.commit();
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
        lists_left.add(new LeftItemMenu(R.drawable.icon_xinxi,"账号信息"));
        lists_left.add(new LeftItemMenu(R.drawable.icon_shezhi,"设置"));
        lists_left.add(new LeftItemMenu(R.drawable.icon_fankui,"反馈"));
        LeftItemAdapter adapter=new LeftItemAdapter(lists_left);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        swipeRecyclerView.setLayoutManager(layoutManager);
        swipeRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(MainActivity.this,"你点击了"+position,Toast.LENGTH_SHORT).show();
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
        Toast.makeText(MainActivity.this,"点击了1",Toast.LENGTH_SHORT).show();
        findViewById(R.id.button_2).setBackgroundColor(Color.parseColor("#00A8bb"));
        findViewById(R.id.button_3).setBackgroundColor(Color.parseColor("#00A8E1"));
        Fragment fragment=new Fragment_main_right();
        Bundle bundle=new Bundle();
        bundle.putString("flag","找失主");
        fragment.setArguments(bundle);
        transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.line_3,fragment);
        transaction.commit();
    }
    @OnClick(R.id.button_3)void button_2(){
        Toast.makeText(MainActivity.this,"点击了2",Toast.LENGTH_SHORT).show();
        findViewById(R.id.button_2).setBackgroundColor(Color.parseColor("#00A8E1"));
        findViewById(R.id.button_3).setBackgroundColor(Color.parseColor("#00A8bb"));
        Fragment fragment=new Fragment_main_right();
        Bundle bundle=new Bundle();
        bundle.putString("flag","找失物");
        fragment.setArguments(bundle);
        transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.line_3,fragment);
        transaction.commit();
    }

    @OnClick(R.id.fab)void fab(){
        Toast.makeText(MainActivity.this,"点击了悬浮按钮",Toast.LENGTH_SHORT).show();
        showMenuDialog();
    }
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
                        Toast.makeText(MainActivity.this, "你选择了 " + items[which], Toast.LENGTH_SHORT).show();
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
