package com.phunware.core.sample;

import android.app.Application;
import com.phunware.analytics.sdk.Analytics;
import com.phunware.analytics.settings.AnalyticsSettings;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Analytics.init(this);
        Analytics.enableAutomaticScreenViewEvents(true);
        AnalyticsSettings.Companion.setLocationAccessEnabled(true);
        AnalyticsSettings.Companion.setWifiInfoAccessEnabled(true);
    }
}
