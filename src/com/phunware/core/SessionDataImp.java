package com.phunware.core;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.http.conn.util.InetAddressUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.phunware.core.internal.Utils;

class SessionDataImp implements PwSessionData {

	private static final String PREFS_SESSION_DATA = String.valueOf("com.phunware.core.Session_!_DAta_Imp!".hashCode());
	private static final String SP_SESSION_DATA = String.valueOf("session_data-jsON@!_String".hashCode());
	private String mSessionId;
	private String mAccessKey;
	private Context mContext;

	public SessionDataImp(Context context) {
		mContext = context;
	}

	@Override
	final public String formatJSONToString() {
		JSONObject obj = new JSONObject();
		
		try {
			// look here https://docs.google.com/a/phunware.com/document/d/1xWkmruJr2rX-UoDWU9Cv434n3Y65A-Igpc-gWxqYfbY/edit
			obj.put("sessionId", getAppSessionId());
			obj.put("timestamp",Utils.getCurrentTimeFormatted());
			
			//app block
			JSONObject o = new JSONObject();
			o.put("applicationId", /*TODO what is applicationId */ "appId");
			o.put("ver", getAppVersionName());
			obj.put("app", o);
			
			//device block
			o = new JSONObject();
			o.put("userAgent", getDeviceUserAgent());
			o.put("os", getAndroidOS());
			o.put("id", getDeviceId());
			o.put("make", getDeviceMake());
			o.put("model", getDeviceModel());
			o.put("osv", getAndroidBuildVersion());
			o.put("macAddress", getDeviceMacAddress());
			o.put("ipAddress", getDeviceIp(true));
			o.put("carrier", getCarrier());
			o.put("connection", getDeviceConnectionType());
			JSONObject oSub = new JSONObject();
			oSub.put("BSSID", getDeviceBSSId());
			oSub.put("SSID", getDeviceSSId());
			o.put("SSID", oSub);
			o.put("SSIDDATA", /*TODO what is SSIDDATA*/ "SSIDDATA");
			obj.put("device", o);
			oSub = null;
			
			//custom block
			o = new JSONObject();
			o.put("appVersionCode", getAppVersionCode());
			o.put("appAccessKey", getAppAccessKey());
			o.put("screenSize", getScreenSize());
			o.put("screenDensity", getScreenDensity());
			o.put("screenDpi", getScreenDpi());
			o.put("openGLVersion", getDeviceOpenGLVersion());
			o.put("language", getDeviceLanguage());
			//device sensors
			JSONArray arr = new JSONArray();
			List<String> sensors = getDeviceSensors();
			if(sensors != null)
			{
				for(String s : sensors)
					arr.put(s);
			}
			o.put("sensors", arr);
			obj.put("custom", o);			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj.toString();
	}

	@Override
	public String getAppSessionId() {
		return mSessionId;
	}

	@Override
	public String getAppVersionName() {
		String packageName = mContext.getApplicationInfo().packageName;
		PackageInfo pInfo;
		try {
			pInfo = mContext.getPackageManager().getPackageInfo(packageName, 0);
			return String.valueOf(pInfo.versionName);

		} catch (NameNotFoundException e) {
			return null;
		}
	}

	@Override
	public String getAppVersionCode() {
		String packageName = mContext.getApplicationInfo().packageName;
		PackageInfo pInfo;
		try {
			pInfo = mContext.getPackageManager().getPackageInfo(packageName, 0);
			return String.valueOf(pInfo.versionCode);

		} catch (NameNotFoundException e) {
			return null;
		}
	}

	@Override
	public String getAppAccessKey() {
		return mAccessKey;
	}

	@Override
	public String getAndroidBuildVersion() {
		return String.valueOf(android.os.Build.VERSION.SDK_INT);
	}

	@Override
	public String getAndroidOS() {
		return "android";
	}

	@Override
	public String getScreenSize() {
		DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		return width + "px X " + height + "px";
	}

	@Override
	public String getScreenDensity() {
		float density = mContext.getResources().getDisplayMetrics().density;

		String densityStr = null;

		if (density == DisplayMetrics.DENSITY_TV) {
			densityStr = "tvdpi";
		} else if (density == DisplayMetrics.DENSITY_XXHIGH) {
			densityStr = "xxhdpi";
		} else if (density == DisplayMetrics.DENSITY_XHIGH) {
			densityStr = "xhdpi";
		} else if (density == DisplayMetrics.DENSITY_HIGH) {
			densityStr = "hdpi";
		} else if (density == DisplayMetrics.DENSITY_MEDIUM) {
			densityStr = "mdpi";
		} else if (density == DisplayMetrics.DENSITY_LOW) {
			densityStr = "ldpi";
		}

		return densityStr;
	}

	@Override
	public String getScreenDpi() {
		float densityDpi = mContext.getResources().getDisplayMetrics().densityDpi;
		return String.valueOf(densityDpi);
	}

	@Override
	public String getCarrier() {
		TelephonyManager manager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String carrierName = manager.getNetworkOperatorName();
		return carrierName;
	}

	@Override
	public String getDeviceOpenGLVersion() {

		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		return String.valueOf(configurationInfo.reqGlEsVersion);
	}

	@Override
	public List<String> getDeviceSensors() {
		// get all sensors
		SensorManager sm = (SensorManager) mContext
				.getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ALL);

		List<String> sensorsStr = null;
		// make sure there are sensors to loop through
		if (sensors != null) {
			sensorsStr = new ArrayList<String>(sensors.size());
			// convert to list of strings
			for (Sensor s : sensors)
				sensorsStr.add(s.getName());
		}
		return sensorsStr;
	}

