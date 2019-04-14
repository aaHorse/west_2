package com.example.zexiger.myapplication.some;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.zexiger.myapplication.http_util.HttpCallbackListener;
import com.example.zexiger.myapplication.http_util.HttpURL;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class InputFile {
    /*
    * 返回文件的存储位置
    * */
    public static void qq_input(final String address, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    File cache;//缓存路径
                    cache=new File(Environment.getExternalStorageDirectory(),"Test");
                    if(!cache.exists()){
                        cache.mkdirs();
                    }
                    HttpURLConnection connection=HttpURL.getConnection(address);
                    if(connection.getResponseCode()==200){
                        InputStream is=connection.getInputStream();
                        Bitmap b=BitmapFactory.decodeStream(is);
                        File imgFile=new File(cache,"woca.jpg");
                        BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(imgFile));
                        b.compress(Bitmap.CompressFormat.JPEG,80,bos);
                        listener.onFinish(cache+"woca.jpg");
                        bos.flush();
                        bos.close();
                    }
                }catch (Exception e){
                    listener.onError(e);
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
