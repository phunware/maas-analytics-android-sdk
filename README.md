MaaS Core
=========

[Android MaaS Core Documentation](http://phunware.github.io/maas-core-android-sdk/)

**v3.5.1**
________________

## Overview
MaaS Core is an all-inclusive Android SDK provided by Phunware.

Core's default caching behavior is 'no cache'. To use/enable caching, the App (SiteVision, etc)
shall enable caching by calling the appropriate Module's setModuleHttpCacheTtl() method, as shown
below, where the argument is in seconds, and zero means no caching:

```Java
@Override
public void onCreate() {
    super.onCreate();
    /* Other Code */
    PwMappingModule.getInstance().setModuleHttpCacheTtl(0); // Zero means no caching
    /* Other code */
}
```

Attribution
-----------
MaaS Core uses the following 3rd party components.

| Component     | Version  | Description   | License  |
| ------------- | -------  |:-------------:| -----:|
| [OkHttp](https://github.com/square/okhttp)      |3.10.0| An HTTP & SPDY client for Android and Java applications. | [Apache 2.0](https://github.com/square/okhttp/blob/master/LICENSE.txt) |
| [Moshi](https://github.com/square/moshi)  |1.1.0| A modern JSON library for Android and Java. | [Apache 2.0](https://github.com/square/moshi/blob/master/LICENSE.txt) |
| [Gson](https://github.com/google/gson)  |2.8.2| A Java serialization/deserialization library to convert Java Objects into JSON and back. | [Apache 2.0](https://github.com/google/gson/blob/master/LICENSE)|
| [Firebase JobDispatcher](https://github.com/firebase/firebase-jobdispatcher-android)   |0.8.5| The Firebase JobDispatcher is a library for scheduling background jobs in your Android app. | [Apache 2.0](https://github.com/firebase/firebase-jobdispatcher-android/blob/master/LICENSE)|
