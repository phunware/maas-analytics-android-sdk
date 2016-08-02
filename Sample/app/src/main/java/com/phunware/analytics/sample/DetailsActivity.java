package com.phunware.analytics.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.phunware.analytics.PwAnalyticsModule;
import com.phunware.core.PwCoreSession;

public class DetailsActivity extends Activity {
	private static final String TAG = "DetailsActivity";
	
	public static final String ARG_TITLE = "title";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		String title = getIntent().getStringExtra(ARG_TITLE);
		if(title != null)
		{
			setTitle(title);
			((TextView)findViewById(R.id.text)).setText(title);
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		PwCoreSession.getInstance().activityStartSession(this);
		//start timing how long the user is on this activity. Do this here so that
		//the time doesn't account for the time the screen is off.
		PwAnalyticsModule.startTimedEvent(this,TAG);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.analytics_sample, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_settings)
		{
			//Send an event that the user clicked on the menu item for settings.
			PwAnalyticsModule.addEventWithParameters(this, "Clicked Menu", 
					Utils.buildParameter("Menu Item", "Settings"));
			Toast.makeText(this, "No implementation, however I've seen you want to see settings :)", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		PwCoreSession.getInstance().activityStopSession(this);
		//Stop timing the event for how long the user is on this activity.
		//Send the parameter for the current orientation.
		PwAnalyticsModule.endTimedEventWithParameters(this, TAG, Utils.getOrientationParam(getResources()) );
	}
}
