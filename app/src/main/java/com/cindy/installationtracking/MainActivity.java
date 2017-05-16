package com.cindy.installationtracking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteApi;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private String TAG = getClass().getSimpleName();

    private TextView vFromWhere;
    private Tracker mTracker;

    public final static int REFRESH_UI = 0x123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processView();
        processTracker();

    }

    private void processView(){
        vFromWhere = (TextView)findViewById(R.id.from_where);
    }

    private FirebaseAnalytics mFirebaseAnalytics;
    private GoogleApiClient mGoogleApiClient;
    private void processTracker(){

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        CustomApplication application = (CustomApplication) getApplication();
        mTracker = application.getDefaultTracker();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(AppInvite.API)
                .build();

        boolean autoLaunchDeepLink = false;
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink)
            .setResultCallback(new ResultCallback<AppInviteInvitationResult>() {
                @Override
                public void onResult(@NonNull AppInviteInvitationResult appInviteInvitationResult) {

                    if(appInviteInvitationResult.getStatus().isSuccess()){
                        Log.i(TAG, "status: " + appInviteInvitationResult.getStatus().getStatus());
                        Log.i(TAG, "status message: " + appInviteInvitationResult.getStatus().getStatusMessage());

                        Intent intent = appInviteInvitationResult.getInvitationIntent();
                        Log.i(TAG, "intent data: " + intent.getData());
                        Log.i(TAG, "intent action: " + intent.getAction());
                        String deepLink = AppInviteReferral.getDeepLink(intent);
                        String inviteId = AppInviteReferral.getInvitationId(intent);
                        Log.d(TAG, "deepLink: " + deepLink + "\ninviteId: " + inviteId);

                        try{
                            deepLink = URLDecoder.decode(deepLink, "UTF-8");
                        }catch (UnsupportedEncodingException e){
                            e.printStackTrace();
                        }
                        Uri uri = Uri.parse(deepLink);
                        String utmSource = uri.getQueryParameter("utm_source");
                        String utmMedium = uri.getQueryParameter("utm_medium");
                        Log.d(TAG, "utmSource: " + utmSource);
                        Log.d(TAG, "utmMedium: " + utmMedium);
                        mTracker.send(new HitBuilders.EventBuilder().setCategory("Category").setAction("Action").setLabel("Label").build());

                    } else {
                        Log.d(TAG, "getInvitation: no deep link found.");
                    }

                }
            });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed.");
    }
}
