package com.adolfopardo.estacionamientoapp;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {

    private static MyApplication INSTANCE;

    public static MyApplication getInstance() {
        return INSTANCE;
    }

    public static Context getContext() {
        return INSTANCE;
    }

    /**
     * Singleton
     */
    @Override
    public void onCreate() {
        INSTANCE = this;
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
