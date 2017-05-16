package com.cindy.installationtracking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.analytics.FirebaseAnalytics;

public class DynamicLinkActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private String TAG = getClass().getSimpleName();

    private TextView vFromWhere;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_link);

        processView();
        processTracker();

    }

    private void processView(){
        vFromWhere = (TextView)findViewById(R.id.from_where);
        vFromWhere.setText(TAG);
    }

    private FirebaseAnalytics mFirebaseAnalytics;
    private GoogleApiClient mGoogleApiClient;
    private void processTracker(){

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        CustomApplication application = (CustomApplication) getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.send(new HitBuilders.EventBuilder().setCategory("Category").setAction("Action").setLabel("Label").build());

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(AppInvite.API)
                .build();

        boolean autoLaunchDeepLink = true;
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink)
                .setResultCallback(new ResultCallback<AppInviteInvitationResult>() {
                    @Override
                    public void onResult(@NonNull AppInviteInvitationResult appInviteInvitationResult) {

                        if(appInviteInvitationResult.getStatus().isSuccess()){

                            Intent intent = appInviteInvitationResult.getInvitationIntent();
                            String deepLink = AppInviteReferral.getDeepLink(intent);
                            String inviteId = AppInviteReferral.getInvitationId(intent);
                            Log.d(TAG, "deepLink: " + deepLink + " inviteId: " + inviteId);
                            vFromWhere.setText(vFromWhere.getText() + "\n" + "deepLink: " + deepLink + " inviteId: " + inviteId);

                        } else {
                            Log.d(TAG, "getInvitation: no deep link found.");
                            vFromWhere.setText(vFromWhere.getText() + "\n" + "getInvitation: no deep link found.");
                        }

                    }
                });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed.");
    }

}
