package com.kidoz.sdk.sample.app.SampleAds.model;

import android.app.Activity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.kidoz.sdk.sample.app.R;

/**
 * Created by orikam on 07/06/2017.
 */

public class SampleAdsAdMobModel
{
    private static final String ADMOB_APP_ID = "ca-app-pub-5967470543517808~3214489975";
    private static final String ADMOB_TEST_DEVICE_ID = "FA1FF90C0CBC7379549B5753F4F8028D";

    //Ad Units
    private static final String ADMOB_INTERSTITIAL_ID = "ca-app-pub-5967470543517808/1318954375";
    private static final String ADMOB_REWARDED_VIDEO_ID = "ca-app-pub-5967470543517808/9701675577";

    private InterstitialAd mAdMobInterstitialAd;
    private RewardedVideoAd mAdMobRewarded;
    private AdView mAdMobBanner;

    /****************
     * Interstitial *
     ****************/
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

    /******************
     * Rewarded Video *
     ******************/
    public void setupAdMobRewarded(RewardedVideoAdListener videoListener, Activity activity)
    {
        mAdMobRewarded = MobileAds.getRewardedVideoAdInstance(activity);
        mAdMobRewarded.setRewardedVideoAdListener(videoListener);
    }

    public void loadRewardedVideo(boolean admobIsTesting){
        AdRequest.Builder requestBuilder = new AdRequest.Builder();
        if (admobIsTesting){
            requestBuilder.addTestDevice(ADMOB_TEST_DEVICE_ID);
        }
        AdRequest request = requestBuilder.build();

        mAdMobRewarded.loadAd(ADMOB_REWARDED_VIDEO_ID, request);
    }

    public RewardedVideoAd getAdMobRewarded()
    {
        return mAdMobRewarded;
    }

    /**********
     * Banner *
     **********/
    public void setupAdMobBanner(Activity activity)
    {
        mAdMobBanner = (AdView) activity.findViewById(R.id.admob_banner);
    }

    public void loadBanner()
    {
        AdRequest bannerAdRequest = new AdRequest.Builder().build();
        mAdMobBanner.loadAd(bannerAdRequest);
    }

    public void showBanner()
    {
        //admob loads and shows on loadAd() call.
    }
}
