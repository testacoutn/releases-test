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

public class KidozAdMobMediationInterstitialAdapter implements CustomEventInterstitial
{
    private static final String TAG = "KidozAdMobMediationAdapter";

    private KidozManager mKidozManager;
    private CustomEventInterstitialListener mAdMobCustomInterstitialListener;

    public KidozAdMobMediationInterstitialAdapter() {
        mKidozManager = KidozManager.getInstance();
    }

    @Override
    public void requestInterstitialAd(Context context, CustomEventInterstitialListener customEventInterstitialListener, String serverParameter, MediationAdRequest mediationAdRequest, Bundle bundle)
    {
        mAdMobCustomInterstitialListener = customEventInterstitialListener;


        //Kidoz requires Activity context to run.
        if (!(context instanceof Activity)){
            mAdMobCustomInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
            Log.d(TAG, "kidozInterstitialAdapter | requestInterstitialAd with non Activity context");
            return;
        }

        //setKidozAd((Activity) context); //and then continue request

        //Kidoz must be initialized before an ad can be requested
        if (!mKidozManager.getIsKidozInitialized()) {

            String appID = mKidozManager.getPublisherIdFromParams(serverParameter);
            String token = mKidozManager.getPublisherTokenFromParams(serverParameter);

            if(appID!=null && token!=null && !appID.equals("") && !token.equals("")) {
                mKidozManager.setKidozPublisherId(appID);
                mKidozManager.setKidozPublisherToken(token);
                initKidoz((Activity) context);
            }


        } else {
            continueRequestInterstitialAd((Activity)context);
        }

    }

    private void initKidoz(final Activity activity)
    {
        mKidozManager.initKidozSDK(activity, new SDKEventListener()
        {
            @Override
            public void onInitSuccess()
            {
                Log.d(TAG, "kidozInterstitialAdapter | onInitSuccess");

                continueRequestInterstitialAd(activity);
            }

            @Override
            public void onInitError(String error)
            {
                mAdMobCustomInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                Log.d(TAG, "kidozInterstitialAdapter | onInitError: " + error);
            }
        });
    }

    private void setKidozAd(Activity activity)
    {
        mKidozManager.setupKidozInterstitial(activity, new BaseInterstitial.IOnInterstitialEventListener()
        {
            @Override
            public void onClosed()
            {
                mAdMobCustomInterstitialListener.onAdClosed();
                Log.d(TAG, "kidozInterstitialAdapter | onAdClosed");
            }

            @Override
            public void onOpened()
            {
                mAdMobCustomInterstitialListener.onAdOpened();
                Log.d(TAG, "kidozInterstitialAdapter | onAdOpened");
            }

            @Override
            public void onReady()
            {
                mAdMobCustomInterstitialListener.onAdLoaded();
                Log.d(TAG, "kidozInterstitialAdapter | onAdLoaded");
            }

            @Override
            public void onLoadFailed()
            {
                mAdMobCustomInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                Log.d(TAG, "kidozInterstitialAdapter | onLoadFailed");
            }

            @Override
            public void onNoOffers()
            {
                mAdMobCustomInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL);
                Log.d(TAG, "kidozInterstitialAdapter | onNoOffers");
            }
        });
    }

    private void setKidozAd()
    {
        mKidozManager.setupKidozInterstitial(mKidozManager.getInterstitial(), new BaseInterstitial.IOnInterstitialEventListener(){
            @Override
            public void onClosed()
            {
                mAdMobCustomInterstitialListener.onAdClosed();
                Log.d(TAG, "kidozInterstitialAdapter | onAdClosed");
            }

            @Override
            public void onOpened()
            {
                mAdMobCustomInterstitialListener.onAdOpened();
                Log.d(TAG, "kidozInterstitialAdapter | onAdOpened");
            }

            @Override
            public void onReady()
            {
                mAdMobCustomInterstitialListener.onAdLoaded();
                Log.d(TAG, "kidozInterstitialAdapter | onAdLoaded");
            }

            @Override
            public void onLoadFailed()
            {
                mAdMobCustomInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                Log.d(TAG, "kidozInterstitialAdapter | onLoadFailed");
            }

            @Override
            public void onNoOffers()
            {
                mAdMobCustomInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL);
                Log.d(TAG, "kidozInterstitialAdapter | onNoOffers");
            }
        });
    }

    private void continueRequestInterstitialAd(Activity activity)
    {
        if(mKidozManager.getInterstitial() == null)
            mKidozManager.createKidozInterstitial(activity);

        setKidozAd();

        Log.d(TAG, "kidozInterstitialAdapter | continueRequestInterstitialAd");
        KidozInterstitial kidozInterstitial = mKidozManager.getInterstitial();

        if(!kidozInterstitial.isLoaded())
            kidozInterstitial.loadAd();
        else
            mAdMobCustomInterstitialListener.onAdLoaded();


    }

    @Override
    public void showInterstitial()
    {
        Log.d(TAG, "kidozInterstitialAdapter | showInterstitial");

        KidozInterstitial kidozInterstitial = mKidozManager.getInterstitial();
        kidozInterstitial.show();
    }

    @Override
    public void onDestroy()
    {
        Log.d(TAG, "kidozInterstitialAdapter | onDestroy");

    }

    @Override
    public void onPause()
    {
        Log.d(TAG, "kidozInterstitialAdapter | onPause");

    }

    @Override
    public void onResume()
    {
        Log.d(TAG, "kidozInterstitialAdapter | onResume");

    }
}
