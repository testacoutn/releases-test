# KIDOZ_ADMOB_ADAPTER
Kidoz adMob mediation adapter

##KIDOZ Interstitial Event Adapter
</br>
**To use the KIDOZ Interstitial adMob adapter do the following steps:**

* Include `KidozAdMobMediationInterstitial.java` file in your project

* Define Interstitial Custom event as explained [HERE](https://support.google.com/admob/answer/3083407):
 
* Set the full path of the file in your project in the `Class Name` field </br>
(Example: your.package.name.KidozAdMobMediationInterstitial.java)

</br>
<a href="url"><img src="https://s3.amazonaws.com/kidoz-cdn/sdk/GitHub_Tutorial_Img/custom_event_tut.JPG" align="left" height="330" width="830" ></a>
</br>
</br>
</br>   

#Lounch your Admob intersitial code
```javascript

 	/** Initiate Kidoz SDK by creating an SdkControler instance
	 * 
	 * @throws RuntimeException in case of invalid or missing publisher_id or security token
	 *  */
	 InterstitialAd mInterstitialAd;
	 
	   mInterstitialAd = new InterstitialAd(this);
       mInterstitialAd.setAdUnitId("YOUR-UNIT-ID");
        mInterstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdClosed()
            {
            }

            @Override
            public void onAdLoaded()
            {
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int i)
            {
            }
        });
        
        adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
```
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

