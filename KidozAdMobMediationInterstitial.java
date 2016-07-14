package com.kidoz.mediation.adaper;

import android.app.Activity;

import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.customevent.CustomEventInterstitial;
import com.google.ads.mediation.customevent.CustomEventInterstitialListener;
import com.kidoz.sdk.api.KidozInterstitial;
import com.kidoz.sdk.api.ui_views.interstitial.BaseInterstitial;
/** Kidoz AdMob mediation Interstitial adapter **/
public class KidozAdMobMediationInterstitial implements CustomEventInterstitial
{
    private KidozInterstitial kidozInterstitial;
    private CustomEventInterstitialListener mEventInterstitialListener;

    @Override
    public void requestInterstitialAd(CustomEventInterstitialListener customEventInterstitialListener, Activity activity, String s, String s1, MediationAdRequest mediationAdRequest, Object o)
    {
        /**
         * In this method, you should:
         * 1. Create your interstitial ad.
         * 2. Set your ad network's listener.
         * 3. Make an ad request.
         */
        mEventInterstitialListener = customEventInterstitialListener;
        kidozInterstitial = new KidozInterstitial(activity);

        kidozInterstitial.setOnInterstitialEventListener(new BaseInterstitial.IOnInterstitialEventListener()
        {
            @Override
            public void onClosed()
            {
                mEventInterstitialListener.onDismissScreen();
            }

            @Override
            public void onOpened()
            {
                mEventInterstitialListener.onPresentScreen();
            }

            @Override
            public void onReady()
            {
                mEventInterstitialListener.onReceivedAd();
            }

            @Override
            public void onLoadFailed()
            {
                mEventInterstitialListener.onFailedToReceiveAd();
            }
        });
        kidozInterstitial.loadAd();
    }

    @Override
    public void showInterstitial()
    {
        // Show your interstitial ad.
        kidozInterstitial.show();
    }

    @Override
    public void destroy()
    {

    }
}
