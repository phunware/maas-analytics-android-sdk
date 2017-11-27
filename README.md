MaaS Core
=========

[Android MaaS Core Documentation](http://phunware.github.io/maas-core-android-sdk/)

**v3.2.0**
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
MaaS Core uses the following 3rd party components.

| Component     | Description   | License  |
| ------------- |:-------------:| -----:|
| [OkHttp](https://github.com/square/okhttp)      | An HTTP & SPDY client for Android and Java applications. | [Apache 2.0](https://github.com/square/okhttp/blob/master/LICENSE.txt) |

MaaS Core
=========

[Android MaaS Core Documentation](http://phunware.github.io/maas-core-android-sdk/)

**v3.2.0**
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
MaaS Core uses the following 3rd party components.

| Component     | Description   | License  |
| ------------- |:-------------:| -----:|
| [OkHttp](https://github.com/square/okhttp)      | An HTTP & SPDY client for Android and Java applications. | [Apache 2.0](https://github.com/square/okhttp/blob/master/LICENSE.txt) |

Privacy
-------
You understand and consent to Phunware’s Privacy Policy located at www.phunware.com/privacy. If your use of Phunware’s software requires a Privacy Policy of your own, you also agree to include the terms of Phunware’s Privacy Policy in your Privacy Policy to your end users.

Terms
-----
Use of this software requires review and acceptance of our terms and conditions for developer use located at http://www.phunware.com/terms/
