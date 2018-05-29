package com.bysj.newlbstruck.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class DriverDetailActivity extends BaseActivity {
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
    @BindView(R.id.reflesh)
    SmartRefreshLayout reflesh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_detail);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("司机详情");
        initDetail();
        reflesh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
            }
        });
        reflesh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initDetail();
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
        user_name.setText(driverOrder.getDriverName());

    }
}
