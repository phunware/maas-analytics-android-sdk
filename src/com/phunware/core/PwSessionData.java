package com.phunware.core;

import java.util.List;

import android.Manifest.permission;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Model for all session related data.
 */
public interface PwSessionData {
	
	/**
	 * This will grab a formated JSON string from {@link PwSessionData#formatJSONToString()} and put it
	 * into {@link SharedPreferences}.
	 * @param applicationContext The application context to access {@link SharedPreferences}.
	 */
	public void collectData(Context applicationContext);
	/*
	 * This may be irrelevant now that this class is public
	 */
	
	
	
	/**
	 * Returns the session Id
	 * @return Session Id
	 */
	public String getAppSessionId();
	
	/**
	 * Get the version code of the app. This is the internal version.
	 * @return App version code.
	 */
	public String getAppVersionCode();
	
	/**
	 * Get the App Access Key
	 * @return Access Key
	 */
	public String getAppAccessKey();
	
	/**
	 * Get the version name of the app. This is the version that users see (ex play store).
	 * @return App version name.
	 */
	public String getAppVersionName();
	
	/**
	 * Get the Android SDK version
	 * @return Android SDK version
	 */
	public String getAndroidBuildVersion();
	
	/**
	 * @return android
	 */
	public String getAndroidOS();
	
	/**
	 * Get the screen dimensions as a combined string. Ex: 320x480<br/>
	 * Where the first number is the width and the second is the height.
	 * @return Screen dimensions as a combined string.
	 */
	public String getScreenSize();
	
	/**
	 * Get the screen density type. Example: ldpi, mdpi, hdpi, xhdpi, xxhdpi, tv
	 * @return Screen density type
	 */
	public String getScreenDensity();
	
	/**
	 * Get the screen density measurement. Example: 320dpi
	 * @return screen density measurement.
	 */
	public String getScreenDpi();
	
	/**
	 * Get the phone's provider. Example: Sprint, Verizon, AT&T
	 * @return The phone's provider.
	 */
	public String getCarrier();
	
	/**
	 * The GLES version used by an application. The upper order 16 bits represent the major version and the lower order 16 bits the minor version.
	 * @return The GLES version used by an application.
	 */
	public String getDeviceOpenGLVersion();
	
	/**
	 * Return a list of the names of all sensors on this device.
	 * Null is returned if no sensors are found.
	 * Example, Accelerometer, light, pressure, proximity, etc
	 * @return A list of names of all sensors on device, or null if not found.
	 */
	public List<String> getDeviceSensors();
	
	/**
	 * Get the display language of the device. 
	 * @return Display language.
	 */
	public String getDeviceLanguage();
	
	/**
	 * <i>Null is returned unless the permission {@link permission#ACCESS_WIFI_STATE} is set.</i><br/>
	 * Get the mac address.
	 * @return Mac address or null if the wifi access permission isn't set.
	 */
	public String getDeviceMacAddress();
	
	/**
	 * <i>Null is returned unless the permission {@link permission#ACCESS_WIFI_STATE} is set.</i><br/>
	 * Returns wifi info.
	 * @return Wifi info or null if the wifi access permission isn't set.
	 */
	public String getDeviceWifi();
	
	/**
	 * <i>Null is returned unless the permission {@link permission#ACCESS_WIFI_STATE} is set.</i><br/>
	 * Returns device IP Address
	 * @param useIPv4 <code>true</code> to get the IPv4 address, <code>false</code> to get IPv6
	 * @return IP Address or null if the wifi access permission isn't set.
	 */
	public String getDeviceIp(boolean useIPv4);
	
	/**
	 * <i>Null is returned unless the permission {@link permission#ACCESS_NETWORK_STATE} is set.</i><br/>
	 * Return a human-readable name describe the type of the network, for example "WIFI" or "MOBILE".
	 * @return the name of the network type or <code>null</code> if there is no connection
	 */
	public String getDeviceConnectionType();
	
	/**
	 * <i>Null is returned unless the permission {@link permission#ACCESS_WIFI_STATE} is set.</i><br/>
	 * Returns the service set identifier (SSID) of the current 802.11 network. If the SSID can be decoded as UTF-8, it will be returned surrounded by double quotation marks. Otherwise, it is returned as a string of hex digits. The SSID may be null if there is no network currently connected.
	 * @return The SSID or null if the wifi access permission isn't set.
	 */
	public String getDeviceSSId();
	
	/**
	 * <i>Null is returned unless the permission {@link permission#ACCESS_WIFI_STATE} is set.</i><br/>
	 * Return the basic service set identifier (BSSID) of the current access point. The BSSID may be null if there is no network currently connected.
	 * @return the BSSID, in the form of a six-byte MAC address: XX:XX:XX:XX:XX:XX
	 */
	public String getDeviceBSSId();
	
	/**
	 * Get the name of the overall product.
	 * @return The name of the overall product.
	 */
	public String getDeviceMake();
	
	/**
	 * Get the end-user-visible name for the end product.
	 * @return The end-user-visible name for the end product.
	 */
	public String getDeviceModel();
	
	/**
	 * Checks for a cached device id. If none exists then one will be generated and returned.
	 * @return The device Id. 
	 */
	public String getDeviceId();
	
	/**
	 * Returns the value of the user agent system property or null if no such property exists. 
	 * @return User Agent string or null if the property doesn't exist.
	 */
	public String getDeviceUserAgent();
	
	/**
	 * Put all session data in a JSON formatted string.
	 * @return JSON Formatted string of all session data.
	 */
	public String formatJSONToString();
}
