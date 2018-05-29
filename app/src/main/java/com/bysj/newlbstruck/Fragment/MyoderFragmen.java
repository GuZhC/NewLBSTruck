package com.bysj.newlbstruck.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bysj.newlbstruck.Activity.BaseFragment;
import com.bysj.newlbstruck.Activity.DriverOrderDetailActivity;
import com.bysj.newlbstruck.Activity.ModifyUserActivity;
import com.bysj.newlbstruck.Activity.OrderDetailActivity;
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
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by guZhongC on 2018/5/28.
 * describe:
 */

public class MyoderFragmen extends BaseFragment {
    @BindView(R.id.home_myorder_all)
    RecyclerView homeMyorderAll;
    @BindView(R.id.reflesh)
    SmartRefreshLayout reflesh;
    @BindView(R.id.order_title)
    TextView order_title;
    private OrderAdapter adapter;
    private List<UserOrder> datas;
    private Boolean isDriv;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_my_oder;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        isDriv = SharedPreferenceUtil.instance(getContext()).getBoolean(Constant.IS_DRIV);
        if(isDriv){
            order_title.setText("所接订单");
        }else {
            order_title.setText("所发订单");
        }
        setUserData();
        reflesh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
            }
        });
        reflesh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                setUserData();
                reflesh.finishRefresh(true);
            }
        });
    }

//    private void LoadData() {
//        datas = new ArrayList<>();
//        driverdatas = new ArrayList<>();
//        setDriverData();
//    }

//    private void setDriverData() {
//        BmobQuery<DriverOrder> query = new BmobQuery<DriverOrder>();
//        String userid = SharedPreferenceUtil.instance(getContext()).getString(Constant.USER_ID);
//        query.addWhereEqualTo("DriverId", userid);
//        query.setLimit(50);
//        query.findObjects(new FindListener<DriverOrder>() {
//            @Override
//            public void done(List<DriverOrder> object, BmobException e) {
//                if (e == null) {
//                    driverdatas.addAll(object);
//                    homeMyorderAll.setLayoutManager(new LinearLayoutManager(getContext()));
//                    driverOrderAdapter = new DriverOrderAdapter(driverdatas);
//                    driverOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                            Intent intent = new Intent(getContext(), DriverOrderDetailActivity.class);
//                            intent.putExtra("order", driverdatas.get(position));
//                            startActivity(intent);
//                        }
//                    });
//                    homeMyorderAll.setAdapter(driverOrderAdapter);
//                } else {
//                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
//                }
//            }
//        });
//    }

    private void setUserData() {
        datas = new ArrayList<>();
        BmobQuery<UserOrder> query = new BmobQuery<UserOrder>();
        String userid = SharedPreferenceUtil.instance(getContext()).getString(Constant.USER_ID);
        if(isDriv){
            query.addWhereEqualTo("DriverId", userid);
        }else {
            query.addWhereEqualTo("UserId", userid);
        }
        query.setLimit(50);
        query.findObjects(new FindListener<UserOrder>() {
            @Override
            public void done(List<UserOrder> object, BmobException e) {
                if (e == null) {
                    datas.addAll(object);
                    homeMyorderAll.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new OrderAdapter(datas,getContext());
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                            intent.putExtra("orderid", datas.get(position).getObjectId());
                            startActivity(intent);
                        }
                    });
                    homeMyorderAll.setAdapter(adapter);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}
