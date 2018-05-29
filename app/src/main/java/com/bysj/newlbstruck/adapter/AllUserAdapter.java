package com.bysj.newlbstruck.adapter;

import android.support.annotation.Nullable;

import com.bysj.newlbstruck.Bean.DriverOrder;
import com.bysj.newlbstruck.Bean.UserOrder;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.utils.StateEnum;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by liuchuan on 2018/5/29.
 */

public class AllUserAdapter extends BaseQuickAdapter<UserOrder, BaseViewHolder> {
    public AllUserAdapter(@Nullable List<UserOrder> data) {
        super(R.layout.item_all_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserOrder item) {
        helper.setText(R.id.tv_nearby_recycler_title, item.getUserName());
        helper.setText(R.id.tv_nearby_recycler_send, item.getDeliveryPlace());
        helper.setText(R.id.tv_nearby_recycler_access, item.getReceiptPlace());
//        for( StateEnum stateEnum : StateEnum.values()){
//            if(stateEnum.getValue() == item.getState()){
//                helper.setText(R.id.tv_nearby_recycler_time, stateEnum.getName());
//            }
//        }
    }
}
