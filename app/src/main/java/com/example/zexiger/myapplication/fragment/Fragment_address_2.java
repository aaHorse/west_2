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
import com.example.zexiger.myapplication.activity.SpecificActivity;
import com.example.zexiger.myapplication.base.MyApplication;


import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_address_2 extends Fragment {
    private SpecificActivity activity;
    private Context context;

    @BindView(R.id.map)MapView mapView;
    private AMap aMap;
    public double latitude;
    public double longtitude;

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
        setUpMap();
        return view;
    }

    private void setUpMap() {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(30)); // 设置地图可视缩放大小
        aMap.clear();
        LatLng latLng = new LatLng(latitude, longtitude);
        latitude=latLng.latitude;
        longtitude=latLng.longitude;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.local));
        markerOptions.position(latLng);
        aMap.addMarker(markerOptions);
    }
}
