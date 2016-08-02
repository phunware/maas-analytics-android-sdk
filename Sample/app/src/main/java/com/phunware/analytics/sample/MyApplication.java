package com.phunware.analytics.sample;

import android.app.Application;

import com.phunware.analytics.PwAnalyticsModule;
import com.phunware.core.PwCoreSession;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		
		PwCoreSession.getInstance().installModules(PwAnalyticsModule.getInstance());
		
	}
}
