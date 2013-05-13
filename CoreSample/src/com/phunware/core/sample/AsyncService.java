package com.phunware.core.sample;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.phunware.core.PwCoreSession;

public class AsyncService extends IntentService {
	private static final String TAG = "AsyncService";

	public AsyncService() {
		super("AsyncService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String session = PwCoreSession.getInstance().getSessionId(getApplicationContext());
		Log.v(TAG, "onHandleIntent - Session Id: " + session);
		String passedSession = intent.getStringExtra(MainActivity.ARG_SESSION_ID);

		Intent i = new Intent("service-session-broadcast");
		// You can also include some extra data.
		i.putExtra("async_is_persisted", session.equals(passedSession));
		LocalBroadcastManager.getInstance(this).sendBroadcast(i);
	}

}
