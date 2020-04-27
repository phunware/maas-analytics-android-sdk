package com.phunware.core.sample;

import android.app.Application;

import com.phunware.core.PwActivityLifecycleCallback;
import com.phunware.core.PwLog;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PwLog.setShowLog(true);
    }
}
