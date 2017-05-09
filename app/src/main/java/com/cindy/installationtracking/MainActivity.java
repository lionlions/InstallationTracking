package com.cindy.installationtracking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class MainActivity extends AppCompatActivity {

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
        vFromWhere.setText("Installation Tracking!! \n You will see nothing in this app!!");
    }

    private void processTracker(){
        CustomApplication application = (CustomApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.EventBuilder().setCategory("Category").setAction("Action").setLabel("Label").build());
    }

}
