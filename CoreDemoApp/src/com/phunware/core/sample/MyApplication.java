package com.phunware.core.sample;

import android.app.Application;
import android.content.res.Resources;

import com.phunware.core.PwCoreSession;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Resources r = getResources();
		
		PwCoreSession.getInstance().registerKeys(this,
				r.getString(R.string.app_accesskey),
				r.getString(R.string.app_signaturekey),
				r.getString(R.string.app_encryptionkey));
	}
}
