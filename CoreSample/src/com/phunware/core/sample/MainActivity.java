package com.phunware.core.sample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.phunware.core.PwCoreSession;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private PwCoreSession mSession; // session instance

	private final String ARG_CHK_STATES = "chk_states";
	public final static String ARG_SESSION_ID = "session_id";
	private final static int REQ_CD_SECOND_ACTIVITY = 1;

	private boolean[] mChkStates = new boolean[9]; // states for check boxes
	// 0 - started
	// 1 - stopped
	// 2 - resumed
	// 3 - expired
	// 4 - back
	// 5 - return
	// 6 - local
	// 7 - async

	private CheckBox mChkStarted;
	private CheckBox mChkStopped;
	private CheckBox mChkResumed;
	private CheckBox mChkExpired;
	private CheckBox mChkBackReturn;
	private CheckBox mChkUpReturn;
	private CheckBox mChkLocalService;
	private CheckBox mChkAsyncService;

	private String mOldSession; // session held before orientation change
	private String mCurrentSession; // session after orientation change

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Register to receive messages.
		// We are registering an observer (mMessageReceiver) to receive Intents
		// with actions named "custom-event-name".
		LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("service-session-broadcast"));

		((TextView) findViewById(R.id.expire_instructions)).setText("The session will expire if the session has been stopped for more than "
				+ PwCoreSession.SESSION_TIMER + " seconds." + "Try turning the screen off for that amount of time and then turn on the screen");

		// setup all of the checkboxes
		mChkStarted = (CheckBox) findViewById(R.id.chk_start);
		mChkStarted.setOnCheckedChangeListener(onCheckedChangeListener);
		mChkStopped = (CheckBox) findViewById(R.id.chk_stop);
		mChkStopped.setOnCheckedChangeListener(onCheckedChangeListener);
		mChkResumed = (CheckBox) findViewById(R.id.chk_resume);
		mChkResumed.setOnCheckedChangeListener(onCheckedChangeListener);
		mChkExpired = (CheckBox) findViewById(R.id.chk_expire);
		mChkExpired.setOnCheckedChangeListener(onCheckedChangeListener);
		mChkBackReturn = (CheckBox) findViewById(R.id.chk_back_return);
		mChkBackReturn.setOnCheckedChangeListener(onCheckedChangeListener);
		mChkUpReturn = (CheckBox) findViewById(R.id.chk_up_return);
		mChkUpReturn.setOnCheckedChangeListener(onCheckedChangeListener);
		mChkLocalService = (CheckBox) findViewById(R.id.chk_local_service);
		mChkLocalService.setOnCheckedChangeListener(onCheckedChangeListener);
		mChkAsyncService = (CheckBox) findViewById(R.id.chk_async_service);
		mChkAsyncService.setOnCheckedChangeListener(onCheckedChangeListener);

		// setup the action buttons
		findViewById(R.id.reset).setOnClickListener(resetListener);
		findViewById(R.id.next).setOnClickListener(nextListener);
		findViewById(R.id.run_local).setOnClickListener(onStartServiceButton);
		findViewById(R.id.run_async).setOnClickListener(onStartServiceButton);

		// get Session Instance
		mSession = PwCoreSession.getInstance();

		// check for extras (from saved state)
		// It is necessary to check like this because of values needing to be
		// saved in onStop.
		// onStop is called after onSaveInstanceState.
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			boolean[] temp = extras.getBooleanArray(ARG_CHK_STATES);
			if (temp != null) {
				mChkStates = temp;
				mChkStarted.setChecked(mChkStates[0]);
				mChkStopped.setChecked(mChkStates[1]);
				mChkResumed.setChecked(mChkStates[2]);
				mChkExpired.setChecked(mChkStates[3]);
				mChkBackReturn.setChecked(mChkStates[4]);
				mChkUpReturn.setChecked(mChkStates[5]);
				mChkLocalService.setChecked(mChkStates[6]);
				mChkAsyncService.setChecked(mChkStates[7]);
			}
			String strTemp = extras.getString(ARG_SESSION_ID);
			if (strTemp != null)
				mOldSession = strTemp;
		} else // maybe returning from up navigation
		{
			// Log.v(TAG, "maybe up nav");
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			String t = sp.getString("upnav", null);

			// if the up nav session flag exists then this is definitely from up
			// nav.
			if (t != null) {
				// Log.v(TAG, "Definitely upNav");
				// everything (except [4] must have been checked for this
				// scenario to happen)
				mOldSession = t;
				mChkStarted.setChecked(mChkStates[0] = true);
				mChkStopped.setChecked(mChkStates[1] = true);
				mChkResumed.setChecked(mChkStates[2] = true);
				mChkExpired.setChecked(mChkStates[3] = true);
				mChkBackReturn.setChecked(mChkStates[4] = sp.getBoolean("backnav", false));
				// check session
				if (t.equals(mSession.getSessionId(this))) {
					// Log.v(TAG, "sessions match!");
					mChkUpReturn.setChecked(mChkStates[5] = true);
				}
				mChkLocalService.setChecked(mChkStates[6] = sp.getBoolean("localservice", false));
				mChkAsyncService.setChecked(mChkStates[7] = sp.getBoolean("asyncservice", false));
			}
		}

		Log.v(TAG, "onCreate - Session: " + mSession.getSessionId(this));
	}

	@Override
	protected void onStart() {
		super.onStart();

		Log.v(TAG, "onStart - Session: " + mSession.getSessionId(this));
		mSession.activityStartSession(this);
		if (mSession.isSessionStarted()) {
			// session is started, is this resumed or fresh?
			mCurrentSession = mSession.getSessionId(this);
			Log.v(TAG, "old session: " + mOldSession);
			Log.v(TAG, "new session: " + mCurrentSession);
			if (mCurrentSession != null && mCurrentSession.equals(mOldSession)) {
				// resumed session
				mChkStates[2] = true;
				mChkResumed.setChecked(true);
			} else {
				if (mOldSession != null) {
					// assume not the first launch and that the session expired
					Toast.makeText(this, "The session has expired, creating a new one...", Toast.LENGTH_SHORT).show();
					mChkStates[3] = true;
					mChkExpired.setChecked(mChkStates[3]);
				} else {
					// new session
					resetUi();
					mOldSession = mCurrentSession;
				}
			}
		}
	}

	@Override
	protected void onPause() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor edit = sp.edit();
		edit.putString("upnav", null);
		edit.commit();
		super.onPause();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// Override this so the checkboxes don't reinitialize...
		// I'm not sure why this happens but overriding this makes it work.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onStop() {
		super.onStop();
		PwCoreSession.getInstance().activityStopSession(this);
		mChkStates[1] = true;
		mChkStopped.setChecked(true);
		/*
		 * Save data to intent extras because this method is called After
		 * onSaveInstanceState, so the changed state from on stop never gets
		 * saved.
		 */
		getIntent().putExtra(ARG_CHK_STATES, mChkStates);
		getIntent().putExtra(ARG_SESSION_ID, mCurrentSession);
	}

	/**
	 * Used when reset button is clicked
	 */
	private OnClickListener resetListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			resetUi();
		}
	};

	private OnClickListener nextListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(MainActivity.this, SecondActivity.class);
			i.putExtra(ARG_SESSION_ID, mCurrentSession);
			startActivityForResult(i, REQ_CD_SECOND_ACTIVITY);
			mOldSession = mCurrentSession;
		}
	};

	/**
	 * Reset the UI to an initial state.
	 */
	private void resetUi() {
		mChkStarted.setChecked(mChkStates[0] = true);
		mChkStopped.setChecked(mChkStates[1] = false);
		mChkResumed.setChecked(mChkStates[2] = false);
		mChkExpired.setChecked(mChkStates[3] = false);
		mChkBackReturn.setChecked(mChkStates[4] = false);
		mChkUpReturn.setChecked(mChkStates[5] = false);
		mChkLocalService.setChecked(mChkStates[6] = false);
		mChkAsyncService.setChecked(mChkStates[7] = false);

		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor edit = sp.edit();
		edit.putBoolean("backnav", false);
		edit.putString("upnav", null);
		edit.putString("localservice", null);
		edit.putString("asyncservice", null);
		edit.commit();

		findViewById(R.id.next).setEnabled(false);
	}

	/**
	 * Called any time a checkbox's checked state changes
	 */
	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// if the box is checked, make the text black,
			// else make it gray.
			if (isChecked)
				buttonView.setTextColor(Color.BLACK);
			else
				buttonView.setTextColor(getResources().getColor(R.color.gray));

			// see if first 4 checkboxes are checked.
			boolean allChecked = true;
			int i = 0;
			for (boolean b : mChkStates) {
				allChecked &= b;
				i++;
				if (i == 4)
					break;
			}
			findViewById(R.id.next).setEnabled(allChecked);

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mChkBackReturn.setChecked(mChkStates[4] = true);
	}

	/**
	 * Called when the run button for either service is clicked.
	 */
	private OnClickListener onStartServiceButton = new OnClickListener() {

		@Override
		public void onClick(View v) {

			// local run button
			if (v.getId() == R.id.run_local) {
				// pass session to make sure it is correct on the other end.
				Intent service = new Intent(MainActivity.this, LocalService.class);
				service.putExtra(ARG_SESSION_ID, mSession.getSessionId(MainActivity.this));
				startService(service);
			}
			else if(v.getId() == R.id.run_async)
			{
				Intent service = new Intent(MainActivity.this, AsyncService.class);
				service.putExtra(ARG_SESSION_ID, mSession.getSessionId(MainActivity.this));
				startService(service);
			}
		}
	};

	/**
	 * Our handler for received Intents
	 */
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Get extra data included in the Intent
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor edit = sp.edit();
			if(intent.hasExtra("local_is_persisted"))
			{
				Log.v(TAG, "local is persisted exists");
				edit.putBoolean("localservice", mChkStates[6] = intent.getBooleanExtra("local_is_persisted", false));
				mChkLocalService.setChecked(mChkStates[6]);
			}
			else if(intent.hasExtra("async_is_persisted"))
			{
				Log.v(TAG, "async is persisted exists");
				edit.putBoolean("asyncservice", mChkStates[7] = intent.getBooleanExtra("async_is_persisted", false));
				mChkAsyncService.setChecked(mChkStates[7]);
			}
			edit.commit();
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Unregister since the activity is about to be closed.
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	};
}
