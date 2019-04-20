package com.example.zexiger.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.activity.MainActivity;
import com.example.zexiger.myapplication.activity.SpecificActivity;
import com.example.zexiger.myapplication.base.MyApplication;


import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment_address_2 extends Fragment {
    private SpecificActivity activity;
    private Context context;

    @BindView(R.id.map)MapView mapView;
    private AMap aMap;
    public double latitude;
    public double longtitude;

    @BindView(R.id.bar_layout) ViewGroup linear_bar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_2, container, false);
        ButterKnife.bind(this,view);
        activity=(SpecificActivity) getActivity();
        context=getContext();
        Bundle bundle=getArguments();
        String address=bundle.getString("flag");
        latitude=(double)Double.parseDouble(address.split(",")[0]);
        longtitude=(double)Double.parseDouble(address.split(",")[1]);

        mapView.onCreate(SpecificActivity.bundle);
        aMap=mapView.getMap();
        LatLng latLng = new LatLng(latitude,longtitude);

        setStatusBar();

        //使用默认点标记
        Marker maker=aMap.addMarker(new MarkerOptions().position(latLng).title("地点:").snippet("hahahah"));
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,30));
        return view;
    }


    @OnClick(R.id.top_bar_icon)void button_back(){
        Fragment_specific.xiugai_show();
    }
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    //
                    Fragment_specific.xiugai_show();
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * 设置沉浸式状态栏
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final int statusHeight = getStatusBarHeight();
            linear_bar.post(new Runnable() {
                @Override
                public void run() {
                    int titleHeight = linear_bar.getHeight();
                    android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) linear_bar.getLayoutParams();
                    params.height = statusHeight + titleHeight;
                    linear_bar.setLayoutParams(params);
                }
            });
        }
    }
    /**
     * 获取状态栏的高度
     * @return
     */
    protected int getStatusBarHeight(){
        try
        {
            Class<?> c=Class.forName("com.android.internal.R$dimen");
            Object obj=c.newInstance();
            Field field=c.getField("status_bar_height");
            int x=Integer.parseInt(field.get(obj).toString());
            return  getResources().getDimensionPixelSize(x);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
