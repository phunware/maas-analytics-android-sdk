#Mobile as a Service

######Android MaaS Core Documentation

________________
##Overview
The MaaS Core is designed to have as little impact on developers as possible. MaaS Core helps to gather data for analytical purposes and also maintains a session throughout an app. MaaS Core is also a necessary requirement for all other MaaS SDKs.
The session is used to uniquely identify analytical data. There are only three steps to setup and maintain sessions in any app. Application keys need to be registered, and then sessions can be started and stopped.
###Session Setup and Usage
Update Android Manifest
The MaaS Core relies on a few settings in order to communicate with the MaaS Server. The first is the internet permission, the second is a service that runs network communication asynchronously.
``` XML
<!-- Necessary for core to communicate with MaaS server -->
<uses-permission android:name="android.permission.INTERNET" />
<service android:name="com.phunware.core.internal.CoreService" />
```
##Install Modules
Each MaaS module requires the MaaS Core SDK to run. In order to use any extra modules they must first be installed into the Core SDK. This is done in code with one line and should be done in the Application’s onCreate method:

``` Java
@Override
public void onCreate() {
        super.onCreate();
              /* Other Code, and install modules here*/
           PwCoreSession.getInstanace().installModules(PwAlertsModule.class, ...);
              /* Other code */
}
```
###Register API Keys
Create an class that extends `Application` and register the `Application` class in the `AndroidManifest.xml` file.
Register the access, signature, and encryption key in the `Application’s onCreate` method:

``` Java
@Override
public void onCreate() {
        super.onCreate();
        PwCoreSession.getInstanace().registerKeys(this,
                        "<my_accesskey>",
                        "<my_signaturekey>",
                        "<my_encryptionkey>");
}
```
###Activities - Start and Stop Session
A session is active once it is started and inactive when it has been stopped.
A session will expire after 20 seconds (aka Expiration Timeout) unless it is started again before then.
####Start
To start the session in an `Activity` get the `PwCoreSession` instance and call `activityStartSession(context)`.
The passed in context can be either the `Application` context or the `Activity` context; either will suffice.

``` Java
@Override
public void onCreate() {
        super.onCreate();
              /* Other Code, and install modules here*/
           PwCoreSession.getInstanace().activityStartSession(this);
              /* Other code */
}
```

####Stop
To stop the session in an `Activity` get the `PwCoreSession` instance and call `activityStopSession(context)`.
The passed in context can be either the `Application` context or the `Activity` context; either will suffice.


``` Java
@Override
public void onStop() {
        super.onStop();
              /* Other code */
           PwCoreSession.getInstanace().activityStopSession(this);
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



######*Requires `ACCESS_WIFI_STATE` Permission
In order to get data for the MAC Address, Wifi Info, SSID, and IP the permission for `ACCESS_WIFI_STATE` needs
to be included in the manifest. If it is not included then the data simply will not be collected.


######** Requires `ACCESS_NETWORK_STATE` Permission
In order to get the device User Agent the permission for `ACCESS_NETWORK_STATE` needs to be included in the manifest.
If it is not included then the data simply will not be collected.
