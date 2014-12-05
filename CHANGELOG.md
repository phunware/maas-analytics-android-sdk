MaaSCore Change Log
==========

Version 1.3.11 *(2014-12-5)*
----------------------------
 * Avoid use GET_TASKS permission

Version 1.3.10 *(2014-11-21)*
----------------------------
 * Fixed sending analytics event while Android restart service issue

Version 1.3.9 *(2014-11-11)*
----------------------------
 * Fixed missing http response cache issue

Version 1.3.8 *(2014-07-28)*
----------------------------
 * Add device type into analytic payload
 * Updated schema version to 1.3

Version 1.3.7 *(2014-06-16)*
----------------------------
 * Updated 'com.squareup.okhttp' to 1.6.0
 * Added 'okhttp-urlconnection (com.squareup.okhttp)' 1.6.0
 * Updated 'com.squareup.retrofit' to 1.6.0

Version 1.3.6 *(2014-03-27)*
----------------------------
 * Added HTTP-based cache support to Network operations.
 * Added cache delete/flush method
 * Updated 'com.squareup.okhttp' to 1.5.2
 * Updated 'com.squareup.retrofit' to 1.5.0
 * Code cleanup/refactoring

Version 1.3.5 *(2014-02-26)*
----------------------------
 * Fixed builds to produce Java 6 compatible binaries using 'sourceCompatibility' and 'targetCompatibility' equal to '1.6'.

Version 1.3.4 *(2014-01-30)*
----------------------------
 * Added "osApiLevel" to standard analytic payload for retrieving Android API Level (1, 2, 3, .... 17, 18, 19, etc).
 * Updated standard analytic payload to use schema version "1.2".

Version 1.3.3 *(2013-12-06)*
----------------------------
 * Added ParcelUtils to be used by other MaaS SDKs.
 * Updated Retrofit to 1.3.0

Version 1.3.2 *(2013-11-08)*
----------------------------
 * Session Pause events are sent much more accurately (when the app closes, the screen turns off, or device rotates, aka when there are no active activities).
 * Removed Session Stop events.
 * Duplicate Session Start events are no longer being sent.
 * Using Location object to help convert location payloads.
 * Added Throwable method variants to PwLog

Version 1.3.1 *(2013-10-23)*
----------------------------
 * Bug fixes
 * Optimization of network and analytic calls
 * Requires Retrofit 1.2.2 jar
 * Requires Gson jar
 * Android Minimum SDK 8
 * Android Target SDK 18

Version 1.2.1 *(2013-08-07)*
----------------------------
 * Updated Analytics Caching to be more reliable.
 * Added PwLog. This class can be used to view logs from all MaaS SDKs.
 * Bug fixes

 Version 1.2.0 *(2013-07-31)*
----------------------------
 * Deprecated activityStartSession(context) in favor of activityStartSession(activity)
 * Deprecated activityStopSession(context) in favor of activityStopSession(activity)
 * Minor bug fixes

Version 1.1.1 *(2013-07-24)*
----------------------------
 * Changed OpenUDID package name from *org* to *com* to fix integration issues with other apps using OpenUDID.
 * Minor bug fixes

 Version 1.1.0 *(2013-07-16)*
----------------------------
 * Minor bug fixes
 * Includes support for integration with Google Play Services Location API
