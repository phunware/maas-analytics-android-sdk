package com.phunware.analytics.sample;

import java.util.HashMap;
import java.util.Map;


import android.content.res.Configuration;
import android.content.res.Resources;

public class Utils {
	
	/**
	 * Get a map object with a parameter for Orientation
	 * @param r app resources object
	 * @return
	 */
	public static Map<String, String> getOrientationParam(Resources r)
	{
		String orientation = "portrait";
		if(r.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			orientation = "landscape";
		return buildParameter("orientation", orientation);
	}

	/**
	 * Build a single parameter set
	 * @param key
	 * @param value
	 * @return
	 */
	public static Map<String, String> buildParameter(String key, String value)
	{
		HashMap<String,String> map = new HashMap<String, String>(1);
		map.put(key, value);
		return map;
	}
}
