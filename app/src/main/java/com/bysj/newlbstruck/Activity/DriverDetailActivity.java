package com.bysj.newlbstruck.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bysj.newlbstruck.Bean.DriverOrder;
import com.bysj.newlbstruck.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DriverDetailActivity extends BaseActivity {
    @BindView(R.id.telphone)
    Button telphone;
    @BindView(R.id.see_route)
    Button seeRoute;
    private DriverOrder driverOrder;

    @BindView(R.id.et_tujindi)
    TextView etTujindi;
    @BindView(R.id.et_huowuleibie)
    TextView etHuowuleibie;
    @BindView(R.id.et_yunfeixianzhi)
    TextView etYunfeixianzhi;
    @BindView(R.id.et_konyudunwei)
    TextView etKonyudunwei;
    @BindView(R.id.et_mudidi)
    TextView etMudidi;
    @BindView(R.id.et_facheshijian)
    TextView etFacheshijian;
    @BindView(R.id.et_chufadi)
    TextView etChufadi;
    @BindView(R.id.et_chexianchicun)
    TextView etChexianchicun;
    @BindView(R.id.et_qichexinhao)
    TextView etQichexinhao;
    @BindView(R.id.et_yunshudunwei)
    TextView etYunshudunwei;
    @BindView(R.id.et_daodashijian)
    TextView etDaodashijian;
    @BindView(R.id.user_name)
    TextView user_name;

    private String[] perms = {Manifest.permission.CALL_PHONE};
    private final int PERMS_REQUEST_CODE = 200;
    //    @BindView(R.id.reflesh)
//    SmartRefreshLayout reflesh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_detail);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("司机详情");
        initDetail();
//        reflesh.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//            }
//        });
//        reflesh.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                initDetail();
//                reflesh.finishRefresh(true);
//            }
//        });
    }

    private void initDetail() {
        driverOrder = (DriverOrder) getIntent().getSerializableExtra("order");
        etTujindi.setText(driverOrder.getPathways());
        etHuowuleibie.setText(driverOrder.getHopeGoodsClass());
        etYunfeixianzhi.setText(driverOrder.getFreightLimit());
        etKonyudunwei.setText(driverOrder.getFreeTonnage());
        etMudidi.setText(driverOrder.getDestination());
        etFacheshijian.setText(driverOrder.getDepartureTime());
        etChufadi.setText(driverOrder.getDeparturePlace());
        etChexianchicun.setText(driverOrder.getCarSize());
        etQichexinhao.setText(driverOrder.getCarModel());
        etYunshudunwei.setText(driverOrder.getTransportTonnage());
        etDaodashijian.setText(driverOrder.getArrivalTime());
        user_name.setText(driverOrder.getDriverName());

    }

    @OnClick({R.id.telphone, R.id.see_route})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.telphone:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {//Android 6.0以上版本需要获取权限
                    requestPermissions(perms,PERMS_REQUEST_CODE);//请求权限
                } else {
                    callPhone();
                }

                break;
            case R.id.see_route:
                Intent intent = new Intent(this,RouteActivity.class);
                intent.putExtra("lat",driverOrder.getStartPointLat());
                intent.putExtra("lng",driverOrder.getStartPointLng());
                intent.putExtra("latend",driverOrder.getEndPointLat());
                intent.putExtra("lngend",driverOrder.getEndPointLng());
                intent.putExtra("weizhi",driverOrder.getDeparturePlace());
                startActivity(intent);

                break;
        }
    }

    //拨打电话
    private void callPhone() {
        //检查拨打电话权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + driverOrder.getDriverPhone()));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case PERMS_REQUEST_CODE:
                boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (storageAccepted) {
                    callPhone();
                } else {
                    Log.i("MainActivity", "没有权限操作这个请求");
                }
                break;

        }
    }
}
