package com.kidoz.sdk.sample.app.SampleAds;

import com.kidoz.sdk.api.KidozInterstitial;
import com.kidoz.sdk.api.interfaces.SDKEventListener;
import com.kidoz.sdk.api.ui_views.interstitial.BaseInterstitial;
import com.kidoz.sdk.sample.app.SampleAds.model.SampleAdsKidozModel;

/**
 * Created by orikam on 07/06/2017.
 */

public class SampleAdsPresenter_Kidoz_Impl implements SampleAdsPresenter
{
    private static final String INTERSTITIAL_ALREADY_LOADED = "Interstitial already loaded.";
    private static final String INTERSTITIAL_NOT_LOADED = "Interstitial not loaded, can't show.";
    private static final String REWARDED_ALREADY_LOADED = "Rewarded already loaded.";
    private static final String REWARDED_NOT_LOADED = "Rewarded not loaded, can't show.";
    private static final String KIDOZ_SDK_INIT_SUCCESS = "KidzoSDK init success.";
    private static final String KIDOZ_SDK_INIT_ERROR_PREFIX = "KidzoSDK init failed. Error: ";

    private SampleAdsView mMainView;
    private SampleAdsKidozModel mKidozModel;

    public SampleAdsPresenter_Kidoz_Impl(SampleAdsView mainView){
        mMainView = mainView;
        mKidozModel = new SampleAdsKidozModel();

        setupKidozAds();
    }

    private void setupKidozAds()
    {
        mKidozModel.setupKidozInterstitial(mMainView.getActivity(), new BaseInterstitial.IOnInterstitialEventListener()
        {
            @Override
            public void onClosed()
            {
                mMainView.showFeedBackText("Interstitial | onClosed");
            }

            @Override
            public void onOpened()
            {
                mMainView.showFeedBackText("Interstitial | onOpened");
            }

            @Override
            public void onReady()
            {
                mMainView.showFeedBackText("Interstitial | onReady");
            }

            @Override
            public void onLoadFailed()
            {
                mMainView.showFeedBackText("Interstitial | onFailed");
            }

            @Override
            public void onNoOffers()
            {
                mMainView.showFeedBackText("Interstitial | onNoOffers");
            }
        });


        mKidozModel.setupKidozRewadrded(mMainView.getActivity(), new BaseInterstitial.IOnInterstitialEventListener()
        {
            @Override
            public void onClosed()
            {
                mMainView.showFeedBackText("Rewarded | onClosed");
            }

            @Override
            public void onOpened()
            {
                mMainView.showFeedBackText("Rewarded | onOpened");
            }

            @Override
            public void onReady()
            {
                mMainView.showFeedBackText("Rewarded | onReady");
            }

            @Override
            public void onLoadFailed()
            {
                mMainView.showFeedBackText("Rewarded | onFailed");
            }

            @Override
            public void onNoOffers()
            {
                mMainView.showFeedBackText("Rewarded | onNoOffers");
            }
        },
        new BaseInterstitial.IOnInterstitialRewardedEventListener()
        {
            @Override
            public void onRewardReceived()
            {
                mMainView.showFeedBackText("Rewarded | onRewardReceived");
            }

            @Override
            public void onRewardedStarted()
            {
                mMainView.showFeedBackText("Rewarded | onRewardedStarted");
            }
        });
    }

    @Override
    public void onCreate()
    {
        mKidozModel.initKidozSDK(mMainView.getActivity(), new SDKEventListener()
        {
            @Override
            public void onInitSuccess()
            {
                //SDK Init | Success().
                mMainView.showFeedBackText(KIDOZ_SDK_INIT_SUCCESS);
            }

            @Override
            public void onInitError(String error)
            {
                //SDK Init | Error
                mMainView.showFeedBackText(KIDOZ_SDK_INIT_ERROR_PREFIX + error);
            }
        });
    }

    @Override
    public void onClick_LoadInterstitial()
    {
        KidozInterstitial kidozInterstitial = mKidozModel.getInterstitial();
        if (kidozInterstitial.isLoaded()){
            mMainView.showFeedBackText(INTERSTITIAL_ALREADY_LOADED);
            return;
        }

        kidozInterstitial.loadAd();
    }

    @Override
    public void onClick_ShowInterstitial()
    {
        KidozInterstitial kidozInterstitial = mKidozModel.getInterstitial();
        if (!kidozInterstitial.isLoaded()){
            mMainView.showFeedBackText(INTERSTITIAL_NOT_LOADED);
            return;
        }

        kidozInterstitial.show();
    }

    @Override
    public void onClick_LoadRewarded()
    {
        KidozInterstitial kidozRewarded = mKidozModel.getRewarded();
        if (kidozRewarded.isLoaded()){
            mMainView.showFeedBackText(REWARDED_ALREADY_LOADED);
            return;
        }

        kidozRewarded.loadAd();
    }

    @Override
    public void onClick_ShowRewarded()
    {
        KidozInterstitial kidozRewarded = mKidozModel.getRewarded();
        if (!kidozRewarded.isLoaded()){
            mMainView.showFeedBackText(REWARDED_NOT_LOADED);
            return;
        }

        kidozRewarded.show();
    }

}
