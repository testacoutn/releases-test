package com.kidoz.sdk.sample.app.SampleAds.model;

import android.app.Activity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
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
    //private RewardedVideoAd mAdMobRewarded;
    private RewardedAd mAdMobRewarded;

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
        mAdMobInterstitialAd.loadAd(CreateAdRequest());
    }

    public InterstitialAd getAdMobInterstitial(){
        return mAdMobInterstitialAd;
    }

    /******************
     * Rewarded Video *
     ******************/
    public void setupAdMobRewarded(Activity activity)
    {
        mAdMobRewarded = new RewardedAd(activity, ADMOB_REWARDED_VIDEO_ID);
    }

    private AdRequest CreateAdRequest(){
        return new AdRequest.Builder().build();
    }

    public void loadRewardedVideo(RewardedAdLoadCallback adLoadCallback){

        mAdMobRewarded.loadAd(CreateAdRequest(),adLoadCallback);
    }

    public RewardedAd getAdMobRewarded()
    {
        return mAdMobRewarded;
    }

    /**********
     * Banner *
     **********/
    public void setupAdMobBanner(Activity activity) {

        mAdMobBanner = (AdView) activity.findViewById(R.id.admob_banner);

    }

    public void loadBanner()
    {
        mAdMobBanner.loadAd(CreateAdRequest());

    }


    public void closeBanner()
    {
        mAdMobBanner.destroy();

    }

    public void showBanner()
    {
        //admob loads and shows on loadAd() call.
    }
}
