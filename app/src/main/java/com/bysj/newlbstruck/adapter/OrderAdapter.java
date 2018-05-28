package com.bysj.newlbstruck.adapter;

import android.support.annotation.Nullable;

import com.bysj.newlbstruck.Bean.UserOrder;
import com.bysj.newlbstruck.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/8.
 * describe:
 */

public class OrderAdapter extends BaseQuickAdapter<UserOrder, BaseViewHolder> {
    public OrderAdapter(@Nullable List<UserOrder> data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserOrder item) {
        helper.setText(R.id.tv_nearby_recycler_title, item.getObjectId());
        helper.setText(R.id.tv_nearby_recycler_send, item.getDeliveryTime());
        helper.setText(R.id.tv_nearby_recycler_access, item.getDeliveryPlace());
        helper.setText(R.id.tv_nearby_recycler_time, item.getReceiptPlace());
    }
}
