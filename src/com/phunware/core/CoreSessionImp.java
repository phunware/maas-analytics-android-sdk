package com.phunware.core;

import java.util.UUID;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * This class only manages session; like starting and stopping, registering.
 */
abstract class CoreSessionImp {

	private static final String TAG = "PwCoreSessionImp";
	private static final String PREFS_SESSION_DATA = String
			.valueOf("com.phunware.core.Session_!_DAta_Imp!".hashCode());
	private static final String SP_SESSION_ID = String.valueOf("Session__ID___"
			.hashCode());
	private String mSignatureKey;
	private String mEncryptionKey;
	/**
	 * Holds the amount of activities that are active. This gets incremented on
	 * every start and decremented on every stop.
	 */
	private int mActivitesActive = 0;

	private boolean mIsSessionDataProcessed = false;
	private boolean mIsSessionStarted = false;
	private SessionDataImp mSessionData;

	/**
	 * A session is stopped using
	 * {@link CoreSessionImp#activityStopSession(Context)}. After this amount of
	 * time (in seconds) then the session will expire.
	 */
	public static final int SESSION_TIMER = 20;

	/*
	 * Google Analytics defaults to 30, Flurry defaults to 10. We are right in
	 * the middle.
	 */

	/**
	 * Start or persist session. Call this on every {@link Activity} onStart()
	 * method.
	 * 
	 * If a session doesn't exist then a new session will be created. If a
	 * session was recently stopped using
	 * {@link CoreSessionImp#activityStopSession(Context)} then it can be
	 * resumed by calling this method before the session expires.
	 * 
	 * <br/>
	 * <br/>
	 * The passed in {@link Context} can be the {@link Application} context or
	 * the {@link Activity} context.
	 * 
	 * <br/>
	 * <br/>
	 * {@link RuntimeException} will be thrown if
	 * {@link CoreSessionImp#registerKeys(Context, String, String, String)} was
	 * not called First in {@link Application#onCreate()}
	 * 
	 * @param context
	 *            The current context. It can be the {@link Application} context
	 *            or the {@link Activity} context.
	 * 
	 * @see CoreSessionImp#SESSION_TIMER
	 */
	public void activityStartSession(Context context) {

		Log.d(TAG, "activityStartSession");
		Log.d(TAG, "Active Activities: " + mActivitesActive);
		if (!mIsSessionDataProcessed) {
			throw new IllegalStateException(
					"Before using activityStartSession, PwCoreSessionImp#registerKeys must be called First in Application#onCreate");
		}

		if (context == null) {
			throw new NullPointerException(
					"Context passed to activityStartSession cannot be null");
		}

		Log.d(TAG, "TimerTask is null, checking system time");
		long currentTime = System.currentTimeMillis();

		// check for timestamp
		SharedPreferences sp = context.getApplicationContext()
				.getSharedPreferences(PREFS_SESSION_DATA, Context.MODE_PRIVATE);
		long timestamp = sp.getLong("timestamp", -1);

		// check if timestamp exists
		if (timestamp == -1) {
			Log.d(TAG, "timestamp doesn't exist");
			// Timestamp doesn't exist, create new session id
			mSessionData.getModifier().setSessionId(
					UUID.randomUUID().toString());
		} // end timestamp doesn't exist
		else /* timestamp exists */
		{
			Log.d(TAG, "timestamp Exists");
			int delta = (int) ((currentTime - timestamp) / 1000);
			Log.d(TAG, "Delta: " + delta);
			// Make sure the delta time is greater than 0 and less than the
			// session timer cap.
			if (delta >= 0 && delta > SESSION_TIMER && mActivitesActive == 0) {
				Log.d(TAG, "timestamp expired, create new session");
				// timestamp expired, create new session
				mSessionData.getModifier().setSessionId(
						UUID.randomUUID().toString());
			} else {
				Log.d(TAG, "within time, get or create session id");
				/* within timer */
				mSessionData.getModifier().setSessionId(
						getOrCreateSessionId(context));
			}
		} // end timestamp exists

		SharedPreferences.Editor edit = sp.edit();
		// store session id
		edit.putString(SP_SESSION_ID, mSessionData.getAppSessionId());
		edit.commit();
		mActivitesActive++;
		mIsSessionStarted = true;
		// Let all modules know the session is ready
		CoreModuleManager.getInstance().onSessionReady();
		Log.d(TAG, "end activityStartSession - Active Activities: "
				+ mActivitesActive);

	}

	/**
	 * Stop session. Call this on every {@link Activity} onStop()
	 * 
	 * The session will be stopped but it can be resumed by calling
	 * {@link CoreSessionImp#activityStartSession(Context)} before the session
	 * expires.
	 * 
	 * <br/>
	 * <br/>
	 * The passed in {@link Context} can be the {@link Application} context or
	 * the {@link Activity} context.
	 * 
	 * <br/>
	 * <br/>
	 * {@link RuntimeException} will be thrown if
	 * {@link CoreSessionImp#registerKeys(Context, String, String, String)} was
	 * not called First in {@link Application#onCreate()}
	 * 
	 * @param context
	 *            The current context. It can be the {@link Application} context
	 *            or the {@link Activity} context.
	 * 
	 * @see CoreSessionImp#SESSION_TIMER
	 */
	public void activityStopSession(Context context) {

		Log.d(TAG, "activityStopSession");
		if (!mIsSessionDataProcessed) {
			throw new IllegalStateException(
					"Before using activityStopSession, PwCoreSessionImp#registerKeys must be called First in Application#onCreate");
		}
		if (context == null) {
			throw new NullPointerException(
					"Context passed to activityStopSession cannot be null");
		}

		mActivitesActive--;
		// keep it at least at 0.
		if (mActivitesActive < 0)
			mActivitesActive = 0;

		// store current time
		saveTimestamp(context, System.currentTimeMillis());

		mIsSessionStarted = false;
	}

