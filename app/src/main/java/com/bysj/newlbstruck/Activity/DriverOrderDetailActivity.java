package com.bysj.newlbstruck.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
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

public class DriverOrderDetailActivity extends BaseActivity {
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
//    @BindView(R.id.et_order_state)
//    TextView etOrderState;
//    @BindView(R.id.et_driver)
//    TextView eTDriver;
//    @BindView(R.id.et_route)
//    TextView etRoute;
    @BindView(R.id.reflesh)
    SmartRefreshLayout reflesh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_order_detail);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("信息详情");
        initDetail();
//        setState();
        reflesh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
            }
        });
        reflesh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initDetail();
//                setState();
                reflesh.finishRefresh(true);
            }
        });
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

    }

//    private void setState() {
//        etOrderState.setText(StateEnum.getState(driverOrder.getState()));
//        if (driverOrder.getUserId() != null) {
//            BmobQuery<User> query = new BmobQuery<User>();
//            query.getObject(driverOrder.getUserId(), new QueryListener<User>() {
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
//        if (driverOrder.getUserOrderId() != null) {
//            BmobQuery<UserOrder> query = new BmobQuery<UserOrder>();
//            query.getObject(driverOrder.getUserOrderId(), new QueryListener<UserOrder>() {
//                @Override
//                public void done(UserOrder object, BmobException e) {
//                    if (e == null) {
//                        etRoute.setText(object.getDeliveryPlace() + "——>" + object.getReceiptPlace());
//                    } else {
//                        Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
//                    }
//                }
//
//            });
//        }else {
//            eTDriver.setText("暂无");
//        }
//    }
}
