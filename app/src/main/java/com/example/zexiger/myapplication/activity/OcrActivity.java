package com.example.zexiger.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.idl.util.FileUtil;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralBasicParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.WordSimple;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.base.BaseActivity;
import com.example.zexiger.myapplication.base.MyApplication;
import com.example.zexiger.myapplication.db.Card;
import com.example.zexiger.myapplication.db.QQ_messege;
import com.example.zexiger.myapplication.entity.Data;
import com.example.zexiger.myapplication.entity.Thing;
import com.example.zexiger.myapplication.http_util.HttpOK;
import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.zexiger.myapplication.base.MyApplication.getContext;

public class OcrActivity extends BaseActivity {
    @BindView(R.id.emptyView)QMUIEmptyView emptyView;

    private String text="null";//联系方式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        ButterKnife.bind(this);
        setStatusBar();
        init_ocr();
        emptyView.show(false, getResources().getString(R.string.str),
                null, getResources().getString(R.string.str_button), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEditTextDialog();
                    }
                });
    }

    private void take_phhoto(){
        // 生成intent对象
        Intent intent = new Intent(OcrActivity.this, CameraActivity.class);
        // 设置临时存储
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, getSaveFile(getApplication()).getAbsolutePath());
        // 调用除银行卡，身份证等识别的activity
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }
    private static int REQUEST_CODE_CAMERA=1;


    /*
    * 初始化ocr
    * */
    private void init_ocr(){
        OCR.getInstance(OcrActivity.this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                String token = result.getAccessToken();
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
            }
        }, getApplicationContext(), "gpAMDD2M4t2apjrIFbx5us0a", "leHNFl6E3v5xTfpzbHbjaHg07e2thq2g");

    }

    /*
    * 临时文件存储位置
    * */
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 获取调用参数
        String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
        // 通过临时文件获取拍摄的图片
        String filePath = getSaveFile(getApplicationContext()).getAbsolutePath();
        // 判断拍摄类型（通用，身份证，银行卡等）
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            // 获取图片文件调用sdk数据接口，见数据接口说明
            // 通用文字识别参数设置
            GeneralBasicParams param = new GeneralBasicParams();
            param.setDetectDirection(true);
            param.setImageFile(new File(filePath));
            final StringBuilder sb=new StringBuilder();

            // 调用通用文字识别服务
            OCR.getInstance(OcrActivity.this).recognizeGeneralBasic(param, new OnResultListener<GeneralResult>() {
                @Override
                public void onResult(GeneralResult result) {
                    // 调用成功，返回GeneralResult对象
                    for (WordSimple wordSimple : result.getWordList()) {
                        // wordSimple不包含位置信息
                        WordSimple word = wordSimple;
                        sb.append(word.getWords());
                        sb.append("\n");
                    }
                    // json格式返回字符串
                    //listener.onResult(result.getJsonRes());
                    Log.d("ttttt","文字识别的返回值为"+result.getJsonRes());
                    Card card=new Gson().fromJson(result.getJsonRes(),Card.class);
                    List<Card.WordsResultBean> words_result=card.getWords_result();
                    /*
                    * 对信息进行初始化，
                    * 并且传到服务器上面
                    * */
                    init_data(words_result);
                }
                @Override
                public void onError(OCRError error) {
                    Log.d("ttttt","调用文字识别失败");
                    // 调用失败，返回OCRError对象
                }
            });
        }
    }

    private void init_data(List<Card.WordsResultBean> words_result){
        String info="";
        if(words_result.size()>4){
            info=words_result.get(1).getWords()+"\n"
                    +words_result.get(2).getWords()+"\n"
                    +words_result.get(3).getWords();
            Log.d("ttttt","初始化的信息为"+info);
        }else{
            Toast.makeText(MyApplication.getContext(),"拍照上传失败",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(OcrActivity.this,OcrActivity.class);
            startActivity(intent);
        }

        Gson gson=new Gson();
        final Data data=new Data();
        data.setInfo(info);
        data.setType("校园卡");
        /*
        * 初始化福州大学
        * */
        data.setAddress(null);
        Date now=new Date();
        String date=getStringDate(now);
        data.setDate(date);
        data.setIsfound(1);
        data.setIsexist(0);
        data.setAddress("26.0557538219,119.1974286674");
        data.setImage("null");
        //
        data.setPhone(text);
        //
        List<QQ_messege>qq_messegeList=DataSupport.findAll(QQ_messege.class);
        QQ_messege obj=qq_messegeList.get(qq_messegeList.size()-1);
        String nickname=obj.getNickname();
        String figureurl_qq_1=obj.getFigureurl_qq_1();
        String number=obj.getNumber();
        data.setQq_name(nickname);
        data.setQq_image(figureurl_qq_1);
        data.setName(number);

        final String json=gson.toJson(data);
        /*
         * 给服务器发送提交
         * */
        Log.d("ttttt",json);
        func(json);
    }

    public String getStringDate(Date now){
        SimpleDateFormat sdfd =new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String str=sdfd.format(now);
        return str;
    }


    /*
    *返回
    * */
    @OnClick(R.id.top_bar_icon)void button_back(){
        Intent intent=new Intent(OcrActivity.this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(OcrActivity.this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    private void func(final String json){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String address="http://192.168.43.61:8080/api/append";
                //提交
                HttpOK.post_json(address,json,new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               Toast.makeText(MyApplication.getContext(),"信息发布失败",Toast.LENGTH_SHORT).show();
/*                               QMUITipDialog tipDialog = new QMUITipDialog.Builder(getContext())
                                       .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                       .setTipWord("信息发布失败")
                                       .create();
                               tipDialog.show();*/
                           }
                       });
                    }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    Toast.makeText(MyApplication.getContext(),"信息发布成功",Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(MyApplication.getContext(),"信息发布失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }

    private void showEditTextDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);
        builder.setTitle("联系方式")
                .setPlaceholder("在此输入你的联系方式")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        text=builder.getEditText().getText().toString();
                        //dialog.dismiss();
                        if (text.isEmpty()){
                            Toast.makeText(OcrActivity.this,"尚未输入联系方式",Toast.LENGTH_SHORT).show();
                        }else if(!isNumericZidai(text)||text.length()!=11){
                            Toast.makeText(OcrActivity.this,"联系方式输入不正确",Toast.LENGTH_SHORT).show();
                        }else{
                            dialog.dismiss();
                            take_phhoto();
                        }
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    public static boolean isNumericZidai(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
