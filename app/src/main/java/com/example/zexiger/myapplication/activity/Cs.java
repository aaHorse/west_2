package com.example.zexiger.myapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.http_util.HttpOK;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Cs extends AppCompatActivity {
    @BindView(R.id.image)ImageView imageView;

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
        setContentView(R.layout.activity_cs);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_1)void button_1(){
        //HttpOK.post_file();
    }
    @OnClick(R.id.button_2)void button_2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //HttpOK.getData();
            }
        }).start();
    }
    @OnClick(R.id.button_3)void button_3(){
        Glide.with(Cs.this).load("http://192.168.43.61:8080/img/1.png").into(imageView);
    }
    @OnClick(R.id.button_4)void button_4(){
        func_2();
    }
    @OnClick(R.id.button_5)void button_5(){
        Intent intent=new Intent(Cs.this,OcrActivity.class);
        startActivity(intent);
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
        imageUri = FileProvider.getUriForFile(Cs.this,
                Cs.this.getApplicationContext().getPackageName() +".my.provider",
                new File(mTempPhotoPath));
        //下面这句指定调用相机拍照后的照片存储的路径
        intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentToTakePhoto, CAMERA_REQUEST_CODE);
    }

    /*
     * 打开相册权限
     * */
    public void func_2(){
        if (ContextCompat.checkSelfPermission(Cs.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
            // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
            // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
            ActivityCompat.requestPermissions(Cs.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    GALLERY_REQUEST_CODE);
        }else{
            takePhoto();
            Log.d("ttttt","已经有权限了");
        }
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
/*                        FragmentTransaction transaction=fragmentManager.beginTransaction();
                        Fragment fragment=new Fragment_cs();
                        transaction.replace(R.id.fragment,fragment);
                        transaction.commit();*/
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                // Permission Denied
                Toast.makeText(Cs.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
//        Log.d("ttttt",fileName+"");
/*        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));*/
    }
}
