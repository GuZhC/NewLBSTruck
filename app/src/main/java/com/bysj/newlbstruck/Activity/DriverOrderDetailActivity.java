package com.bysj.newlbstruck.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView;

import com.bysj.newlbstruck.Bean.DriverOrder;
import com.bysj.newlbstruck.Bean.UserOrder;
import com.bysj.newlbstruck.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_order_detail);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("订单详情");

        initDetail();
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
}
