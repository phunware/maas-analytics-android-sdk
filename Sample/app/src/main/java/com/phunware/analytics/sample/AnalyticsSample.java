package com.phunware.analytics.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.phunware.analytics.PwAnalyticsModule;
import com.phunware.core.PwCoreSession;

public class AnalyticsSample extends ListActivity {
	private static final String TAG = "AnalyticsSample";
	private boolean permissionGranted;
	private static final int RC_PERM = 2000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				getResources().getStringArray(R.array.planets));
		setListAdapter(adapter);
		checkPermissions();

	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if (!permissionGranted) {
			return;
		}
		PwCoreSession.getInstance().activityStartSession(this);
		//start timing how long the user is on this activity. Do this here so that
		//the time doesn't account for the time the screen is off.
		PwAnalyticsModule.startTimedEvent(this,TAG);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String text = ((TextView)v.findViewById(android.R.id.text1)).getText().toString();
		Intent i = new Intent(this, DetailsActivity.class);
		i.putExtra(DetailsActivity.ARG_TITLE, text);
		startActivity(i);
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
		if (!permissionGranted) {
			return;
		}
		PwCoreSession.getInstance().activityStopSession(this);
		//Stop timing the event for how long the user is on this activity.
		//Send the parameter for the current orientation.
		PwAnalyticsModule.endTimedEventWithParameters(this, TAG, Utils.getOrientationParam(getResources()) );
	}


	private void checkPermissions() {
		if (!canAccessLocation()) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, RC_PERM);
			}
		} else {
			onLocationPermissionGranted();
		}
	}

	private boolean canAccessLocation() {
		return(hasPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION));
	}

	private boolean hasPermission(String perm) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			return(PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
		}
		return true;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == RC_PERM) {
			if (canAccessLocation()) {
				onLocationPermissionGranted();
			} else {
				Toast.makeText(AnalyticsSample.this, "Location permission denied", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void onLocationPermissionGranted() {
		permissionGranted = true;
		PwCoreSession.getInstance().registerKeys(this,
				getString(R.string.app_appid),
				getString(R.string.app_accesskey),
				getString(R.string.app_signaturekey),
				getString(R.string.app_encryptionkey));

		PwAnalyticsModule.addEvent(this, "Featured Page View");
		PwAnalyticsModule.startTimedEvent(this, "My Awesome Game Level 1");
		PwAnalyticsModule.endTimedEvent(this, "My Awesome Game Level 1");
	}
}
