package com.example.zexiger.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OcrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        ButterKnife.bind(this);
        init_ocr();
    }

    private static int REQUEST_CODE_CAMERA=1;

    @OnClick(R.id.ocr_button_1)void button_1(){
// 生成intent对象
        Intent intent = new Intent(OcrActivity.this, CameraActivity.class);

// 设置临时存储
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                getSaveFile(getApplication()).getAbsolutePath());

// 调用除银行卡，身份证等识别的activity
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

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
                    Log.d("ttttt","文字识别的返回值为"+sb.toString());
                }
                @Override
                public void onError(OCRError error) {
                    Log.d("ttttt","调用文字识别失败");
                    // 调用失败，返回OCRError对象
                }
            });
        }
    }

}
