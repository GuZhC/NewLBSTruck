package com.bysj.newlbstruck.Activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bysj.newlbstruck.Bean.User;
import com.bysj.newlbstruck.Constant;
import com.bysj.newlbstruck.R;
import com.bysj.newlbstruck.utils.MyLoader;
import com.bysj.newlbstruck.utils.SharedPreferenceUtil;
import com.bysj.newlbstruck.utils.ToastUtils;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscription;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.sb_register)
    SwitchButton sbRegister;
    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_register_psw)
    EditText etRegisterPsw;
    @BindView(R.id.et_register_psw_two)
    EditText etRegisterPswTwo;
    @BindView(R.id.ll_register_is_shops)
    LinearLayout llRegisterIsShops;
    @BindView(R.id.cb_register_look_agreement)
    CheckBox cbRegisterLookAgreement;
    @BindView(R.id.tv_register_agreement)
    TextView tvRegisterAgreement;
    @BindView(R.id.btn_register_ok)
    Button btnRegisterOk;
    @BindView(R.id.et_register_carnum)
    EditText etRegisterCarnum;
    @BindView(R.id.et_register_carinfo)
    EditText etRegisterCarinfo;

    boolean isAgree = false;
    boolean isMember = true;

    private String Role = "member";
    String mCarInfo;
    String mCarnum;
    String mMame;
    String phone;
    String mPassword;
    String mPasswordTwo;


    private int mLayoutHeight = 0;  //动画执行的padding高度
    boolean mSwitchButtonChick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("注册");
        initShowHide();
    }

    public void Register() {
        mCarnum = etRegisterCarnum.getText().toString();
        mCarInfo = etRegisterCarinfo.getText().toString();
        mMame = etRegisterName.getText().toString();
        mPassword = etRegisterPsw.getText().toString();
        mPasswordTwo = etRegisterPswTwo.getText().toString();
        phone = etRegisterPhone.getText().toString();

        if (TextUtils.isEmpty(mMame) || TextUtils.isEmpty(mPassword) || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(mPasswordTwo)) {
            ToastUtils.showError(RegisterActivity.this, "请填写完整信息");
            return;
        } else {
            if (isAgree) {
                if (mPassword.equals(mPasswordTwo)) {
                    if (isMember) {
                        doRejister(true);
                    } else {
                        if (TextUtils.isEmpty(mCarInfo) ||TextUtils.isEmpty(mCarnum)){
                            ToastUtils.showError(RegisterActivity.this, "信息不完整");
                        } else {
                            doRejister(false);
                        }
                    }
                } else {
                    ToastUtils.showError(RegisterActivity.this, "两次密码不一致");
                }
            } else {
                ToastUtils.showError(RegisterActivity.this, "请勾选注册协议");
            }
        }
    }


    private void doRejister(final boolean isMember) {
        User user = new User();
        user.setUsername(mMame);
        user.setMobilePhoneNumber(phone);
        if (!isMember){
            user.setCarInfo(mCarInfo);
            user.setLicenseNumber(mCarnum);
        }
        user.setPassword(mPassword);
        user.setDrive(!isMember);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User s, BmobException e) {
                if (e == null) {
                    try {
                        MyLoader.stopLoading();
                        SharedPreferenceUtil.instance(RegisterActivity.this).saveString(Constant.USER_NAME,mMame);
                        SharedPreferenceUtil.instance(RegisterActivity.this).saveString(Constant.USER_PSW,mPassword);
                        ToastUtils.showSuccess(RegisterActivity.this, "注册成功");
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e1) {
                        MyLoader.stopLoading();
                        e1.printStackTrace();
                        ToastUtils.showError(RegisterActivity.this, "注册失败稍后再试");
                    }

                } else {
                    if (e.getErrorCode() == 209)
                        ToastUtils.showError(RegisterActivity.this, "号码已经注册");
                    else if (e.getErrorCode() == 202)
                        ToastUtils.showError(RegisterActivity.this, "用户名已经注册");
                    else {
                        Log.e(TAG, "done: " + e.getMessage() + e.getErrorCode() + "");
                        ToastUtils.showError(RegisterActivity.this, "失败：" + e.getMessage() + e.getErrorCode());
                    }
                    MyLoader.stopLoading();
                }
            }
        });


    }

    private void initShowHide() {
        //布局完成
        llRegisterIsShops.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //移除所有监听
                llRegisterIsShops.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mLayoutHeight = llRegisterIsShops.getHeight();
                //隐藏当前控件
                llRegisterIsShops.setPadding(0, -mLayoutHeight, 0, 0);
            }
        });
        sbRegister.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSwitchButtonChick = isChecked;
                ValueAnimator valueAnimator = new ValueAnimator();
                if (mSwitchButtonChick) {
                    valueAnimator.setIntValues(-mLayoutHeight, 0);
                    isMember = false;
                    Role = "merchant";
                } else {
                    isMember = true;
                    Role = "member";
                    valueAnimator.setIntValues(0, -mLayoutHeight);
                }
                //设置监听的值
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        int value = (int) animator.getAnimatedValue();
                        llRegisterIsShops.setPadding(0, value, 0, 0);
                    }
                });
                //动画执行中监听
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        //动画开始，不能点击
                        etRegisterCarnum.setClickable(false);
                        etRegisterCarinfo.setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        etRegisterCarnum.setClickable(true);
                        etRegisterCarinfo.setClickable(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                valueAnimator.setDuration(500);
                valueAnimator.start();
            }
        });
    }

    @OnClick({R.id.cb_register_look_agreement, R.id.btn_register_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_register_look_agreement:
                if (cbRegisterLookAgreement.isChecked())
                    isAgree = true;
                else
                    isAgree = false;
                break;
            case R.id.btn_register_ok:
                Register();
                break;
        }
    }

}
