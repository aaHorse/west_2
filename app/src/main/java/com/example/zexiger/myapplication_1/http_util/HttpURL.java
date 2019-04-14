package com.example.zexiger.myapplication_1.http_util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
}
