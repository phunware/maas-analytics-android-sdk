# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [4.1.0][] - 2022-08-22

### Changed

- Bumped targetSdkVersion to API 31
- Bumped compileSdkVersion to API 32

## [4.0.1][] - 2021-10-18

### Fixed

- Fixed a crash that could occur when a network call failed

## [4.0.0][] - 2021-08-11

### Added

- Repurposed for Analytics usage only

### Changed

- Migrated to AndroidX
- Bumped minSdkVersion to API 23 (Android 6.0)
- Bumped targetSdkVersion to API 30 (Android 11)
- Updated to modern Google play services
- Updated to modern Firebase dependencies
- Updated Phunware dependencies to 4.0.0

### Removed

- Removed Content Management API
- Removed Core API
- Removed environment switching
- Removed programmatic initialization
- Removed all existing deprecated API

### Fixed

- Fixed several crashes
- Fixed several performance issues

## [3.5.2][] - 2020-04-27

### Added

- Added support for automatic screen views from fragments
- New APIs for supporting customized screen views

## [3.5.1][] - 2019-09-16

### Fixed

- Added unique names for modules to address issues with duplicate kotlin_module files

## [3.5.0][] - 2018-12-14

### Added

- New Analytics API

### Deprecated

- Old Analytics API

### Removed

- Bluetooth permission

## [3.4.2][] - 2018-09-12

### Added

- Added Logger interface

## [3.4.1][] - 2018-08-29

### Fixed

- Crash fix for certain Android 8.0 devices

## [3.4.0][] - 2018-06-26

### Changed

- Refactored Content Management APIs

## [3.3.0][] - 2018-04-03

### Changed

- Refactored public APIs
- Updated to Google Play Services 11.8.0

## [3.2.0][] - 2017-10-02

### Changed

- Refactored public APIs
- Updated to Google Play Services 11.4.0

## [3.1.1][] - 2017-08-29

### Added

- Added `PwActivityLifecycleCallback`

### Fixed

- Various bug fixes

## [3.1.0][] - 2017-08-02

### Added

- Added Analytics and Content Management APIs

### Changed

- Updated libraries

### Removed

- Removed OpenUDID and CoreService

### Fixed

- Various bug fixes

## [3.0.3][] - 2016-12-07

### Fixed

- Fixed an Analytics API call

## [3.0.2][] - 2016-10-05

### Changed

- Updated to Google Play Services 9.6.1

### Removed

- Removed coarse location permission

### Fixed

- Various bug fixes

## [3.0.1][] - 2016-08-30

### Fixed

- Fixed null Session Ids
- Analytics changes and fixes

## [3.0.0][] - 2016-08-02

### Changed

- Updated to Google Play Services 9.0.2
- Updated to OkHttp 3.2.0

### Removed

- Removed Retrofit requirement

## 1.3.12 - 2015-02-02

### Changed

- Updated to Google Play Services 6

## 1.3.11 - 2014-12-09

### Removed

- Removed GET_TASKS permission

## 1.3.10 - 2014-11-21

### Fixed

- Fixed sending analytics event upon service restart

## 1.3.9 - 2014-11-11

### Fixed

- Fixed missing http response cache

## 1.3.8 - 2014-07-28

### Changed

- Updated analytic payload to include Device Id
- Updated to schema 1.3

## 1.3.7 - 2014-06-16

### Changed

- Updated to OkHttp 1.6.0
- Updated to Retrofit 1.6.0

## 1.3.6 - 2014-03-27

### Added

- Added HTTP-based cache support for network operations
- Added delete/flush cache method

### Changed

- Updated to OkHttp 1.5.2
- Updated to Retrofit 1.5.0

## 1.3.5 - 2014-02-26

### Changed

- Published as Java 6 compatible binaries

## 1.3.4 - 2014-01-30

### Changed

- Updated analytic payload to include Android API level
- Updated to schema 1.3

## 1.3.3 - 2013-12-06

### Added

- Added ParcelUtils

### Changed

- Updated to Retrofit 1.3.0

## 1.3.2 - 2013-11-08

### Added

- Added throwable method variants to `PwLog`

### Changed

- Improved Session event handling

## 1.3.1 - 2013-10-23

### Changed

- Bumped minSdkVersion to API 18 (Android 4.3)
- Bumped targetSdkVersion to API 18 (Android 4.3)
- Optimized network and analytic calls
- Requires Retrofit 1.2.2 jar
- Requires Gson jar

## 1.2.1 - 2013-08-07

### Added

- Added `PwLog` to view logs from all MaaS SDKs

### Changed

- Improved Analytics caching strategy

### Fixed

- Various bug fixes

## 1.2.0 - 2013-07-31

### Deprecated

- Deprecated `activityStartSession(context)` in favor of `activityStartSession(activity)`
- Deprecated `activityStopSession(context)` in favor of `activityStopSession(activity)`

### Fixed

- Various bug fixes

## 1.1.1 - 2013-07-24

### Changed

- Changed OpenUDID package name from org to com

### Fixed

- Various bug fixes

## 1.1.0 - 2013-07-16

### Added

- Added support for integration with Google Play Services Location API

### Fixed

- Various bug fixes

[4.0.1]: https://github.com/phunware/maas-analytics-android-sdk/compare/4.0.0...4.0.1
[4.0.0]: https://github.com/phunware/maas-analytics-android-sdk/compare/v3.5.2...4.0.0
[3.5.2]: https://github.com/phunware/maas-analytics-android-sdk/compare/v3.5.1...v3.5.2
[3.5.1]: https://github.com/phunware/maas-analytics-android-sdk/compare/v3.5.0...v3.5.1
[3.5.0]: https://github.com/phunware/maas-analytics-android-sdk/compare/v3.4.2...v3.5.0
[3.4.2]: https://github.com/phunware/maas-analytics-android-sdk/compare/v3.4.1...v3.4.2
[3.4.1]: https://github.com/phunware/maas-analytics-android-sdk/compare/v3.4.0...v3.4.1
[3.4.0]: https://github.com/phunware/maas-analytics-android-sdk/compare/3.3.0...v3.4.0
[3.3.0]: https://github.com/phunware/maas-analytics-android-sdk/compare/3.2.0...3.3.0
[3.2.0]: https://github.com/phunware/maas-analytics-android-sdk/compare/3.1.1...3.2.0
[3.1.1]: https://github.com/phunware/maas-analytics-android-sdk/compare/v3.1.0...3.1.1
[3.1.0]: https://github.com/phunware/maas-analytics-android-sdk/compare/v3.0.3...v3.1.0
[3.0.3]: https://github.com/phunware/maas-analytics-android-sdk/compare/v3.0.2...v3.0.3
[3.0.2]: https://github.com/phunware/maas-analytics-android-sdk/compare/v3.0.1...v3.0.2
[3.0.1]: https://github.com/phunware/maas-analytics-android-sdk/compare/v3.0.0...v3.0.1
[3.0.0]: https://github.com/phunware/maas-analytics-android-sdk/releases/tag/v3.0.0
