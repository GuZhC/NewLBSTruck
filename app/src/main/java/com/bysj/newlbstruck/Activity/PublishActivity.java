package com.bysj.newlbstruck.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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
import com.bysj.newlbstruck.adapter.PoiAdapter;
import com.bysj.newlbstruck.lbs.GaodeLbsLayerImpl;
import com.bysj.newlbstruck.lbs.ILbsLayer;
import com.bysj.newlbstruck.lbs.LocationInfo;
import com.bysj.newlbstruck.utils.SharedPreferenceUtil;
import com.bysj.newlbstruck.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


//发布订单
public class PublishActivity extends BaseActivity {

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
    AutoCompleteTextView etChufadi;
    @BindView(R.id.et_addgoods_locationtwo)
    AutoCompleteTextView etMudidi;
    @BindView(R.id.btn_addgoods)
    Button btnAddgoods;

    private PoiAdapter mEndAdapter;
    private PoiAdapter mStartAdapter;
    private ILbsLayer mLbsLayer;
    private LocationInfo mStartLocation;
    private LocationInfo mEndLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("信息填写");

        mLbsLayer = new GaodeLbsLayerImpl(this);
        mLbsLayer.onCreate(savedInstanceState);

        etChufadi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //  关键搜索推荐地点
                mLbsLayer.poiSearch(s.toString(), new ILbsLayer.OnSearchedListener() {
                    @Override
                    public void onSearched(List<LocationInfo> results) {
                        // 更新列表
                        updatePoiList(results, true);
                    }

                    @Override
                    public void onError(int rCode) {

                    }
                });
            }
        });

        etMudidi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //  关键搜索推荐地点
                mLbsLayer.poiSearch(s.toString(), new ILbsLayer.OnSearchedListener() {
                    @Override
                    public void onSearched(List<LocationInfo> results) {
                        // 更新列表
                        updatePoiList(results, false);
                    }

                    @Override
                    public void onError(int rCode) {

                    }
                });
            }
        });
    }

    /**
     * 更新 POI 列表
     *
     * @param results
     */
    private void updatePoiList(final List<LocationInfo> results, boolean isStart) {

        List<String> listString = new ArrayList<String>();
        for (int i = 0; i < results.size(); i++) {
            listString.add(results.get(i).getName());
        }
        if (isStart){
            if (mStartAdapter == null) {
                mStartAdapter = new PoiAdapter(getApplicationContext(), listString);
                etChufadi.setAdapter(mStartAdapter);
            } else {
                mStartAdapter.setData(listString);
            }
        } else{
            if (mEndAdapter == null) {
                mEndAdapter = new PoiAdapter(getApplicationContext(), listString);
                etMudidi.setAdapter(mEndAdapter);
            } else {
                mEndAdapter.setData(listString);
            }
        }

        if (isStart){
            etChufadi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                ToastUtils.showSuccess(PublishDriveActivity.this, results.get(position).getName());
                    //  记录终点
                    mStartLocation = results.get(position);
                    mStartLocation.setKey("10000end");
                }
            });
            mStartAdapter.notifyDataSetChanged();
        }else {
            etMudidi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                ToastUtils.showSuccess(PublishDriveActivity.this, results.get(position).getName());
                    //  记录终点
                    mEndLocation = results.get(position);
                    mEndLocation.setKey("10000start");
                }
            });
            mEndAdapter.notifyDataSetChanged();
        }

    }


    @OnClick({R.id.et_addgoods_time, R.id.et_addgoods_time_two, R.id.et_addgoods_money, R.id.btn_addgoods})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_addgoods_time:
                TimePickerView pvTime = new TimePickerBuilder(PublishActivity.this, new OnTimeSelectListener() {
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
                TimePickerView pvTimetwo = new TimePickerBuilder(PublishActivity.this, new OnTimeSelectListener() {
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
                TextUtils.isEmpty(etAddgoodsMoney.getText().toString()) || TextUtils.isEmpty(etChufadi.getText().toString()) ||
                TextUtils.isEmpty(etMudidi.getText().toString()) || TextUtils.isEmpty(etAddgoodsType.getText().toString())) {
            ToastUtils.showError(PublishActivity.this, "请填写完整信息");
        } else {

            UserOrder userOrder = new UserOrder();
            userOrder.setState(0);
            String phone = SharedPreferenceUtil.instance(this).getString(Constant.PHONE);
            userOrder.setUserPhone(phone);
            userOrder.setCategoryGoods(etAddgoodsType.getText().toString());
            userOrder.setUserId(SharedPreferenceUtil.instance(this).getString(Constant.USER_ID));
            userOrder.setUserName(SharedPreferenceUtil.instance(this).getString(Constant.NAME_USER));
            userOrder.setStartPointLat( String.valueOf( mStartLocation.getLatitude()));
            userOrder.setStartPointLng(String.valueOf( mStartLocation.getLongitude()));
            userOrder.setReceiptTime(etAddgoodsTimeTwo.getText().toString());
            userOrder.setReceiptPlace(etMudidi.getText().toString());
            userOrder.setPackingForm(etAddgoodsBaozhuangtype.getText().toString());
            userOrder.setPackagingSize(etAddgoodsGoodssize.getText().toString());
            userOrder.setNumber(etAddgoodsNum.getText().toString());

            userOrder.setFreightLimit(etAddgoodsMoney.getText().toString());
            userOrder.setEndPointLat(String.valueOf( mEndLocation.getLatitude()));
            userOrder.setEndPointLng(String.valueOf( mEndLocation.getLongitude()));
            userOrder.setDeliveryTime(etAddgoodsTime.getText().toString());
            userOrder.setDeliveryPlace(etChufadi.getText().toString());
            userOrder.setWeight(etAddgoodsWeight.getText().toString());
            userOrder.setUserName(SharedPreferenceUtil.instance(this).getString(Constant.NAME_USER));
            userOrder.save(new SaveListener<String>() {
                               @Override
                               public void done(String s, BmobException e) {
                                   if (e == null) {
                                       ToastUtils.showSuccess(PublishActivity.this, "发布成功");
                                       finish();
                                   } else {
                                       ToastUtils.showError(PublishActivity.this, "请填写完整信息|" + e.getMessage());
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
        OptionsPickerView pvOptions = new OptionsPickerBuilder(PublishActivity.this, new OnOptionsSelectListener() {
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
