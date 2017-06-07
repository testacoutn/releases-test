package com.kidoz.mediation.admob.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitial;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener;
import com.kidoz.sdk.api.KidozInterstitial;
import com.kidoz.sdk.api.interfaces.SDKEventListener;
import com.kidoz.sdk.api.ui_views.interstitial.BaseInterstitial;

/**
 * Created by orikam on 07/06/2017.
 */

public class OlderRewardedAdapter implements CustomEventInterstitial
{
    private static final String TAG = "KidozAdMobMediationAdapter";

    private KidozManager mKidozManager;
    private CustomEventInterstitialListener mAdMobCustomInterstitialListener;

    public OlderRewardedAdapter() {
        mKidozManager = new KidozManager();
    }

    @Override
    public void requestInterstitialAd(Context context, CustomEventInterstitialListener customEventInterstitialListener, String s, MediationAdRequest mediationAdRequest, Bundle bundle)
    {
        mAdMobCustomInterstitialListener = customEventInterstitialListener;

        //Kidoz requires Activity context to run.
        if (!(context instanceof Activity)){
            mAdMobCustomInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
            Log.d(TAG, "Kidoz | requestInterstitialAd with non Activity context");
            return;
        }

        //Kidoz must be initialized before an ad can be requested
        if (!mKidozManager.getIsKidozInitialized())
        {
            initKidoz((Activity) context);

            setKidozAd((Activity) context); //and then continue request

        } else {
            continueRequestInterstitialAd();
        }

    }

    private void initKidoz(Activity activity)
    {
        mKidozManager.initKidozSDK(activity, new SDKEventListener()
        {
            @Override
            public void onInitSuccess()
            {
                continueRequestInterstitialAd();
            }

            @Override
            public void onInitError(String error)
            {
                mAdMobCustomInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                Log.d(TAG, "Kidoz | onInitError: " + error);
            }
        });
    }

    private void setKidozAd(Activity activity)
    {
        mKidozManager.setupKidozRewadrded(activity, new BaseInterstitial.IOnInterstitialEventListener()
                {
                    @Override
                    public void onClosed()
                    {
                        mAdMobCustomInterstitialListener.onAdClosed();
                        Log.d(TAG, "Kidoz | onAdClosed");
                    }

                    @Override
                    public void onOpened()
                    {
                        mAdMobCustomInterstitialListener.onAdOpened();
                        Log.d(TAG, "Kidoz | onAdOpened");
                    }

                    @Override
                    public void onReady()
                    {
                        mAdMobCustomInterstitialListener.onAdLoaded();
                        Log.d(TAG, "Kidoz | onAdReady");
                    }

                    @Override
                    public void onLoadFailed()
                    {
                        mAdMobCustomInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                        Log.d(TAG, "Kidoz | onLoadFailed");
                    }

                    @Override
                    public void onNoOffers()
                    {
                        mAdMobCustomInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL);
                        Log.d(TAG, "Kidoz | onNoOffers");
                    }
                },
                new BaseInterstitial.IOnInterstitialRewardedEventListener()
                {
                    @Override
                    public void onRewardReceived()
                    {
                        BaseInterstitial.IOnInterstitialRewardedEventListener devListener = mKidozManager.getDeveloperRewardedListener();
                        if (devListener != null){
                            devListener.onRewardReceived();
                        }

                        Log.d(TAG, "Kidoz | onRewardReceived");
                    }

                    @Override
                    public void onRewardedStarted()
                    {
                        BaseInterstitial.IOnInterstitialRewardedEventListener devListener = mKidozManager.getDeveloperRewardedListener();
                        if (devListener != null){
                            devListener.onRewardedStarted();
                        }

                        Log.d(TAG, "Kidoz | onRewardedStarted");
                    }
                });
    }

    private void continueRequestInterstitialAd()
    {
        KidozInterstitial kidozInterstitial = mKidozManager.getRewarded();
        kidozInterstitial.loadAd();
    }

    @Override
    public void showInterstitial()
    {
        KidozInterstitial kidozInterstitial = mKidozManager.getRewarded();
        kidozInterstitial.show();
    }

    @Override
    public void onDestroy()
    {
    }

    @Override
    public void onPause()
    {
    }

    @Override
    public void onResume()
    {
    }
}
