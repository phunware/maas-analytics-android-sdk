#Mobile as a Service

[Android MaaS Core Documentation](http://phunware.github.io/maas-core-android-sdk/)

**v 1.3.3**

________________
##Overview
MaaS Core is designed to have as little impact on developers as possible. It is also a necessary requirement for all other MaaS SDKs.

MaaS Core also helps to gather data for analytical purposes and also maintains a session throughout an app. A session is the duration that a user interacts with an app.
The session is used to uniquely identify analytical data. There are only two steps to setup and maintain sessions in any app. Application keys need to be registered, and then sessions can be started and stopped.

###Build requirements
* Android SDK 2.2+ (API level 8) or above.
* Android Target 4.1.1.4
* Android Support v4 18.0.+
* Retrofit 1.3.0
* Gson 2.2.4


###Session Setup and Usage
Update Android Manifest
The MaaS Core relies on a few settings in order to communicate with the MaaS Server. 
The first is the internet permission, the second is a service that runs network communication asynchronously.
The third helps to uniquely identify the device.
``` XML
<!-- Necessary for core to communicate with MaaS server -->
<uses-permission android:name="android.permission.INTERNET" />

<application>
	<!-- other definitions -->
	
	<!-- Necessary for core to communicate with MaaS server -->
	<service android:name="com.phunware.core.internal.CoreService" />
	
	<!-- Necessary to generate a UDID -->
	<service android:name="com.OpenUDID.OpenUDID_service">
		<intent-filter>
			<action android:name="com.openudid.GETUDID" />
		</intent-filter>
	</service>
</application>
```

####Optional Setup
There are optional configurations that can be set in the `AndroidManifest.xml`. These may be configured, however they can
safely not be.

The first is a permission `ACCESS_NETWORK_STATE`. This permission allows access to the network state (connected, disconnected, etc).
This SDK uses it to determine if there is a network connection before sending Analyitic events. If there is no connected
then the events are queued up to be sent when a connection is available.
```XML
<!-- Optional: if used this will check result in making network calls more efficiently. -->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- Optional: Set this permission In order to get more detailed analytics -->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

<!-- Optional: Set these following permissions to to get location data in analytics reports -->    
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

In addition there is a `CoreReceiver` that can be used. Currently this is only used to receive connectivity change events.
This is also used to help send Analytic events more efficiently.

```
<!-- Used by Core for efficient Analytic Caching and Flushing -->
<receiver android:name="com.phunware.core.internal.CoreReceiver">
    <intent-filter>
        <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
    </intent-filter>
</receiver>
```

*Note:* Although the `CoreReceiver` and `ACCESS_NETWORK_STATE` permission do similar things they are still unique and
are beneficial with simultaneous use. The receiver will get updates when connectivity changes, however so will every
other `BroadcastReceiver` that is definied with that intent filter, so the update may not be instantaneous.
Connectivity could drop, so checking if a connection is available could be faster and more reliable.
The `CoreReceiver` will also send any queued up analytic events once connection is restored.
##Install Modules
Each MaaS module requires the MaaS Core SDK to run. In order to use any extra modules they must first be installed into the Core SDK. This is done in code with one line and should be done in the Application’s onCreate method:

``` Java
@Override
public void onCreate() {
    super.onCreate();
    /* Other Code */
    PwCoreSession.getInstance().installModules(PwAlertsModule.getInstance(), ...);
    /* Other code */
}
```
###Register API Keys
Create an class that extends `Application` and register the `Application` class in the `AndroidManifest.xml` file.
This should be called *after* a call to install additional modules. 
Register the access, signature, and encryption key in the `Application’s onCreate` method:

``` Java
@Override
public void onCreate() {
    super.onCreate();
    /* Other Code */
    /* Install additional modules */
    PwCoreSession.getInstance().registerKeys(this,
    		"<my_appid>",
                "<my_accesskey>",
                "<my_signaturekey>",
                "<my_encryptionkey>");
    
    /* Other code */
}
```
####(Optional) Defining keys in the Manifest

```Java
@Override
public void onCreate() {
    super.onCreate();
    /*
     * Alternatively, register keys when the keys are defined
     * in the manifest under meta-data tags.
     */
    PwCoreSession.getInstance().registerKeys(this);
}
```

The `meta-data` tags must be defined *inside* of the `application` tag.
```XML
<meta-data android:name="META_DATA_APPLICATION_ID" android:value="@string/app_id" />
<meta-data android:name="META_DATA_ACCESS_KEY" android:value="@string/access_key" />
<meta-data android:name="META_DATA_SIGNATURE_KEY" android:value="@string/signature_key" />
<meta-data android:name="META_DATA_ENCRYPTION_KEY" android:value="@string/encrypt_key" />
```

######Application ID
You can find your application key in MaaS Portal
######Access Key
A unique key that identifies the client making the request. You can find your access key in MaaS Portal.
######Signature Key
A unique key that is used to sign requests. The signature is used to both check request authorization, as well as data integrity. You can find your signature key in MaaS Portal.
######Encryption Key
The key used to encrypt and decrypt data that is exchanged between the client and the server. You can find your encryption key in MaaS Portal.
###Activities - Start and Stop Session
A session is active once it is started and inactive when it has been stopped.
A session will expire after two minutes (aka Expiration Timeout) unless it is started again before then.
####Start
To start the session in an `Activity` get the `PwCoreSession` instance and call `activityStartSession(activity)`.
The passed in `Activity` should be the current activity. *This
should be called in the activities `onStart` method*. This will ensure the session is properly created before fragments
can be attached to the activity.

``` Java
@Override
public void onStart() {
    super.onStart();
    /* Other Code */
    PwCoreSession.getInstance().activityStartSession(this);
    /* Other code */
}
```

####Stop
To stop the session in an `Activity` get the `PwCoreSession` instance and call `activityStopSession(activity)`.
The passed in `Activity` should be the current activity. *This
should be called in the activities `onStop` method*.


``` Java
@Override
public void onStop() {
    super.onStop();
    /* Other code */
    PwCoreSession.getInstance().activityStopSession(this);
    /* Other code */
}
```

Calling `activityStopSession(context)` will stop the session, however if `activityStartSession(context)` is
called before the expiration timeout is reached then the session will be resumed.
For example, this is how a session is persisted between activity transitions.
###Analytical Data
Various types of analytical data are collected and sent to the MaaS server for usage.
Most are available without any extra permissions:

1. App Session Id
2. App Version Name
3. App Version Code
4. App Access Key
5. Android Build Version
6. Android OS
7. Screen Size
8. Screen Density
9. Screen DPI
10. Carrier
11. Device OpenGL Version
12. Device Sensors (Accelerometer, proximity monitor, etc)
13. Device Language
14. *Device MAC Address
15. *Device Wifi Info
16. *Device SSID
17. *Device IP
18. **Device User Agent
19. Device Make
20. Device Model
21. Device Id
22. Timestamp
23. Timezone


######*Requires `ACCESS_WIFI_STATE` Permission
In order to get data for the MAC Address, Wifi Info, SSID, and IP the permission for `ACCESS_WIFI_STATE` needs
to be included in the manifest. If it is not included then the data simply will not be collected.


######** Requires `ACCESS_NETWORK_STATE` Permission
In order to get the device User Agent the permission for `ACCESS_NETWORK_STATE` needs to be included in the manifest.
If it is not included then the data simply will not be collected.

##Verify Manifest
`PwCoreModule` has a convenience method to check if the manifest for the Core SDK is setup properly. This should only
be used for development and testing, not in production.
Call the method with the line `PwCoreModule.validateManifestCoreSetup(context)`. The passed in context should be the
application context. If there is an error then an `IllegalStateException` will be thrown with an error message on what
couldn't be found.

## Compiling with Proguard
If you use Proguard in your app, be sure to include the following lines in your proguard configuration:
```
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
```

## Integrating with Google Play Services API
Google Play Services API offers many benefits that your app can use. Go to the [GooglePlayServicesIntegration sample app](https://github.com/phunware/maas-core-android-sdk/tree/master/GooglePlayServicesIntegration) to see how
MaaS SDKs utilize the API.

## PwLog
To view logs from the MaaS SDKs, use `PwLog.setShowDebug(true);`. The logs are all turned off by default.


Attribution
-----------
MaaS Core uses the following 3rd party components. 

| Component     | Description   | License  |
| ------------- |:-------------:| -----:|
| [Retrofit](https://github.com/square/retrofit)      | Type-safe REST client for Android and Java by Square, Inc. | [Apache 2.0](https://github.com/square/retrofit/blob/master/LICENSE.txt) |
| [GSON](https://code.google.com/p/google-gson/)      | A Java library to convert JSON to Java objects and vice-versa      |   [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0) |

