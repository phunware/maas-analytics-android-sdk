package com.phunware.core;

import com.phunware.core.internal.CoreService;

import android.content.Context;

/**
 * Listener interface for specific Core events. In order for this to be
 * accessible by other classes the implementation of this must be public. This
 * listener should only be used for event callbacks or unimportant data, and
 * <b>NOT</b> for passing sensitive data.
 * 
 * @see CoreModule
 */
interface CoreModuleListener {

	public String getPackageName();

	/**
	 * Called when the Session has been created and is ready.
	 */
	public void onSessionReady();

	/**
	 * Called when the Core detects that a new version of the app has been
	 * installed.
	 */
	public void onAppUpdate();

	/**
	 * Called as soon as the Core has been initialized. AKA at the end of
	 * {@link PwCoreSession#registerKeys(android.content.Context, String, String, String)}
	 * 
	 * @Param context The application context
	 */
	public void onCoreInitialized(Context context);

	/**
	 * Called by {@link CoreModuleManager#onServiceAction(Context, String)
	 * CoreModuleManager.onServiceAction} from {@link CoreService CoreService}. Each
	 * module can use this callback to handle a service action (meaning start a
	 * service queue and specify an action, service handler will use this
	 * callback to trigger the action.).
	 * 
	 * @param context
	 * @param actionId
	 *            - id use call a particular class method. (i.e. id is
	 *            "com.phunware.core.PwLog.setShowLog))
	 */
	public void onServiceAction(Context context, String actionId);
}
