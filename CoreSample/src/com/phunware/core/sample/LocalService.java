package com.phunware.core.sample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.phunware.core.PwCoreSession;

public class LocalService extends Service {
	private static final String TAG = "LocalService";

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String session = PwCoreSession.getInstance().getSessionId(getApplicationContext());
		Log.v(TAG, "onStartCommand - Session Id: " + session);
		String passedSession = intent.getStringExtra(MainActivity.ARG_SESSION_ID);

		Intent i = new Intent("service-session-broadcast");
		// You can also include some extra data.
		i.putExtra("local_is_persisted", session.equals(passedSession));
		LocalBroadcastManager.getInstance(this).sendBroadcast(i);

		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// for communication return IBinder implementation
		return null;
	}
}
