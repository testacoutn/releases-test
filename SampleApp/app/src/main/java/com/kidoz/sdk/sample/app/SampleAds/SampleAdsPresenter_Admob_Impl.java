package com.kidoz.sdk.sample.app.SampleAds;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.kidoz.sdk.sample.app.SampleAds.model.SampleAdsAdMobModel;

/**
 * Created by orikam on 07/06/2017.
 */

public class SampleAdsPresenter_Admob_Impl implements SampleAdsPresenter
{
    private static final boolean ADMOB_IS_TESTING = false;

    private static final String ADMOB_INTERSTITIAL_NOT_LOADED = "The interstitial wasn't loaded yet.";
    private static final String ADMOB_REWADED_NOT_LOADED = "Rewarded video wasn't loaded yet.";

    private SampleAdsView mMainView;
    private SampleAdsAdMobModel mAdMobModel;

    public SampleAdsPresenter_Admob_Impl(SampleAdsView mainView)
    {
        mMainView = mainView;
        mAdMobModel = new SampleAdsAdMobModel();

        setupAdMobInterstitial();
        setupAdMobRewarded();
    }

    private void setupAdMobInterstitial()
    {
        mAdMobModel.setupAdMobInterstitial(new AdListener()
        {
            @Override
            public void onAdClosed()
            {
                super.onAdClosed();
                mMainView.showFeedBackText("AdMob | onAdClosed()");
            }

            @Override
            public void onAdFailedToLoad(int i)
            {
                super.onAdFailedToLoad(i);
                mMainView.showFeedBackText("AdMob | onAdFailedToLoad(" + i + ").");
            }

            @Override
            public void onAdLeftApplication()
            {
                super.onAdLeftApplication();
                mMainView.showFeedBackText("AdMob | onAdLeftApplication()");
            }

            @Override
            public void onAdOpened()
            {
                super.onAdOpened();
                mMainView.showFeedBackText("AdMob | onAdOpened()");
            }

            @Override
            public void onAdLoaded()
            {
                super.onAdLoaded();
                mMainView.showFeedBackText("AdMob | onAdLoaded()");
            }
        }, mMainView.getActivity());
    }

    @Override
    public void onCreate()
    {
        mAdMobModel.initAdMob(mMainView.getActivity());
    }

    @Override
    public void onClick_LoadInterstitial()
    {
        mMainView.showFeedBackText("AdMob | trying to load interstitial ad...");
        mAdMobModel.loadInterstitial(ADMOB_IS_TESTING);
    }

    @Override
    public void onClick_ShowInterstitial()
    {
        mMainView.showFeedBackText("AdMob | trying to show ad...");
        InterstitialAd adMobInterstitial = mAdMobModel.getAdMobInterstitial();
        if (adMobInterstitial.isLoaded()) {
            adMobInterstitial.show();
        } else {
            mMainView.showFeedBackText(ADMOB_INTERSTITIAL_NOT_LOADED);
        }
    }


    //Rewarded Video
    private void setupAdMobRewarded()
    {
        mAdMobModel.setupAdMobRewarded(new RewardedVideoAdListener()
        {
            @Override
            public void onRewardedVideoAdLoaded()
            {
                mMainView.showFeedBackText("AdMob | onRewardedVideoAdLoaded()");
            }

            @Override
            public void onRewardedVideoAdOpened()
            {
                mMainView.showFeedBackText("AdMob | onRewardedVideoAdOpened()");
            }

            @Override
            public void onRewardedVideoStarted()
            {
                mMainView.showFeedBackText("AdMob | onRewardedVideoStarted()");
            }

            @Override
            public void onRewardedVideoAdClosed()
            {
                mMainView.showFeedBackText("AdMob | onRewardedVideoAdClosed()");
            }

            @Override
            public void onRewarded(RewardItem rewardItem)
            {
                mMainView.showFeedBackText("AdMob | onRewarded | currency: " + rewardItem.getType() + "  amount: " + rewardItem.getAmount());
            }

            @Override
            public void onRewardedVideoAdLeftApplication()
            {
                mMainView.showFeedBackText("AdMob | onRewardedVideoAdLeftApplication()");
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i)
            {
                mMainView.showFeedBackText("AdMob | onRewardedVideoAdFailedToLoad(" + i + ").");
            }
        }, mMainView.getActivity());
    }

    @Override
    public void onClick_LoadRewarded()
    {
        mMainView.showFeedBackText("AdMob | trying to load rewarded ad...");
        mAdMobModel.loadRewardedVideo(ADMOB_IS_TESTING);
    }

    @Override
    public void onClick_ShowRewarded()
    {
        RewardedVideoAd adMobRewarded = mAdMobModel.getAdMobRewarded();
        if (adMobRewarded.isLoaded()) {
            adMobRewarded.show();
        } else {
            mMainView.showFeedBackText(ADMOB_REWADED_NOT_LOADED);
        }
    }
}
