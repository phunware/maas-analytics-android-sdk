package com.phunware.core;

import java.util.ArrayList;

import android.content.Context;

/**
 * This singleton class manages all attached modules. This is also a direct link
 * from the Core to any module.
 */
public final class CoreModuleManager {

	// instance of this class
	private static CoreModuleManager mInstance;
	private ArrayList<CoreModuleListener> mCoreListeners;

	private CoreModuleManager() {
		// empty for singleton pattern
		mCoreListeners = new ArrayList<CoreModuleListener>(10);
	}

	/**
	 * Get the instance of this class.
	 * 
	 * @return {@link CoreModuleManager} instance.
	 */
	public static CoreModuleManager getInstance() {
		if (mInstance == null) {
			mInstance = new CoreModuleManager();
		}
		return mInstance;
	}

	/**
	 * Attach a module to the Core
	 * 
	 * @param pwCoreListener
	 *            Listener from the module to attach
	 */
	protected void attachToCore(CoreModuleListener pwCoreListener) {
		// don't add it if it already exists
		if (mCoreListeners.contains(pwCoreListener)) {
			return;
		}

		// else add it.
		mCoreListeners.add(pwCoreListener);
	}

	/**
	 * Tell all {@link CoreModuleListener}s that the session is ready to be
	 * used.
	 */
	protected void onSessionReady() {
		for (CoreModuleListener p : mCoreListeners) {
			p.onSessionReady();
		}
	}

	/**
	 * Tell all {@link CoreModuleListener}s that the app version has been
	 * updated.
	 */
	protected void onAppUpdate() {
		for (CoreModuleListener p : mCoreListeners) {
			p.onAppUpdate();
		}
	}

	/**
	 * Tell all {@link CoreModuleListener}s that the core is initialized
	 * updated.
	 * 
	 * @param context
	 *            Application context
	 */
	protected void onCoreInitialized(Context context) {
		for (CoreModuleListener p : mCoreListeners) {
			p.onCoreInitialized(context);
		}
	}

	/**
	 * List of modules that are referenced by a project. Use for core analytics.
	 * 
	 * @return List of modules' package name.
	 */
	public String[] getInstallModules() {

		String[] arr = new String[mCoreListeners.size()];
		int size = mCoreListeners.size();

		for (int i = 0; i < size; i++) {
			arr[i] = mCoreListeners.get(i).getPackageName();
		}

		return arr;
	}

	/**
	 * Tell all {@link CoreModuleListener}s that there is a request from the
	 * core service to perform an action..
	 * 
	 * @param context
	 *            Application context
	 */
	public void onServiceAction(Context context, String actionId) {
		for (CoreModuleListener p : mCoreListeners) {
			p.onServiceAction(context, actionId);
		}
	}
}
