# Migration guide

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
