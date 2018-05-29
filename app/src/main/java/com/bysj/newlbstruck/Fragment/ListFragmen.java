package com.bysj.newlbstruck.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bysj.newlbstruck.Activity.BaseFragment;
import com.bysj.newlbstruck.Activity.DriverDetailActivity;
import com.bysj.newlbstruck.Activity.DriverOrderDetailActivity;
import com.bysj.newlbstruck.Activity.OrderDetailActivity;
import com.bysj.newlbstruck.Activity.UserDetailActivity;
import com.bysj.newlbstruck.Bean.DriverOrder;
import com.bysj.newlbstruck.Bean.UserOrder;
import com.bysj.newlbstruck.Constant;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.adapter.AllDriverAdapter;
import com.bysj.newlbstruck.adapter.AllUserAdapter;
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

public class ListFragmen extends BaseFragment {
    @BindView(R.id.home_myorder_all)
    RecyclerView homeMyorderAll;
    @BindView(R.id.reflesh)
    SmartRefreshLayout reflesh;
    @BindView(R.id.list_title)
    TextView list_title;

    private AllUserAdapter adapter;
    private AllDriverAdapter driverOrderAdapter;
    private List<UserOrder> datas;
    private List<DriverOrder> driverdatas;
    private Boolean isDriv;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        isDriv = SharedPreferenceUtil.instance(getContext()).getBoolean(Constant.IS_DRIV);
        LoadData();
        reflesh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
            }
        });
        reflesh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                LoadData();
                reflesh.finishRefresh(true);
            }
        });
    }

    private void LoadData(){
        datas = new ArrayList<>();
        driverdatas = new ArrayList<>();
        if (isDriv) {
            //司机展示用户信息

            list_title.setText("附近用户");
            setAllUserData();
        } else {
            //用户展示司机信息
            list_title.setText("附近司机");
            setAllDriverData();
        }
    }

    private void setAllDriverData() {
        BmobQuery<DriverOrder> query = new BmobQuery<DriverOrder>();
        String userid = SharedPreferenceUtil.instance(getContext()).getString(Constant.USER_ID);
//        query.addWhereEqualTo("DriverId", userid);
//        query.addWhereEqualTo("state", 0);
        query.setLimit(50);
        query.findObjects(new FindListener<DriverOrder>() {
            @Override
            public void done(List<DriverOrder> object, BmobException e) {
                if (e == null) {
                    driverdatas.addAll(object);
                    homeMyorderAll.setLayoutManager(new LinearLayoutManager(getContext()));
                    driverOrderAdapter = new AllDriverAdapter(driverdatas);
                    driverOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(getContext(), DriverDetailActivity.class);
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

    private void setAllUserData() {
        BmobQuery<UserOrder> query = new BmobQuery<UserOrder>();
        String userid = SharedPreferenceUtil.instance(getContext()).getString(Constant.USER_ID);
//        query.addWhereEqualTo("UserId", userid);
        query.addWhereEqualTo("state", 0);
        query.setLimit(50);
        query.findObjects(new FindListener<UserOrder>() {
            @Override
            public void done(List<UserOrder> object, BmobException e) {
                if (e == null) {
                    datas.addAll(object);
                    homeMyorderAll.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new AllUserAdapter(datas);
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(getContext(), UserDetailActivity.class);
                            intent.putExtra("order",datas.get(position));
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
