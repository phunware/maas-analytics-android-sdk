package com.phunware.core.sample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.phunware.core.PwCoreSession;

public class CoreDemo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.next).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CoreDemo.this, SecondActivity.class);
                startActivity(i);
            }
        });

        PwCoreSession.getInstance().registerKeys(this,
                getString(R.string.app_appid),
                getString(R.string.app_accesskey),
                getString(R.string.app_signaturekey),
                getString(R.string.app_encryptionkey));
    }
    @Override
    protected void onStart() {
        super.onStart();

		/*
		 * Start Core Session
		 */
        PwCoreSession.getInstance().activityStartSession(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

		/*
		 * Stop Core Session
		 */
        PwCoreSession.getInstance().activityStopSession(this);
    }
}