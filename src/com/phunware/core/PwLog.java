package com.phunware.core;

import android.util.Log;

/**
 * For internal use.
 */
public class PwLog {

	private static boolean mShowLog = false;

	/**
	 * Pass in <code>true</code> to show all {@link PwLog}s, pass in <code>false</code> to hide debug, info, and verbose logs.
	 * @param showLog True to show, false to hide.
	 */
	public static void setShowLog(boolean showLog) {
		mShowLog = showLog;
	}

	/**
	 * Send a {@link Log#DEBUG} log message.
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.  
	 * @param msg The message you would like logged. 
	 */
	public static void d(String tag, String msg) {
		if (mShowLog) {
			Log.d(tag, msg);
		}
	}

	/**
	 * Send a {@link Log#INFO} log message.
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.  
	 * @param msg The message you would like logged. 
	 */
	public static void i(String tag, String msg) {
		if (mShowLog) {
			Log.i(tag, msg);
		}
	}

	/**
	 * Send a {@link Log#VERBOSE} log message.
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.  
	 * @param msg The message you would like logged. 
	 */
	public static void v(String tag, String msg) {
		if (mShowLog) {
			Log.v(tag, msg);
		}
	}

	/**
	 * Send a {@link Log#WARN} log message.
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.  
	 * @param msg The message you would like logged. 
	 */
	public static void w(String tag, String msg) {
		Log.w(tag, msg);
	}

	/**
	 * Send a {@link Log#ERROR} log message.
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.  
	 * @param msg The message you would like logged. 
	 */
	public static void e(String tag, String msg) {
		Log.e(tag, msg);
	}
}
