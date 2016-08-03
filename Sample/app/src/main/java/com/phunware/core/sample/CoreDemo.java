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

    private static final int RC_PERM = 2330;
    private boolean permissionGranted = false;

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

        checkPermissions();
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (!permissionGranted) {
            return;
        }

		/*
		 * Start Core Session
		 */
        PwCoreSession.getInstance().activityStartSession(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!permissionGranted) {
            return;
        }

		/*
		 * Stop Core Session
		 */
        PwCoreSession.getInstance().activityStopSession(this);
    }

    private void checkPermissions() {
        if (!canAccessLocation()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, RC_PERM);
            }
        } else {
            onLocationPermissionGranted();
        }
    }

    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    private boolean hasPermission(String perm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return(PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RC_PERM) {
            if (canAccessLocation()) {
                onLocationPermissionGranted();
            } else {
                Toast.makeText(CoreDemo.this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onLocationPermissionGranted() {
        permissionGranted = true;
        PwCoreSession.getInstance().registerKeys(this,
                getString(R.string.app_appid),
                getString(R.string.app_accesskey),
                getString(R.string.app_signaturekey),
                getString(R.string.app_encryptionkey));

    }
}