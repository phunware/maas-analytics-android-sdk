# Core Change Log
## Version 3.3.0 *(2018-04-03)*
#### Features
* Updated to Google Play Service 11.8.0

## Version 3.2.0 *(2017-10-02)*
#### Features
* Updated to Google Play Service 11.4.0
* Refactor of public APIs, see migration.md for more info

## Version 3.1.1 *(2017-08-29)*
#### Features
* Added PwActivityLifecycleCallback
* various bug fixes

## Version 3.1.0 *(2017-08-02)*
#### Features
* Added Analytics and CME as part of Core SDK 3.1.0
* updated libraries
* Removed OpenUDID and CoreService
* various bug fixes

## Version 3.0.3 *(2016-12-07)*
#### Features
* Analytics endpoint fix

## Version 3.0.2 *(2016-10-05)*
#### Features
* Updated to Google Play Services 9.6.1
* Removed coarse location permission
* misc bug fixes

## Version 3.0.1 *(2016-08-30)*
#### Features
* fix for null session ids
* analytics changes and fixes

## Version 3.0.0 *(2016-08-02)*
#### Features
* Updated to Google Play Services 9.0.2
* Updated 'com.squareup.okhttp' to 3.2.0
* Removed Retrofit requirement

## Version 1.3.12 *(2015-02-02)*
#### Features
* Updated Google Play Service to 6.+

## Version 1.3.11 *(2014-12-9)*
#### Features
* Avoid use GET_TASKS permission

## Version 1.3.10 *(2014-11-21)*
#### Features
* Fixed sending analytics event while Android restart service issue

## Version 1.3.9 *(2014-11-11)*
#### Features
* Fixed missing http response cache issue

## Version 1.3.8 *(2014-07-28)*
#### Features
* Add device type into analytic payload
* Updated schema version to 1.3

## Version 1.3.7 *(2014-06-16)*
#### Features
* Updated 'com.squareup.okhttp' to 1.6.0
* Added 'okhttp-urlconnection (com.squareup.okhttp)' 1.6.0
* Updated 'com.squareup.retrofit' to 1.6.0

## Version 1.3.6 *(2014-03-27)*
#### Features
* Added HTTP-based cache support to Network operations.
* Added cache delete/flush method
* Updated 'com.squareup.okhttp' to 1.5.2
* Updated 'com.squareup.retrofit' to 1.5.0
* Code cleanup/refactoring

## Version 1.3.5 *(2014-02-26)*
#### Features
* Fixed builds to produce Java 6 compatible binaries using 'sourceCompatibility' and 'targetCompatibility' equal to '1.6'.

## Version 1.3.4 *(2014-01-30)*
#### Features
* Added "osApiLevel" to standard analytic payload for retrieving Android API Level (1, 2, 3, .... 17, 18, 19, etc).
* Updated standard analytic payload to use schema version "1.2".

## Version 1.3.3 *(2013-12-06)*
#### Features
* Added ParcelUtils to be used by other MaaS SDKs.
* Updated Retrofit to 1.3.0

## Version 1.3.2 *(2013-11-08)*
#### Features
* Session Pause events are sent much more accurately (when the app closes, the screen turns off, or device rotates, aka when there are no active activities).
* Removed Session Stop events.
* Duplicate Session Start events are no longer being sent.
* Using Location object to help convert location payloads.
* Added Throwable method variants to PwLog

## Version 1.3.1 *(2013-10-23)*
#### Features
* Bug fixes
* Optimization of network and analytic calls
* Requires Retrofit 1.2.2 jar
* Requires Gson jar
* Android Minimum SDK 8
* Android Target SDK 18

## Version 1.2.1 *(2013-08-07)*
#### Features
* Updated Analytics Caching to be more reliable.
* Added PwLog. This class can be used to view logs from all MaaS SDKs.
* Bug fixes

## Version 1.2.0 *(2013-07-31)*
#### Features
* Deprecated activityStartSession(context) in favor of activityStartSession(activity)
* Deprecated activityStopSession(context) in favor of activityStopSession(activity)
* Minor bug fixes

## Version 1.1.1 *(2013-07-24)*
#### Features
* Changed OpenUDID package name from *org* to *com* to fix integration issues with other apps using OpenUDID.
* Minor bug fixes

## Version 1.1.0 *(2013-07-16)*
#### Features
* Minor bug fixes
* Includes support for integration with Google Play Services Location API
