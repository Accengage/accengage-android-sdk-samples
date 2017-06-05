# A4S SDK Samples (Android)
Some developers samples demonstrating how to integrate A4S SDK features into a fully working Android application

- AccBeacons : Enabling Beacon detection and interaction
- A4SEvents : Working with analytics events
- AccGeofences : Enabling Geofences detection and interaction
- A4SInbox : Implementing a fully working Push "Inbox"
- A4SIntegration (DEPRECATED) : Integrating our SDK into an application without our "UseA4S" plugin
- A4SSample : Basic Integration of our SDK into a simple application
- <a href="ACCCustomInApps" target="_blank">AccCustomInApps</a> : Advanced usage of custom In-Apps

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
