package com.phunware.core;

// TODO:
// Start session id
// GET request for enabled services
// POST request for installed services
// Data Collection of device. Store and persist.
// Setter for access key, Signature key, encryption key

/**
 * This class only manages session; like starting and stopping, registering.
 */
public class PwCoreSession extends CoreSessionImp {
	static enum MySingleton {
		SOLE;
		private static PwCoreSession mInstance;
		private PwCoreSession getInstance() {
			if (mInstance == null)
				mInstance = new PwCoreSession();
			return mInstance;
		}
	}
	
	protected PwCoreSession()
	{
		//private to only let it be used as a singleton.
	}

	/**
	 * Return an instance of {@link PwCoreSession}. This will provide a handle to be able to start and stop
	 * sessions.
	 * @return A Core session instance.
	 * @see PwCoreSession#activityStartSession(android.content.Context)
	 * @see PwCoreSession#activityStopSession(android.content.Context)
	 */
	public static PwCoreSession getInstance() {
		return MySingleton.SOLE.getInstance();
	}
	
	/**
	 * This will completely wipe out all session related data as well as keys.
	 * If this is called, {@link PwCoreSession#registerKeys(android.content.Context, String, String, String)}
	 * must be called again to start a new session.
	 */
	public static void killSession()
	{
		MySingleton.mInstance = null;
	}
}
