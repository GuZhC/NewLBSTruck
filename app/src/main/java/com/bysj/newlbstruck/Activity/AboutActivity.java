package com.bysj.newlbstruck.Activity;

import android.os.Bundle;


import com.bysj.newlbstruck.R;

import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("关于我们");
    }
}
