package com.cindy.installationtracking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.analytics.CampaignTrackingReceiver;

public class CustomCampaignTrackingReceiver extends BroadcastReceiver {

    private final String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "intent: " + intent);

        new CampaignTrackingReceiver().onReceive(context, intent);
    }
}
