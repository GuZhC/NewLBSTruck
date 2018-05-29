package com.bysj.newlbstruck.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.bysj.newlbstruck.Bean.DriverOrder;
import com.bysj.newlbstruck.Bean.User;
import com.bysj.newlbstruck.Bean.UserOrder;
import com.bysj.newlbstruck.Constant;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.utils.SharedPreferenceUtil;
import com.bysj.newlbstruck.utils.StateEnum;
import com.bysj.newlbstruck.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

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
    @BindView(R.id.userdriver)
    TextView userdriver;
    @BindView(R.id.operation_user)
    Button operation_user;
    @BindView(R.id.operation_driver)
    Button operation_driver;
    private Boolean isDriv;
    private String userOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("订单详情");
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

        //查询数据
        userOrderId = getIntent().getStringExtra("orderid");
        userOrder = new UserOrder();
        BmobQuery<UserOrder> query = new BmobQuery<UserOrder>();
        query.getObject(userOrderId, new QueryListener<UserOrder>() {
            @Override
            public void done(UserOrder object, BmobException e) {
                if (e == null) {
                    userOrder.setCategoryGoods(object.getCategoryGoods());
                    userOrder.setNumber(object.getNumber());
                    userOrder.setWeight(object.getWeight());
                    userOrder.setPackingForm(object.getPackingForm());
                    userOrder.setPackagingSize(object.getPackagingSize());
                    userOrder.setDeliveryTime(object.getDeliveryTime());
                    userOrder.setReceiptTime(object.getReceiptTime());
                    userOrder.setFreightLimit(object.getFreightLimit());
                    userOrder.setDeliveryPlace(object.getDeliveryPlace());
                    userOrder.setReceiptPlace(object.getReceiptPlace());
                    userOrder.setState(object.getState());
                    userOrder.setUserName(object.getUserName());
                    userOrder.setDriverName(object.getDriverName());
                    userOrder.setObjectId(object.getObjectId());
                    setData();
                    setState();
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }

        });
    }

    private void setData() {
        //设置数据
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
        isDriv = SharedPreferenceUtil.instance(OrderDetailActivity.this).getBoolean(Constant.IS_DRIV);
        if (isDriv) {
            userdriver.setText("用户姓名");
            eTDriver.setText(userOrder.getUserName());
            //不同状态显示不同按钮
            if (userOrder.getState() == StateEnum.RECEIPT.getValue()) {
                operation_driver.setVisibility(View.VISIBLE);
                operation_driver.setText("已到达出发地");
            } else if (userOrder.getState() == StateEnum.ARRIVE.getValue()) {
                operation_driver.setVisibility(View.VISIBLE);
                operation_driver.setText("开始运货");
            } else if (userOrder.getState() == StateEnum.DELIVERY.getValue()) {
                operation_driver.setVisibility(View.VISIBLE);
                operation_driver.setText("已完成");
            } else {
                operation_driver.setVisibility(View.GONE);
            }
        } else {
            userdriver.setText("司机姓名");
            if (userOrder.getDriverName() != null) {
                eTDriver.setText(userOrder.getDriverName());
            } else {
                eTDriver.setText("暂无");
            }
            if (userOrder.getState() == StateEnum.COMPLETE.getValue()) {
                operation_user.setVisibility(View.VISIBLE);
                operation_user.setText("支付订单");
            }else {
                operation_user.setVisibility(View.GONE);
            }
        }
    }

    @OnClick({R.id.operation_user, R.id.operation_driver})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.operation_user:
                if (userOrder.getState() == StateEnum.COMPLETE.getValue()) {
                    userOrder.setState(StateEnum.PAYMENT.getValue());
                    updateData();
                }
                break;
            case R.id.operation_driver:
                //司机操作
                if (userOrder.getState() == StateEnum.RECEIPT.getValue()) {
                    userOrder.setState(StateEnum.ARRIVE.getValue());
                    updateData();
                } else if (userOrder.getState() == StateEnum.ARRIVE.getValue()) {
                    userOrder.setState(StateEnum.DELIVERY.getValue());
                    updateData();
                } else if (userOrder.getState() == StateEnum.DELIVERY.getValue()) {
                    userOrder.setState(StateEnum.COMPLETE.getValue());
                    updateData();
                }
                break;
        }
    }

    private void updateData() {
        userOrder.update(userOrder.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.showSuccess(OrderDetailActivity.this, "更新成功");
                    initDetail();
                } else {
                    ToastUtils.showError(OrderDetailActivity.this, "更新失败");
                }
            }
        });
    }


}
