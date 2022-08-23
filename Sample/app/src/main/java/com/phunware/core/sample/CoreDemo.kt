package com.phunware.core.sample

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.view.View

internal class CoreDemo : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.next).setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
}