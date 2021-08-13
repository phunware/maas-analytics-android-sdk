package com.phunware.core.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class CoreDemo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.next).setOnClickListener(v -> {
            Intent i = new Intent(CoreDemo.this, SecondActivity.class);
            startActivity(i);
        });
    }
}