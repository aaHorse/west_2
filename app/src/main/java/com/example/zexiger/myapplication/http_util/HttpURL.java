package com.example.zexiger.myapplication.http_util;

import android.app.Notification;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class HttpURL {
    /*
    * GET请求
    * */
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
        HttpURLConnection connection=null;
        try{
            URL url=new URL(address);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            InputStream in=connection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder response=new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                response.append(line);
            }
            if(listener!=null){
                listener.onFinish(response.toString());
            }
        } catch(Exception e){
            if(listener!=null){
                listener.onError(e);
            }
        }finally{
            if(connection!=null){
                connection.disconnect();
            }
        }
    }

    public static HttpURLConnection getConnection(String address){
        HttpURLConnection connection=null;
        try{
            URL url=new URL(address);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            return connection;
        } catch(Exception e){
            Log.d("ttttt","请求获取Connection出错");
            return null;
        }
    }

    /**
     * POST请求
     */
    public static void sendPostRequest(String content,HttpCallbackListener listener){
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://192.168.43.61:8080/upload/setFileUpload");
            connection = (HttpURLConnection) url.openConnection();
            // 设置请求方式
            connection.setRequestMethod("POST");
            // 设置编码格式
            connection.setRequestProperty("Charset", "UTF-8");
            // 传递自定义参数
            //connection.setRequestProperty("MyProperty", "this is me!");
            // 设置容许输出
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // 上传一张图片
/*            FileInputStream file = new FileInputStream(Environment.getExternalStorageDirectory().getPath()
                    + "zxing_image.png");*/
            FileInputStream file = new FileInputStream(Environment.getExternalStorageDirectory()+"//liliyuan//temp.jpg");
            OutputStream os = connection.getOutputStream();
            int count = 0;
            while((count=file.read()) != -1){
                os.write(count);
            }
            os.flush();
            os.close();

    /*        // 获取返回数据
            if(connection.getResponseCode() == 1000){
                InputStream is = connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                StringBuilder response=new StringBuilder();
                String line;
                while((line=reader.readLine())!=null){
                    response.append(line);
                }
                if(listener!=null){
                    listener.onFinish(response.toString());
                }
                Log.d("ttttt",response.toString());
            }else{
                Log.d("ttttt","获得的码是"+connection.getResponseCode());
            }*/
            InputStream is = connection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
            StringBuilder response=new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                response.append(line);
            }
            if(listener!=null){
                listener.onFinish(response.toString());
            }
            Log.d("ttttt",response.toString());
        }catch (IOException e) {
            if (listener!=null){
                listener.onError(e);
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

/*    *//* 上传文件至Server的方法 *//*
    private void uploadFile()
    {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try
        {
            URL url = new URL("http://192.168.43.61:8080/upload/setFileUpload");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
          *//* Output to the connection. Default is false,
             set to true because post method must write something to the connection *//*
            con.setDoOutput(true);
            *//* Read from the connection. Default is true.*//*
            con.setDoInput(true);
            *//* Post cannot use caches *//*
            con.setUseCaches(false);
            *//* Set the post method. Default is GET*//*
            con.setRequestMethod("POST");
            *//* 设置请求属性 *//*
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            *//*设置StrictMode 否则HTTPURLConnection连接失败，因为这是在主进程中进行网络连接*//*
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            *//* 设置DataOutputStream，getOutputStream中默认调用connect()*//*
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());  //output to the connection
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; " +
                    "name=\"file\";filename=\"" +
                    fileName + "\"" + end);
            ds.writeBytes(end);
            *//* 取得文件的FileInputStream *//*
            FileInputStream fStream = new FileInputStream(uploadFile);
            *//* 设置每次写入8192bytes *//*
            int bufferSize = 8192;
            byte[] buffer = new byte[bufferSize];   //8k
            int length = -1;
            *//* 从文件读取数据至缓冲区 *//*
            while ((length = fStream.read(buffer)) != -1)
            {
                *//* 将资料写入DataOutputStream中 *//*
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            *//* 关闭流，写入的东西自动生成Http正文*//*
            fStream.close();
            *//* 关闭DataOutputStream *//*
            ds.close();
            *//* 从返回的输入流读取响应信息 *//*
            InputStream is = con.getInputStream();  //input from the connection 正式建立HTTP连接
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1)
            {
                b.append((char) ch);
            }
            *//* 显示网页响应内容 *//*
//            Toast.makeText(MainActivity.this, b.toString().trim(), Toast.LENGTH_SHORT).show();//Post成功
            System.out.println(b.toString());
        } catch (Exception e)
        {
            *//* 显示异常信息 *//*
//            Toast.makeText(MainActivity.this, "Fail:" + e, Toast.LENGTH_SHORT).show();//Post失败
            System.out.println(e);
        }
    }*/
}
