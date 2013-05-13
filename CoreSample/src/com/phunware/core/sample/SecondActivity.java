package com.phunware.core.sample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.phunware.core.PwCoreSession;

public class SecondActivity extends Activity {

	private PwCoreSession mSession;
	private static final String TAG = "SecondActivity";

	private CheckBox mChkResumed;
	private CheckBox mChkExpired;

	// passed from previous activity
	private String mPassedSessionId;
	private String mCurrentSessionId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		// Show the Up button in the action bar.
		setupActionBar();

		// setup checkbox
		mChkResumed = (CheckBox) findViewById(R.id.chk_resume);
		mChkResumed.setOnCheckedChangeListener(onCheckedChangeListener);
		mChkExpired = (CheckBox) findViewById(R.id.chk_expire);
		mChkExpired.setOnCheckedChangeListener(onCheckedChangeListener);

		mSession = PwCoreSession.getInstance();
		Log.v(TAG, "onCreate: " + mSession.getSessionId(this));

		// get the session id that was passed from the previous activity
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			mPassedSessionId = extras.getString(MainActivity.ARG_SESSION_ID);
		}
		Log.v(TAG, "onCreate - passed in session: "+mPassedSessionId);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		mSession.activityStartSession(this);

		if (mSession.isSessionStarted()) {
			mCurrentSessionId = mSession.getSessionId(this);
			// session is started, is this resumed or fresh?
			Log.v(TAG, "old session: " + mPassedSessionId);
			Log.v(TAG, "new session: " + mCurrentSessionId);

			//check if session is getting restored/resumed
			if (mPassedSessionId != null && mPassedSessionId.equals(mCurrentSessionId)){
				mChkExpired.setChecked(false);
				mChkResumed.setChecked(true);
			}
			else {
				// assume that the session expired
				Toast.makeText(this, "The session has expired, creating a new one...", Toast.LENGTH_SHORT).show();
				mChkResumed.setChecked(false);
				mChkExpired.setChecked(true);
			}
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// Override this so the checkboxes don't reinitialize...
		// I'm not sure why this happens but overriding this makes it work.
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor edit = sp.edit();
			edit.putString("upnav", mSession.getSessionId(this));
			edit.commit();
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mSession.activityStopSession(this);
		Log.v(TAG, "onStop: " + mSession.getSessionId(this));
		/*
		 * Save data to intent extras because this method is called After onSaveInstanceState,
		 * so the changed state from on stop never gets saved.
		 */
		getIntent().putExtra(MainActivity.ARG_SESSION_ID, mCurrentSessionId);
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
		}
	};
	
	public void onBackPressed() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor edit = sp.edit();
		edit.putBoolean("backnav", true);
		edit.commit();
		super.onBackPressed();
	};
}
