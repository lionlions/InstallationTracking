package com.cindy.installationtracking;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by cindy on 2017/4/29.
 */

public class CustomApplication extends Application {

    private Tracker mTracker;

    synchronized public Tracker getDefaultTracker(){

        if(mTracker == null){
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker("UA-77928531-3");
        }
        return mTracker;

    }


}
