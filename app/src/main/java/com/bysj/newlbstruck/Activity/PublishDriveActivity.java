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
import com.bysj.newlbstruck.Bean.DriverOrder;
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


    @BindView(R.id.et_tujindi)
    EditText etTujindi;
    @BindView(R.id.et_huowuleibie)
    EditText etHuowuleibie;
    @BindView(R.id.et_yunfeixianzhi)
    TextView etYunfeixianzhi;
    @BindView(R.id.et_konyudunwei)
    EditText etKonyudunwei;
    @BindView(R.id.et_mudidi)
    EditText etMudidi;
    @BindView(R.id.et_facheshijian)
    TextView etFacheshijian;
    @BindView(R.id.et_chufadi)
    EditText etChufadi;
    @BindView(R.id.et_chexianchicun)
    EditText etChexianchicun;
    @BindView(R.id.et_qichexinhao)
    EditText etQichexinhao;
    @BindView(R.id.et_yunshudunwei)
    EditText etYunshudunwei;
    @BindView(R.id.btn_adddrive)
    Button btnAdddrive;
    @BindView(R.id.et_daodashijian)
    TextView etDaodashijian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishdrive);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("信息填写");
    }

    private void doPost() {
        if (TextUtils.isEmpty(etTujindi.getText().toString()) || TextUtils.isEmpty(etHuowuleibie.getText().toString()) ||
                TextUtils.isEmpty(etYunfeixianzhi.getText().toString()) || TextUtils.isEmpty(etKonyudunwei.getText().toString()) ||
                TextUtils.isEmpty(etMudidi.getText().toString()) || TextUtils.isEmpty(etFacheshijian.getText().toString()) ||
                TextUtils.isEmpty(etChufadi.getText().toString()) || TextUtils.isEmpty(etChexianchicun.getText().toString()) ||
                TextUtils.isEmpty(etQichexinhao.getText().toString()) || TextUtils.isEmpty(etYunshudunwei.getText().toString())) {
            ToastUtils.showError(PublishDriveActivity.this, "请填写完整信息");
        } else {
            DriverOrder driverOrder = new DriverOrder();
            driverOrder.setState(0);
            driverOrder.setArrivalTime(etDaodashijian.getText().toString());
            driverOrder.setDriverId(SharedPreferenceUtil.instance(this).getString(Constant.USER_ID));
            driverOrder.setDriverName(SharedPreferenceUtil.instance(this).getString(Constant.NAME_USER));
            driverOrder.setStartPointLat("11111");
            driverOrder.setStartPointLng("22222");
            driverOrder.setPathways(etTujindi.getText().toString());
            driverOrder.setHopeGoodsClass(etHuowuleibie.getText().toString());
            driverOrder.setFreightLimit(etYunfeixianzhi.getText().toString());
            driverOrder.setFreeTonnage(etKonyudunwei.getText().toString());
            driverOrder.setDestination(etMudidi.getText().toString());

            driverOrder.setDepartureTime(etFacheshijian.getText().toString());
            driverOrder.setEndPointLat("33333");
            driverOrder.setEndPointLng("444444");
            driverOrder.setDeparturePlace(etChufadi.getText().toString());
            driverOrder.setCarSize(etChexianchicun.getText().toString());
            driverOrder.setCarModel(etQichexinhao.getText().toString());
            driverOrder.setTransportTonnage(etYunshudunwei.getText().toString());
            driverOrder.save(new SaveListener<String>() {
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

    @OnClick({R.id.et_yunfeixianzhi, R.id.btn_adddrive,R.id.et_daodashijian,R.id.et_facheshijian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_yunfeixianzhi:
                final List<String> datasB = new LinkedList<>();
                datasB.add("100以下");
                datasB.add("100-300");
                datasB.add("300-500");
                datasB.add("500-700");
                datasB.add("700-1000");
                datasB.add("1000-1500");
                datasB.add("1500-2000");
                datasB.add("2000以上");
                hideKeyboard(etYunfeixianzhi.getWindowToken());
                optionsPicker(datasB, etYunfeixianzhi);
                break;
            case R.id.btn_adddrive:
                doPost();
                break;
                case R.id.et_daodashijian:
                    TimePickerView pvTime = new TimePickerBuilder(PublishDriveActivity.this, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            etDaodashijian.setText(sdf.format(date));
                        }
                    })
                            .setTextColorCenter(getResources().getColor(R.color.blue)) //设置选中项文字颜色
                            .build();
                    pvTime.show();
                break;
                case R.id.et_facheshijian:
                    TimePickerView pvTimetwo = new TimePickerBuilder(PublishDriveActivity.this, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            etFacheshijian.setText(sdf.format(date));
                        }
                    })
                            .setTextColorCenter(getResources().getColor(R.color.blue)) //设置选中项文字颜色
                            .build();
                    pvTimetwo.show();
                break;
        }
    }

}
