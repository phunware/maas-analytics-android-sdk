package com.phunware.locationtester;

import android.app.Application;
import android.content.res.Resources;

import com.phunware.core.PwCoreSession;
import com.phunware.core.PwLog;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Resources r = getResources();
		PwLog.setShowLog(true);
		PwCoreSession.getInstance().registerKeys(this,
				r.getString(R.string.app_appid),
				r.getString(R.string.app_accesskey),
				r.getString(R.string.app_signaturekey),
				r.getString(R.string.app_encryptionkey));
		//PwCoreSession.getInstance().registerKeys(this);
	}
}
