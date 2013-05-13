package com.phunware.core;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;

/**
 * Core Analytics will dispatch device info along with what modules are
 * installed in app.
 * 
 * @author Yoong
 * 
 */
class Analytics {
	private static final String PREFS_CORE_ANALYTICS = "com.phunware.core.analytics";
	private static final String PREFS_CORE_ANALYTICS_HAS_SENT = "com.phunware.core.analytics.hasSentAnalytics";
	private Integer mApplicationVersionCode;

	/**
	 * Entry point to start core analytics.
	 * 
	 * @param context
	 */
	public void startCoreAnalytics(final Context context) {
		if (sendCoreAnalytics(context)) {
			new Thread(new Runnable() {
				public void run() {
					try {
						String[] modules = CoreModuleManager.getInstance()
								.getInstallModules();
						postInstalledModules(modules);
						storeSentAnalyics(context);

					} catch (IOException e) {
						clearStoredSentAnalyics(context);
					}
				}
			}).start();
		}
	}

	/**
	 * Determine if core analytics needs to be delivered.
	 * 
	 * @param context
	 * @return
	 */
	private boolean sendCoreAnalytics(Context context) {
		// Fetch stored version
		Integer appStoredVersionCode = getStoredApplicatinVersionCode(context);

		// Fetch application version
		mApplicationVersionCode = getApplicationVersionCode(context);

		// Check if core should process analytics.
		if (appStoredVersionCode == null) {
			// On first launch. Send analytics.
			return true;

		} else if (appStoredVersionCode != null
				&& mApplicationVersionCode != null
				&& appStoredVersionCode >= mApplicationVersionCode) {
			
			// App has been updated. Re-send analytics.
			CoreModuleManager.getInstance().onAppUpdate();
			return true;

		} else {
			// Do not send analytics.
			return false;
		}
		
	}

	//TODO Replace defaultSharedPrefs...
	private Integer getStoredApplicatinVersionCode(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context.getApplicationContext());

		Integer appStoredVersionCode = null;
		try {
			String appStoredVersionStr = sp.getString(
					PREFS_CORE_ANALYTICS_HAS_SENT, null);
			appStoredVersionCode = Integer.valueOf(appStoredVersionStr);
		} catch (NumberFormatException e) {

		}

		return appStoredVersionCode;
	}

	private Integer getApplicationVersionCode(Context context) {
		Integer appVersionCode = null;
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
		}

		if (info != null) {
			appVersionCode = info.versionCode;
		}

		return appVersionCode;
	}

	private void clearStoredSentAnalyics(Context context) {
		SharedPreferences sp = context.getSharedPreferences(
				PREFS_CORE_ANALYTICS, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(PREFS_CORE_ANALYTICS_HAS_SENT);
		editor.commit();
	}

	private void storeSentAnalyics(Context context) {

		if (mApplicationVersionCode != null) {
			SharedPreferences sp = context.getSharedPreferences(
					PREFS_CORE_ANALYTICS, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString(PREFS_CORE_ANALYTICS_HAS_SENT,
					mApplicationVersionCode.toString());
			editor.commit();
		}
	}

	protected void postInstalledModules(String[] installedModules)
			throws IOException {
		// TODO post network call along with Session Data
		// TODO find out what is the json format.
	}
}
