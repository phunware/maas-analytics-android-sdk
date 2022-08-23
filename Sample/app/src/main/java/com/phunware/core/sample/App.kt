package com.phunware.core.sample

import android.app.Application
import com.phunware.analytics.settings.AnalyticsSettings.Companion.setLocationAccessEnabled
import com.phunware.analytics.settings.AnalyticsSettings.Companion.setWifiInfoAccessEnabled
import com.phunware.analytics.sdk.Analytics

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Analytics.init(this)
        Analytics.enableAutomaticScreenViewEvents(true)
        setLocationAccessEnabled(true)
        setWifiInfoAccessEnabled(true)
    }
}