package com.bysj.newlbstruck.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bysj.newlbstruck.Bean.User;
import com.bysj.newlbstruck.Constant;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.utils.MyLoader;
import com.bysj.newlbstruck.utils.SharedPreferenceUtil;
import com.bysj.newlbstruck.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_login_chose_member)
    TextView tvLoginChoseMember;
    @BindView(R.id.tv_login_chose_merchant)
    TextView tvLoginChoseMerchant;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_psw)
    EditText etLoginPsw;
    @BindView(R.id.cb_login_remenberpsd)
    CheckBox cbLoginRemenberpsd;
    @BindView(R.id.tv_login_forget_psd)
    TextView tvLoginForgetPsd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;


    private String name = "";
    private String psw = "";
    private String Role = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        hideToolbar();
        InitNamePsw();
    }

    public void InitNamePsw() {
        try {
            etLoginPhone.setText(SharedPreferenceUtil.instance(this).getString(Constant.USER_NAME));
            etLoginPsw.setText((SharedPreferenceUtil.instance(this).getString(Constant.USER_PSW)));
        } catch (Exception e) {
            e.printStackTrace();
            etLoginPhone.setText("");
            etLoginPsw.setText("");
        }
    }


    @OnClick({R.id.btn_login, R.id.tv_login_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                LoginClick();
                break;
            case R.id.tv_login_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    public void LoginClick() {
        name = etLoginPhone.getText().toString();
        psw = etLoginPsw.getText().toString();
        SharedPreferenceUtil.instance(this).saveString(Constant.USER_NAME, name);
        SharedPreferenceUtil.instance(this).saveString(Constant.USER_PSW, psw);
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(psw))
            Login();
        else
            ToastUtils.showError(LoginActivity.this, "请输入用户名或密码");

    }

    private void Login() {
        MyLoader.showLoading(this);
        User user = new User();
        user.setUsername(etLoginPhone.getText().toString());
        user.setPassword(etLoginPsw.getText().toString());  //
        user.login(new SaveListener<User>() {
            @Override
            public void done(User s, BmobException e) {
                if (e == null) {
                    SharedPreferenceUtil.instance(LoginActivity.this).saveString(Constant.USER_ID, s.getObjectId());
                    SharedPreferenceUtil.instance(LoginActivity.this).saveBoolean(Constant.IS_DRIV, s.isDrive());
                    SharedPreferenceUtil.instance(LoginActivity.this).saveString(Constant.NAME_USER, s.getUsername());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    MyLoader.stopLoading();
                    finish();
                } else {
                    ToastUtils.showError(LoginActivity.this, "帐号或者密码错误");
                    MyLoader.stopLoading();
                }

            }
        });
    }
}
