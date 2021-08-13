# Phunware Analytics SDK for Android

[![Nexus](https://img.shields.io/nexus/r/com.phunware.analytics/analytics-sdk?color=brightgreen&server=https%3A%2F%2Fnexus.phunware.com)](https://nexus.phunware.com/content/groups/public/com/phunware/analytics/analytics-sdk/)

Phunware's Analytics SDK for Android. Visit https://www.phunware.com/ for more information or [sign into the MaaS Portal](http://maas.phunware.com/) to set up MaaS.

### Requirements
* minSdk 23.
* AndroidX.

### Download
Add the following repository to your top level `build.gradle` file:
```groovy
repositories {
    maven {
            url "https://nexus.phunware.com/content/groups/public/"
        }
}
```

Add the following dependency to your app level `build.gradle` file:
```groovy
dependencies {
    implementation "com.phunware.analytics:analytics-sdk:<version>"
}
```

### Android project setup
##### Keys
To use any of the Phunware MaaS SDKs you'll need to add the following entries to your AndroidManifest.xml, making sure to replace the `value` properties with your actual App ID and Access Key:

``` xml
<meta-data
    android:name="com.phunware.maas.APPLICATION_ID"
    android:value="YOUR_APP_ID"/>

<meta-data
    android:name="com.phunware.maas.ACCESS_KEY"
    android:value="YOUR_ACCESS_KEY"/>
```

For instructions on how to obtain an App ID and an Access key, please see the `MaaS Setup` section below.

##### Permissions
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

### Usage
##### Initializing the SDK
```kotlin
Analytics.init(applicationContext: Context)
```

##### Sending events
After initializing the SDK, it's ready to send analytic events to MaaS. You can send events by simply calling:

```kotlin
Analytics.sendEvent(eventName: String)
```
You can also send parameters with your events:
```kotlin
Analytics.sendEvent(eventName: String, parameters: Map<String, Any>)
```
The parameter map accepts a `String` as the key for the parameter and `Any` as the value. You can pass any object as a parameter value, but you need to make sure it's `Serializable`.

##### Enabling automatic screen view tracking
If you want the Analytics SDK to automatically track how long your users spent on each screen, call:
```kotlin
Analytics.enableAutomaticScreenViewEvents(true);
```
If you want to disable this feature, just call the same method with `false`:
```kotlin
Analytics.enableAutomaticScreenViewEvents(false);
```

##### Manually tracking screen view events
If you want to manually track your screen view events, call the following method when the user start to see your screen:
```kotlin
Analytics.startScreenView(className: String, screenName: String, appSection: String?, customParams: Map<String, String>?)
```

And the following method when the user move away from that screen:
```kotlin
Analytics.endScreenView(screenName: String)
```
##### Enabling location and WiFi info tracking
Some info cannot be tracked without first notifying the user about it, like tracking the SSID for the WiFi network or the user's location.
If you'd like that information to be included in your analytic events, show a message explaining it to your user and only enable it after it has been acknowledged by your user.

To include user location data in your analytic events, call:
```kotlin
AnalyticsSettings.setLocationAccessEnabled(enabled: Boolean)
```

To include wifi data in your analytic events, call:
```kotlin
AnalyticsSettings.setWifiInfoAccessEnabled(enabled: Boolean)
```

You're all set to send analytic events from your App!

###  Privacy
You understand and consent to Phunware’s Privacy Policy located at www.phunware.com/privacy. If your use of Phunware’s software requires a Privacy Policy of your own, you also agree to include the terms of Phunware’s Privacy Policy in your Privacy Policy to your end users.

### Terms
Use of this software requires review and acceptance of our terms and conditions for developer use located at http://www.phunware.com/terms/
