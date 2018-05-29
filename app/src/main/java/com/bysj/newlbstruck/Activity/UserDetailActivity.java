package com.bysj.newlbstruck.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bysj.newlbstruck.Bean.UserOrder;
import com.bysj.newlbstruck.Constant;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.utils.SharedPreferenceUtil;
import com.bysj.newlbstruck.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UserDetailActivity extends BaseActivity {
    private UserOrder userOrder;
    @BindView(R.id.et_addgoods_type)
    TextView etAddgoodsType;
    @BindView(R.id.et_addgoods_num)
    TextView etAddgoodsNum;
    @BindView(R.id.et_addgoods_weight)
    TextView etAddgoodsWeight;
    @BindView(R.id.et_addgoods_baozhuangtype)
    TextView etAddgoodsBaozhuangtype;
    @BindView(R.id.et_addgoods_goodssize)
    TextView etAddgoodsGoodssize;
    @BindView(R.id.et_addgoods_time)
    TextView etAddgoodsTime;
    @BindView(R.id.et_addgoods_time_two)
    TextView etAddgoodsTimeTwo;

    @BindView(R.id.et_addgoods_money)
    TextView etAddgoodsMoney;
    @BindView(R.id.et_addgoods_location)
    TextView etAddgoodsLocation;
    @BindView(R.id.et_addgoods_locationtwo)
    TextView etAddgoodsLocationtwo;
    @BindView(R.id.user_name)
    TextView user_name;

    private String[] perms = {Manifest.permission.CALL_PHONE};
    private final int PERMS_REQUEST_CODE = 200;

    //    @BindView(R.id.reflesh)
//    SmartRefreshLayout reflesh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        setBackBtn();
        initDetail();
        setTitle("用户详情");
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
        userOrder = (UserOrder) getIntent().getSerializableExtra("order");
        etAddgoodsType.setText(userOrder.getCategoryGoods());
        etAddgoodsNum.setText(userOrder.getNumber());
        etAddgoodsWeight.setText(userOrder.getWeight());
        etAddgoodsBaozhuangtype.setText(userOrder.getPackingForm());
        etAddgoodsGoodssize.setText(userOrder.getPackagingSize());
        etAddgoodsTime.setText(userOrder.getDeliveryTime());
        etAddgoodsTimeTwo.setText(userOrder.getReceiptTime());
        etAddgoodsMoney.setText(userOrder.getFreightLimit());
        etAddgoodsLocation.setText(userOrder.getDeliveryPlace());
        etAddgoodsLocationtwo.setText(userOrder.getReceiptPlace());
        user_name.setText(userOrder.getUserName());

    }

    @OnClick({R.id.telphone, R.id.see_route, R.id.jieta})
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
                intent.putExtra("lat",userOrder.getStartPointLat());
                intent.putExtra("lng",userOrder.getStartPointLng());
                intent.putExtra("latend",userOrder.getEndPointLat());
                intent.putExtra("lngend",userOrder.getEndPointLng());
                intent.putExtra("weizhi",userOrder.getDeliveryPlace());
                startActivity(intent);
                break;
            case R.id.jieta:
                ToastUtils.showSuccess(UserDetailActivity.this, "系统为您接单中，请稍等");
                ReceiptOrder();
                break;
        }
    }
    //拨打电话
    private void callPhone() {
        //检查拨打电话权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + userOrder.getUserPhone()));
            startActivity(intent);
        }
    }
    private void ReceiptOrder() {
        String userid = SharedPreferenceUtil.instance(this).getString(Constant.USER_ID);
        String username = SharedPreferenceUtil.instance(this).getString(Constant.NAME_USER);
        userOrder.setDriverId(userid);
        userOrder.setDriverName(username);
        userOrder.setState(1);
        if (userid != null && userOrder.getObjectId() != null) {
            userOrder.update(userOrder.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        ToastUtils.showSuccess(UserDetailActivity.this, "接单成功");
                    } else {
                        ToastUtils.showError(UserDetailActivity.this, "接单失败，请检查您的网络设置");
                    }
                }
            });
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
