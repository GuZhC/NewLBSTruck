package com.bysj.newlbstruck.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bysj.newlbstruck.Bean.UserOrder;
import com.bysj.newlbstruck.Constant;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.utils.SharedPreferenceUtil;
import com.bysj.newlbstruck.utils.StateEnum;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/8.
 * describe:
 */

public class OrderAdapter extends BaseQuickAdapter<UserOrder, BaseViewHolder> {
    private Boolean isDriv;
    private Context mcontext;
    public OrderAdapter(@Nullable List<UserOrder> data, Context context) {
        super(R.layout.item_order, data);
        mcontext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserOrder item) {
        isDriv = SharedPreferenceUtil.instance(mcontext).getBoolean(Constant.IS_DRIV);
        if(isDriv){
            helper.setText(R.id.tv_nearby_recycler_title, item.getUserName());
            helper.setText(R.id.username, "用户姓名:");
        }else {
            if(item.getDriverId()!=null){
                helper.setText(R.id.tv_nearby_recycler_title, item.getDriverName());
                helper.setText(R.id.username, "司机姓名:");
            }else {
                helper.setText(R.id.tv_nearby_recycler_title, item.getObjectId());
                helper.setText(R.id.username, "订单编号:");
            }

        }
        helper.setText(R.id.tv_nearby_recycler_send, item.getDeliveryPlace());
        helper.setText(R.id.tv_nearby_recycler_access, item.getReceiptPlace());
        for( StateEnum stateEnum : StateEnum.values()){
            if(stateEnum.getValue() == item.getState()){
                helper.setText(R.id.tv_nearby_recycler_time, stateEnum.getName());
            }
        }
    }
}
