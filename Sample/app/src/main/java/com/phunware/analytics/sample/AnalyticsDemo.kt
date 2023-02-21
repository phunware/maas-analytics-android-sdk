package com.phunware.analytics.sample

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View

private const val REQUEST_PERMISSION = 1

internal class AnalyticsDemo : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.next).setOnClickListener {
            val intent = Intent(
                this,
                SecondActivity::class.java
            )
            startActivity(intent)
        }
        checkPermissions()
    }

    private fun checkPermissions() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                REQUEST_PERMISSION
            )
        }
    }
}