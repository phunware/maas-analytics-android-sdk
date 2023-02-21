package com.phunware.analytics.sample

import android.app.Application
import com.phunware.analytics.settings.AnalyticsSettings
import com.phunware.analytics.sdk.Analytics

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Analytics.init(this)
        Analytics.enableAutomaticScreenViewEvents(true)
        AnalyticsSettings.setLocationAccessEnabled(true)
    }
}