package com.bysj.newlbstruck.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bysj.newlbstruck.Bean.UserOrder;
import com.bysj.newlbstruck.Constant;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.utils.SharedPreferenceUtil;
import com.bysj.newlbstruck.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


//发布订单
public class PublishDriveActivity extends BaseActivity {

    @BindView(R.id.et_addgoods_type)
    EditText etAddgoodsType;
    @BindView(R.id.et_addgoods_num)
    EditText etAddgoodsNum;
    @BindView(R.id.et_addgoods_weight)
    EditText etAddgoodsWeight;
    @BindView(R.id.et_addgoods_baozhuangtype)
    EditText etAddgoodsBaozhuangtype;
    @BindView(R.id.et_addgoods_goodssize)
    EditText etAddgoodsGoodssize;
    @BindView(R.id.et_addgoods_time)
    TextView etAddgoodsTime;
    @BindView(R.id.et_addgoods_time_two)
    TextView etAddgoodsTimeTwo;
    @BindView(R.id.et_addgoods_money)
    TextView etAddgoodsMoney;
    @BindView(R.id.et_addgoods_location)
    EditText etAddgoodsLocation;
    @BindView(R.id.et_addgoods_locationtwo)
    EditText etAddgoodsLocationtwo;
    @BindView(R.id.btn_addgoods)
    Button btnAddgoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("信息填写");
    }

    @OnClick({R.id.et_addgoods_time, R.id.et_addgoods_time_two, R.id.et_addgoods_money, R.id.btn_addgoods})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_addgoods_time:
                TimePickerView pvTime = new TimePickerBuilder(PublishDriveActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        etAddgoodsTime.setText(sdf.format(date));
                    }
                })
                        .setTextColorCenter(getResources().getColor(R.color.blue)) //设置选中项文字颜色
                        .build();
                pvTime.show();
                break;
            case R.id.et_addgoods_time_two:
                TimePickerView pvTimetwo = new TimePickerBuilder(PublishDriveActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        etAddgoodsTimeTwo.setText(sdf.format(date));
                    }
                })
                        .setTextColorCenter(getResources().getColor(R.color.blue)) //设置选中项文字颜色
                        .build();
                pvTimetwo.show();
                break;
            case R.id.et_addgoods_money:
                final List<String> datasB = new LinkedList<>();
                datasB.add("100以下");
                datasB.add("100-300");
                datasB.add("300-500");
                datasB.add("500-700");
                datasB.add("700-1000");
                datasB.add("1000-1500");
                datasB.add("1500-2000");
                datasB.add("2000以上");
                hideKeyboard(etAddgoodsMoney.getWindowToken());
                optionsPicker(datasB, etAddgoodsMoney);
                break;
            case R.id.btn_addgoods:
                doPost();
                break;
        }
    }

    private void doPost() {
        if (TextUtils.isEmpty(etAddgoodsNum.getText().toString()) || TextUtils.isEmpty(etAddgoodsWeight.getText().toString()) ||
                TextUtils.isEmpty(etAddgoodsBaozhuangtype.getText().toString()) || TextUtils.isEmpty(etAddgoodsGoodssize.getText().toString()) ||
                TextUtils.isEmpty(etAddgoodsTime.getText().toString()) || TextUtils.isEmpty(etAddgoodsTimeTwo.getText().toString()) ||
                TextUtils.isEmpty(etAddgoodsMoney.getText().toString()) || TextUtils.isEmpty(etAddgoodsLocation.getText().toString()) ||
                TextUtils.isEmpty(etAddgoodsLocationtwo.getText().toString()) || TextUtils.isEmpty(etAddgoodsType.getText().toString())) {
            ToastUtils.showError(PublishDriveActivity.this, "请填写完整信息");
        } else {

            UserOrder userOrder = new UserOrder();
            userOrder.setCategoryGoods(etAddgoodsType.getText().toString());
            userOrder.setUserId(SharedPreferenceUtil.instance(this).getString(Constant.USER_ID));
            userOrder.setStartPointLat("11111");
            userOrder.setStartPointLng("22222");
            userOrder.setReceiptTime(etAddgoodsTimeTwo.getText().toString());
            userOrder.setReceiptPlace(etAddgoodsLocationtwo.getText().toString());
            userOrder.setPackingForm(etAddgoodsBaozhuangtype.getText().toString());
            userOrder.setPackagingSize(etAddgoodsGoodssize.getText().toString());
            userOrder.setNumber(etAddgoodsNum.getText().toString());

            userOrder.setFreightLimit(etAddgoodsMoney.getText().toString());
            userOrder.setEndPointLat("33333");
            userOrder.setEndPointLng("444444");
            userOrder.setDeliveryTime(etAddgoodsTime.getText().toString());
            userOrder.setDeliveryPlace(etAddgoodsLocation.getText().toString());
            userOrder.setWeight(etAddgoodsWeight.getText().toString());
            userOrder.save(new SaveListener<String>() {
                               @Override
                               public void done(String s, BmobException e) {
                                   if (e == null) {
                                       ToastUtils.showSuccess(PublishDriveActivity.this, "发布成功");
                                       finish();
                                   } else {
                                       ToastUtils.showError(PublishDriveActivity.this, "请填写完整信息|" + e.getMessage());
                                   }
                               }
                           }
            );
        }
    }

    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void optionsPicker(final List<String> datas, final TextView textView) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(PublishDriveActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                textView.setText(datas.get(options1).toString());
            }
        })
                .setTextColorCenter(getResources().getColor(R.color.blue)) //设置选中项文字颜色
                .setOutSideCancelable(false)
                .build();
        pvOptions.setPicker(datas);
        pvOptions.show();
    }
}
