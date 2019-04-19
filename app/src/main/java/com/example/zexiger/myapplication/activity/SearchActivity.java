package com.example.zexiger.myapplication.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zexiger.myapplication.BuildConfig;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.base.BaseActivity;
import com.example.zexiger.myapplication.fragment.Fragment_cs;
import com.example.zexiger.myapplication.fragment.Fragment_search;
import com.example.zexiger.myapplication.http_util.HttpCallbackListener;
import com.example.zexiger.myapplication.http_util.HttpURL;
import com.example.zexiger.myapplication.widget.PhotoClipperUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {
    public static void startActivity(Context context,int n){
        Intent intent=new Intent(context,SearchActivity.class);
        intent.putExtra("flag",n);
        context.startActivity(intent);
    }

    public static FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    /*
     * 给碎片提供Bundle
     * */
    public static Bundle bundle;

    /*
    * 经纬度
    * */
    private static double latitude;
    private static double longtitude;

    /*
    *
    * */
    // 拍照回传码
    public final static int CAMERA_REQUEST_CODE = 0;
    // 相册选择回传吗
    public final static int GALLERY_REQUEST_CODE = 1;
    // 拍照的照片的存储位置
    private String mTempPhotoPath;
    // 照片所在的Uri地址
    private Uri imageUri;

    public static Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        fragmentManager=getSupportFragmentManager();
        bundle=savedInstanceState;

        Intent intent=getIntent();
        int n=intent.getIntExtra("flag",-1);
        switch (n){
            case 0:
                init_fragment(0);
                break;
            case 1:
                init_fragment(1);
                break;
            case 2:
                Intent intent_1=new Intent(SearchActivity.this,OcrActivity.class);
                startActivity(intent_1);
                //init_fragment(2);
                break;
            default:
                Log.d("ttttt","SearchActivity出错");
        }
    }

    private void init_fragment(int n){
        Fragment fragment=new Fragment_search();
        Bundle bundle=new Bundle();
        bundle.putInt("flag",n);
        fragment.setArguments(bundle);
        transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment,fragment);
        transaction.commit();
    }

    public static void setLocaltion(double a,double b){
        latitude=a;
        longtitude=b;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static double getLongtitude() {
        return longtitude;
    }



    /*
    * 动态申请权限
    * */
    /*
    * 相机权限
    * */
    public void func_1(){
        //第二个参数是需要申请的权限
        if (ContextCompat.checkSelfPermission(SearchActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
            // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
            // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
            ActivityCompat.requestPermissions(SearchActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CAMERA_REQUEST_CODE);

        }else { //权限已经被授予，在这里直接写要执行的相应方法即可
            takePhoto();
        }
    }

    /*
     * 打开相机
     * */
    private void takePhoto(){
        // 跳转到系统的拍照界面
        Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定照片存储位置为sd卡本目录下
        // 这里设置为固定名字 这样就只会只有一张temp图 如果要所有中间图片都保存可以通过时间或者加其他东西设置图片的名称
        // File.separator为系统自带的分隔符 是一个固定的常量
        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.jpeg";
        // 获取图片所在位置的Uri路径    *****这里为什么这么做参考问题2*****
        /*imageUri = Uri.fromFile(new File(mTempPhotoPath));*/
        imageUri = FileProvider.getUriForFile(SearchActivity.this,
                SearchActivity.this.getApplicationContext().getPackageName() +".my.provider",
                new File(mTempPhotoPath));
        //下面这句指定调用相机拍照后的照片存储的路径
        intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentToTakePhoto, CAMERA_REQUEST_CODE);
    }

    /*
     * 打开相册权限
     * */
    public void func_2(){
        if (ContextCompat.checkSelfPermission(SearchActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
            // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
            // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
            ActivityCompat.requestPermissions(SearchActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    GALLERY_REQUEST_CODE);
        }else{
            choosePhoto();
            Log.d("ttttt","已经有权限了");
        }
    }

    /*
     * 打开相册
     * */
    private void choosePhoto(){
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
        Log.d("ttttt","打开了相册");
        startActivityForResult(intentToPickPic, GALLERY_REQUEST_CODE);
    }


    //当拍摄照片完成时会回调到onActivityResult 在这里处理照片的裁剪
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //fragment4ImageView0 = findViewById(R.id.fragment4ImageView0);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                // 获得图片
                try {
                    Log.d("ttttt","到了这里？？？");
                    //该uri就是照片文件夹对应的uri
                    Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    // 给相应的ImageView设置图片 未裁剪
                    //fragment4ImageView0.setImageBitmap(bit);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case GALLERY_REQUEST_CODE: {
                // 获取图片
                /*
                 * 相册
                 * */
                try {
                    //该uri是上一个Activity返回的
                    imageUri = data.getData();
                    if(imageUri!=null) {
                        bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        Log.d("ttttt", "aaa"+String.valueOf(bit));
                        saveBitmap(bit,"temp.jpg");
                    }else{
                        Log.d("ttttt","图片地址为空");
                    }
                } catch (Exception e) {
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

        if (requestCode == CAMERA_REQUEST_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                takePhoto();
            } else
            {
                // Permission Denied
                Toast.makeText(SearchActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.d("ttttt","申请了权限");
                choosePhoto();
            } else
            {
                // Permission Denied
                Toast.makeText(SearchActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void get_pic(){
        func_2();
        //choosePhoto();
    }

    public void saveBitmap(Bitmap bitmap,String name) {
        /*
        * 申请权限
        * */
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
        File appDir = new File(Environment.getExternalStorageDirectory(),"liliyuan");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name;
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Log.d("ttttt","图片保存成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


/*
* File file = new File(Environment.getExternalStorageDirectory()+"//liliyuan//temp.jpg");
* */
