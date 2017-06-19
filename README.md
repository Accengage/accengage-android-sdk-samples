# A4S SDK Samples (Android)
Some developers samples demonstrating how to integrate A4S SDK features into a fully working Android application

- AccBeacons : Enabling Beacon detection and interaction
- AccCustomInApps : Advanced usage of custom In-Apps
- AccEvents : Working with analytics events
- AccFcmPushDemo : Basic app integrating our FCM plugin
- AccGeofences : Enabling Geofences detection and interaction
- AccInAppCallbacks : Demo of how to use the basic inapps callbacks provided with the A4S ADK
- AccInAppMultiAction : Demo of how to create a custom inapp using the custom parameters set on the Accengage User Interface
- AccInbox : Implementing a fully working Push "Inbox"
- AccIntegration (DEPRECATED) : Integrating our SDK into an application without our "UseA4S" plugin
- AccSample : Basic Integration of our SDK into a simple application

# Sample App compilation and launch
Our Sample apps are built with gradle and designed to work with Android Studio

You must have access to our User Interface : http://mobilecrm.accengage.com in order to test all the features.

Before launching an app, please edit the AndroidManifest.xml file and
replace the following meta-data with your app credentials (partner id/private key and for some samples your GCM senderid) :
```
<meta-data
                android:name="com.ad4screen.partnerid"
                android:value="comptedemosdk2d0e8680a7acc7f" />
            <meta-data
                android:name="com.ad4screen.privatekey"
                android:value="a8161ed9ef9fb146c1e252dee2ffaf0a84039092" />
            <meta-data
                android:name="com.ad4screen.senderid"
                android:value="gcm:255322792147" />
```
