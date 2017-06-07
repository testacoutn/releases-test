package com.kidoz.sdk.sample.app.SampleAds;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.kidoz.sdk.sample.app.SampleAds.model.SampleAdsAdMobModel;

/**
 * Created by orikam on 07/06/2017.
 */

public class SampleAdsPresenter_Admob_Impl implements SampleAdsPresenter
{
    private static final boolean ADMOB_IS_TESTING = true;

    private static final String ADMOB_INTERSTITIAL_NOT_LOADED = "The interstitial wasn't loaded yet.";

    private SampleAdsView mMainView;
    private SampleAdsAdMobModel mAdMobModel;

    public SampleAdsPresenter_Admob_Impl(SampleAdsView mainView)
    {
        mMainView = mainView;
        mAdMobModel = new SampleAdsAdMobModel();

        setupAdMobAds();
    }

    private void setupAdMobAds()
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
        mAdMobModel.loadInterstitial(ADMOB_IS_TESTING);
    }

    @Override
    public void onClick_ShowInterstitial()
    {
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
        //TODO: implement!
    }

    @Override
    public void onClick_ShowRewarded()
    {
        //TODO: implement!
    }
}
