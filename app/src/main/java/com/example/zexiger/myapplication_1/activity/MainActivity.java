package com.example.zexiger.myapplication_1.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zexiger.myapplication_1.R;
import com.example.zexiger.myapplication_1.adapter.LeftItemAdapter;
import com.example.zexiger.myapplication_1.base.BaseActivity;
import com.example.zexiger.myapplication_1.base.DefineView;
import com.example.zexiger.myapplication_1.db.QQ_messege;
import com.example.zexiger.myapplication_1.entity.LeftItemMenu;
import com.example.zexiger.myapplication_1.widget.DragLayout;
import com.nineoldandroids.view.ViewHelper;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bundle=savedInstanceState;

        setStatusBar();
        initView();
        initValidata();
        initListener();
        bindData();
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
    }
    @OnClick(R.id.button_3)void button_2(){
        Toast.makeText(MainActivity.this,"点击了2",Toast.LENGTH_SHORT).show();
        findViewById(R.id.button_2).setBackgroundColor(Color.parseColor("#00A8E1"));
        findViewById(R.id.button_3).setBackgroundColor(Color.parseColor("#00A8bb"));
    }
}
