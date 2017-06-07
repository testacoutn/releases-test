package com.kidoz.sdk.sample.app.SampleAds.model;

import android.app.Activity;

import com.kidoz.sdk.api.KidozInterstitial;
import com.kidoz.sdk.api.KidozSDK;
import com.kidoz.sdk.api.interfaces.SDKEventListener;
import com.kidoz.sdk.api.ui_views.interstitial.BaseInterstitial;

/**
 * Created by orikam on 07/06/2017.
 */

public class SampleAdsKidozModel
{
    private static final String KIDOZ_PUBLISHER_ID = "5";
    private static final String KIDOZ_PUBLISHER_TOKEN = "i0tnrdwdtq0dm36cqcpg6uyuwupkj76s";

    private KidozInterstitial mKidozInterstitial;
    private KidozInterstitial mKidozRewarded;

    public void setupKidozInterstitial(Activity activity, BaseInterstitial.IOnInterstitialEventListener interstitialListener){
        mKidozInterstitial = new KidozInterstitial(activity, KidozInterstitial.AD_TYPE.INTERSTITIAL);
        mKidozInterstitial.setOnInterstitialEventListener(interstitialListener);
    }

    public void setupKidozRewadrded(Activity activity, BaseInterstitial.IOnInterstitialEventListener interstitialListener, BaseInterstitial.IOnInterstitialRewardedEventListener rewardedListener){
        mKidozRewarded = new KidozInterstitial(activity, KidozInterstitial.AD_TYPE.REWARDED_VIDEO);
        mKidozRewarded.setOnInterstitialEventListener(interstitialListener);

        mKidozRewarded.setOnInterstitialRewardedEventListener(rewardedListener);
    }

    public void initKidozSDK(Activity activity, SDKEventListener sdkEventsListener)
    {
        KidozSDK.setSDKListener(sdkEventsListener);

        KidozSDK.initialize(activity, KIDOZ_PUBLISHER_ID, KIDOZ_PUBLISHER_TOKEN);
    }

    public KidozInterstitial getInterstitial(){
        return mKidozInterstitial;
    }

    public KidozInterstitial getRewarded(){
        return mKidozRewarded;
    }
}
