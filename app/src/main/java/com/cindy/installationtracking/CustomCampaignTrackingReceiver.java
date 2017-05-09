package com.cindy.installationtracking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class CustomCampaignTrackingReceiver extends BroadcastReceiver {

    private final String TAG = getClass().getSimpleName();
    private Tracker mTracker;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "intent: " + intent);
        String playStoreUrl = "https://play.google.com/store/apps/details?id=com.cindy.installationtracking&referrer=";
        String referrer = intent.getStringExtra("referrer");
        Log.i(TAG, "intent referrer: " + referrer);
        CustomApplication application = (CustomApplication) context.getApplicationContext();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder()
        .setCampaignParamsFromUrl(playStoreUrl+referrer)
        .build());

    }
}
