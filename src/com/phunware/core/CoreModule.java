package com.phunware.core;

import android.content.Context;

import com.phunware.core.CoreModuleListener;

/**
 * Intended to be extended by any main class of a module. Any class extending
 * this should have a default constructor and call the super constructor to
 * properly initialize the module.
 */
public class CoreModule implements CoreModuleListener {

	private String mPackageName;

	public CoreModule(String packageName) {
		mPackageName = packageName;
		// Attach a module to the core by passing a PwCoreModuleListener to it.
		CoreModuleManager.getInstance().attachToCore(this);
	}

	@Override
	public String getPackageName() {
		return mPackageName;
	}

	@Override
	public void onSessionReady() {
	}

	@Override
	public void onAppUpdate() {
	}

	@Override
	public void onCoreInitialized(Context context) {
	}

	@Override
	public void onServiceAction(Context context, String actionId) {
		// Handle a service call here.
	}
}