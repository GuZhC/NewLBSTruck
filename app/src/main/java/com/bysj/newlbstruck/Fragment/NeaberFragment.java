package com.bysj.newlbstruck.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.Marker;
import com.bysj.newlbstruck.Activity.BaseFragment;
import com.bysj.newlbstruck.Activity.DriverDetailActivity;
import com.bysj.newlbstruck.Activity.PublishActivity;
import com.bysj.newlbstruck.Activity.PublishDriveActivity;
import com.bysj.newlbstruck.Activity.UserDetailActivity;
import com.bysj.newlbstruck.Bean.DriverOrder;
import com.bysj.newlbstruck.Bean.UserOrder;
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
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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

    private void getData(final boolean isDrive) {
        if (isDrive) {
            BmobQuery<UserOrder> bmobQuery = new BmobQuery<UserOrder>();
            bmobQuery.findObjects(new FindListener<UserOrder>() {
                @Override
                public void done(List<UserOrder> list, BmobException e) {
                    List<LocationInfo> data = new ArrayList<>();
                    int i = 0;
                    for (UserOrder userOrder : list) {
                        LocationInfo locationInfo = new LocationInfo(Double.valueOf(userOrder.getStartPointLat()),
                                Double.valueOf(userOrder.getStartPointLng()));
                        locationInfo.setKey("key" + userOrder.getUserId()+i);
                        locationInfo.setName("name" + userOrder.getUserName()+i);
                        i++;
                        data.add(locationInfo);
                    }
                    showNears(data, isDrive, list, null);
                }
            });
        } else {
            BmobQuery<DriverOrder> bmobQuery = new BmobQuery<DriverOrder>();
            bmobQuery.findObjects(new FindListener<DriverOrder>() {
                @Override
                public void done(List<DriverOrder> list, BmobException e) {
                    List<LocationInfo> data = new ArrayList<>();
                    int i = 0;
                    for (DriverOrder driverOrder : list) {
                        LocationInfo locationInfo = new LocationInfo(Double.valueOf(driverOrder.getStartPointLat()),
                                Double.valueOf(driverOrder.getStartPointLng()));
                        locationInfo.setKey("key" + driverOrder.getDriverId()+i);
                        locationInfo.setName("name" + driverOrder.getDriverName()+i);
                        i++;
                        data.add(locationInfo);
                    }
                    showNears(data, isDrive, null, list);
                }
            });
        }

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
                getData(isDrive);
//                getNearDrivers(locationInfo.getLatitude(),
//                        locationInfo.getLongitude());
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
        mLbsLayer.addOrUpdateMarker(mStartLocation, mLocationBit, new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }


//    private void getNearDrivers(double latitude, double longitude) {
//
//        List<LocationInfo> data = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            LocationInfo locationInfo = new LocationInfo(latitude + (i + 1) * 0.001, longitude - (i + 1) * 0.001);
//            locationInfo.setKey("key" + i);
//            locationInfo.setName("name" + i);
//            data.add(locationInfo);
//        }
//        showNears(data);
//    }

    @OnClick(R.id.btn_register_put)
    public void onViewClicked() {
        if (SharedPreferenceUtil.instance(getContext()).getBoolean(Constant.IS_DRIV))
            startActivity(new Intent(getContext(), PublishDriveActivity.class));
        else {
            startActivity(new Intent(getContext(), PublishActivity.class));
        }
    }

    public void showNears(List<LocationInfo> data, boolean isDrive, final List<UserOrder> listuser, final List<DriverOrder> listDrive) {
        for (int i = 0; i < data.size(); i++) {
            final int finalI = i;
            LocationInfo locationInfo = data.get(i);
            if (isDrive) {
                mShopBit = BitmapFactory.decodeResource(getResources(), R.mipmap.location);
                mLbsLayer.addOrUpdateMarker(locationInfo, mShopBit, new AMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
//                            ToastUtils.showSuccess(getContext(), listuser.get(finalI).getUserName());
                        Intent intent = new Intent(getContext(), UserDetailActivity.class);
                        intent.putExtra("order", listuser.get(finalI));
                        startActivity(intent);
                        return false;
                    }
                });
            } else {
                mShopBit = BitmapFactory.decodeResource(getResources(), R.mipmap.car_green);
                mLbsLayer.addOrUpdateMarker(locationInfo, mShopBit, new AMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
//                            ToastUtils.showSuccess(getContext(), listDrive.get(finalI).getDriverName());
                        Intent intent = new Intent(getContext(), DriverDetailActivity.class);
                        intent.putExtra("order", listDrive.get(finalI));
                        startActivity(intent);
                        return false;
                    }
                });
            }
        }

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
        if (outState != null) {
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
