package com.bysj.newlbstruck.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


import com.bysj.newlbstruck.Bean.DriverOrder;
import com.bysj.newlbstruck.Bean.UserOrder;
import com.bysj.newlbstruck.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.et_addgoods_money)
    TextView etAddgoodsMoney;
    @BindView(R.id.et_addgoods_location)
    TextView etAddgoodsLocation;
    @BindView(R.id.et_addgoods_locationtwo)
    TextView etAddgoodsLocationtwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("订单详情");
        initDetail();
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
}
