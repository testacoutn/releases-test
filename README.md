

# KIDOZ_ADMOB_ADAPTER
Kidoz AdMob mediation adapter Version 1.6 
Built and tested with Google Mobile Ads SDK 18.2.0

</br>

**Prerequisits:**
* To use the Kidoz SDK adapter for AdMob you should make sure you have:
1. Google Mobile Ads SDK 17.2.0 or higher integrated in your project as explained [HERE](https://developers.google.com/admob/android/quick-start):
2. A fully functional AdMob ad placement.
3. Kidoz SDK built with your project.

```css

*** Please note - Kidoz SDK adapter for AdMob Version 1.5 and above is applicable only from Kidoz SDK 0.8.8.2 release 

```

3.1. You can get Kidoz SDK as a Gradle dependency (together with it's needed dependencies) using the following lines:
```
    compile group: 'org.greenrobot', name: 'eventbus', version: '3.0.0'
    compile 'com.kidoz.sdk:KidozSDK:0.8.8.7@aar'
```
3.2. Please make sure you have a set up Kidoz publisher account.
3.3. The plugin itself consists of the java files inside the 'pluginFiles' directory, copy this entire package to your own project or you can copy the kidoz-admob.jar file instead.

3.4. Set your Kidoz PublisherId & PublisherToken in the adapter using the following(Only if using the pluginFiles):
```
KidozManager.setKidozPublisherId(<publisherId>)
KidozManager.setKidozPublisherToken(<publisherToken>)
```
3.5 Set your Kidoz PublisherId & PublisherToken by setting  a Custom Events settings in the `Parameter` field(If using the kidoz-admob.jar):
```
 For Banner:
 {"AppID":"publisherId", "Token":"publisherToken"}

 For Interstitial:
 {"AppID":"publisherId", "Token":"publisherToken"}

 For Rewarded Video:
 {"AppID":"publisherId", "Token":"publisherToken"}
```
3.6. If you want to connect directly with the Kidoz reward events use the following:
```
KidozManager.setRewardedEvents(<new BaseInterstitial.IOnInterstitialRewardedEventListener>);
(Only if using the pluginFiles)
```


</br>

**Integration Steps:**

* Include the 'com.kidoz.mediation.admob.adapters' package in your project or the kidoz-admob.jar.

* Define Kidoz Interstitial and/or Rewarded Video Custom events as explained [HERE](https://support.google.com/admob/answer/3083407):
 
## KIDOZ Interstitial Adapter
* Set the following full path in the `Class Name` field: </br>
(Example: com.kidoz.mediation.admob.adapters.KidozAdMobMediationInterstitialAdapter)

## KIDOZ Rewarded Video Adapter
* Set the following full path in the `Class Name` field: </br>
(Example: com.kidoz.mediation.admob.adapters.KidozAdMobMediationRewardedAdapter)

    ```
    Note: for Google Mobile Ads SDK lower than 17.2.0 
      Set the full path in the Class Name field:  
      com.kidoz.mediation.admob.adapters.KidozAdMobMediationRewardedLegacyAdapterr
    ```

## KIDOZ Banner Adapter
* Set the following full path in the `Class Name` field: </br>
(Example: com.kidoz.mediation.admob.adapters.KidozAdMobMediationBannerAdapter)
</br>
* Please Note: you can change the Kidoz adapter classpath in your project but make sure the class names you put in the AdMob dashboard correspond to your final adapter location.

 

License
--------

    Copyright 2015 KIDOZ, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

