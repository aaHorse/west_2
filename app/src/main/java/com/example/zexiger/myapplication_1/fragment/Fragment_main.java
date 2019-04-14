package com.example.zexiger.myapplication_1.fragment;

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
import com.example.zexiger.myapplication_1.R;
import com.example.zexiger.myapplication_1.activity.MainActivity;
import com.example.zexiger.myapplication_1.base.MyApplication;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment_main extends Fragment {
    private Activity activity;
    private Context context;

    @BindView(R.id.map)MapView mapView;
    private AMap aMap;
    private LatLonPoint searchLatlonPoint;
    double latitude;
    double longtitude;

    //////////
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
                    Log.d("ttttt","经度"+longtitude+"纬度"+latitude);
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,view);
        activity=(MainActivity)getActivity();
        context=getContext();

        mapView.onCreate(MainActivity.bundle);
        aMap=mapView.getMap();
        setUpMap();
        dingwei();

        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                //
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                searchLatlonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
            }
        });

        return view;
    }

    @OnClick(R.id.button_1)void button_1(){
        Toast.makeText(context,"你点击了测试地图",Toast.LENGTH_SHORT).show();
    }

    private void setUpMap() {
        LatLng latLng = new LatLng(longtitude, latitude);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));//设置中心点
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
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
            }
        });
    }

    private void dingwei() {
/*        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。*/

        aMap.setMyLocationEnabled(true);
//初始化定位
        mLocationClient = new AMapLocationClient(MyApplication.getContext());
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

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();
    }
}
