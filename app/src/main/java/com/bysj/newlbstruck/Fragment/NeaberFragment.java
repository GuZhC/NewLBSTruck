package com.bysj.newlbstruck.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bysj.newlbstruck.Activity.BaseFragment;
import com.bysj.newlbstruck.Activity.LoginActivity;
import com.bysj.newlbstruck.Activity.PublishActivity;
import com.bysj.newlbstruck.Activity.PublishDriveActivity;
import com.bysj.newlbstruck.Constant;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.utils.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by guZhongC on 2018/5/28.
 * describe:
 */

public class NeaberFragment extends BaseFragment {
    @BindView(R.id.btn_register_put)
    Button btnRegisterPut;
    Unbinder unbinder;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_neaber;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_register_put)
    public void onViewClicked() {
        if ( SharedPreferenceUtil.instance(getContext()).getBoolean(Constant.IS_DRIV))
        startActivity(new Intent(getContext(),PublishDriveActivity.class));
        else{
            startActivity(new Intent(getContext(),PublishActivity.class));
        }
    }
}
