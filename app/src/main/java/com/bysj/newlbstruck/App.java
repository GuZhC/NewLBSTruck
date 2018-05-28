package com.bysj.newlbstruck;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by guZhongC on 2018/5/28.
 * describe:
 */

public class App extends Application {
    private static App INSTANCE;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        Bmob.initialize(this, "693a10d6bd5edbdfe79a0579c0e99a38");
    }

    public static synchronized App getInstance() {
        return INSTANCE;
    }
}
