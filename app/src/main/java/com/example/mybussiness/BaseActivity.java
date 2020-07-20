package com.example.mybussiness;

import android.content.Context;

public class BaseActivity extends android.app.Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        BaseActivity.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return BaseActivity.context;
    }
}
