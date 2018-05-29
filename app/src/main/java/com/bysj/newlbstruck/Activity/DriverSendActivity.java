package com.bysj.newlbstruck.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bysj.newlbstruck.Bean.DriverOrder;
import com.bysj.newlbstruck.Bean.UserOrder;
import com.bysj.newlbstruck.Constant;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.adapter.DriverOrderAdapter;
import com.bysj.newlbstruck.adapter.OrderAdapter;
import com.bysj.newlbstruck.utils.SharedPreferenceUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class DriverSendActivity extends BaseActivity {
    @BindView(R.id.home_myorder_all)
    RecyclerView homeMyorderAll;
    @BindView(R.id.reflesh)
    SmartRefreshLayout reflesh;
    private DriverOrderAdapter driverOrderAdapter;
    private List<DriverOrder> driverdatas;
    private Boolean isDriv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_send);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("司机发布的货车信息");
        setDriverData();
        reflesh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
            }
        });
        reflesh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                setDriverData();
                reflesh.finishRefresh(true);
            }
        });
    }

    private void setDriverData() {
        driverdatas = new ArrayList<>();
        BmobQuery<DriverOrder> query = new BmobQuery<DriverOrder>();
        String userid = SharedPreferenceUtil.instance(DriverSendActivity.this).getString(Constant.USER_ID);
        query.addWhereEqualTo("DriverId", userid);
        query.setLimit(50);
        query.findObjects(new FindListener<DriverOrder>() {
            @Override
            public void done(List<DriverOrder> object, BmobException e) {
                if (e == null) {
                    driverdatas.addAll(object);
                    homeMyorderAll.setLayoutManager(new LinearLayoutManager(DriverSendActivity.this));
                    driverOrderAdapter = new DriverOrderAdapter(driverdatas);
                    driverOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(DriverSendActivity.this, DriverOrderDetailActivity.class);
                            intent.putExtra("order",driverdatas.get(position));
                            startActivity(intent);
                        }
                    });
                    homeMyorderAll.setAdapter(driverOrderAdapter);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}
