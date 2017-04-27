package com.cindy.installationtracking;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CampaignTrackingService extends Service {
    public CampaignTrackingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
