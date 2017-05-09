package com.cindy.installationtracking;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by cindy on 2017/4/29.
 */

public class CustomApplication extends MultiDexApplication {

    private Tracker mTracker;

    synchronized public Tracker getDefaultTracker(){

        if(mTracker == null){
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.global_tracker);
            mTracker.enableAdvertisingIdCollection(true); //自动收集广告用户信息
            mTracker.enableAutoActivityTracking(true);//开启页面访问
        }
        return mTracker;

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
