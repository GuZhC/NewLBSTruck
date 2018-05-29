package com.bysj.newlbstruck.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.lbs.GaodeLbsLayerImpl;
import com.bysj.newlbstruck.lbs.ILbsLayer;
import com.bysj.newlbstruck.lbs.LocationInfo;
import com.bysj.newlbstruck.lbs.RouteInfo;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouteActivity extends BaseActivity {

    @BindView(R.id.map_container_root)
    FrameLayout mapContainer;
    @BindView(R.id.btn_register_put_root)
    Button btnRegisterPutRoot;
    private GaodeLbsLayerImpl mLbsLayer;
    private LocationInfo mStartLocation;
    private LocationInfo mEndLocation;
    private LocationInfo mLocation;
    private float mCost;
    private Bitmap mStartBit;
    private Bitmap mEndBit;
    private static final String LOCATION_END = "10000end";
    private static final int BAIDU_READ_PHONE_STATE =100;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ButterKnife.bind(this);
        setBackBtn();
        context = this;
        setTitle("路径预览");
        setMap(savedInstanceState);
    }

    private void setMap(Bundle savedInstanceState) {
        mStartLocation = new LocationInfo(Double.valueOf(getIntent().getStringExtra("lat")), Double.valueOf(getIntent().getStringExtra("lng")));
        mEndLocation = new LocationInfo(Double.valueOf(getIntent().getStringExtra("latend")), Double.valueOf(getIntent().getStringExtra("lngend")));
        // 地图服务
        mLbsLayer = new GaodeLbsLayerImpl(this);
        mLbsLayer.onCreate(savedInstanceState);

        mLbsLayer.setLocationChangeListener(new ILbsLayer.CommonLocationChangeListener() {
            @Override
            public void onLocationChanged(LocationInfo locationInfo) {

            }

            @Override
            public void onLocation(LocationInfo locationInfo) {
                // 记录起点
                mLocation = locationInfo;
                //  记录终点
                mEndLocation.setKey(LOCATION_END);
                // 绘制路径
                showRoute(mStartLocation, mEndLocation, new ILbsLayer.OnRouteCompleteListener() {
                    @Override
                    public void onComplete(RouteInfo result) {
                        mLbsLayer.moveCamera(mStartLocation, mEndLocation);
                    }
                });
            }
        });
        // 添加地图到容器
        mapContainer.addView(mLbsLayer.getMapView());


    }

    /**
     * 绘制起点终点路径
     */

    private void showRoute(final LocationInfo mStartLocation,
                           final LocationInfo mEndLocation,
                           ILbsLayer.OnRouteCompleteListener listener) {

        mLbsLayer.clearAllMarkers();
        addStartMarker();
        addEndMarker();
        mLbsLayer.driverRoute(mStartLocation,
                mEndLocation,
                Color.GREEN,
                listener
        );
    }

    private void addStartMarker() {
        if (mStartBit == null || mStartBit.isRecycled()) {
            mStartBit = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.start);
        }
        mLbsLayer.addOrUpdateMarker(mStartLocation, mStartBit, new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }

    private void addEndMarker() {
        if (mEndBit == null || mEndBit.isRecycled()) {
            mEndBit = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.end);
        }
        mLbsLayer.addOrUpdateMarker(mEndLocation, mEndBit, new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }
    @OnClick(R.id.btn_register_put_root)
    public void onViewClicked() {

        Intent intent;
        if (isAvilible(context, "com.autonavi.minimap")) {
            try {
                //项目中使用高德sdk，在百度地图里面使用高德坐标系是有误差的，
                intent = Intent.getIntent("androidamap://route?sourceApplication=softname&sname=我的位置&dlat=" +
                        mStartLocation.getLatitude() + "&dlon=" + mStartLocation.getLongitude() + "&dname=" + getIntent().getStringExtra("weizhi") + "&dev=0&m=0&t=1");
//                                        intent = Intent.getIntent("androidamap://navi?sourceApplication=慧医&poiname=我的目的地&lat=" + lat + "&lon=" + lng + "&dev=0");
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    public boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }


//    public void showContacts(){
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
//                != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(getApplicationContext(),"没有权限,请手动开启定位权限",Toast.LENGTH_SHORT).show();
//            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
//            ActivityCompat.requestPermissions(RouteActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
//        }else{
//            getadress();
//        }
//    }

    //Android6.0申请权限的回调方法
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
//            case BAIDU_READ_PHONE_STATE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
//                    getadress();
//                } else {
//                    // 没有获取到权限，做特殊处理
//                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                break;
//        }
//    }

//    //解析指定坐标的地址
//    public void getadress() {
//        Log.e("Shunxu", "调用getadress");
//
//        GeocodeSearch geocodeSearch = new GeocodeSearch(this);//地址查询器
//
//        //设置查询参数,
//        //三个参数依次为坐标，范围多少米，坐标系
//        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(new LatLonPoint(39.22, 116.39), 200, GeocodeSearch.AMAP);
//
//        //设置查询结果监听
//        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
//            //根据坐标获取地址信息调用
//            @Override
//            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
//                String s = regeocodeResult.getRegeocodeAddress().getFormatAddress();
//                Log.e("Shunxu", "获得请求结果");
//                makepoint("");
//            }
//
//            //根据地址获取坐标信息是调用
//            @Override
//            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
//            }
//        });
//
//        geocodeSearch.getFromLocationAsyn(regeocodeQuery);//发起异步查询请求
//    }

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
