package com.bysj.newlbstruck.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bysj.newlbstruck.Bean.User;
import com.bysj.newlbstruck.Constant;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.utils.SharedPreferenceUtil;
import com.bysj.newlbstruck.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyUserActivity extends BaseActivity {
    @BindView(R.id.et_fogetpsw_name)
    EditText eEt_fogetpsw_name;
    @BindView(R.id.et_fogetpsw_psw)
    TextView eEt_fogetpsw_psw;
    @BindView(R.id.et_fogetpsw_pswtwo)
    TextView eEt_fogetpsw_pswtwo;
    private String name = "";
    private String psw = "";
    private String pswtwo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("修改用户信息");
    }

    @OnClick({R.id.btn_fogetpsw_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_fogetpsw_ok:
                ModifyUser();
                break;
        }
    }

    private void ModifyUser() {
        name = eEt_fogetpsw_name.getText().toString();
        psw = eEt_fogetpsw_psw.getText().toString();
        pswtwo = eEt_fogetpsw_pswtwo.getText().toString();
        System.out.println(name);
        System.out.println(psw);
        System.out.println(pswtwo);
        if (TextUtils.isEmpty(pswtwo) || TextUtils.isEmpty(psw) || TextUtils.isEmpty(name)) {
            ToastUtils.showError(ModifyUserActivity.this, "请填写您要修改的信息");
            return;
        }else {
            if (psw.equals(pswtwo)) {
                User user = new User();
                if (!TextUtils.isEmpty(psw)) {
                    user.setPassword(psw);
                }
                if (!TextUtils.isEmpty(psw)) {
                    user.setUsername(name);
                }
                String userid = SharedPreferenceUtil.instance(ModifyUserActivity.this).getString(Constant.USER_ID);
                user.setObjectId(userid);
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        ToastUtils.showSuccess(ModifyUserActivity.this, "更新成功");
                    }
                });
            } else {
                ToastUtils.showError(ModifyUserActivity.this, "两次密码不一致");
            }
        }
    }
}
