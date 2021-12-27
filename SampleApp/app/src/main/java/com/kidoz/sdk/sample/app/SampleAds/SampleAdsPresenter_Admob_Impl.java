package com.kidoz.sdk.sample.app.SampleAds;

import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
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

        setupAdMobBanner();
    }


    @Override
    public void onCreate(){
    }

    private void setupAdMobBanner()
    {
        mAdMobModel.setupAdMobBanner(mMainView.getActivity());
    }

    @Override
    public void onClick_LoadInterstitial()
    {
        mMainView.showFeedBackText("AdMob | trying to load interstitial ad...");
        mAdMobModel.loadInterstitial(mMainView);
    }

    @Override
    public void onClick_ShowInterstitial()
    {
        mMainView.showFeedBackText("AdMob | trying to show ad...");
        InterstitialAd adMobInterstitial = mAdMobModel.getAdMobInterstitial();
        if (adMobInterstitial != null) {
            adMobInterstitial.show(mMainView.getActivity());
        } else {
            mMainView.showFeedBackText(ADMOB_INTERSTITIAL_NOT_LOADED);
        }
    }

    @Override
    public void onClick_LoadRewarded()
    {
        mMainView.showFeedBackText("AdMob | trying to load rewarded ad...");
        mAdMobModel.loadRewardedVideo(mMainView);
    }

    @Override
    public void onClick_ShowRewarded()
    {
        RewardedAd adMobRewarded = mAdMobModel.getAdMobRewarded();
        if (adMobRewarded != null) {
            adMobRewarded.show(mMainView.getActivity(), new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    mMainView.showFeedBackText("AdMob | Reward received");
                    Toast.makeText(mMainView.getActivity(),"Rewarded received",Toast.LENGTH_LONG).show();
                }
            });
        } else {
            mMainView.showFeedBackText(ADMOB_REWADED_NOT_LOADED);
        }
    }

    @Override
    public void onClick_LoadBanner()
    {
        mAdMobModel.loadBanner(mMainView);
    }

    @Override
    public void onClick_CloseBanner()
    {
        mAdMobModel.closeBanner();
    }
}
