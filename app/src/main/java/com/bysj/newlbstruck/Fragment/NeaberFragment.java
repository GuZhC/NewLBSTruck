package com.bysj.newlbstruck.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bysj.newlbstruck.Activity.BaseFragment;
import com.bysj.newlbstruck.Activity.PublishActivity;
import com.bysj.newlbstruck.Activity.PublishDriveActivity;
import com.bysj.newlbstruck.Constant;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.lbs.GaodeLbsLayerImpl;
import com.bysj.newlbstruck.lbs.ILbsLayer;
import com.bysj.newlbstruck.lbs.LocationInfo;
import com.bysj.newlbstruck.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by guZhongC on 2018/5/28.
 * describe:
 */

public class NeaberFragment extends BaseFragment {
    @BindView(R.id.btn_register_put)
    Button btnRegisterPut;
    Unbinder unbinder;
    @BindView(R.id.map_container)
    FrameLayout mapContainer;
    private GaodeLbsLayerImpl mLbsLayer;
    private LocationInfo mStartLocation;
    private Bitmap mLocationBit;
    private Bitmap mShopBit;
    private boolean mIsLocate;
    private boolean isDrive = false;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_neaber;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        isDrive = SharedPreferenceUtil.instance(getContext()).getBoolean(Constant.IS_DRIV);
        setMap(savedInstanceState);
    }


    private void setMap(Bundle savedInstanceState) {
        // 地图服务
        mLbsLayer = new GaodeLbsLayerImpl(getContext());
        mLbsLayer.onCreate(savedInstanceState);
        mLbsLayer.setLocationChangeListener(new ILbsLayer.CommonLocationChangeListener() {
            @Override
            public void onLocationChanged(LocationInfo locationInfo) {

            }

            @Override
            public void onLocation(LocationInfo locationInfo) {
                // 记录起点
                mStartLocation = locationInfo;
                //  设置标题
//                nearbyCity.setText(mLbsLayer.getCity());

//                // 获取附近司机
                getNearDrivers(locationInfo.getLatitude(),
                        locationInfo.getLongitude());
                Log.e("lat,lon", String.valueOf(locationInfo.getLatitude()) + "  " + String.valueOf(locationInfo.getLongitude()));
                // 首次定位，添加当前位置的标记
                addLocationMarker();
                mIsLocate = true;
            }
        });
        // 添加地图到容器
        mapContainer.addView(mLbsLayer.getMapView());
    }


    private void addLocationMarker() {
        if (mLocationBit == null || mLocationBit.isRecycled()) {
            mLocationBit = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.navi_map_gps_locked);
        }
        mLbsLayer.addOrUpdateMarker(mStartLocation, mLocationBit);
    }


    private void getNearDrivers(double latitude, double longitude) {

        List<LocationInfo> data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LocationInfo locationInfo = new LocationInfo(latitude+ (i+1) * 0.001, longitude - (i+1) * 0.001);
            locationInfo.setKey("key" + i);
            locationInfo.setName("name" + i);
            data.add(locationInfo);
        }
        showNears(data);
    }

    @OnClick(R.id.btn_register_put)
    public void onViewClicked() {
        if (SharedPreferenceUtil.instance(getContext()).getBoolean(Constant.IS_DRIV))
            startActivity(new Intent(getContext(), PublishDriveActivity.class));
        else {
            startActivity(new Intent(getContext(), PublishActivity.class));
        }
    }

    public void showNears(List<LocationInfo> data) {
        for (LocationInfo locationInfo : data) {
            showLocationChange(locationInfo);
        }
    }


    public void showLocationChange(LocationInfo locationInfo) {
        if (mShopBit == null || mShopBit.isRecycled()) {
            if (isDrive)
            mShopBit = BitmapFactory.decodeResource(getResources(), R.mipmap.location);
            else
                mShopBit = BitmapFactory.decodeResource(getResources(), R.mipmap.car_green);
        }
        mLbsLayer.addOrUpdateMarker(locationInfo, mShopBit);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mLbsLayer.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mLbsLayer.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState !=null){
            mLbsLayer.onSaveInstanceState(outState);
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mLbsLayer.onDestroy();
    }
}