	/**
	 * Helper method to save the current timestamp into
	 * {@link SharedPreferences}
	 * 
	 * @param context
	 *            {@link Context} to use to access {@link SharedPreferences}
	 *            with.
	 * @param timestamp
	 *            The time stamp in milliseconds to save into
	 *            {@link SharedPreferences}
	 */
	private void saveTimestamp(Context context, long timestamp) {
		Log.d(TAG, "saving timestamp");
		SharedPreferences sp = context.getApplicationContext()
				.getSharedPreferences(PREFS_SESSION_DATA, Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		// save timestamp
		edit.putLong("timestamp", timestamp);
		edit.commit();
	}

	/**
	 * Check if the session has been started or not.
	 * 
	 * @return True if the session is started and running, false if not started
	 *         or paused.
	 */
	public boolean isSessionStarted() {
		return mIsSessionStarted;
	}

	/**
	 * Call this before making any additional calls to {@link CoreSessionImp}.
	 * This must be called in {@link Application#onCreate()}. This performs
	 * initializations and setup that is required by other method calls.
	 */
	final public void registerKeys(Context context, String accessKey,
			String signatureKey, String encryptionKey) {
		if (context == null) {
			throw new NullPointerException(
					"Context passed to registerKeys cannot be null");
		}
		if (accessKey == null) {
			throw new NullPointerException(
					"accessKey passed to registerKeys cannot be null");
		}
		if (signatureKey == null) {
			throw new NullPointerException(
					"signatureKey passed to registerKeys cannot be null");
		}
		if (encryptionKey == null) {
			throw new NullPointerException(
					"encryptionKey passed to registerKeys cannot be null");
		}
		Log.d(TAG, "in registerKeys");
		mSignatureKey = signatureKey;
		mEncryptionKey = encryptionKey;

		mIsSessionDataProcessed = true;
		Log.d(TAG, "registerKeys calling activityStartSession");
		mSessionData = new SessionDataImp(context);
		activityStartSession(context);
		activityStopSession(context); // TODO this seems weird. It could be
										// cleaned up.

		// TODO this next line is very odd. activityStartSession is already
		// setting the session id into session data.
		// So this should be able to be taken out. However i think that part of
		// the logic from activity start session
		// should be copied here. That way we don't have to call start and then
		// stop immediately. We can just
		// use what we need to.
		mSessionData.getModifier().setSessionId(mSessionData.getAppSessionId());
		mSessionData.getModifier().setAccessKey(accessKey);
		mSessionData.collectData(context.getApplicationContext());

		// TODO change logic to check version update/upgrade then if so call analytics.
		// Check and run core analytics
		// CoreModuleManager.getInstance().onAppUpdate() gets call in this method(startCoreAnalytics).
		new Analytics().startCoreAnalytics(context);

		CoreModuleManager.getInstance().onCoreInitialized(context);
	}

	/**
	 * Check for a stored sessionId. If it doesn't exist then generate a new
	 * one.
	 * 
	 * @param context
	 *            Context to use {@link SharedPreferences} with.
	 * @return Session Id.
	 */
	private String getOrCreateSessionId(Context context) {
		if (context == null) {
			throw new RuntimeException(
					"Context passed to getSessionId cannot be null");
		}

		// check for already existing session id.
		String sessionId = mSessionData.getAppSessionId();
		if (sessionId != null)
			return sessionId;

		SharedPreferences sp = context.getApplicationContext()
				.getSharedPreferences(PREFS_SESSION_DATA, Context.MODE_PRIVATE);
		sessionId = sp.getString(SP_SESSION_ID, null);

		if (sessionId == null) {
			Log.d(TAG, "creating a new session id, it didn't exist");
			sessionId = UUID.randomUUID().toString();
		}

		return sessionId;
	}

	/**
	 * Get the current session id. If there is no active session then null is
	 * returned.
	 * 
	 * @param context
	 *            Non null context to use {@link SharedPreferences} with.
	 * @return Session id, or null if none is active.
	 */
	public String getSessionId(Context context) {
		if (context == null) {
			throw new NullPointerException(
					"Context passed to getSessionId cannot be null");
		}

		String sessionId = mSessionData.getAppSessionId();
		if (sessionId != null)
			return sessionId;

		SharedPreferences sp = context.getApplicationContext()
				.getSharedPreferences(PREFS_SESSION_DATA, Context.MODE_PRIVATE);
		sessionId = sp.getString(SP_SESSION_ID, null);
		mSessionData.getModifier().setSessionId(sessionId);
		return sessionId;
	}

	/**
	 * Get the Access Key that was passed into
	 * {@link PwCoreSession#registerKeys(Context, String, String, String)}.
	 * 
	 * @return Access Key
	 */
	public String getAccessKey() {
		return mSessionData.getAppAccessKey();
	}

	/**
	 * Get the Signature Key that was passed into
	 * {@link PwCoreSession#registerKeys(Context, String, String, String)}.
	 * 
	 * @return Signature Key
	 */
	public String getSignatureKey() {
		return mSignatureKey;
	}

	/**
	 * Get the EncryptionKey that was passed into {@link PwCoreSession}
	 * {@link #registerKeys(Context, String, String, String)}.
	 * 
	 * @return Encryption Key
	 */
	public String getEncryptionKey() {
		return mEncryptionKey;
	}

	/**
	 * Get the {@link PwSessionData} object.
	 * 
	 * @return Session data
	 */
	public PwSessionData getSessionData() {
		return mSessionData;
	}
}