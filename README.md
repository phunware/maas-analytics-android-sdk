MaaS Core
=========

[Android MaaS Core Documentation](http://phunware.github.io/maas-core-android-sdk/)

**v3.1.1**
________________

##Overview
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
MaaS Mapping uses the following 3rd party components.

| Component     | Description   | License  |
| ------------- |:-------------:| -----:|
| [OkHttp](https://github.com/square/okhttp)      | An HTTP & SPDY client for Android and Java applications. | [Apache 2.0](https://github.com/square/okhttp/blob/master/LICENSE.txt) |
| [Retrofit](https://github.com/square/retrofit)  | Type-safe REST client for Android and Java applications. | [Apache 2.0](https://github.com/square/retrofit/blob/master/LICENSE.txt) |
