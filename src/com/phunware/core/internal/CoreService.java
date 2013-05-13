package com.phunware.core.internal;

import com.phunware.core.CoreModuleManager;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public final class CoreService extends Service {
	
	public static final String ACTION_SERVICE = "com.phunware.core.PwService.action_service";
	
	// Timer variables
	private static final int TIMEOUT_SEC = 9; // timeout value in seconds
	private Handler mTimeoutHandler; // thread for timeout
	private int mCount = 0;

	@Override
	public IBinder onBind(Intent intent) {
		// We don't provide binding, so return null
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String actionId = intent.getStringExtra(ACTION_SERVICE);

		CoreModuleManager.getInstance().onServiceAction(
				getApplicationContext(), actionId);

		return START_REDELIVER_INTENT;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mTimeoutHandler != null) {
			mTimeoutHandler.removeCallbacks(mTimeout);
			mTimeoutHandler = null;
		}
	}

	private Runnable mTimeout = new Runnable() {
		public void run() {
			mCount++;
			Log.i("TEST", "TEST Timer: " + mCount);

			// Timeout check.
			if (mCount > TIMEOUT_SEC) {
				stopSelf();
				return;
			}

			mTimeoutHandler.postDelayed(this, 1000);
		}
	};

	/**
	 * Look for the next task at hand from queue.
	 */
	private void executeNext() {
	}
}
