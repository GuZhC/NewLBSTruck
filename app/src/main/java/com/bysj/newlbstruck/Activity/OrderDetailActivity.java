package com.bysj.newlbstruck.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;


import com.bysj.newlbstruck.Bean.DriverOrder;
import com.bysj.newlbstruck.Bean.User;
import com.bysj.newlbstruck.Bean.UserOrder;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.utils.StateEnum;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class OrderDetailActivity extends BaseActivity {
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
    @BindView(R.id.et_order_state)
    TextView etOrderState;
    @BindView(R.id.et_driver)
    TextView eTDriver;
//    @BindView(R.id.et_route)
//    TextView etRoute;

    @BindView(R.id.et_addgoods_money)
    TextView etAddgoodsMoney;
    @BindView(R.id.et_addgoods_location)
    TextView etAddgoodsLocation;
    @BindView(R.id.et_addgoods_locationtwo)
    TextView etAddgoodsLocationtwo;
    @BindView(R.id.reflesh)
    SmartRefreshLayout reflesh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("订单详情");
        initDetail();
        setState();

        reflesh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
            }
        });
        reflesh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initDetail();
                setState();
                reflesh.finishRefresh(true);
            }
        });
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
    }

    private void setState() {
        etOrderState.setText(StateEnum.getState(userOrder.getState()));
        if(userOrder.getDriverName()!=null){
            eTDriver.setText(userOrder.getDriverName());
        }else {
            eTDriver.setText("暂无");
        }

//        if (userOrder.getDriverId() != null) {
//            BmobQuery<User> query = new BmobQuery<User>();
//            query.getObject(userOrder.getDriverId(), new QueryListener<User>() {
//                @Override
//                public void done(User object, BmobException e) {
//                    if (e == null) {
//                        eTDriver.setText(object.getUsername());
//                    } else {
//                        Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
//                    }
//                }
//            });
//        }else {
//            eTDriver.setText("暂无");
//        }
//        if (userOrder.getDriverOrderId() != null) {
//            BmobQuery<DriverOrder> query = new BmobQuery<DriverOrder>();
//            query.getObject(userOrder.getDriverOrderId(), new QueryListener<DriverOrder>() {
//                @Override
//                public void done(DriverOrder object, BmobException e) {
//                    if (e == null) {
//                        etRoute.setText(object.getDeparturePlace() + "——>" + object.getDestination());
//                    } else {
//                        Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
//                    }
//                }
//
//            });
//        }else {
//            etRoute.setText("暂无");
//        }
    }
}
