package com.phunware.core.sample.test;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import android.util.Log;

import com.phunware.core.PwCoreSession;

public class TestSessionData extends AndroidTestCase {

	private static final int SP_SESSION_DATA = "session_data-jsON@!_String".hashCode();

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		///CLOVER:FLUSH
		super.tearDown();
		PwCoreSession.killSession();
		clearSharedPreferences(getContext());
	}

	public void clearSharedPreferences(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getContext());
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(String.valueOf(SP_SESSION_DATA));
		editor.commit();
	}

	/*
	 * Test for the Core integration points.
	 */
	public void testCoreIntegrationOrder() {
		// Should throw an exception if registerKey(..) is not called first.
		try {
			PwCoreSession.getInstance().activityStopSession(getContext());
			PwCoreSession.getInstance().activityStartSession(getContext());
			assertTrue(false);
		} catch (RuntimeException e) {
			assertTrue(true);
		}

		PwCoreSession.killSession();

		// Should pass if registerKey is called first.
		try {
			PwCoreSession.getInstance().registerKeys(getContext(),
					"accessKey", "signatureKey", "encryptionKey");
			PwCoreSession.getInstance().activityStartSession(getContext());
			PwCoreSession.getInstance().activityStopSession(getContext());
			assertTrue(true);
		} catch (RuntimeException e) {
			assertTrue(false);
		}

	}

	/**
	 * Test how and when session data should be collected.
	 */
	public void testCollectingSessionData() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getContext());
		String hashedKey = String.valueOf(SP_SESSION_DATA);
		String result = sp.getString(hashedKey, null);

		// Test with the incorrect integration order, session data will not be
		// stored
		try {
			PwCoreSession.getInstance().activityStartSession(getContext());
		} catch (RuntimeException e) {

		}
		hashedKey = String.valueOf(SP_SESSION_DATA);
		result = sp.getString(hashedKey, null);
		assertNull(result);

		PwCoreSession.killSession();
		clearSharedPreferences(getContext());

		// Test with the correct integration order, session data will be stored
		PwCoreSession.getInstance().registerKeys(getContext(), "", "", "");
		PwCoreSession.getInstance().activityStartSession(getContext());
		hashedKey = String.valueOf(SP_SESSION_DATA);
		Log.v("TestSessionData", "hashedKey: "+hashedKey);
		result = sp.getString(hashedKey, null);
		assertNotNull(result);

		try {
			JSONObject json = new JSONObject(result);
			json.getString("sessionId");
			assertTrue(true);
		} catch (JSONException e) {
			assertTrue(false);
		}

		hashedKey = String.valueOf("test".hashCode());
		result = sp.getString(hashedKey, null);
		assertNull(result);
	}

	public void testNoNullsForCoreAPI() {
		try {
			PwCoreSession.getInstance().registerKeys(null, "", "", "");
			assertTrue(false);
		} catch (RuntimeException e) {
			assertTrue(true);
		}
		try {
			PwCoreSession.getInstance().registerKeys(getContext(), null, "",
					"");
			assertTrue(false);
		} catch (RuntimeException e) {
			assertTrue(true);
		}
		try {
			PwCoreSession.getInstance().registerKeys(getContext(), "", null,
					"");
			assertTrue(false);
		} catch (RuntimeException e) {
			assertTrue(true);
		}
		try {
			PwCoreSession.getInstance().registerKeys(getContext(), "", "",
					null);
			assertTrue(false);
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}

	// TODO add test methods for all session data getters

}
