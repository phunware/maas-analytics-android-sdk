package com.phunware.core.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.phunware.core.analytics.PwAnalyticsModule;

public class CoreDemo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PwAnalyticsModule.enableAutomaticScreenViewEvents(true);
        // screen views will be tracked automatically for all activities and fragments loaded after this
        PwAnalyticsModule.registerScreenViews(this);

        findViewById(R.id.next).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CoreDemo.this, SecondActivity.class);
                startActivity(i);
            }
        });
    }
}