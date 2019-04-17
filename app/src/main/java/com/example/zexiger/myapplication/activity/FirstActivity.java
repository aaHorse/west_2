package com.example.zexiger.myapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.base.BaseActivity;
import com.example.zexiger.myapplication.db.FlagFirst;
import com.example.zexiger.myapplication.fragment.Fragment_cs;
import com.example.zexiger.myapplication.fragment.Fragment_number;
import com.example.zexiger.myapplication.fragment.QQLogin;
import com.example.zexiger.myapplication.http_util.HttpCallbackListener;
import com.example.zexiger.myapplication.http_util.HttpURL;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.zexiger.myapplication.fragment.QQLogin.mIUiListener;

public class FirstActivity extends BaseActivity {

    @BindView(R.id.frame_layout)FrameLayout frameLayout;
    @BindView(R.id.line)FrameLayout frameLayout_2;
    private Fragment fragment;
    private static FragmentManager fragmentManager;
    private static LinearLayout linearLayout_4;
    private static LinearLayout linearLayout_5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
        fragmentManager= getSupportFragmentManager();
        linearLayout_4=findViewById(R.id.line_4);
        linearLayout_5=findViewById(R.id.line_5);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<FlagFirst> lists=DataSupport.findAll(FlagFirst.class);
                if(lists.size()!=0){
                    FlagFirst flag_first=(FlagFirst)lists.get(lists.size()-1);
                    if(flag_first.getStr().equals("已经登录过了")){
                        /* 如果已经登录过了，就直接跳转，不用再调用QQ进行登录*/

                        MainActivity.startActivity(FirstActivity.this);
                        finish();
                    }else{
                        frameLayout_2.setVisibility(View.VISIBLE);
                    }
                }else{
                    frameLayout_2.setVisibility(View.VISIBLE);
                }
            }
        });

        fragment=(QQLogin)getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ttttt","在result中");
        if(requestCode == Constants.REQUEST_LOGIN){
            Log.d("ttttt","在result中");
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

  /*  * 用输入学号界面替换掉QQ点击登录界面
    * */

    public static void replace_QQ(){
        linearLayout_4.setVisibility(View.GONE);
        linearLayout_5.setVisibility(View.VISIBLE);
    }
}

/*
public class FirstActivity extends BaseActivity {
    @BindView(R.id.image)ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cs);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.button_1)void button_1(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURL.sendHttpRequest("http://192.168.43.61:8080/query/list", new HttpCallbackListener() {
                    @Override
                    public void onFinish(final String response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FirstActivity.this,response,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FirstActivity.this,"查询全部出错",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }).start();
    }
    @OnClick(R.id.button_2)void button_2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURL.sendPostRequest("123", new HttpCallbackListener() {
                    @Override
                    public void onFinish(final String response) {
                        Log.d("ttttt","服务器返回的数据为："+response);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FirstActivity.this,"服务器返回的数据为："+response,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("ttttt","访问网络失败");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FirstActivity.this,"访问网络失败：",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }).start();
    }
    @OnClick(R.id.button_3)void button_3(){
        try{
            File file = new File(Environment.getExternalStorageDirectory()+"//zxing_image//zxing_image.png");
            //File file= new File("../res/drawable/picture.jpg");
            Glide.with(this).load(file).into(imageView);
        }catch (Exception e){
            e.printStackTrace();
            Log.d("ttttt","文件无法打开");
        }
    }
    @OnClick(R.id.button_4)void button_4(){
        func_2();
    }



    */
/*
     * 打开相册权限
     * *//*

    public void func_2(){
        if (ContextCompat.checkSelfPermission(FirstActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
            // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
            // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
            ActivityCompat.requestPermissions(FirstActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }else{
            choosePhoto();
            Log.d("ttttt","已经有权限了");
        }
    }

    */
/*
     * 打开相册
     * *//*

    private void choosePhoto(){
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
        Log.d("ttttt","打开了相册");
        startActivityForResult(intentToPickPic, 1);
    }


    //当拍摄照片完成时会回调到onActivityResult 在这里处理照片的裁剪
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //fragment4ImageView0 = findViewById(R.id.fragment4ImageView0);
        switch (requestCode) {
            case 1: {
                try{
                    Log.d("ttttt","获取照片成功");
                    Uri imageUri = data.getData();
                    if(imageUri!=null) {
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bit);
                        saveBitmap(bit);
                        Log.d("ttttt", "aaa"+String.valueOf(bit));
                    }else{
                        Log.d("ttttt","图片地址为空");
                    }
                }catch (Exception e){
                    Log.d("ttttt","存文件出错");
                    e.printStackTrace();
                }
                break;
            }
            default:
                Log.d("ttttt","没有找到匹配的标志码");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == 1)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.d("ttttt","申请了权限");
                choosePhoto();
            } else
            {
                // Permission Denied
                Toast.makeText(FirstActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void saveBitmap(Bitmap bitmap) {
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE" };
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),"zxing_image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "zxing_image" + ".png";
        Log.d("ttttt",fileName);
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("ttttt",fileName+"");
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
    }
}
*/
