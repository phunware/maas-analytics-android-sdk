##Overview
Google Play Services API provides a number of features that can be used in your apps. Currently the MaaS SDK supports integration with the Location Services API.
As a developer, you are responsible for setting up an app to use the API and handle cases where the API may not be available. Once integrated,
MaaS SDK will make more efficient use of gathering location data for Analytics when integrated with Google Play Services API.

###Google Play Services API Setup
To prepare your workspace with the API, see detailed instructions on Google's [developer website](http://developer.android.com/google/play-services/setup.html).

###Sample App
The sample app provided here demonstrates an example of how to make an app location aware with the Location Services API.
For detailed instructions on how to set this up from scratch, visit the [setup guide](http://developer.android.com/training/location/retrieve-current.html).

*The sample app requires MaaS Core SDK v1.1.0 or later.*

###Register a `LocationClient`
A `LocationClient` is used to get `Location` data. This must be initialized and connected to Google Play Services.
Once it is connected, call this line to register the client in MaaS Core. This method will not accept null values.
```PwCoreModule.getInstance().registerLocationClient(mLocationClient);```

###Unregister a `LocationClient`
When the `LocationClient` becomes disconnected be sure to unregister the `LocationClient` from MaaS Core. *Do not forget this step.*
```PwCoreModule.getInstance().registerLocationClient(mLocationClient);```

### Compiling with Proguard
If you use Proguard in your app, be sure to include the following lines in your proguard configuration:
```
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
```