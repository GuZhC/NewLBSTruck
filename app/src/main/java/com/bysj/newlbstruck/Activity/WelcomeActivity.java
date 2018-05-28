package com.bysj.newlbstruck.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bysj.newlbstruck.R;

public class WelcomeActivity extends BaseActivity {
    private static final int sleepTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        hideToolbar();

        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        rootLayout.startAnimation(animation);
    }

    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            public void run() {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    finish();
            }
        }).start();

    }
}
