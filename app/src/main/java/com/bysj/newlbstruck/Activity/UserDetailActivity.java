package com.bysj.newlbstruck.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bysj.newlbstruck.Bean.UserOrder;
import com.bysj.newlbstruck.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.et_order_state)
    TextView etOrderState;
    @BindView(R.id.et_driver)
    TextView eTDriver;
    @BindView(R.id.et_route)
    TextView etRoute;

    @BindView(R.id.et_addgoods_money)
    TextView etAddgoodsMoney;
    @BindView(R.id.et_addgoods_location)
    TextView etAddgoodsLocation;
    @BindView(R.id.et_addgoods_locationtwo)
    TextView etAddgoodsLocationtwo;
    @BindView(R.id.user_name)
    TextView user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        setBackBtn();
        initDetail();
        setTitle("用户详情");
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
}
