package com.example.zexiger.myapplication.http_util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.amap.api.col.sl2.iz.k;

public class HttpOK {
    /*
    * GET
    * */
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /*
    * get同步
    * */
    public void getDatasync(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String address="";
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(address)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        Log.d("kwwl","response.code()=="+response.code());
                        Log.d("kwwl","response.message()=="+response.message());
                        Log.d("kwwl","res=="+response.body().string());
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    * get异步
    * */
    public static void getDataAsync() {
        String address="http://192.168.43.61:8080/query/list";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("ttttt","查询全部数据失败");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("ttttt",response.body().string());
            }
        });
    }

    /*
    * post异步
    * */
    private void postDataWithParame() {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("username","zhangsan");//传递键值对参数
        Request request = new Request.Builder()//创建Request 对象。
                .url("http://www.baidu.com")
                .post(formBody.build())//传递请求体
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /*
    * post的文件
    * */
    public static void post_file(){
        /*
        *   json : application/json
            xml : application/xml
            png : image/png
            jpg : image/jpg
            gif : imge/gi
        * */
        OkHttpClient client = new OkHttpClient();
        File file = new File(Environment.getExternalStorageDirectory()+"//liliyuan//temp.jpg");
        MultipartBody multipartBody =new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file",file.getName(),RequestBody.create(MediaType.parse("image/jpg"), file))//添加文件
                .build();
        final Request request = new Request.Builder()
                .url("http://192.168.43.61:8080/upload/setFileUpload")
                .post(multipartBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("ttttt","失败了");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("ttttt",response.body().string());
            }
        });

    }

    /*
     * post的json
     * */
    public void post_json(){
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        String jsonStr = "{\"username\":\"lisi\",\"nickname\":\"李四\"}";//json数据.
        RequestBody body = RequestBody.create(JSON,jsonStr);
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });//此处省略回调方法。
    }


}
