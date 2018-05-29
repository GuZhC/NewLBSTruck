package com.bysj.newlbstruck.adapter;

import android.support.annotation.Nullable;

import com.bysj.newlbstruck.Bean.DriverOrder;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.utils.StateEnum;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by liuchuan on 2018/5/29.
 */

public class AllDriverAdapter extends BaseQuickAdapter<DriverOrder, BaseViewHolder> {
    public AllDriverAdapter(@Nullable List<DriverOrder> data) {
        super(R.layout.item_all_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DriverOrder item) {
        helper.setText(R.id.tv_nearby_recycler_title, item.getDriverName());
        helper.setText(R.id.tv_nearby_recycler_send, item.getDestination());
        helper.setText(R.id.tv_nearby_recycler_access, item.getDeparturePlace());
//        for (StateEnum stateEnum : StateEnum.values()) {
//            if (stateEnum.getValue() == item.getState()) {
//                helper.setText(R.id.tv_nearby_recycler_time, stateEnum.getName());
//            }
//        }
    }
}