	@Override
	public String getDeviceLanguage() {
		return Locale.getDefault().getDisplayLanguage();
	}

	@Override
	public String getDeviceMacAddress() {
		String mac = null;
		// try getting wifi state.
		try {
			// this will fail if the ACCESS_WIFI_STATE permission isn't set
			WifiManager wifiManager = (WifiManager) mContext
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			mac = wifiInfo.getMacAddress();
		} catch (Exception e) {
			// no permission set
		}
		return mac;
	}

	@Override
	public String getDeviceWifi() {
		String wifi = null;
		// try getting wifi state.
		try {
			// this will fail if the ACCESS_WIFI_STATE permission isn't set
			WifiManager wifiManager = (WifiManager) mContext
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			wifi = wifiInfo.toString();
		} catch (Exception e) {
			// no permission set
		}
		return wifi;
	}

	@Override
	public String getDeviceSSId() {
		String ssid = null;
		// try getting wifi state.
		try {
			// this will fail if the ACCESS_WIFI_STATE permission isn't set
			WifiManager wifiManager = (WifiManager) mContext
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			ssid = wifiInfo.getSSID();
		} catch (Exception e) {
			// no permission set
		}
		return ssid;
	}
	
	@Override
	public String getDeviceBSSId() {
		String ssid = null;
		// try getting wifi state.
		try {
			// this will fail if the ACCESS_WIFI_STATE permission isn't set
			WifiManager wifiManager = (WifiManager) mContext
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			ssid = wifiInfo.getBSSID();
		} catch (Exception e) {
			// no permission set
		}
		return ssid;
	}

	@Override
	public String getDeviceMake() {
		return android.os.Build.PRODUCT;
	}

	@Override
	public String getDeviceModel() {
		return android.os.Build.MODEL;
	}

	@Override
	final public void collectData(Context applicationContext) {
		// Get json string
		String str = formatJSONToString();
		// add to sp
		SharedPreferences sp = applicationContext.getSharedPreferences(PREFS_SESSION_DATA, Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString(SP_SESSION_DATA, str);
		edit.commit();
	}
	
	public String getDeviceId(){
		//check for a saved uid first.
		//if exists return it.
		SharedPreferences sp = mContext.getSharedPreferences(PREFS_SESSION_DATA, Context.MODE_PRIVATE);
		String dataStr = sp.getString(SP_SESSION_DATA, null);
		if(dataStr != null)
		{
			try {
				JSONObject obj = new JSONObject(dataStr);
				return obj.getString("deviceId");
			} catch (JSONException e) {
				// device id doesn't exist, continue to create one create one.
			}
		}
		
		//no id exists, create one
		//get package name
		String packageName = mContext.getApplicationInfo().packageName;
		String id = packageName.hashCode()+UUID.randomUUID().toString();
		return id;
	}

	/**
	 * Get a {@link SessionDataModifier} object so that values can be modified.
	 * This is only accessible in this package
	 * 
	 * @return
	 */
	SessionDataModifier getModifier() {
		return new SessionDataModifier();
	}

	/**
	 * This is used internally to safely set data in the {@link PwSessionData}
	 * object.
	 */
	class SessionDataModifier {
		public void setSessionId(String sessionId) {
			mSessionId = sessionId;
		}

		public void setAccessKey(String accessKey) {
			mAccessKey = accessKey;
		}
	}

	@Override
	public String getDeviceIp(boolean useIPv4) {
		try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                    	String sAddr = addr.getHostAddress().toUpperCase(Locale.US);
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr); 
                        if (useIPv4) {
                            if (isIPv4) 
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim<0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return null;
	}

	@Override
	public String getDeviceConnectionType() {
		try
		{
			ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo info = cm.getActiveNetworkInfo();
	        if(info.isConnected())
	        {
	        	return info.getTypeName();
	        }
		}
		catch(SecurityException e)
		{
			//ACCESS_NETWORK_STATE permission not set
		}
        //else
        return null;
	}
	
	public String getDeviceUserAgent()
	{
		return System.getProperty( "http.agent" );
	}
}
