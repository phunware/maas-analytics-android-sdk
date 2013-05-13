package com.phunware.core.internal;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CoreServiceManager {
	public static final String ACTION_LOCATION_NEW_THREAD = "com.phunware.core.internal.coreservicemanager.ACTION_START_SERVICE_LOCATION";
	public static final String ACTION_NEW_THREAD = "com.phunware.core.internal.coreservicemanager.ACTION_START_SERVICE_LOCATION";

	protected static final String KEY_ID = "com.phunware.core.internal.coreservicemanager.CLASS_PATH";
	protected static final String KEY_ACTION = "com.phunware.core.internal.coreservicemanager.SERVICE_KEY";

	private Context mContext;

	public CoreServiceManager(Context context) {
		mContext = context;
	}

	public void addTask(String action, String id) {
		if (action == null || id == null) {
			throw new RuntimeException("Arguments cannot be null");
		}

		// TODO Synchronize and add to queue.

		if (!isServiceRunning()) {
			// Start service.
			Bundle extras = new Bundle();
			extras.putString(KEY_ACTION, action);
			extras.putString(KEY_ACTION, id);

			Intent service = new Intent(mContext, CoreService.class);
			service.putExtras(extras);
			mContext.startService(service);
		}
	}

	public boolean isServiceRunning() {
		ActivityManager manager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (CoreService.class.getName().equals(
					service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	// TODO
	protected boolean popTask() {
		return false;
	}

	// TODO
	protected boolean hasEmptyTasks() {
		return false;
	}
}
