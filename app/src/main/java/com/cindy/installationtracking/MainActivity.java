package com.cindy.installationtracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private TextView vFromWhere;

    public final static int REFRESH_UI = 0x123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processView();

    }

    private void processView(){
        vFromWhere = (TextView)findViewById(R.id.from_where);
        vFromWhere.setText("Installation Tracking!! \n You will see nothing in this app!!");
    }

}
