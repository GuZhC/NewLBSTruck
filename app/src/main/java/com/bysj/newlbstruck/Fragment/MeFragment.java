package com.bysj.newlbstruck.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bysj.newlbstruck.Activity.AboutActivity;
import com.bysj.newlbstruck.Activity.BaseFragment;
import com.bysj.newlbstruck.Activity.DriverSendActivity;
import com.bysj.newlbstruck.Activity.LoginActivity;
import com.bysj.newlbstruck.Activity.ModifyUserActivity;
import com.bysj.newlbstruck.Bean.User;
import com.bysj.newlbstruck.Constant;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.utils.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by guZhongC on 2018/5/28.
 * describe:
 */

public class MeFragment extends BaseFragment {
    @BindView(R.id.username)
    TextView eUsername;
    @BindView(R.id.userphone)
    TextView eUserphone;
    @BindView(R.id.l_me_safety)
    LinearLayout lMeSafety;
    @BindView(R.id.l_me_news)
    LinearLayout lMeNews;
    @BindView(R.id.l_me_aboutme)
    LinearLayout lMeAboutme;
    @BindView(R.id.driversend)
    LinearLayout driversend;
    private Boolean isDriv;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        String userid = SharedPreferenceUtil.instance(getContext()).getString(Constant.USER_ID);
        bmobQuery.getObject(userid, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                eUsername.setText(user.getUsername());
                eUserphone.setText(user.getMobilePhoneNumber());
            }
        });
        isDriv = SharedPreferenceUtil.instance(getContext()).getBoolean(Constant.IS_DRIV);
        if (isDriv) {
            driversend.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({
            R.id.l_me_safety, R.id.l_me_news, R.id.l_me_aboutme, R.id.me_toolbar_set, R.id.driversend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.me_toolbar_set:
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.l_me_safety:
                Intent intent = new Intent(getContext(), ModifyUserActivity.class);
                startActivity(intent);
                break;
            case R.id.l_me_news:
                startActivity(new Intent(getContext(), AboutActivity.class));
                break;
            case R.id.l_me_aboutme:
//                ToastUtils.showInfo(getContext(), "正在退出");
//                android.os.Process.killProcess(android.os.Process.myPid());//获取PID
//                System.exit(0);
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.driversend:
                startActivity(new Intent(getContext(), DriverSendActivity.class));
                break;
        }
    }
}
