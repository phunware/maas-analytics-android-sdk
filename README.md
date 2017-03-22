MaaS Analytics SDK for Android
==================

Version 1.3.7

This is Phunware's Android SDK for the Analytics module. Visit http://maas.phunware.com/ for more details and to sign up.


Documentation
------------

Phunware Analytics documentation is included in the Documents folder in the repository as both HTML and via maven. You can also find the latest documentation here: http://phunware.github.io/maas-analytics-android-sdk/



Overview
-----------

The Phunware Analytics SDK provides the ability to generate custom analytic events. Events can be created at a single point
in time or with duration data for timed events.

### Build Requirements
* Android SDK 4.0.3+ (API level 15) or above
* Android Studio


Installation
------------
Add the following to your `repositories` tag in your top level `build.gradle` file.

 ```XML
 projects {
   repositories {
     ...
     maven {
         url "https://nexus.phunware.com/content/groups/public/"
     }
     ...
   }
 }
 ```


Import the Phunware Analytics library by adding the following to your app's `build.gradle` file:
```
compile 'com.phunware.analytics:analytics:1.3.7'
```

Update your `AndroidManifest.xml` to include the following permissions:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```
See [AndroidManifest.xml](https://github.com/phunware/maas-analytics-android-sdk/blob/master/Sample/app/src/main/AndroidManifest.xml) for an example manifest file.


Prerequisites
-------------

The PW Analytics SDK automatically imports the latest `PW Core SDK`.
Be sure to install the module in the `Application` `onCreate` method before registering keys. For example:
``` Java
@Override
public void onCreate() {
    super.onCreate();
    /* other code */
    PwCoreSession.getInstance().installModules(PwAnalyticsModule.getInstance(), ...);
    /* other code */
}
```

Integration
------------

### Adding Events

To add events with Pw Analytics:
*You cannot add events until after you call `registerKeys` on `PwCoreSession.getInstance()`*

```JAVA
public class AnalyticsSample extends Activity
{
    public void viewFeaturedPage()
    {
        /* other code */

        //Requires a context and event name.
	     PwAnalyticsModule.addEvent(this, "Featured Page View");

        /* other code */
    }
}
```

*All event names must be alphanumeric strings.*


### Timed Events

MaaS Analytics supports timed analytics:
```Java
public void startLevel()
{
    // Start a timed event like so:
    PwAnalyticsModule.startTimedEvent(this, "My Awesome Game - Level 1");
    // 'This' refers to a context.
}

public void endLevel()
{
	// To end a timed event, pass in the same event name like so:
	PwAnalyticsModule.endTimedEvent(this, "My Awesome Game - Level 1");
  // 'This' refers to a context.
}
```

#### Pausing and Resuming Timed Events
MaaS Analytics allows you to pause and resume timed events. If an event is in a paused state when `endTimedEvent` is called on it, then the paused timestamp will be used to calculate an event's duration.
```
public void pauseGame()
{
    // Pause a timed event like so:
    PwAnalyticsModule.pauseTimedEvent("My Awesome Game - Level 1");
}

public void resumeGame()
{
	// To end a timed event, pass in the same event name like so:
	PwAnalyticsModule.resumeTimedEvent("My Awesome Game - Level 1");
}
```

### Event Parameters

PW Analytics allows you to parameterize all of your events with up to 10 string key / value pairs.
*All parameter keys and values must be alphanumeric strings.*

```Java
public void trackEventWithParams()
{
    /* other code */

    HashMap<String, String> params = new HashMap<String, String>();
    params.put("gender", "male");
    PwAnalyticsModule.addEventWithParameters(this, "Featured Page View", params);

    params = new HashMap<String, String>();
    params.put("difficulty", "easy");
    PwAnalyticsModule.startTimedEventWithParameters(this, "My Awesome Game - Level 1", params);
    /* other code */
}

@Override
public void onStop()
{
    super.onStop();
    params = new HashMap<String, String>();
    params.put("difficulty", "easy");
    params.put("attempts", "5");
    // Keep in mind that calling endTimedEvent:withParameters will replace any parameters that you specified in startTimedEvent:withParameters.
    PwAnalyticsModule.endTimedEventWithParameters(this, "My Awesome Game - Level 1", params);
}
```
-----------

You understand and consent to Phunware’s Privacy Policy located at www.phunware.com/privacy. If your use of Phunware’s software requires a Privacy Policy of your own, you also agree to include the terms of Phunware’s Privacy Policy in your Privacy Policy to your end users.
