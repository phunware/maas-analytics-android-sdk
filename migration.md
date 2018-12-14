# Migration guide

## 3.4.2 to 3.5.0

### Library updates
- compileSdkVersion - 28
- targetSdkVersion - 28
- Support Library version - 28.0.0
- Google Play Services version - 16.0.0

### New Analytics API
#### General
This release adds automatic screen view analytic events and simplifies the analytic custom event API. New addCustomEvent methods will appear in the new Maas Portal analytics dashboard. Existing deprecated addEvent methods will continue to appear in the old Maas Portal analytics dashboard.

##### addCustomEvent(context, eventName)
- add a named custom event.

##### addCustomEvent(context, eventName, parameters)
- add a named custom event with a dictionary of parameters.  These parameters will be merged with any global parameters, if set.

##### enableAutomaticScreenViewEvents()
- toggle automatic screen view events. An analytic event will occur for each Activity transition. Enabled by default.

##### setCustomScreen(name)
- Set a custom screen name to override the automatically generated screen view analytics. Use this to add analytics for fragment transitions.

##### setAppSection(name)
- Set a custom app section name

##### setGlobalParameters(parameters)
-Set a dictionary of values that will be added to all analytic events.

#### Changes
- Old analytics API has been deprecated.

## 3.4.1 to 3.4.2

### Changes
- PwLog : deprecated `p()` methods

## 3.3.0 to 3.4.0

### Changes
- CMEModule : added depth parameter to `getContents()` methods

## 3.2.x to 3.3.0

### Library updates
- compileSdkVersion - 27
- buildToolsVersion - 27.0.3
- targetSdkVersion - 26
- Support Library version - 27.1.0
- Google Play Services version - 11.8.0
- Okhttp version - 3.10.0
- Gson version - 2.8.2

#### Removed Classes
- com.phunware.core.internal.LocationServicesListener

#### Changes
- fixed typo in com.phunware.core.analytics.PwAnalyticsModule: MAX_PARMETER_CHAR_LIMIT changed to MAX_PARAMETER_CHAR_LIMIT

## 3.1.x to 3.2.0

### Library updates
- Android Gradle plugin - 2.3.3
- compileSdkVersion - 26
- buildToolsVersion - 26.0.1
- targetSdkVersion - 26
- Support Library version - 26.0.1
- Google Play Services version - 11.4.0
- Okhttp version - 3.9.0
- Gson version - 2.8.2

#### Removed Classes
- com.phunware.core.cme.ServerUtilities
- com.phunware.core.internal.ServerUtilities
- com.phunware.core.internal.LocationServicesConnector
- com.phunware.core.internal.Utils
- com.phunware.core.AnalyticsUtils
- com.phunware.core.ParcelUtils

#### Changes

3.1.x

```java
Intent i = new Intent(context, AnalyticsCacheIntentService.class);
i.putExtra(AnalyticsCacheIntentService.ARG_EVENT, event);
context.startService(i);
```
3.2.0
```java
Intent i = new Intent(context, AnalyticsCacheIntentService.class);
i.putExtra(AnalyticsCacheIntentService.ARG_EVENT, event);
AnalyticsCacheIntentService.enqueueWork(context, i);
```
