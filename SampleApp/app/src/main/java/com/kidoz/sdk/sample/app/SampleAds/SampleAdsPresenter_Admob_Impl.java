package com.kidoz.sdk.sample.app.SampleAds;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.kidoz.sdk.sample.app.SampleAds.model.SampleAdsAdMobModel;

import androidx.annotation.NonNull;

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
        setupAdMobBanner();
    }

    private void setupAdMobBanner()
    {
        mAdMobModel.setupAdMobBanner(mMainView.getActivity());
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

    @Override
    public void onClick_LoadRewarded()
    {
        mAdMobModel.setupAdMobRewarded(mMainView.getActivity());
        mMainView.showFeedBackText("AdMob | trying to load rewarded ad...");
        mAdMobModel.loadRewardedVideo(new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdLoaded() {
                mMainView.showFeedBackText("AdMob | onRewardedAdLoaded()");
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                mMainView.showFeedBackText("AdMob | onRewardedAdFailedToLoad(" + errorCode + ").");
            }

        });
    }

    @Override
    public void onClick_ShowRewarded()
    {
        RewardedAd adMobRewarded = mAdMobModel.getAdMobRewarded();
        if (adMobRewarded.isLoaded()) {
            adMobRewarded.show(mMainView.getActivity(),new RewardedAdCallback(){
                @Override
                public void onRewardedAdOpened() {
                    mMainView.showFeedBackText("AdMob | onRewardedAdOpened()");
                }

                @Override
                public void onRewardedAdClosed() {
                    mMainView.showFeedBackText("AdMob | onRewardedAdClosed()");
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    mMainView.showFeedBackText("AdMob | onRewarded | currency: " + reward.getType() + "  amount: " + reward.getAmount());
                }

                @Override
                public void onRewardedAdFailedToShow(int errorCode) {
                    mMainView.showFeedBackText("AdMob | onRewardedAdFailedToShow()");
                }
            });
        } else {
            mMainView.showFeedBackText(ADMOB_REWADED_NOT_LOADED);
        }
    }

    @Override
    public void onClick_LoadBanner()
    {
        mAdMobModel.loadBanner();
    }

    @Override
    public void onClick_ShowBanner()
    {
        mAdMobModel.showBanner(); ////admob loads and shows on loadAd() call (this call does nothing atm).
    }

    @Override
    public void onClick_CloseBanner()
    {
        mAdMobModel.closeBanner();
    }
}
