package com.kidoz.sdk.sample.app.SampleAds.model;

import android.app.Activity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by orikam on 07/06/2017.
 */

public class SampleAdsAdMobModel
{
    private static final String ADMOB_APP_ID = "ca-app-pub-5967470543517808~3214489975";
    private static final String ADMOB_TEST_DEVICE_ID = "FA1FF90C0CBC7379549B5753F4F8028D";
    private static final String ADMOB_INTERSTITIAL_ID = "ca-app-pub-5967470543517808/1318954375";

    private InterstitialAd mAdMobInterstitialAd;

    public void setupAdMobInterstitial(AdListener adListener, Activity activity)
    {
        mAdMobInterstitialAd = new InterstitialAd(activity);
        mAdMobInterstitialAd.setAdUnitId(ADMOB_INTERSTITIAL_ID);
        mAdMobInterstitialAd.setAdListener(adListener);
    }

    public void initAdMob(Activity activity)
    {
        MobileAds.initialize(activity, ADMOB_APP_ID);
    }

    public void loadInterstitial(boolean admobIsTesting)
    {
        AdRequest.Builder requestBuilder = new AdRequest.Builder();
        if (admobIsTesting){
            requestBuilder.addTestDevice(ADMOB_TEST_DEVICE_ID);
        }
        AdRequest request = requestBuilder.build();
        mAdMobInterstitialAd.loadAd(request);
    }

    public InterstitialAd getAdMobInterstitial(){
        return mAdMobInterstitialAd;
    }

}
