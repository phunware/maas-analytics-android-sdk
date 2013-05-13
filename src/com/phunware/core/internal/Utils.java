package com.phunware.core.internal;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Utilities and helper methods
 */
public final class Utils {
	
	/*
	 * Utility methods that are only needed by the core package (internally) can be declared with no modifier type
	 * so that they're only accessible internally.
	 * Other methods can be public.
	 */

	/**
	 * Encrypt the given <code>base</code> with the SHA-256 encryption algorithm. Return the result as a string
	 * @param base Value to encrypt.
	 * @return Encrypted value.
	 */
	public static String sha256Encrypt(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Convenience method to get the current time as a string formatted as <code>yyyy-MM-dd'T'HH:mm:ss.SSS'Z'</code>.
	 * @return The current time formatted as <code>yyyy-MM-dd'T'HH:mm:ss.SSS'Z'</code>; RFC3339 format
	 */
	public static String getCurrentTimeFormatted() {
		long now = System.currentTimeMillis();
		TimeZone utc = TimeZone.getTimeZone("UTC");
		GregorianCalendar cal = new GregorianCalendar(utc);
		cal.setTimeInMillis(now);

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		formatter.setTimeZone(utc);
		return formatter.format(cal.getTime());
	}
	
	/**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(String action, String key, Context context, String message) {
        Intent intent = new Intent(action);
        intent.putExtra(key, message);
        context.sendBroadcast(intent);
    }
    
    /**
     * Get the {@link SharedPreferences} with a given context and name. Getting the preferences here will
     * ensure that the object is process safe on gingerbread devices and above (>10). This is legacy (but undocumented)
     * behavior in pre gingerbread devices.
     * @param context the application context
     * @param name Desired preferences file. If a preferences file by this name does not exist, it will be created when you retrieve an editor (SharedPreferences.edit()) and then commit changes (Editor.commit()).
     * @return {@link SharedPreferences} that is process safe
     */
    public static SharedPreferences getSharedPreferences(Context context, String name)
    {
    	if(Build.VERSION.SDK_INT >= 10)
    		return getSharedPreferencesPost9(context, name);
    	return getSharedPrefrencesPre10(context, name);
    }
    
    @TargetApi(10)
    private static SharedPreferences getSharedPreferencesPost9(Context context, String name)
    {
    	return context.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
    }
    
    @TargetApi(8)
    private static SharedPreferences getSharedPrefrencesPre10(Context context, String name)
    {
    	return context.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }
}
