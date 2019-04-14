package com.example.zexiger.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.activity.MainActivity;
import com.example.zexiger.myapplication.db.FlagFirst;
import com.example.zexiger.myapplication.db.QQ_messege;
import com.example.zexiger.myapplication.http_util.HttpCallbackListener;
import com.example.zexiger.myapplication.some.InputFile;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class QQLogin extends Fragment {
    /*
     * QQ登录
     * */
    private static final String APP_ID = "1108179346";//官方获取的APPID
    private Tencent mTencent;
    public static BaseUiListener mIUiListener;
    private UserInfo mUserInfo;

    private Activity activity;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qq_login, container, false);
        ButterKnife.bind(this,view);
        activity=getActivity();
        context=getContext();
        return view;
    }

    @OnClick(R.id.button_1)void button_1(){
        qq_launch();
    }

    private void qq_launch(){
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,context.getApplicationContext());
        //all表示获取所有权限
        if(!mTencent.isSessionValid()){
            mTencent.login((Activity) context,"all", new BaseUiListener());
        }
    }


    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();
            Log.d("ttttt", "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                final String openID = obj.getString("openid");
                final String accessToken = obj.getString("access_token");
                final String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(context.getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        JSONObject jsonObject=(JSONObject)response;
                        initOpenidAndToken(jsonObject);
                        getUserInfo();
                    }

                    public void initOpenidAndToken(JSONObject jsonObject) {
                        try {
                            String openid = jsonObject.getString("openid");
                            String token = jsonObject.getString("access_token");
                            String expires = jsonObject.getString("expires_in");

                            mTencent.setAccessToken(token, expires);
                            mTencent.setOpenId(openid);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    public void getUserInfo() {
                        //sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我们可以通过这个类拿到这些信息
                        QQToken mQQToken = mTencent.getQQToken();
                        UserInfo userInfo = new UserInfo(context, mQQToken);
                        userInfo.getUserInfo(new IUiListener() {
                            @Override
                            public void onComplete(final Object o) {
                                JSONObject userInfoJson = (JSONObject) o;
                                try {
                                    String nickname = userInfoJson.getString("nickname");
                                    String figureurl_qq_1=userInfoJson.getString("figureurl_qq_2");
                                    InputFile.qq_input(figureurl_qq_1, new HttpCallbackListener() {
                                        @Override
                                        public void onFinish(String response) {
                                            Log.d("ttttt","QQLogin,头像的位置："+response);
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Log.d("ttttt","QQLogin，头像写入文件失败");
                                        }
                                    });
                                    Log.d("ttttt","获取QQ昵称为："+nickname);
                                    /*
                                    * 将用户的昵称和头像的网址存到数据库
                                    * */
                                    QQ_messege obj=new QQ_messege();
                                    obj.setNickname(nickname);
                                    obj.setFigureurl_qq_1(figureurl_qq_1);
                                    obj.save();
                                    /*
                                    * 修改记录是否为第一次登录的数据库
                                    * */
                                    FlagFirst flag_first=new FlagFirst();
                                    flag_first.setStr("已经登录过了");
                                    flag_first.save();
                                    /*
                                    * 跳转页面
                                    * */
                                    MainActivity.startActivity(context);
                                    activity.finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(UiError uiError) {
                                Log.d("ttttt", "获取qq用户信息错误");
                                Toast.makeText(context, "获取qq用户信息错误", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel() {
                                Log.d("ttttt", "获取qq用户信息取消");
                                Toast.makeText(context, "获取qq用户信息取消", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.d("ttttt","登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.d("ttttt","登录取消");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(UiError uiError) {
            Log.d("ttttt","失败");
            Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Log.d("ttttt","失败");
            Toast.makeText(context, "授权取消", Toast.LENGTH_SHORT).show();
        }
    }
}
