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


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.example.zexiger.myapplication.R;
import com.example.zexiger.myapplication.activity.MainActivity;
import com.example.zexiger.myapplication.base.MyApplication;


import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_address_2 extends Fragment {
    private MainActivity activity;
    private Context context;

    @BindView(R.id.map)MapView mapView;
    private AMap aMap;
    private LatLonPoint searchLatlonPoint;
    public double latitude;
    public double longtitude;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    latitude=amapLocation.getLatitude();//获取纬度
                    longtitude=amapLocation.getLongitude();//获取经度
                    Log.d("ttttt","这个反了，先设置纬度，再设置经度,经度"+longtitude+"纬度"+latitude);
                    /*
                     * 获取一次定位之后，才设置地图
                     * */
                    setUpMap();
                    LatLng latLng = new LatLng(latitude, longtitude);
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));//设置中心点
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.d("ttttt", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_2, container, false);
        ButterKnife.bind(this,view);
        activity=(MainActivity) getActivity();
        context=getContext();

        mapView.onCreate(MainActivity.bundle);
        aMap=mapView.getMap();
        dingwei();

        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                //
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                searchLatlonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                //
                //activity.setLocaltion(latitude,longtitude);
            }
        });
        return view;
    }

    private void setUpMap() {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(30)); // 设置地图可视缩放大小
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                aMap.clear();
                latitude=latLng.latitude;
                longtitude=latLng.longitude;
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.local));
                markerOptions.position(latLng);
                aMap.addMarker(markerOptions);
                //aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
            }
        });
    }

    private void dingwei() {
        aMap.setMyLocationEnabled(true);
        //初始化定位
        mLocationClient = new AMapLocationClient(MyApplication.getContext());
        mLocationClient.setApiKey("acca13bdbabbc9aa9add26592325c921");
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //设置只定位一次
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
}
