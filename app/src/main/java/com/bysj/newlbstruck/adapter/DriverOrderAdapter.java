package com.bysj.newlbstruck.adapter;

import android.support.annotation.Nullable;

import com.bysj.newlbstruck.Bean.DriverOrder;
import com.bysj.newlbstruck.Bean.UserOrder;
import com.bysj.newlbstruck.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by liuchuan on 2018/5/28.
 */

public class DriverOrderAdapter  extends BaseQuickAdapter<DriverOrder, BaseViewHolder> {
    public DriverOrderAdapter(@Nullable List<DriverOrder> data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DriverOrder item) {
        helper.setText(R.id.tv_nearby_recycler_title, item.getObjectId());
        helper.setText(R.id.tv_nearby_recycler_send, item.getCarSize());
        helper.setText(R.id.tv_nearby_recycler_access, item.getDeparturePlace());
        helper.setText(R.id.tv_nearby_recycler_time, item.getCarModel());
    }
}
